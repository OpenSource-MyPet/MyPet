package com.example.mypet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "posting_file")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostingFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rid ;
    private String originalFileName ;
    private String storedFileName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posting_rid")
    private PostingEntity postingEntity;

    public static PostingFileEntity toPostingFileEntity(PostingEntity postingEntity, String fileName, String storedFileName) {
        PostingFileEntity entity = new PostingFileEntity();
        entity.setOriginalFileName(fileName);
        entity.setStoredFileName(storedFileName);
        entity.setPostingEntity(postingEntity);
        return entity;
    }
}
