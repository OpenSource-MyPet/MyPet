package com.example.mypet.memberDTO;

import com.example.mypet.entity.CommentEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommnetDTO {
    private int postId;
    private String userId;
    private String nickname;
    private String comments;
    private LocalDateTime createdAt;
    private String createdAtFormat;

    public static CommnetDTO toCommentDTO(CommentEntity e) {
        CommnetDTO dto = new CommnetDTO();
        dto.setComments(e.getComments());
        dto.setNickname(e.getNickname());
        dto.setCreatedAt(e.getCreatedAt());
        dto.setPostId(e.getPostId());
        dto.setUserId(e.getUserId());
        dto.setCreatedAtFormat(e.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        return dto;
    }
}
