package habuma.springaiessentialexample;

import habuma.springaiessentialexample.service.LangChain4JService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.UserName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@RestController
public class JokeController {

    private static final Logger logger = LoggerFactory.getLogger(JokeController.class);

    private final LangChain4JService langChain4JService;

    @Autowired
    public JokeController(LangChain4JService langChain4JService) {
        this.langChain4JService = langChain4JService;
    }

    @PostMapping(value = "/tell-joke", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter tellJoke(
            @RequestParam("sessionId") String sessionId,
            @UserMessage @RequestParam("message") String message,
            @UserName @RequestParam("username") String username) {
        
        logger.debug("Generating joke about subject: {}", message);
        
        SseEmitter emitter = new SseEmitter();
        
        CompletableFuture.runAsync(() -> {
            try {
                langChain4JService.generateJoke(message).thenAccept(joke -> {
                    try {
                        emitter.send(SseEmitter.event().data(new JokeResponse(joke)));
                        emitter.complete();
                    } catch (IOException e) {
                        emitter.completeWithError(e);
                    }
                });
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });
        
        return emitter;
    }
    
    @GetMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter chatCompletion(@RequestParam String input) {
        logger.debug("Performing chat completion with input: {}", input);
        
        SseEmitter emitter = new SseEmitter();
        
        CompletableFuture.runAsync(() -> {
            try {
                langChain4JService.chatCompletion(input).thenAccept(response -> {
                    try {
                        emitter.send(SseEmitter.event().data(response));
                        emitter.complete();
                    } catch (IOException e) {
                        emitter.completeWithError(e);
                    }
                });
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });
        
        return emitter;
    }
}
