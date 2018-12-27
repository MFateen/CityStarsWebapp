package com.citystartravel.backend.entity.spare;

import com.citystartravel.backend.entity.bus.Bus;
import com.citystartravel.backend.entity.bus.BusService;
import com.citystartravel.backend.entity.bus.event.BusEvent;
import com.citystartravel.backend.entity.bus.event.BusEventType;
import com.citystartravel.backend.entity.sparetype.SpareTypeService;
import com.citystartravel.backend.entity.voucher.item.VoucherItem;
import com.citystartravel.backend.entity.voucher.stockreceived.StockReceived;
import com.citystartravel.backend.exception.AppException;
import com.citystartravel.backend.payload.response.PagedResponse;
import com.citystartravel.backend.security.CurrentUser;
import com.citystartravel.backend.security.UserPrincipal;
import com.citystartravel.backend.util.UtilityMethods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

@Service
public class SpareService {
    @Autowired
    private SpareRepository spareRepository;

    @Autowired
    private SpareTypeService spareTypeService;

    private static final Logger logger = LoggerFactory.getLogger(SpareService.class);

    private UtilityMethods<Spare> utilityMethods = new UtilityMethods<>();

    @Autowired
    private BusService busService;

    public PagedResponse<Spare> getAllSpares(UserPrincipal currentUser, int page, int size) {
        return utilityMethods.getAll(spareRepository,currentUser,page,size);
    }

    public Spare getSpareById(Long spareId, @CurrentUser UserPrincipal currentUser) {
        return utilityMethods.getById(spareRepository, currentUser, spareId,"Spare");
    }

    public List<Spare> getSparesForBus(Long busId, @CurrentUser UserPrincipal currentUser) {
        return spareRepository.findAllByBusId(busId, Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    // --------------------------- Stock Received ---------------------------
    public List<Spare> createSparesFromVoucherItems(List<VoucherItem> voucherItems, @CurrentUser UserPrincipal currentUser) {
        List<Spare> spares = new ArrayList<>();
        for(VoucherItem voucherItem : voucherItems) {
            for(int i=0 ; i<voucherItem.getQuantity() ; i++) {
                if(voucherItem.getName() != null) {
                    Spare spare = new Spare(voucherItem.getName(), voucherItem.getSpareType(), (StockReceived) voucherItem.getVoucher());
                    spareRepository.save(spare);
                    spares.add(spare);
                }
                else {
                    StockReceived sr = (StockReceived) voucherItem.getVoucher();
                    String spareDefaultName = voucherItem.getSpareType().getName()+sr.getDate();
                    Spare spare = new Spare(spareDefaultName, voucherItem.getSpareType(), sr);
                    spareRepository.save(spare);
                    spares.add(spare);
                }
            }
        }

        return spares;
    }


    // --------------------------- Stock Issue ---------------------------
    private List<Spare> getSparesAvailableBySpareType(Long spareTypeId, @CurrentUser UserPrincipal currentUser) {
        return spareRepository.findBySpareTypeIdAndAvailable(spareTypeId, true);
    }

    public boolean assignSparesToBus(List<VoucherItem> voucherItems, Bus bus, @CurrentUser UserPrincipal currentUser) {
        for(VoucherItem voucherItem : voucherItems) {
            List<Spare> availableSpares = getSparesAvailableBySpareType(voucherItem.getSpareType().getId(), currentUser);
            int quantityAvailableOfSpareType = availableSpares.size();
            if(voucherItem.getQuantity() <= quantityAvailableOfSpareType) {
                for(Spare spare : availableSpares) {
                    spare.setAvailable(false);
                    spare.setBus(bus);
                    spareRepository.save(spare);
                    String eventString = generateBusEventString(spare.getId(), spare.getSpareType().getName());
                    busService.addBusEvents(bus, new BusEvent(BusEventType.SPARE_PART_ADDED, eventString), currentUser);
                }
            }
            else
                throw new AppException("Not enough quantity of ["+voucherItem.getSpareType().getName()+"] to assign to bus ["
                        +bus.getName()+"]. Quantity available is: "+quantityAvailableOfSpareType);
        }
        return true;
    }

    private String generateBusEventString(long spareId, String spareName) {
        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder);
        formatter.format("تم صرف القطعة رقم %d%n من نوع %s.", spareId, spareName);
        return stringBuilder.toString();
    }
}
