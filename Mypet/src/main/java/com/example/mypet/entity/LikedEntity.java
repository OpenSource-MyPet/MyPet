package com.example.mypet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "liked")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rid ;
    private int postId;
    private String userId;

}
