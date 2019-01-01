package com.citystartravel.backend.entity.voucher.stockreceived;

import com.citystartravel.backend.entity.spare.SpareService;
import com.citystartravel.backend.entity.voucher.item.VoucherItem;
import com.citystartravel.backend.entity.voucher.item.VoucherUtility;
import com.citystartravel.backend.entity.voucher.purchaserequest.PurchaseRequestService;
import com.citystartravel.backend.payload.response.PagedResponse;
import com.citystartravel.backend.security.CurrentUser;
import com.citystartravel.backend.security.UserPrincipal;
import com.citystartravel.backend.util.Mapper;
import com.citystartravel.backend.util.UtilityMethods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockReceivedService {
    
    @Autowired
    private StockReceivedRepository stockReceivedRepository;
    @Autowired
    private Mapper<StockReceivedDtoRequest, StockReceived> mapper;
    @Autowired
    private Mapper<StockReceived, StockReceivedDtoRequest> mapperEntityToDto;
    @Autowired
    private VoucherUtility voucherUtility;
    @Autowired
    private SpareService spareService;
    @Autowired
    private PurchaseRequestService purchaseRequestService;
    private static final Logger logger = LoggerFactory.getLogger(StockReceivedService.class);
    private UtilityMethods<StockReceived> utilityMethods = new UtilityMethods<>();



    public PagedResponse<StockReceived> getAllStockReceivedVouchers(UserPrincipal currentUser, int page, int size) {
        return utilityMethods.getAll(stockReceivedRepository,currentUser,page,size);
    }

    public PagedResponse<StockReceivedDtoRequest> getAllStockReceivedDtos(UserPrincipal currentUser, int page, int size) {
        return mapEntityPagesToDtoPages(utilityMethods.getAll(stockReceivedRepository,currentUser,page,size));
    }

    public StockReceived getStockReceivedVoucherById(Long purchaseRequestVoucherId, @CurrentUser UserPrincipal currentUser) {
        return utilityMethods.getById(stockReceivedRepository, currentUser, purchaseRequestVoucherId,"StockReceived");
    }

    public StockReceived createStockReceivedVoucher(StockReceivedDtoRequest stockReceivedDtoRequest, @CurrentUser UserPrincipal currentUser) {
        StockReceived stockReceived = new StockReceived();
        stockReceived.setPurchaseRequest(purchaseRequestService.getPurchaseRequestVoucherById(stockReceivedDtoRequest.getPurchaseOrder(), currentUser));
        stockReceived = stockReceivedRepository.save(stockReceived); // creates a new StockReceived Voucher in the database to attach it to Voucher Item
        stockReceived = mapDtoToEntity(stockReceivedDtoRequest, stockReceived, currentUser);

        String eventLog = utilityMethods.generateEntityCreationMessage("StockReceived",String.valueOf(stockReceived.getId()),currentUser);
        logger.info(eventLog);

        spareService.createSparesFromVoucherItems(stockReceived.getVoucherItems(), currentUser);

        return stockReceivedRepository.save(stockReceived);
    }

    public StockReceived updateStockReceivedVoucher(StockReceived purchaseRequest) {
        return utilityMethods.update(stockReceivedRepository, purchaseRequest);
    }

    public void deleteStockReceivedVoucher(StockReceived purchaseRequest) {
        utilityMethods.delete(stockReceivedRepository, purchaseRequest);
    }

    // ---------------------------------- util ----------------------------------

    private StockReceived mapDtoToEntity(StockReceivedDtoRequest dto, StockReceived stockReceived, @CurrentUser UserPrincipal currentUser) {
        // creates new PRV from PRVRequest
        mapper.mapEntityToDto(dto, stockReceived);
        // handle voucher items
        List<VoucherItem> voucherItems = voucherUtility.getVoucherItemsFromRequest(dto, currentUser, stockReceived);
        stockReceived.setVoucherItems(voucherItems);
        return stockReceived;
    }

    private StockReceivedDtoRequest mapEntityToDto(StockReceived stockReceived, @CurrentUser UserPrincipal currentUser) {
        StockReceivedDtoRequest dto = mapperEntityToDto.mapEntityToDto(stockReceived, StockReceivedDtoRequest.class);
        dto.setPurchaseOrder(stockReceived.getPurchaseRequest().getId());
        dto.setVoucherItemRequests(voucherUtility.getVoucherItemsDtos(dto, currentUser, stockReceived));
        return dto;
    }

    private PagedResponse<StockReceivedDtoRequest> mapEntityPagesToDtoPages(PagedResponse<StockReceived> srPagedResponse) {
        return mapperEntityToDto.mapEntityPagesToDtoPages(srPagedResponse, StockReceivedDtoRequest.class);
    }


}
