package com.example.demo.model.service;

import lombok.*; // Lombok 어노테이션 자동 생성
import com.example.demo.model.domain.Member; // Member 엔티티 임포트

@NoArgsConstructor // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 매개변수로 받는 생성자 자동 생성
@Data // getter, setter, equals, hashCode, toString 자동 생성
public class AddMemberRequest {

    private String name;
    private String email;
    private String password;
    private String age;
    private String mobile;
    private String address;

    // DTO -> Entity 변환 메서드
    public Member toEntity() 
    {
        return Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .age(age)
                .mobile(mobile)
                .address(address)
                .build();
    }
}
