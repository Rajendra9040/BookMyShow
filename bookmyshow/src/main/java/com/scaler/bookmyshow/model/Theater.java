package com.scaler.bookmyshow.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Theater extends BaseModel {
    private String name;
    @ManyToOne
    private Region region;
    @OneToMany(mappedBy = "theater")
    private List<Screen> screens;
}
