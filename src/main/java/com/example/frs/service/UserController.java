package com.example.frs.service;
import java.util.List;

import com.example.frs.bean.*;
import com.example.frs.dao.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @Autowired
    private CustomerProfileDao customerProfileDao;
    @GetMapping("/userdata")
    public String get_user_data(Model model){
        List<ProfileBean> profiles = customerProfileDao.list();
        model.addAttribute("profiles", profiles);
        return "userdata";
    }

}
