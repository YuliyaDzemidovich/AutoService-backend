import org.example.TempBean;
import org.example.config.AppConfig;
import org.example.config.HibernateConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringTest {
    @Test
    void getBean() {
        String shouldBe = "hello from temp bean";

        Class<?>[] configurations = new Class<?>[] {AppConfig.class, HibernateConfig.class};
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(configurations);
        TempBean bean = (TempBean) context.getBean("tempBean");

        Assertions.assertEquals(bean.greet(), shouldBe);
    }
}
