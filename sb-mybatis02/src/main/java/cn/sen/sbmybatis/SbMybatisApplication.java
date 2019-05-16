package cn.sen.sbmybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

@SpringBootApplication
@MapperScan("cn.sen.sbmybatis.mapper")
public class SbMybatisApplication {

    private static Logger logger = LoggerFactory.getLogger(SbMybatisApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(SbMybatisApplication.class, args);
        logger.info("spring-boot 启动 !");
    }

}
