package com.citystartravel.backend.entity.sparetype;

public class SpareTypeDto {

    private long id;

    private String name;

    public SpareTypeDto() {}

    public SpareTypeDto(long id, String name) {
        this.id = id;
        this.name = name;
    }

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
}
