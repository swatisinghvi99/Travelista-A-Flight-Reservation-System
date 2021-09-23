package com.example.frs.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "FRS_TBL_Flight")
@Getter
@Setter
@NoArgsConstructor
public class FlightBean {
    @Id
    @Column(name = "flight_id")
    private String flight_id;

    @Column(name = "flight_name")
    private String flight_name;

    @Column(name = "seating_capacity")
    private int seating_capacity;

    @Column(name = "reservation_capacity")
    private int reservation_capacity;
}
