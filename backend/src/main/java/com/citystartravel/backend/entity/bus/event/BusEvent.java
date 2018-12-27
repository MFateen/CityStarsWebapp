package com.citystartravel.backend.entity.bus.event;

import com.citystartravel.backend.config.audit.UserDateAudit;
import com.citystartravel.backend.entity.bus.Bus;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "busevents")
public class BusEvent extends UserDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="bus_id", nullable = false)
    @JsonBackReference
    private Bus bus;

    @NotNull
    private String text;

    private String busCondition;

    private boolean resolved;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BusEventType type;

    public BusEvent() {}

    public BusEvent(@NotNull BusEventType type, @NotNull String text) {
        this.type = type;
        this.text = text;
    }

    public BusEvent(Bus bus, @NotNull String text, String busCondition, @NotNull BusEventType type) {
        this.bus = bus;
        this.text = text;
        this.busCondition = busCondition;
        this.type = type;
    }

    public BusEvent(Bus bus, @NotBlank String text) {
        this.bus = bus;
        this.text = text;
        this.resolved = false;
    }

    public BusEvent(Bus bus, @NotBlank String text, String busCondition) {
        this.bus = bus;
        this.text = text;
        this.busCondition = busCondition;
        this.resolved = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
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

    public BusEventType getType() {
        return type;
    }

    public void setType(BusEventType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusEvent event = (BusEvent) o;
        return Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
