package com.citystartravel.backend.entity.voucher;

import com.citystartravel.backend.entity.sparetype.SpareTypeDto;
import com.citystartravel.backend.entity.voucher.item.VoucherItem;
import com.citystartravel.backend.entity.voucher.item.VoucherItemRequest;
import com.citystartravel.backend.payload.response.PagedResponse;
import com.citystartravel.backend.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MapperVoucherDto {

    @Autowired
    private Mapper<VoucherDto, Voucher> mapperDtoToVoucher;
    @Autowired
    private Mapper<Voucher, VoucherDto> mapperVoucherToDto;
    @Autowired
    private Mapper<VoucherItem, VoucherItemRequest> mapperVoucherItem;

    public MapperVoucherDto() {}

    public void mapDtoToVoucher(VoucherDto dto) {}

    public VoucherDto mapVoucherToDto(Voucher voucher) {
        VoucherDto dto = mapperVoucherToDto.mapEntityToDto(voucher, VoucherDto.class);

        // handle voucher items
        List<VoucherItem> voucherItems = voucher.getVoucherItems();
        List<VoucherItemRequest> voucherItemRequests = new ArrayList<>(voucherItems.size());
        for(VoucherItem item : voucherItems) {
            VoucherItemRequest voucherItemRequest = mapperVoucherItem.mapEntityToDto(
              item, VoucherItemRequest.class
            );
            voucherItemRequest.setSpareType(new SpareTypeDto(
                    item.getSpareType().getId(), item.getSpareType().getName()
            ));
            voucherItemRequests.add(voucherItemRequest);
        }
        dto.setVoucherItemRequests(voucherItemRequests);
        return dto;
    }

    public PagedResponse<VoucherDto> mapEntityPagesToDtoPages(PagedResponse<Voucher> entityPagedResponse, Class<VoucherDto> dClass) {
        return mapperVoucherToDto.mapEntityPagesToDtoPages(entityPagedResponse, VoucherDto.class);
    }
}
