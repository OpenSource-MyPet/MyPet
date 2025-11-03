package com.example.mypet.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;	//pk

    @Column(nullable = false, unique = true)
    private String loginId;	//로그인 아이디(중복x)

    @Column(nullable = false)
    private String password;  //비밀번호(암호화x)

    @Column(nullable = false)
    private String name;      //이름(실명임)

    @Column(nullable = false)
    private Integer birthYear; //생년(ex.2004)

    private String nickname;  //닉네임(중복 허용)인데 선택사항으로 할지 필수로 할지 띵킹

    //getter setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLoginId() { return loginId; }
    public void setLoginId(String loginId) { this.loginId = loginId; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getBirthYear() { return birthYear; }
    public void setBirthYear(Integer birthYear) { this.birthYear = birthYear; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
}
