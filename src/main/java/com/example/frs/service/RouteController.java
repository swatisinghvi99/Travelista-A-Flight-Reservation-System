package com.example.frs.service;

import com.example.frs.bean.RouteBean;
import com.example.frs.dao.RouteDao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RouteController {

    @Autowired
    private RouteDao routeDao;

    @GetMapping("/addRoute")
    public String get_add_route(Model model){
        model.addAttribute("route", new RouteBean());
        return "addRoute";
    }

    @PostMapping("/addRoute")
    public String post_add_route(Model model, @ModelAttribute("route") RouteBean route){
        routeDao.save(route);
        model.addAttribute("route", new RouteBean());
        return "redirect:/addRoute";
    }

    @GetMapping("/manageRoute")
    public String get_manage_route(Model model){
        List<RouteBean> routes = routeDao.list();
        model.addAttribute("routes", routes);
        return "manageRoute";
    }

    @PostMapping("/manageRoute")
    public String post_manage_route(Model model, @ModelAttribute("route") RouteBean route){
        routeDao.save(route);
        model.addAttribute("route", new RouteBean());
        return "redirect:/manageRoute";
    }


    @GetMapping("/updateRoute")
    public String get_update_route(Model model){
        model.addAttribute("route", new RouteBean());
        return "updateRoute";
    }

    @PostMapping("/updateRoute")
    public String post_update_route(Model model, @ModelAttribute("route") RouteBean route){
        routeDao.update(route);
        List<RouteBean> routes = routeDao.list();
        model.addAttribute("routes", routes);
        return "manageRoute";
    }

    @GetMapping("/deleteRoute")
    public String get_delete_route(Model model,@ModelAttribute("route") RouteBean route){
        routeDao.delete(route.getRoute_id());
        List<RouteBean> routes = routeDao.list();
        model.addAttribute("routes", routes);
        return "manageRoute";
    }
}
