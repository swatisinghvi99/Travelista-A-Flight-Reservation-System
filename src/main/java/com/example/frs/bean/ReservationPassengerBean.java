package com.example.frs.bean;


import lombok.Value;

@Value
public class ReservationPassengerBean {
    private ReservationBean reservation;
    private PassengerBean passenger;
}
