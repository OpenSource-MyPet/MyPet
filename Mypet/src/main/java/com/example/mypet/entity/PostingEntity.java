package com.example.mypet.entity;

import com.example.mypet.memberDTO.PostingDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Posting")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rid ;
    private String id ;
    private String nickname;
    private String title;
    private String content;
    private int fileAttached;
    @OneToMany(mappedBy = "postingEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PostingFileEntity> PostingFileEntityList = new ArrayList<>();

    public static PostingEntity toPostingEntity(PostingDTO dto) {
        PostingEntity entity = new PostingEntity();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setNickname(dto.getNickname());
        entity.setFileAttached(0);
        return entity;
    }

    public static PostingEntity toPostingFileEntity(PostingDTO dto) {
        PostingEntity entity = new PostingEntity();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setNickname(dto.getNickname());
        entity.setFileAttached(1);
        return entity;
    }
}
