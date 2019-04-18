package car;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 把两个容器合并
 */
@Configuration
@Import({JavaConfigA.class,JavaConfigB.class})
public class ParentConfig {


}
