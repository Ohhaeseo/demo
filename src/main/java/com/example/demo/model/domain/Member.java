package com.example.demo.model.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Builder;

// 패키지 선언: 이 클래스가 속한 패키지를 정의합니다.
// com.example.demo.model.domain 패키지에 속한 클래스임을 나타냅니다.

import lombok.*; // Lombok 라이브러리의 모든 어노테이션을 자동으로 가져옵니다.
import jakarta.persistence.*; // JPA 관련 어노테이션을 위한 패키지. (javax의 후속 버전입니다.)

@Getter // Lombok 어노테이션으로, 모든 필드에 대한 getter 메서드를 자동 생성합니다.
// setter는 제공하지 않아서 무분별한 필드 변경을 방지합니다.

@Entity // JPA 어노테이션으로, 이 클래스가 DB 테이블과 매핑된 엔티티 클래스임을 나타냅니다.
@Table(name = "member") // 이 엔티티가 매핑될 테이블의 이름을 "article"로 지정합니다.
// 만약 이 어노테이션이 없으면 클래스 이름을 테이블 이름으로 사용하게 됩니다.

@NoArgsConstructor(access = AccessLevel.PROTECTED) 
// Lombok 어노테이션으로, 매개변수가 없는 기본 생성자를 자동 생성합니다.
// AccessLevel.PROTECTED는 외부에서 이 생성자를 호출하지 못하게 보호 수준을 설정하여 무분별한 객체 생성을 방지합니다.

public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 1씩 증가
    @Column(name = "id", updatable = false) // 수정 x
    private Long id;
    @Column(name = "name", nullable = false) // null x
    private String name = "";
    @Column(name = "email", unique = true, nullable = false) // unique 중복 x
    private String email = "";
    @Column(name = "password", nullable = false)
    private String password = "";
    @Column(name = "age", nullable = false)
    private String age = "";
    @Column(name = "mobile", nullable = false)
    private String mobile = "";
    @Column(name = "address", nullable = false)
    private String address = "";

    @Builder // 생성자에 빌더 패턴 적용(불변성)
    public Member(String name, String email, String password, String age, String mobile, String address)
    {
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.mobile = mobile;
        this.address = address;
    } 
}   