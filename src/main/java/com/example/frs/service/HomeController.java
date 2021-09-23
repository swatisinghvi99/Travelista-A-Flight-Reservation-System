package com.example.frs.service;

import com.example.frs.bean.ProfileBean;
import com.example.frs.dao.UserCredentialsDao;
import com.example.frs.dao.CustomerProfileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    @Autowired
    CustomerProfileDao customerProfileDao;

    @Autowired
    UserCredentialsDao userCredentialsDao;

    @GetMapping("/userHome")
    public String get_userHome(Model model, HttpServletRequest request){
        String user_id = (String)request.getSession().getAttribute("SESSION_UID");
        ProfileBean profile = customerProfileDao.getByUserId(user_id);
        model.addAttribute("profile_firstName", profile.getFirst_name());
        model.addAttribute("profile_lastName", profile.getLast_name());
        return "userHome";
    }

    @GetMapping("/adminHome")
    public String get_adminHome(Model model, HttpServletRequest request){
        return "adminHome";
    }

    @GetMapping("/logout")
    public String get_logout(Model model, HttpServletRequest request){
        String userID = (String)request.getSession().getAttribute("SESSION_UID");
        HttpSession session = request.getSession(false);
        if(session!=null) {
            userCredentialsDao.changeLoginStatus0(userID);
            session.invalidate();
        }
        return "redirect:/";
    }

}
