package cn.sen.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo04ApplicationTests {

    private final static Logger logger = LoggerFactory.getLogger(Demo04ApplicationTests.class);

    @Test
    public void contextLoads() {
        logger.info("test log");
    }


}
