package com.example.demo.service;

import com.example.demo.entity.Account;

import java.util.List;

/**
 * @author created by shaos on 2019/6/19
 */
public interface AccountService {
    List<Account> findAll();

    Account findOne(int id);

    Account saveAndFlush(Account account);

    Account save(Account account);
}
