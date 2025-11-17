package com.example.mypet.service;

import com.example.mypet.entity.BookmarkEntity;
import com.example.mypet.entity.CommentEntity;
import com.example.mypet.entity.LikedEntity;
import com.example.mypet.entity.PostingEntity;
import com.example.mypet.memberDTO.CommnetDTO;
import com.example.mypet.memberDTO.LikesDTO;
import com.example.mypet.repository.BookmarkRepository;
import com.example.mypet.repository.CommentRepository;
import com.example.mypet.repository.LikedRepository;
import com.example.mypet.repository.PostingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InteractionService {

    LikedRepository repo ;
    PostingRepository postRepo;
    BookmarkRepository markRepo ;
    CommentRepository commentRepo;

    @Autowired
    public InteractionService(LikedRepository repo, PostingRepository postRepo, BookmarkRepository markRepo, CommentRepository commentRepo){
        this.repo = repo ;
        this.postRepo = postRepo;
        this.markRepo = markRepo;
        this.commentRepo = commentRepo;
    }
    @Transactional
    public void saveLike(int rid, String id, int isLiked) {
        if(isLiked == 1){
            LikedEntity entity = new LikedEntity();
            entity.setUserId(id);
            entity.setPostId(rid);
            repo.save(entity);
            PostingEntity postEntity = postRepo.findById(rid).get();
            postEntity.increaseLikes();
            postRepo.save(postEntity);
        }
        else{
            deletefromDB(rid, id);
            PostingEntity postEntity = postRepo.findById(rid).get();
            postEntity.decreaseLikes();
            postRepo.save(postEntity);
        }


    }

    public void deletefromDB(int rid, String id) {
        repo.deleteByPostIdAndUserId(rid, id);
    }
    
    //유저 아이디를 바탕으로 해당 유저의 좋아요 목록들을 뽑아오고 sort하는데 필요없음 index get할때 뿌릴 방법 생각 
    public List<Integer> getLikes(String id) {
        List<LikedEntity> list = repo.findByUserId(id);
        List<Integer> dtos = new ArrayList<>();
        for(LikedEntity e : list){
            dtos.add(e.getPostId());
        }
        return dtos;

    }

    @Transactional
    public void saveMark(int rid, String id, int isMarked) {
        if(isMarked == 1){
            BookmarkEntity entity = new BookmarkEntity();
            entity.setUserId(id);
            entity.setPostId(rid);
            markRepo.save(entity);
        }
        else{
            deleteMarkfromDB(rid, id);
        }
    }

    private void deleteMarkfromDB(int rid, String id) {
        markRepo.deleteByPostIdAndUserId(rid, id);
    }

    public List<Integer> getBookMarks(String id) {
        List<BookmarkEntity> list = markRepo.findByUserId(id);
        List<Integer> dtos = new ArrayList<>();
        for(BookmarkEntity e : list){
            dtos.add(e.getPostId());
        }
        return dtos;
    }

    @Transactional
    public void saveComments(String comments, int postId, String userId, String nickname) {
        CommentEntity entity = new CommentEntity();
        entity.setComments(comments);
        entity.setNickname(nickname);
        entity.setUserId(userId);
        entity.setPostId(postId);
        commentRepo.save(entity);
        PostingEntity postingEntity = postRepo.findById(postId).get();
        postingEntity.increaseComments();
        postRepo.save(postingEntity);

    }

    public List<CommnetDTO> getComments(int id) {
        List<CommentEntity> list = commentRepo.findAllByPostId(id);
        List<CommnetDTO> dtos = new ArrayList<>();
        for(CommentEntity e : list){
            dtos.add(CommnetDTO.toCommentDTO(e));
        }
        return dtos;
    }
}
