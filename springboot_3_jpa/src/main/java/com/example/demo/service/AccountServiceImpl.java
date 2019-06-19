package com.example.demo.service;

import com.example.demo.dao.AccountDao;
import com.example.demo.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author created by shaos on 2019/6/19
 */

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Override
    public List<Account> findAll() {
        return accountDao.findAll();
    }

    @Override
    public Account findOne(int id) {
        return accountDao.getOne(id);
    }

    @Override
    public Account saveAndFlush(Account account) {
        return accountDao.saveAndFlush(account);
    }

    @Override
    public Account save(Account account) {
        return accountDao.save(account);
    }
}
