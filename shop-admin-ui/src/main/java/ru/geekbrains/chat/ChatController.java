package ru.geekbrains.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final Logger logger = LoggerFactory.getLogger(ChatController.class);

    private SimpMessagingTemplate template;

    @Autowired
    public void setTemplate(SimpMessagingTemplate template) {
        this.template = template;
    }

    @GetMapping("/test/message")
    public void sendMessage() {
        template.convertAndSend("chat_out/receive_message", new ChatMessage("SERVER", "test message from server"));
    }

    @MessageMapping("/send_message")
    public void messageReceiver(@Payload ChatMessage chatmessage, SimpMessageHeaderAccessor headerAccessor) {
        if(headerAccessor.getUser() == null) {
            logger.info("There is no user to send the message");
            return;
        }
        template.convertAndSendToUser(headerAccessor.getUser().getName(), "/chat_out/receive_message",
                new ChatMessage("SERVER", "Answer to :" + chatmessage.getMessage()),
                createHeaders(headerAccessor.getSessionId()));
    }

    private MessageHeaders createHeaders(String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }
}
