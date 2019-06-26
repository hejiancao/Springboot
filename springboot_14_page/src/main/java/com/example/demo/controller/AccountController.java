package com.example.demo.controller;

import com.example.demo.entity.Account;
import com.example.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService accountService;


    /** 使用Example进行分页查询
     * @param account
     * @param pageable
     * @author shaos
     * @date 2019/6/26 15:37
     */
    @PostMapping("/datagrid")
    public List<Account> dataGrid(@RequestBody Account account, Pageable pageable) {
        Page<Account> page = accountService.findAll(account, pageable);
        return page.getContent();
    }


}


