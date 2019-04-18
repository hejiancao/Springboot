package car;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ContextLoadTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ParentConfig.class);
        Car toyota = (Car) context.getBean("toyota");
        toyota.print();
        Car bmw = (Car) context.getBean("bmw");
        bmw.print();
    }
}
