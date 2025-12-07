package com.example.mypet.memberDTO;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AIVetFormDTO {
    private String name;
    private String type;
    private String detail;
    private Integer age;
    private MultipartFile image ;
    private String symptom;
    private String urgency ;
    private String base64;
    private String contentType;
}
