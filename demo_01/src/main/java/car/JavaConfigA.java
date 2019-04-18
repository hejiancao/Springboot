package car;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 相当于spring的IOC容器
 */
@Configuration
public class JavaConfigA {

    @Bean("toyota")
    public Car getBean() {
        return new Toyota();
    }

}
