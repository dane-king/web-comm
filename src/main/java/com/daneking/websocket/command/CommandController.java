package com.daneking.websocket.command;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class CommandController {
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public OutputMessage chat(Message message) {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new OutputMessage(message.getFrom(), message.getText() + " in here", time);
    }

    @MessageMapping("/command")
    @SendToUser("/queue/response")
    public OutputMessage command(Message message) {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new OutputMessage(message.getFrom(), "This is the " + message.getText(), time);
    }

}
