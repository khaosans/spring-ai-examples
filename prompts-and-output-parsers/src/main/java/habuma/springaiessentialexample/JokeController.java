package habuma.springaiessentialexample;

import habuma.springaiessentialexample.service.LangChain4JService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@RestController
public class JokeController {

    private static final Logger logger = LoggerFactory.getLogger(JokeController.class);

    private final LangChain4JService langChain4JService;

    @Autowired
    public JokeController(LangChain4JService langChain4JService) {
        this.langChain4JService = langChain4JService;
    }

    @GetMapping("/joke")
    public JokeResponse tellJoke(@RequestParam(name="subject", defaultValue="Spring") String subject) {
        logger.debug("Generating joke about subject: {}", subject);
        String joke = langChain4JService.generateJoke(subject);
        logger.debug("Generated joke: {}", joke);
        return new JokeResponse(subject, joke);
    }

    @GetMapping("/chat-completion")
    public String chatCompletion(@RequestParam String input) {
        logger.debug("Performing chat completion with input: {}", input);
        String response = langChain4JService.chatCompletion(input);
        logger.debug("Chat completion response: {}", response);
        return response;
    }
}
