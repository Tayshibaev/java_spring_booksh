package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.security.BookstoreUser;
import com.example.MyBookShopApp.security.BookstoreUserRegister;
import com.example.MyBookShopApp.security.RegistrationForm;
import com.example.MyBookShopApp.services.SmsService;
import com.example.MyBookShopApp.services.TokenBlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfileController {

    private final BookstoreUserRegister userRegister;
    private final TokenBlackListService tokenBlackListService;
    private final SmsService smsService;
    private final JavaMailSender javaMailSender;

    @Autowired
    public ProfileController(BookstoreUserRegister userRegister, TokenBlackListService tokenBlackListService, SmsService smsService, JavaMailSender javaMailSender) {
        this.userRegister = userRegister;
        this.tokenBlackListService = tokenBlackListService;
        this.smsService = smsService;
        this.javaMailSender = javaMailSender;
    }

    @GetMapping("/profile")
    public String handleProfile(Model model) {
        RegistrationForm rf = new RegistrationForm();
        BookstoreUser user = (BookstoreUser) userRegister.getCurrentUser();
        rf.setMail(user.getEmail());
        rf.setName(user.getName());
        rf.setPhone(user.getPhone());
        model.addAttribute("regForm", rf);
        model.addAttribute("curUsr", user);
        return "profile";
    }

    @PostMapping(path = "/profile/saveData")
    public String handleUserregistration(RegistrationForm registrationForm, Model model) {
        System.out.println(registrationForm);
        if (registrationForm.getPass().equals("")) {
            model.addAttribute("changeOk", false);
            model.addAttribute("pswNotApr", false);
            model.addAttribute("pswIsNull", true);
        } else {
            if (registrationForm.getPassApprove().equals(registrationForm.getPass())) {
                userRegister.changeDataClient(registrationForm);
                model.addAttribute("changeOk", true);
                model.addAttribute("pswNotApr", false);
                model.addAttribute("pswIsNull", false);
            } else {
                model.addAttribute("pswNotApr", true);
                model.addAttribute("changeOk", false);
                model.addAttribute("pswIsNull", false);
            }
            registrationForm.setPass("");//обнуляем пароли
            registrationForm.setPassApprove("");
        }
        System.out.println("profile");
        BookstoreUser user = (BookstoreUser) userRegister.getCurrentUser();
        model.addAttribute("regForm", registrationForm);
        model.addAttribute("curUsr", user);
        return "profile";
    }
}
