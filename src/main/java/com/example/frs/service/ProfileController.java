package com.example.frs.service;



import com.example.frs.bean.ProfileBean;
import com.example.frs.dao.CustomerProfileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfileController {
    @Autowired
    private CustomerProfileDao customerProfileDao;

    @GetMapping("/signup")
    public String get_signup(Model model){
        model.addAttribute("profile", new ProfileBean());
        return "signup";
    }

    @PostMapping("/signup")
    public String post_signup(@ModelAttribute("profile") ProfileBean profile){
        customerProfileDao.save(profile);
        return "login";
    }
}


