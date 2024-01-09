package com.scaler.bookmyshow.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Scanner;

@Getter
@Setter
@Entity
@Table(name = "shows")
public class Show extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @ManyToOne
    @JoinColumn(name = "screen_id")
    private Screen screen;

}
