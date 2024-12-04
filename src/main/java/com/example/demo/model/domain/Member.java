package com.example.demo.model.domain;

// 패키지 선언: 이 클래스가 속한 패키지를 정의합니다.
// com.example.demo.model.domain 패키지에 속한 클래스임을 나타냅니다.

import lombok.*; // Lombok 라이브러리의 모든 어노테이션을 자동으로 가져옵니다.
import jakarta.persistence.*; // JPA 관련 어노테이션을 위한 패키지. (javax의 후속 버전입니다.)
import jakarta.validation.constraints.*; // 유효성 검사를 위한 제약조건 어노테이션들을 가져옵니다.

@Getter // 모든 필드의 getter 메서드를 자동 생성합니다.
@Entity // 이 클래스가 JPA 엔티티임을 나타냅니다. DB 테이블과 매핑됩니다.
@Table(name = "member") // DB 테이블 이름을 "member"로 지정합니다.
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자를 protected로 생성합니다.
public class Member {
    @Id // 기본키(Primary Key) 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 전략 사용
    @Column(name = "id", updatable = false) // id 컬럼 매핑, 수정 불가
    private Long id;

    // 이름 필드: 공백 불가, 특수문자 사용 불가 (영문자와 한글만 허용)
    @NotBlank(message = "이름은 공백일 수 없습니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣]*$", message = "이름은 특수문자를 포함할 수 없습니다.")
    @Column(name = "name", nullable = false)
    private String name = "";

    // 이메일 필드: 공백 불가, 이메일 형식 검증, 중복 불가
    @NotBlank(message = "이메일은 공백일 수 없습니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    @Column(name = "email", unique = true, nullable = false)
    private String email = "";

    // 비밀번호 필드: 공백 불가, 8자 이상 & 대소문자 포함 필수
    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).{8,}$", 
            message = "비밀번호는 8자 이상이며, 대문자와 소문자를 모두 포함해야 합니다.")
    @Column(name = "password", nullable = false)
    private String password = "";

    // 나이 필드: null 불가, 19세 이상 99세 이하만 허용
    @NotNull(message = "나이를 입력해주세요.")
    @Min(value = 19, message = "19세 이상만 가입 가능합니다.")
    @Max(value = 99, message = "99세 이하만 가입 가능합니다.")
    @Column(name = "age", nullable = false)
    private Integer age;

    // 선택적 입력 필드들: 휴대폰 번호와 주소
    @Column(name = "mobile")
    private String mobile = "";

    @Column(name = "address")
    private String address = "";

    // 빌더 패턴을 사용한 생성자: 객체 생성을 더 유연하고 가독성 있게 만듭니다.
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