package com.scaler.bookmyshow.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Screen extends BaseModel{
    private String name;
    @ManyToOne
    private Theater theater;
    @Enumerated(EnumType.STRING)
    private List<Feature> features;
    @OneToMany(mappedBy = "screen")
    private List<Seat> seats;
}
