package com.example.mypet.entity;

import com.example.mypet.memberDTO.ChatDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rid;    // 고유 roomid
    private String title;
    private String content;
    private int user;   // 유저 수
    private String userId ;
    private String nickname ;
    private LocalDateTime createdAt;

    public static ChatEntity toNewChatEntity(ChatDTO dto) {
        ChatEntity entity = new ChatEntity();
        entity.setTitle(dto.getTitle());
        entity.setUserId(dto.getUserId());
        entity.setNickname(dto.getNickname());
        entity.setContent(dto.getContent());
        entity.setUser(1);
        return entity;
    }

    @PrePersist
    public void createDate(){
        this.createdAt = LocalDateTime.now();
    }

    public void increaseUser(){
        this.user++;
    }
    public void decreaseUser(){
        if(this.user > 0) this.user--;
    }
}
