package cn.sen.springboot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 启动类必须位于根目录下
 * <p>
 * /@SpringBootApplication : Spring Boot的核心注解，主要组合包含了以下3个注解：
 * /@SpringBootConfiguration：组合了@Configuration注解，实现配置文件的功能。
 * /@EnableAutoConfiguration：打开自动配置的功能，也可以关闭某个自动配置的选项，如关闭数据源自动配置功能：
 * /@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })。
 * /@ComponentScan：Spring组件扫描。
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    private static final org.apache.commons.logging.Log logger1 = org.apache.commons.logging
            .LogFactory.getLog(Application.class);

    private static final org.slf4j.Logger logger2 = org.slf4j.LoggerFactory
            .getLogger(Application.class);

    private static final java.util.logging.Logger logger3 = java.util.logging.Logger
            .getLogger("Application");


    /**
     * 如果你想在Spring Boot启动的时候运行一些特定的代码，你可以实现接口 ApplicationRunner或者 CommandLineRunner，这两个接
     * 口实现方式一样，它们都只提供了一个run方法。
     * https://mp.weixin.qq.com/s?__biz=MzI3ODcxMzQzMw==&mid=2247484366&idx=1&sn=7dc94038861fe9e10cdf132ffc83092f&scene=21#wechat_redirect
     * @return
     */
    @Bean
    public CommandLineRunner loggerLineRunner() {
        return (args) -> {
            logger1.debug("commons logging debug...");
            logger1.info("commons logging info...");
            logger1.error("commons logging error...");

            logger2.info("slf4j info...");
            logger3.info("java util logging info...");
        };
    }



}
