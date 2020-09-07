package com.daneking.websocket.command;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class ChatController {
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public OutputMessage chat(SimpMessageHeaderAccessor sha, Message message) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        return new OutputMessage(message.getFrom(), message.getText() + " in here", time );
    }



}
