package com.example.mypet.repository;

import com.example.mypet.entity.BookmarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<BookmarkEntity, Integer> {
    void deleteByPostIdAndUserId(int rid, String id);

    List<BookmarkEntity> findByUserId(String id);
}
