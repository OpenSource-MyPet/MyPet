package com.example.mypet.entity;

import com.example.mypet.memberDTO.UserDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "User")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    private String id ;
    private String name;
    private String nickname ;
    private int age ;
    private String password ;

    public static UserEntity toUserEntity(UserDTO dto) {
        UserEntity entity = new UserEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setNickname(dto.getNickname());
        entity.setAge(dto.getAge());
        entity.setPassword(dto.getPassword());
        return entity;
    }
}
