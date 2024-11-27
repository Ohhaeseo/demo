package com.example.demo.model.domain;

// 패키지 선언: 이 클래스가 속한 패키지를 정의합니다.
// com.example.demo.model.domain 패키지에 속한 클래스임을 나타냅니다.

import lombok.*; // Lombok 라이브러리의 모든 어노테이션을 자동으로 가져옵니다.
import jakarta.persistence.*; // JPA 관련 어노테이션을 위한 패키지. (javax의 후속 버전입니다.)
import jakarta.validation.constraints.*;

@Getter
@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @NotBlank(message = "이름은 공백일 수 없습니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣]*$", message = "이름은 특수문자를 포함할 수 없습니다.")
    @Column(name = "name", nullable = false)
    private String name = "";

    @NotBlank(message = "이메일은 공백일 수 없습니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    @Column(name = "email", unique = true, nullable = false)
    private String email = "";

    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).{8,}$", 
            message = "비밀번호는 8자 이상이며, 대문자와 소문자를 모두 포함해야 합니다.")
    @Column(name = "password", nullable = false)
    private String password = "";

    @NotNull(message = "나이를 입력해주세요.")
    @Min(value = 19, message = "19세 이상만 가입 가능합니다.")
    @Max(value = 99, message = "99세 이하만 가입 가능합니다.")
    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "mobile")
    private String mobile = "";

    @Column(name = "address")
    private String address = "";

    @Builder
    public Member(String name, String email, String password, Integer age, String mobile, String address) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.mobile = mobile;
        this.address = address;
    }
}