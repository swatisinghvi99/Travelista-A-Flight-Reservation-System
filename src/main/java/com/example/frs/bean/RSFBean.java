package com.example.frs.bean;

import lombok.Value;

@Value
public class RSFBean {
    private RouteBean route;
    private ScheduleBean schedule;
    private FlightBean flight;
}
