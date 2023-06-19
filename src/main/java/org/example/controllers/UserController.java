package org.example.controllers;

import org.example.dto.UserDTO;
import org.example.entity.PaymentInfo;
import org.example.entity.User;
import org.example.entity.UserInfoMapper;
import org.example.repository.UserRepo;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
     UserInfoMapper userInfoMapper;

    @Autowired
     UserService service;



    @PostMapping("/save")
    public User saveNewUser(@RequestBody User user) {
        return service.saveNewUser(user);
    }


    @PostMapping("/retrievalEUR")
    public String performTransaction(@RequestBody User user, @RequestParam double amount) {
        if (service.checkBalanceEUR(user, amount)) {
            service.performTransaction(user, amount);
            return "Transaction successful";
        } else {
            return "Insufficient balance";
        }

    }
    @RequestMapping("/retrievalBtc")
    public String BTCRetrieval(@RequestBody User user, @RequestParam double amount){
        if (service.checkBalanceBTC(user, amount)) {
            service.performTransaction(user, amount);
            return "Transaction successful";
        } else {
            return "Insufficient balance";
        }
    }
    @RequestMapping("/currentValueBtc")
    public String currentValueBTC(@RequestBody User user, @RequestParam double amount) throws IOException {
        String currentValueOfBTC = service.getCurrentValueOfBTC();
            return "The current value of the BTC is: " + currentValueOfBTC;

    }
}
