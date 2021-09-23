package com.example.frs.service;

import com.example.frs.bean.CredentialsBean;
import com.example.frs.dao.UserCredentialsDao;
import com.example.frs.dao.CustomerProfileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CredentialsController {
    @Autowired
    private UserCredentialsDao userCredentialsDao;
    @Autowired
    private CustomerProfileDao customerProfileDao;

    @GetMapping("/")
    public String render_login(Model model){
        return "login";
    }

    @PostMapping("/")
    public String check_login(Model model, @RequestParam("emailID") String emailId , @RequestParam("password") String password, HttpServletRequest request){

        CredentialsBean credential = userCredentialsDao.getByUserId(emailId, password);

        if (credential == null){
            model.addAttribute("reply", "Invalid Email or Password!");
            return "login";
        }

        request.getSession().setAttribute("SESSION_UID", credential.getUser_id());
        String userID = (String)request.getSession().getAttribute("SESSION_UID");
        userCredentialsDao.changeLoginStatus1(userID);
        if("C".equals(credential.getUser_type())){
            return "redirect:/userHome";
        }
        return "redirect:/adminHome";
    }
}
