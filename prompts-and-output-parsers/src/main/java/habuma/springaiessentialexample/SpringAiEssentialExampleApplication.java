package habuma.springaiessentialexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import dev.langchain4j.model.input.PromptTemplate;

@SpringBootApplication
@EnableAspectJAutoProxy
public class SpringAiEssentialExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAiEssentialExampleApplication.class, args);
    }

    @Bean
    public PromptTemplate jokePromptTemplate() {
        return PromptTemplate.from("Tell me a joke about {subject}");
    }
}
