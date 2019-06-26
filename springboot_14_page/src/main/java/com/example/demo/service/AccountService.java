package com.example.demo.service;

import com.example.demo.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * @author created by shaos on 2019/6/19
 */
public interface AccountService {

    Page<Account> findAll(Account account, Pageable pageable);
}
