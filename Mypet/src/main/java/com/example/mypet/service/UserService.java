package com.example.mypet.service;

import com.example.mypet.entity.User;
import com.example.mypet.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //아이디 중복 여부(treu면 중복)
    public boolean isDuplicateLoginId(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    //회원 가입    
    public User register(User user) {
        //중복검사
        if (isDuplicateLoginId(user.getLoginId())) {
            //중복이면 에러
            //나중에 컨트롤러에서 이렇게 응답
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다: " + user.getLoginId());
        }
        
        //중복 아니면 DB저장
        return userRepository.save(user);
    }

    //로그인 시도
    public Optional<User> login(String loginId, String password) {
        return userRepository.findByLoginId(loginId)
                .filter(u -> u.getPassword().equals(password));
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
