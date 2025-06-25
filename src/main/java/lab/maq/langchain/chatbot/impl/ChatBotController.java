package lab.maq.langchain.chatbot.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chatbot")
public class ChatBotController {

    final private ChatBotService chatBotService;

    @SneakyThrows
    @PostMapping("/embed")
    ResponseEntity<String> postEmbed(@RequestBody EmbedModel embedRequest) {
        chatBotService.embed(embedRequest.text());
        return ResponseEntity.status(201).body("SUCCESSFUL");
    }

    @DeleteMapping("/embed")
    ResponseEntity<String> clearEmbeddings() {
        chatBotService.clearEmbeddings();
        return ResponseEntity.ok("ALL_EMBEDDINGS_CLEARED");
    }

    @PostMapping("/chat")
    ResponseEntity<ChatModel> postChat(@RequestBody ChatModel chatRequest) {
        String text = chatBotService.chat(chatRequest.message());
        ChatModel chatResponse = new ChatModel(text);

        return ResponseEntity.ok(chatResponse);
    }

}
