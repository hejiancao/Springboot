package com.example.demo.service;

import com.example.demo.dao.AccountRepository;
import com.example.demo.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;


/**
 * @author created by shaos on 2019/6/19
 */

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Page<Account> findAll(Account account, Pageable pageable) {

        //Sort.Direction是个枚举有ASC(升序)和DESC(降序)
        //Sort.Direction sort = Sort.Direction.ASC;
        //PageRequest继承于AbstractPageRequest并且实现了Pageable
        //获取PageRequest对象 index:页码 从0开始  size每页容量 sort排序方式 "id"->properties 以谁为准排序
        //Pageable pageable = PageRequest.of(index, size, sort, "id");


        System.out.println(account);
        // 定义匹配规则
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                // 精确匹配
                .withMatcher("id", ExampleMatcher.GenericPropertyMatchers.exact())
                // 模糊查询
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
                // 目前似乎不支持大小比较

        Example<Account> example = Example.of(account,exampleMatcher);
        Page<Account> page = accountRepository.findAll(example, pageable);
        return page;
    }
}
