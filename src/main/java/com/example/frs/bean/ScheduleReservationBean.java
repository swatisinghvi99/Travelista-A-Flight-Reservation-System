package com.example.frs.bean;

import lombok.Value;

@Value
public class ScheduleReservationBean {
    private RouteBean route;
    private ScheduleBean schedule;
    private FlightBean flight;
    private ReservationBean reservation;
}
