package com.example.demo.dao;

import com.example.demo.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountJPA extends JpaRepository<Account,Integer> {
}


