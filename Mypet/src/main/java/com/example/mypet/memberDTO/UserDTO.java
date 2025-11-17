package com.example.mypet.memberDTO;

import com.example.mypet.entity.UserEntity;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
    private String id ;
    private String name ;
    private String nickname;
    private int age;
    private String password;

    public static UserDTO toUserDTO(UserEntity entity) {
        UserDTO dto = new UserDTO() ;
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setNickname(entity.getNickname());
        dto.setAge(entity.getAge());
        dto.setPassword(entity.getPassword());
        return dto;
    }
}
