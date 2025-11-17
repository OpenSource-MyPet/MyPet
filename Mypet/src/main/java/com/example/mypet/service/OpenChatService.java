package com.example.mypet.service;

import com.example.mypet.controller.OpenChatController;
import com.example.mypet.entity.ChatEntity;
import com.example.mypet.memberDTO.ChatDTO;
import com.example.mypet.repository.ChatRepository;
import jakarta.transaction.Transactional;
import org.codehaus.groovy.transform.trait.Traits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OpenChatService {

    ChatRepository repo ;

    @Autowired
    public OpenChatService(ChatRepository repo){
        this.repo = repo ;
    }

    public void savetodb(ChatDTO dto) {
        ChatEntity entity = ChatEntity.toNewChatEntity(dto);
        repo.save(entity);
    }

    public List<ChatDTO> getChatList() {
        List<ChatEntity> list = repo.findAll();
        List<ChatDTO> dtos = new ArrayList<>();
        for(ChatEntity e : list){
            dtos.add(ChatDTO.tochatDTO(e));
        }
        return dtos;
    }

    public ChatDTO getChatRoom(int roomid) {
        ChatEntity entity = repo.findById(roomid).get();
        ChatDTO dto = ChatDTO.tochatDTO(entity);
        return dto;
    }

    @Transactional
    public void chatCountup(int roomId) {
        ChatEntity entity =  repo.findById(roomId).get();
        entity.increaseUser();
        repo.save(entity);
    }
}
