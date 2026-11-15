package org.example.accountaiagent.mcp_client;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Component;

@Component
public class AccountAiAgent {
    private final ChatClient chatClient;

    public AccountAiAgent(ChatClient.Builder chatClientBuilder, ToolCallbackProvider toolCallbackProvider) {
        this.chatClient = chatClientBuilder
                .defaultTools(toolCallbackProvider)
                .build();
    }

    public String chat(String query) {
        return chatClient.prompt(query).call().content();
    }
}
