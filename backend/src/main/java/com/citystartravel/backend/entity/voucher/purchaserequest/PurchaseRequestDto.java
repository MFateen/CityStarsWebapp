package com.citystartravel.backend.entity.voucher.purchaserequest;

import com.citystartravel.backend.entity.voucher.VoucherDto;
import com.citystartravel.backend.entity.voucher.item.VoucherItemRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PurchaseRequestDto extends VoucherDto {

    private String needsRequest;

    private String address;

    public PurchaseRequestDto() {}

    public String getNeedsRequest() {
        return needsRequest;
    }

    public void setNeedsRequest(String needsRequest) {
        this.needsRequest = needsRequest;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
