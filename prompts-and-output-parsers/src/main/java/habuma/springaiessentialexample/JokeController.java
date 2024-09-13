package habuma.springaiessentialexample;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class JokeController {

    private final PromptTemplate promptTemplate;
    private final ChatClient chatClient;

    @Autowired
    public JokeController(PromptTemplate promptTemplate, ChatClient chatClient) {
        this.promptTemplate = promptTemplate;
        this.chatClient = chatClient;
    }

    @GetMapping("/joke")
    public JokeResponse tellJoke(@RequestParam(name="subject", defaultValue="Spring") String subject) {
        Prompt prompt = promptTemplate.create(Map.of("subject", subject));
        String joke = chatClient.call(prompt).getResult().getOutput().getContent();
        return new JokeResponse(subject, joke);
    }

    @GetMapping("/chat-completion")
    public String chatCompletion(@RequestParam String input) {
        Prompt prompt = new Prompt("Tell me a joke about " + input);
        return chatClient.call(prompt).getResult().getOutput().getContent();
    }
}
