package habuma.springaiessentialexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "habuma.springaiessentialexample")
public class SpringAiEssentialExampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringAiEssentialExampleApplication.class, args);
    }
}
