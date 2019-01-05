package com.citystartravel.backend.entity.voucher.stockissue;

import com.citystartravel.backend.entity.bus.BusService;
import com.citystartravel.backend.entity.spare.SpareService;
import com.citystartravel.backend.entity.voucher.item.VoucherItem;
import com.citystartravel.backend.entity.voucher.item.VoucherUtility;
import com.citystartravel.backend.payload.response.PagedResponse;
import com.citystartravel.backend.security.CurrentUser;
import com.citystartravel.backend.security.UserPrincipal;
import com.citystartravel.backend.util.Mapper;
import com.citystartravel.backend.util.UtilityMethods;
import org.modelmapper.PropertyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class StockIssueService {
    
    @Autowired
    private StockIssueRepository stockIssueRepository;
    @Autowired
    private Mapper<StockIssueDtoRequest, StockIssue> mapper;
    @Autowired
    private VoucherUtility voucherUtility;
    @Autowired
    private SpareService spareService;
    @Autowired
    private BusService busService;
    private static final Logger logger = LoggerFactory.getLogger(StockIssueService.class);
    private UtilityMethods<StockIssue> utilityMethods = new UtilityMethods<>();

    @PostConstruct
    private void addSkippedMappings() {
        PropertyMap<StockIssueDtoRequest, StockIssue> skippedFieldsMap = new PropertyMap<StockIssueDtoRequest, StockIssue>() {
            protected void configure() {
                skip().setId(null);
            }
        };
        mapper.addMappings(skippedFieldsMap);
    }

    public PagedResponse<StockIssue> getAllStockIssueVouchers(UserPrincipal currentUser, int page, int size) {
        return utilityMethods.getAll(stockIssueRepository,currentUser,page,size);
    }

    public StockIssue getStockIssueVoucherById(Long purchaseRequestVoucherId, @CurrentUser UserPrincipal currentUser) {
        return utilityMethods.getById(stockIssueRepository, currentUser, purchaseRequestVoucherId,"StockIssue");
    }

    public StockIssue createStockIssueVoucher(StockIssueDtoRequest stockIssueDtoRequest, @CurrentUser UserPrincipal currentUser) {
        StockIssue stockIssue = new StockIssue();
        stockIssue.setBus(busService.getBusByIdEntity(stockIssueDtoRequest.getBusID(), currentUser));
        stockIssue = stockIssueRepository.save(stockIssue); // creates a new StockIssue Voucher in the database to attach it to Voucher Item
        stockIssue = createStockIssueFromDto(stockIssueDtoRequest, stockIssue, currentUser);

        String eventLog = utilityMethods.generateEntityCreationMessage("StockIssue",String.valueOf(stockIssue.getId()),currentUser);
        logger.info(eventLog);

        spareService.assignSparesToBus(stockIssue.getVoucherItems(), stockIssue.getBus(), currentUser);

        return stockIssueRepository.save(stockIssue);
    }

    public StockIssue updateStockIssueVoucher(StockIssue purchaseRequest) {
        return utilityMethods.update(stockIssueRepository, purchaseRequest);
    }

    public void deleteStockIssueVoucher(StockIssue purchaseRequest) {
        utilityMethods.delete(stockIssueRepository, purchaseRequest);
    }

    // ---------------------------------- util ----------------------------------

    private StockIssue createStockIssueFromDto(StockIssueDtoRequest dto, StockIssue stockIssue, @CurrentUser UserPrincipal currentUser) {
        dto.setId(stockIssue.getId());
        mapper.mapEntityToDto(dto, stockIssue);
        // handle voucher items
        List<VoucherItem> voucherItems = voucherUtility.getVoucherItemsFromRequest(dto, currentUser, stockIssue);
        stockIssue.setVoucherItems(voucherItems);
        return stockIssue;
    }

}
