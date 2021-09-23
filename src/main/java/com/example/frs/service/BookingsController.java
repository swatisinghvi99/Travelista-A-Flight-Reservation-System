package com.example.frs.service;

import java.util.List;

import com.example.frs.bean.*;
import com.example.frs.dao.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookingsController {

    @Autowired
    private AdminDao adminDao;

    @GetMapping("/bookings")
    public String get_booking_data(Model model){
        List<ScheduleReservationBean> SWR =  adminDao.viewBookedTicketAll();
        model.addAttribute("SWR", SWR);
        return "bookings";
    }
}
