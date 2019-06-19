package com.example.demo.service;

import com.example.demo.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Transactional(rollbackFor = Exception.class)
    public void transfer() throws RuntimeException{
        // 用户1减10块 用户2加10块
        accountMapper.update(90,1);
        int i = 1/0;
        accountMapper.update(110,2);
    }
}

