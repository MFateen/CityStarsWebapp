package com.citystartravel.backend.entity.spare;

public class SpareResponse {

    private long id;
    private long serialNo;
    private String name;
    private boolean available = true;
    private long spareTypeID;
    private long stockReceivedID;
    private long busID;


    public SpareResponse() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(long serialNo) {
        this.serialNo = serialNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public long getSpareTypeID() {
        return spareTypeID;
    }

    public void setSpareTypeID(long spareTypeID) {
        this.spareTypeID = spareTypeID;
    }

    public long getStockReceivedID() {
        return stockReceivedID;
    }

    public void setStockReceivedID(long stockReceivedID) {
        this.stockReceivedID = stockReceivedID;
    }

    public long getBusID() {
        return busID;
    }

    public void setBusID(long busID) {
        this.busID = busID;
    }
}
