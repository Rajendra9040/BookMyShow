package com.scaler.bookmyshow.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "screens")
public class Screen extends BaseModel{
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "theater_id")
    private Theater theater;

    @ManyToMany
    @JoinTable(
            name = "screen_features",
            joinColumns = @JoinColumn(name = "screen_id"),
            inverseJoinColumns = @JoinColumn(name = "feature_id")
    )
    private List<Feature> features;

    @OneToMany(mappedBy = "screen")
    private List<Seat> seats;
}
