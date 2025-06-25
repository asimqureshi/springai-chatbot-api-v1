package lab.maq.langchain.chatbot.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatBotService {
    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public void embed(String text) {
        Document doc = new Document(text);
        List<Document> documents = new TokenTextSplitter().apply(List.of(doc));
        vectorStore.add(documents);
    }

    public String chat(String userMessage) {
        Prompt prompt = new Prompt(userMessage);
        return chatClient.prompt(prompt).call().content();
    }

    public void clearEmbeddings() {
        vectorStore.delete("1==1");
    }
}

