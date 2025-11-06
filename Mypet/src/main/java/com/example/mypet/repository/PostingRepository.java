package com.example.mypet.repository;

import com.example.mypet.entity.PostingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostingRepository extends JpaRepository<PostingEntity, Integer> {
}
