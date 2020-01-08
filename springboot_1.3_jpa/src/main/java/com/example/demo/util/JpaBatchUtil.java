package com.example.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Iterator;

/**
 * @author created by shaos on 2020/1/8
 */
@Component
public class JpaBatchUtil {

    @Autowired
    private EntityManager em;
    private final int BATCH_SIZE = 10000;

    @Transactional(rollbackOn = Exception.class)
    public <S> Iterable<S> batchSave(Iterable<S> var1) {
        Iterator<S> iterator = var1.iterator();
        int index = 0;
        while (iterator.hasNext()){
            em.persist(iterator.next());
            index++;
            if (index % BATCH_SIZE == 0){
                em.flush();
                em.clear();
            }
        }
        if (index % BATCH_SIZE != 0){
            em.flush();
            em.clear();
        }
        return var1;
    }


    @Transactional(rollbackOn = Exception.class)
    public <S> Iterable<S> batchUpdate(Iterable<S> var1) {
        Iterator<S> iterator = var1.iterator();
        int index = 0;
        while (iterator.hasNext()){
            em.merge(iterator.next());
            index++;
            if (index % BATCH_SIZE == 0){
                em.flush();
                em.clear();
            }
        }
        if (index % BATCH_SIZE != 0){
            em.flush();
            em.clear();
        }
        return var1;
    }

}
