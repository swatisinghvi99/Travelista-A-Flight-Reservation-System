package com.example.frs.service;

import com.example.frs.bean.FlightBean;
import com.example.frs.bean.RouteBean;
import com.example.frs.bean.ScheduleBean;
import com.example.frs.dao.FlightDao;
import com.example.frs.dao.RouteDao;
import com.example.frs.dao.ScheduleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ScheduleController {

    @Autowired
    private ScheduleDao scheduleDao;

    @Autowired
    private FlightDao flightDao;

    @Autowired
    private RouteDao routeDao;



    @GetMapping("/addSchedule")
    public String get_add_schedule(Model model){
        model.addAttribute("schedule", new ScheduleBean());
        List<RouteBean> routes = routeDao.list();
        List<FlightBean> flights = flightDao.list();
        model.addAttribute("routes", routes);
        model.addAttribute("flights",flights);
        return "addSchedule";
    }

    @PostMapping("/addSchedule")
    public String post_add_schedule(Model model, @ModelAttribute("schedule") ScheduleBean schedule){
        scheduleDao.save(schedule);
        List<RouteBean> routes = routeDao.list();
        List<FlightBean> flights = flightDao.list();
        model.addAttribute("routes", routes);
        model.addAttribute("flights",flights);
        model.addAttribute("schedule", new ScheduleBean());
        return "redirect:/addSchedule";
    }

    @GetMapping("/manageSchedule")
    public String get_manage_schedule(Model model){
        List<ScheduleBean> schedules = scheduleDao.list();
        model.addAttribute("schedules", schedules);
        return "manageSchedule";
    }
    @PostMapping("/manageSchedule")
    public String post_manage_schedule(Model model, @ModelAttribute("schedule") ScheduleBean schedule){
        scheduleDao.save(schedule);
        model.addAttribute("schedules", new RouteBean());
        return "redirect:/manageSchedule";
    }

    @GetMapping("/updateSchedule")
    public String get_update_schedule(Model model){
        model.addAttribute("schedule", new ScheduleBean());
        List<RouteBean> routes = routeDao.list();
        List<FlightBean> flights = flightDao.list();
        model.addAttribute("routes", routes);
        model.addAttribute("flights",flights);
        return "updateSchedule";
    }

    @PostMapping("/updateSchedule")
    public String post_update_schedule(Model model, @ModelAttribute("schedule") ScheduleBean schedule){
        scheduleDao.update(schedule);
        List<RouteBean> routes = routeDao.list();
        List<FlightBean> flights = flightDao.list();
        model.addAttribute("routes", routes);
        model.addAttribute("flights",flights);
        return "manageSchedule";
    }

    @GetMapping("/deleteSchedule")
    public String get_delete_schedule(Model model,@ModelAttribute("schedule") ScheduleBean schedule){
        scheduleDao.delete(schedule.getSchedule_id());
        List<ScheduleBean> schedules = scheduleDao.list();
        model.addAttribute("schedules", schedules);
        return "manageSchedule";
    }

}
