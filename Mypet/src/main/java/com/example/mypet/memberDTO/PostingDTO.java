package com.example.mypet.memberDTO;

import com.example.mypet.entity.PostingEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostingDTO {
    private int rid ;
    private String id ;
    private String nickname;
    private String title;
    private String content;
    private MultipartFile file;
    private String originalFileName;
    private String storedFileName;
    private int fileAttached;


    public static PostingDTO toPostingDTO(PostingEntity element) {
        PostingDTO dto = new PostingDTO() ;
        dto.setRid(element.getRid());
        dto.setId(element.getId());
        dto.setNickname(element.getNickname());
        dto.setContent(element.getContent());
        dto.setTitle(element.getTitle());
        if(element.getFileAttached() == 0){
            dto.setFileAttached(element.getFileAttached());
        }
        else{
            dto.setFileAttached(element.getFileAttached());
            dto.setOriginalFileName(element.getPostingFileEntityList().get(0).getOriginalFileName());
            dto.setStoredFileName(element.getPostingFileEntityList().get(0).getStoredFileName());
        }
        return dto;
    }
}
