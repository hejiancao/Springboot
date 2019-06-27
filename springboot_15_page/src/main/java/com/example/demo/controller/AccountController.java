package com.example.demo.controller;

import com.example.demo.entity.Account;
import com.example.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping("/datagrid")
    public Page<Account> dataGrid(@RequestBody Account account, Pageable pageable) {
        Page<Account> page = accountService.findAll(account, pageable);
        System.out.println("page-content:" + page.getContent());
        return page;
    }


}


