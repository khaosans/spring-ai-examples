package habuma.springaiessentialexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.ai.chat.prompt.PromptTemplate;

@SpringBootApplication
public class SpringAiEssentialExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAiEssentialExampleApplication.class, args);
    }

    @Bean
    public PromptTemplate jokePromptTemplate() {
        return new PromptTemplate("Tell me a joke about {subject}.");
    }
}
