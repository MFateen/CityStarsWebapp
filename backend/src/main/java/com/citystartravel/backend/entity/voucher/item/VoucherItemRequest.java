package com.citystartravel.backend.entity.voucher.item;

import com.citystartravel.backend.entity.sparetype.SpareTypeDto;

import javax.validation.constraints.Min;

public class VoucherItemRequest {

    private long id;
    private SpareTypeDto spareType;
    private String name;
    private String description;
    private String unit;
    @Min(value = 1)
    private int quantity;
    private String notes;

    public VoucherItemRequest() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SpareTypeDto getSpareType() {
        return spareType;
    }

    public void setSpareType(SpareTypeDto spareTypeDto) {
        this.spareType = spareTypeDto;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
