package com.example.mypet.service;

import com.example.mypet.controller.LoginController;
import com.example.mypet.entity.UserEntity;
import com.example.mypet.memberDTO.UserDTO;
import com.example.mypet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {
    UserRepository repo ;

    @Autowired
    public LoginService(UserRepository repo){
        this.repo = repo ;
    }

    public void adduser(UserDTO dto) {
        UserEntity entity = UserEntity.toUserEntity(dto);
        repo.save(entity);
    }

    public String checkId(String id) {
        Optional<UserEntity> entity = repo.findById(id);
        if(entity.isEmpty()){
            return "ok";
        }
        else{
            return "duplicate";
        }
    }

    public UserDTO checkUser(UserDTO dto) {
        String id = dto.getId();
        String password = dto.getPassword();
        Optional<UserEntity> entity = repo.findById(id);
        if(entity.isEmpty()){
            return null;
        }
        else{
            String checkPW = entity.get().getPassword();
            if(checkPW.equals(password)){
                return UserDTO.toUserDTO(entity.get());
            }
            return null;
        }


    }
}
