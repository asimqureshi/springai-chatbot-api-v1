package lab.maq.langchain.chatbot;

import io.modelcontextprotocol.client.McpSyncClient;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.SneakyThrows;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;
import java.util.List;

@Configuration
public class ChatBotConfig {

    private PostgreSQLContainer<?> postgres;

    @PostConstruct
    public void startContainer() {
        postgres = new PostgreSQLContainer<>("pgvector/pgvector:pg15")
                .withDatabaseName("demodb")
                .withUsername("demo")
                .withPassword("demo")
                .withInitScript("init.sql");
        postgres.start();
    }

    @PreDestroy
    public void stopContainer() {
        if (postgres != null) {
            postgres.stop();
        }
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url(postgres.getJdbcUrl())
                .username(postgres.getUsername())
                .password(postgres.getPassword())
                .driverClassName(postgres.getDriverClassName())
                .build();
    }

    @SneakyThrows
    @Bean
    public ChatClient chatClient(VectorStore vectorStore, ChatModel chatModel, ChatMemory chatMemory, List<McpSyncClient> mcpSyncClients) {

        PromptTemplate template = PromptTemplate.builder()
                .resource(new ClassPathResource("qa.prompt"))
                .build();

        return ChatClient.builder(chatModel)
                .defaultAdvisors(
                        SimpleLoggerAdvisor.builder().build(),
                        PromptChatMemoryAdvisor.builder(chatMemory).build(),
                        QuestionAnswerAdvisor.builder(vectorStore)
                                .promptTemplate(template)
                                .build()

                )
                .defaultToolCallbacks(new SyncMcpToolCallbackProvider(mcpSyncClients))
                .build();
    }

} 