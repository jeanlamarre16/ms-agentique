package org.example.accountaiagent.mcp_client;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountAgentController {

    private final AccountAiAgent accountAiAgent;

    public AccountAgentController(AccountAiAgent accountAiAgent) {
            this.accountAiAgent = accountAiAgent;
        }

    @GetMapping(path = "/chat", produces = MediaType.TEXT_PLAIN_VALUE )
    public String chat(String query) {
            return accountAiAgent.chat(query);
        }
}
