package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.DTO.SearchWordDto;
import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.payments.BalanceTransactionEntity;
import com.example.MyBookShopApp.data.user.UserEntity;
import com.example.MyBookShopApp.security.BookstoreUserRegister;
import com.example.MyBookShopApp.security.RegistrationForm;
import com.example.MyBookShopApp.services.BalanceTransactionService;
import com.example.MyBookShopApp.services.SmsService;
import com.example.MyBookShopApp.services.TokenBlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfileController {

    private final BookstoreUserRegister userRegister;
    private final BalanceTransactionService balanceTransactionService;


    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @Autowired
    public ProfileController(BookstoreUserRegister userRegister, BalanceTransactionService balanceTransactionService) {
        this.userRegister = userRegister;
        this.balanceTransactionService = balanceTransactionService;
    }

    @GetMapping("/profile")
    public String handleProfile(Model model) {
        RegistrationForm rf = new RegistrationForm();
        UserEntity user = (UserEntity) userRegister.getCurrentUser();
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
        UserEntity user = (UserEntity) userRegister.getCurrentUser();
        model.addAttribute("regForm", registrationForm);
        model.addAttribute("curUsr", user);
        return "profile";
    }

    @ModelAttribute("trans")
    public List<BalanceTransactionEntity> transactionsPage() {
        return new ArrayList<>();
    }

    @GetMapping("/transactions")
    public String handleTransactions(Model model) {
        UserEntity user = (UserEntity) userRegister.getCurrentUser();
        List<BalanceTransactionEntity> trans = balanceTransactionService.getTransactionsDesc(user, 0, 4);
        model.addAttribute("trans", trans);
        return "transactions";
    }


    @GetMapping("/transactionsElse")
    public String handleTransactionsElse(@RequestParam("offset") Integer offset,
                                     @RequestParam("limit") Integer limit,
                                     @RequestParam(value = "sort", required = false) String sort, Model model) {

        return "transactions";
    }
}
