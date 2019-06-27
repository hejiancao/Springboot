package com.example.demo.service;

import com.example.demo.repository.AccountRepository;
import com.example.demo.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;


/**
 * @author created by shaos on 2019/6/19
 */

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    /**
     * 使用Specification进行分页查询
     *
     * @param account
     * @param pageable
     * @author shaos
     * @date 2019/6/27 10:14
     */
    public Page<Account> findAll(Account account, Pageable pageable) {

        // 分页信息
        Pageable pb = PageRequest.of(0, 5, Sort.Direction.DESC,"id");

        Specification specification = new Specification<Account>() {
            /**
             * @param root 实体对象引用
             * @param query 规则查询对象
             * @param cb 规则查询对象
             * @author shaos
             * @date 2019/6/27 10:16
             */
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                ArrayList<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(account.getName())) {
                    Predicate predicateName = cb.like(root.get("name"), "%" + account.getName() + "%");
                    predicates.add(predicateName);
                }
                if (account.getId() != null) {
                    Predicate predicateId = cb.equal(root.get("id"), account.getId());
                    predicates.add(predicateId);
                }
                return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            }
        };

        Page<Account> page = accountRepository.findAll(specification, pb);
        return page;
    }
}
