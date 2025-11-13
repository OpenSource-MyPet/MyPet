package com.example.mypet.entity;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bookmark")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rid;
    private int postId;
    private String userId;
}
