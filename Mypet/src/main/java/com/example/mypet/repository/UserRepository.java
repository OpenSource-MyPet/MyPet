package com.example.mypet.repository;

import com.example.mypet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByLoginId(String loginId);      //아이디 중복 체크용
    Optional<User> findByLoginId(String loginId); //로그인 시 사용
}
