package com.example.mypet.repository;

import com.example.mypet.entity.LikedEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikedRepository extends JpaRepository<LikedEntity, Integer> {
    @Transactional
    void deleteByPostIdAndUserId(int postId, String id);

    List<LikedEntity> findByUserId(String id);
}
