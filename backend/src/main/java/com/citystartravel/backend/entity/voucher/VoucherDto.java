package com.citystartravel.backend.entity.voucher;

import com.citystartravel.backend.entity.voucher.item.VoucherItemRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class VoucherDto {


    private Long id;
    private Date date;
    private String supplierCode;
    private String supplierName;
    private String supplierInvoice;
    private String deliveryNote;
    private String inspectionVoucher;
    private String needsRequest;
    private String address;

    private List<VoucherItemRequest> voucherItemRequests = new ArrayList<>();


    public VoucherDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierInvoice() {
        return supplierInvoice;
    }

    public void setSupplierInvoice(String supplierInvoice) {
        this.supplierInvoice = supplierInvoice;
    }

    public String getDeliveryNote() {
        return deliveryNote;
    }

    public void setDeliveryNote(String deliveryNote) {
        this.deliveryNote = deliveryNote;
    }

    public String getInspectionVoucher() {
        return inspectionVoucher;
    }

    public void setInspectionVoucher(String inspectionVoucher) {
        this.inspectionVoucher = inspectionVoucher;
    }

    public List<VoucherItemRequest> getVoucherItemRequests() {
        return voucherItemRequests;
    }

    public void setVoucherItemRequests(List<VoucherItemRequest> voucherItemRequests) {
        this.voucherItemRequests.retainAll(voucherItemRequests);
        this.voucherItemRequests.addAll(voucherItemRequests);
    }

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
