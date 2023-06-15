package org.example.controllers;

import org.example.dto.UserDTO;
import org.example.entity.User;
import org.example.entity.UserInfoMapper;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserService service;

    @PostMapping("/save")
    public User saveNewUser(@RequestBody UserDTO dto) {
        return service.saveNewUser(userInfoMapper.toEntity(dto));
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
