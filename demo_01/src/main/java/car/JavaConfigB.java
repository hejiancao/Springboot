package car;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 相当于spring的IOC容器
 */
@Configuration
public class JavaConfigB {

    @Bean("bmw")
    public Car getBean() {
        return new BMW();
    }

}
