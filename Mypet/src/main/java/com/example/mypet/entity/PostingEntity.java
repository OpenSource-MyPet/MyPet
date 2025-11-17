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
@Table(name = "posting")
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
    private int likes;
    private int comments;
    @OneToMany(mappedBy = "postingEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PostingFileEntity> postingFileEntityList = new ArrayList<>();

    public static PostingEntity toPostingEntity(PostingDTO dto) {
        PostingEntity entity = new PostingEntity();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setNickname(dto.getNickname());
        entity.setFileAttached(0);
        entity.setLikes(0);
        entity.setComments(0);
        return entity;
    }

    public static PostingEntity toPostingFileEntity(PostingDTO dto) {
        PostingEntity entity = new PostingEntity();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setNickname(dto.getNickname());
        entity.setFileAttached(1);
        entity.setLikes(0);
        entity.setComments(0);
        return entity;
    }

    public void increaseLikes(){
        this.likes++;
    }
    public void decreaseLikes(){
        if(this.likes > 0) this.likes--;
    }
    public void increaseComments(){
        this.comments++;
    }
}
