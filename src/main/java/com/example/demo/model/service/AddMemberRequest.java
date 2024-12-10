package com.example.demo.model.service;

// Lombok 라이브러리의 어노테이션들을 사용하기 위한 임포트
import lombok.*; 

// 유효성 검증을 위한 Validated 어노테이션 임포트
import org.springframework.validation.annotation.Validated;

// Member 엔티티 클래스 임포트 
import com.example.demo.model.domain.Member;
// 데이터 유효성 검증을 위한 제약조건 어노테이션들 임포트
import jakarta.validation.constraints.*;

@Validated // 이 클래스에 유효성 검증 기능 활성화
@NoArgsConstructor // 매개변수 없는 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 매개변수로 받는 생성자 자동 생성  
@Data // getter, setter, toString 등 기본 메서드 자동 생성
public class AddMemberRequest {
    // AddMemberRequest 클래스 선언
    // 이 클래스는 회원 가입 요청 데이터를 담는 DTO(Data Transfer Object)입니다.
    // 클라이언트에서 전달받은 데이터를 엔티티(Member)로 변환하거나, 유효성 검증을 수행하는 역할을 합니다.

    // 이름 필드
    @NotBlank(message = "이름은 공백일 수 없습니다.")
    // 유효성 검증: 값이 공백이 아니어야 합니다.
    @Pattern(regexp = "^[a-zA-Z가-힣]*$", message = "이름은 특수문자를 포함할 수 없습니다.")
    // 유효성 검증: 이름은 영문자(a-z, A-Z)와 한글(가-힣)만 허용합니다. 숫자나 특수문자는 허용되지 않습니다.
    private String name; 
    // 사용자의 이름을 저장하는 필드입니다.

    // 이메일 필드
    @NotBlank(message = "이메일은 공백일 수 없습니다.")
    // 유효성 검증: 값이 공백이 아니어야 합니다.
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    // 유효성 검증: 값이 유효한 이메일 형식이어야 합니다.
    private String email; 
    // 사용자의 이메일을 저장하는 필드입니다.

    // 비밀번호 필드
    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    // 유효성 검증: 값이 공백이 아니어야 합니다.
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).{8,}$", 
            message = "비밀번호는 8자 이상이며, 대문자와 소문자를 모두 포함해야 합니다.")
    // 유효성 검증: 비밀번호는 최소 8자 이상이며, 소문자와 대문자가 적어도 하나씩 포함되어야 합니다.
    private String password; 
    // 사용자의 비밀번호를 저장하는 필드입니다.

    // 나이 필드
    @NotNull(message = "나이를 입력해주세요.")
    // 유효성 검증: 값이 null이 아니어야 합니다.
    @Min(value = 19, message = "19세 이상만 가입 가능합니다.")
    // 유효성 검증: 나이는 최소 19세 이상이어야 합니다.
    @Max(value = 99, message = "99세 이하만 가입 가능합니다.")
    // 유효성 검증: 나이는 최대 99세 이하여야 합니다.
    private Integer age; 
    // 사용자의 나이를 저장하는 필드입니다.

    // 선택적 입력 필드들
    private String mobile;  
    // 사용자의 휴대폰 번호를 저장하는 필드입니다. 유효성 검증은 적용되지 않습니다.

    private String address; 
    // 사용자의 주소를 저장하는 필드입니다. 유효성 검증은 적용되지 않습니다.

    // DTO를 엔티티로 변환하는 메서드
    public Member toEntity() {
        // 클라이언트에서 전달받은 요청 데이터를 Member 엔티티 객체로 변환합니다.
        return Member.builder()  // 빌더 패턴을 사용하여 Member 객체를 생성합니다.
                .name(name)      // DTO의 name 값을 Member 엔티티의 name 필드에 설정.
                .email(email)    // DTO의 email 값을 Member 엔티티의 email 필드에 설정.
                .password(password)  // DTO의 password 값을 Member 엔티티의 password 필드에 설정.
                .age(age)        // DTO의 age 값을 Member 엔티티의 age 필드에 설정.
                .mobile(mobile)  // DTO의 mobile 값을 Member 엔티티의 mobile 필드에 설정.
                .address(address)  // DTO의 address 값을 Member 엔티티의 address 필드에 설정.
                .build();        // Member 객체를 생성하고 반환합니다.
    }
}

