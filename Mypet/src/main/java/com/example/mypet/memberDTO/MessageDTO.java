package com.example.mypet.memberDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
    private MessageType type;
    private String roomId;
    private String sender;
    private String content;
}
