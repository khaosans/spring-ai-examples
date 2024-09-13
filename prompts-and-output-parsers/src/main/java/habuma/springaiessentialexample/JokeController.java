package habuma.springaiessentialexample;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class JokeController {

    private final ChatClient chatClient;

    @Autowired
    public JokeController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/joke")
    public JokeResponse tellJoke(@RequestParam String subject) {
        return new JokeResponse(subject, "This is a joke about " + subject);
    }

    @GetMapping("/chat-completion")
    public String chatCompletion(@RequestParam String input) {
        Prompt prompt = new Prompt("tell me a joke about " + input);
        ChatResponse response = chatClient.call(prompt);
        return response.getResult().getOutput().getContent();
    }
}
