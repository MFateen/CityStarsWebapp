package com.citystartravel.backend.entity.voucher;

import com.citystartravel.backend.config.audit.UserDateAudit;
import com.citystartravel.backend.entity.voucher.item.VoucherItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "voucher")
@Inheritance(strategy = InheritanceType.JOINED)
public class Voucher extends UserDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    protected Long serialNo;

    private Date date;

    private String supplierCode;

    private String supplierName;

    private String address;

    @OneToMany(
            mappedBy = "voucher",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Fetch(FetchMode.SELECT)
    @JsonManagedReference
    private List<VoucherItem> voucherItems = new ArrayList<>();

    public Voucher() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Long getSerialNo() {
//        return serialNo;
//    }
//
//    public void setSerialNo(Long serialNo) {
//        this.serialNo = serialNo;
//    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<VoucherItem> getVoucherItems() {
        return voucherItems;
    }

    public void setVoucherItems(List<VoucherItem> voucherItems) {
        this.voucherItems.retainAll(voucherItems);
        this.voucherItems.addAll(voucherItems);
    }
}
