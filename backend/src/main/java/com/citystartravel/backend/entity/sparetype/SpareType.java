package com.citystartravel.backend.entity.sparetype;

import com.citystartravel.backend.config.audit.UserDateAudit;
import com.citystartravel.backend.entity.spare.Spare;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "spare_types", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "name"
        })
})
public class SpareType extends UserDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long serialNo;

    @NaturalId
    @Column(length = 100)
    private String name;

    private String unit;

    @OneToMany(
            mappedBy = "spareType",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @Fetch(FetchMode.SELECT)
    @JsonManagedReference
    @JsonIgnore
    private List<Spare> spares = new ArrayList<>();

    public SpareType() {}

    public SpareType(String name) {
        this.name = name;
    }

    public SpareType(String name, String unit) {
        this.name = name;
        this.unit = unit;
    }

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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<Spare> getSpares() {
        return spares;
    }

    public void setSpares(List<Spare> spares) {
        this.spares.retainAll(spares);
        this.spares.addAll(spares);
    }
}
