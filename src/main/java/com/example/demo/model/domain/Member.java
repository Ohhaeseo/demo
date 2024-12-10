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
    // Member 클래스 선언
    // 데이터베이스의 'member' 테이블과 매핑되는 JPA 엔티티 클래스입니다.
    // 사용자 정보를 저장하고 관리하는 역할을 합니다.

    @Id // 기본 키(Primary Key)로 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    // 기본 키 값 자동 생성. 데이터베이스에서 자동으로 증가하는 값을 사용합니다.
    @Column(name = "id", updatable = false) 
    // "id" 컬럼과 매핑. 수정할 수 없는(updatable = false) 필드로 설정하여 변경을 방지합니다.
    private Long id; 
    // 회원 고유 번호(ID)를 저장하는 필드입니다.

    // 이름 필드 설정
    @NotBlank(message = "이름은 공백일 수 없습니다.") 
    // 유효성 검증: 값이 공백이 아니어야 함.
    @Pattern(regexp = "^[a-zA-Z가-힣]*$", message = "이름은 특수문자를 포함할 수 없습니다.")
    // 유효성 검증: 이름은 영문자와 한글만 허용합니다. 특수문자나 숫자는 허용되지 않습니다.
    @Column(name = "name", nullable = false)
    // "name" 컬럼과 매핑. null 값을 허용하지 않습니다.
    private String name = ""; 
    // 회원 이름을 저장하는 필드입니다. 기본값으로 빈 문자열을 설정합니다.

    // 이메일 필드 설정
    @NotBlank(message = "이메일은 공백일 수 없습니다.")
    // 유효성 검증: 값이 공백이 아니어야 함.
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    // 유효성 검증: 값이 유효한 이메일 형식이어야 함.
    @Column(name = "email", unique = true, nullable = false)
    // "email" 컬럼과 매핑. 값이 고유해야 하며(null 허용 불가), 중복을 허용하지 않음.
    private String email = ""; 
    // 회원 이메일을 저장하는 필드입니다. 기본값으로 빈 문자열을 설정합니다.

    // 비밀번호 필드 설정
    @NotBlank(message = "비밀번호는 공백일 수 없습니다.") 
    // 유효성 검증: 값이 공백이 아니어야 함.
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).{8,}$", 
            message = "비밀번호는 8자 이상이며, 대문자와 소문자를 모두 포함해야 합니다.")
    // 유효성 검증: 비밀번호는 최소 8자 이상이며, 대문자와 소문자를 하나씩 포함해야 함.
    @Column(name = "password", nullable = false)
    // "password" 컬럼과 매핑. null 값을 허용하지 않습니다.
    private String password = ""; 
    // 회원 비밀번호를 저장하는 필드입니다. 기본값으로 빈 문자열을 설정합니다.

    // 나이 필드 설정
    @NotNull(message = "나이를 입력해주세요.") 
    // 유효성 검증: 값이 null이 아니어야 함.
    @Min(value = 19, message = "19세 이상만 가입 가능합니다.") 
    // 유효성 검증: 나이가 최소 19세 이상이어야 함.
    @Max(value = 99, message = "99세 이하만 가입 가능합니다.") 
    // 유효성 검증: 나이가 최대 99세 이하여야 함.
    @Column(name = "age", nullable = false)
    // "age" 컬럼과 매핑. null 값을 허용하지 않습니다.
    private Integer age; 
    // 회원 나이를 저장하는 필드입니다.

    // 선택 입력 필드 설정
    @Column(name = "mobile")
    // "mobile" 컬럼과 매핑. null 값을 허용합니다.
    private String mobile = ""; 
    // 회원 휴대폰 번호를 저장하는 필드입니다. 기본값으로 빈 문자열을 설정합니다.

    @Column(name = "address")
    // "address" 컬럼과 매핑. null 값을 허용합니다.
    private String address = ""; 
    // 회원 주소를 저장하는 필드입니다. 기본값으로 빈 문자열을 설정합니다.

    @Builder
    // Lombok의 @Builder 어노테이션: 빌더 패턴을 적용하여 객체 생성을 유연하게 만듭니다.
    public Member(String name, String email, String password, Integer age, String mobile, String address) {
        // 매개변수로 전달된 값을 통해 객체를 생성합니다.
        this.name = name; 
        // 매개변수 name의 값을 필드 name에 할당합니다.
        this.email = email; 
        // 매개변수 email의 값을 필드 email에 할당합니다.
        this.password = password; 
        // 매개변수 password의 값을 필드 password에 할당합니다.
        this.age = age; 
        // 매개변수 age의 값을 필드 age에 할당합니다.
        this.mobile = mobile; 
        // 매개변수 mobile의 값을 필드 mobile에 할당합니다.
        this.address = address; 
        // 매개변수 address의 값을 필드 address에 할당합니다.
    }
}

