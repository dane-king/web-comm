package com.daneking.websocket.details;

import com.daneking.websocket.command.CommandEvent;
import com.daneking.websocket.command.OutputMessage;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class LookupListener {
    private LookupService lookupService;
    private SimpMessageSendingOperations simpMessagingTemplate;


    public LookupListener(LookupService lookupService, SimpMessageSendingOperations simpMessagingTemplate) {
        this.lookupService = lookupService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @EventListener
    @Async
    public void commandResponse(CommandEvent message) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        OutputMessage outputMessage = new OutputMessage("system", "hey", time);
        simpMessagingTemplate.convertAndSendToUser(message.getMessage(),"/queue/response", outputMessage);
        simpMessagingTemplate.convertAndSend("/topic/messages", lookupService.getResults("1").block());
    }
}
