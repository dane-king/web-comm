package com.daneking.websocket.command;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class CommandController {
    private ApplicationEventPublisher applicationEventPublisher;

    public CommandController(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @MessageMapping("/command")
    @SendToUser("/queue/request")
    public OutputMessage commandRequest(SimpMessageHeaderAccessor sha, Message message) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        this.applicationEventPublisher.publishEvent(new CommandEvent(this, sha.getUser().getName()));
        return new OutputMessage(message.getFrom(), "Received:" + message.getText() + ",to:" + sha.getSessionId(), time);
    }




}

