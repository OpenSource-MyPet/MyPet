package com.example.mypet.controller;

import com.example.mypet.memberDTO.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private SimpMessagingTemplate msgtmp ;

    @Autowired
    public ChatController(SimpMessagingTemplate msgtmp){
        this.msgtmp = msgtmp;
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(MessageDTO message){
        message.setType(MessageDTO.MessageType.CHAT);
        msgtmp.convertAndSend("/topic/"+ message.getRoomId(), message);

    }

    @MessageMapping("/chat.addUser")
    public void addUser(MessageDTO message){
        message.setType(MessageDTO.MessageType.JOIN);
        msgtmp.convertAndSend("/topic/"+ message.getRoomId(), message);

    }
    @MessageMapping("/chat.leave")
    public void leaveUser(MessageDTO message){
        message.setType(MessageDTO.MessageType.LEAVE);
        msgtmp.convertAndSend("/topic/"+ message.getRoomId(), message);

    }
}
