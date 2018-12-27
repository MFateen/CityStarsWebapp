package com.citystartravel.backend.entity.bus.event;

public class BusEventRequest {

    private long busId;

    private String text;

    private String busCondition;

    private boolean resolved;

    private BusEventType busEventType;

    public BusEventRequest() {
    }

    public long getBusId() {
        return busId;
    }

    public void setBusId(long busId) {
        this.busId = busId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getBusCondition() {
        return busCondition;
    }

    public void setBusCondition(String busCondition) {
        this.busCondition = busCondition;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public BusEventType getBusEventType() {
        return busEventType;
    }

    public void setBusEventType(BusEventType busEventType) {
        this.busEventType = busEventType;
    }
}
