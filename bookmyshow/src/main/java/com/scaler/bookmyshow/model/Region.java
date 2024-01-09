package com.scaler.bookmyshow.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Region extends BaseModel {
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "region")
    private List<Theater> theaters;
}
