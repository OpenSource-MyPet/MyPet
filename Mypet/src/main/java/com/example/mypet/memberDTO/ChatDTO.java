package com.example.mypet.memberDTO;

import com.example.mypet.entity.ChatEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatDTO {
    private int roomId;
    private String title;
    private String content;
    private int user;
    private String userId ;
    private String nickname ;
    private LocalDateTime createdAt;
    private String createdAtFormat;

    public static ChatDTO tochatDTO(ChatEntity e) {
        ChatDTO dto = new ChatDTO();
        dto.setRoomId(e.getRid());
        dto.setNickname(e.getNickname());
        dto.setTitle(e.getTitle());
        dto.setContent(e.getContent());
        dto.setUser(e.getUser());
        dto.setUserId(e.getUserId());
        dto.setCreatedAt(e.getCreatedAt());
        dto.setCreatedAtFormat(e.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        return dto ;
    }
}
