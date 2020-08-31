package com.daneking.websocket.command;

import org.springframework.context.ApplicationEvent;


public class CommandEvent extends ApplicationEvent {
    private String message;

    public CommandEvent(Object source, String message) {
        super(source);
        this.message = message;
    }
    public String getMessage() {
        return message;
    }


}