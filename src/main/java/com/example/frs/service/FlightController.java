package com.example.frs.service;

import com.example.frs.bean.FlightBean;
import com.example.frs.bean.RouteBean;
import com.example.frs.dao.FlightDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class FlightController {

    @Autowired
    private FlightDao flightDao;

    @GetMapping("/addFlight")
    public String get_add_flight(Model model){
        model.addAttribute("flight", new FlightBean());
        return "addFlight";
    }
    @PostMapping("/addFlight")
    public String post_add_flight(Model model, @ModelAttribute("flight") FlightBean flight){
        flightDao.save(flight);
        model.addAttribute("flight", new FlightBean());
        return "redirect:/addFlight";
    }

    
    @GetMapping("/manageFlight")
    public String get_manage_flight(Model model){
        List<FlightBean> flights = flightDao.list();
        model.addAttribute("flights", flights);
        return "manageFlight";
    }
    @PostMapping("/manageFlight")
    public String post_manage_flight(Model model, @ModelAttribute("flight") FlightBean flight){
        flightDao.save(flight);
        model.addAttribute("flight", new FlightBean());
        return "redirect:/manageFlight";
    }

    @GetMapping("/updateFlight")
    public String get_update_flight(Model model){
        model.addAttribute("flight", new FlightBean());
        return "updateFlight";
    }
    
    @PostMapping("/updateFlight")
    public String post_update_flight(Model model, @ModelAttribute("flight") FlightBean flight){
        flightDao.update(flight);
        List<FlightBean> flights = flightDao.list();
        model.addAttribute("flights", flights);
        return "manageFlight";
    }
    
    
    @GetMapping("/deleteFlight")
    public String get_delete_flight(Model model, @ModelAttribute("flight") FlightBean flight){
    	flightDao.delete(flight.getFlight_id());
        List<FlightBean> flights = flightDao.list();
        model.addAttribute("flights", flights);
        return "manageFlight";
    }
}
