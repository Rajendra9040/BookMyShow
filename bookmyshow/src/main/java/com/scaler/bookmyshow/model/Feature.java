package com.scaler.bookmyshow.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "features")
public class Feature extends BaseModel {
    @Column(name = "name")
    private String name;
//    TWO_D,
//    THREE_D,
//    DOLBY_SOUND,
//    IMAX,
//    ULTRA_HD

}
