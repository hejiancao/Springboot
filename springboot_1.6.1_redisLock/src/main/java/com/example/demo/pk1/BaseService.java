package com.example.demo.pk1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;


@Component
public class BaseService {

    private static final Logger logger = LoggerFactory.getLogger(BaseService.class);
    private static final Long RELEASE_SUCCESS = 1L;

    @Autowired
    private JedisPool jedisPool;

    public boolean tryLock(Jedis jedis, String lockKey, String requestId, int expireTime) {
        String result = jedis.set(lockKey, requestId, "NX", "PX", expireTime);
        return "OK".equals(result);
    }

    public boolean releaseLock(Jedis jedis, String lockKey, String requestId) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
        return RELEASE_SUCCESS.equals(result);
    }

    public Jedis getJedis() {
        try {
            Jedis jedis = jedisPool.getResource();
            logger.info("jedis:{}", jedis);
            return jedis;
        } catch (Exception e) {
            logger.error("获取jedis连接失败：{}", e.toString());
            throw e;
        }
    }


}
