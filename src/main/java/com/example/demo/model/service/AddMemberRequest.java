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
    // 이름 필드: 공백 불가 및 특수문자 사용 제한
    @NotBlank(message = "이름은 공백일 수 없습니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣]*$", message = "이름은 특수문자를 포함할 수 없습니다.")
    private String name;

    // 이메일 필드: 공백 불가 및 이메일 형식 검증
    @NotBlank(message = "이메일은 공백일 수 없습니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private String email;

    // 비밀번호 필드: 공백 불가 및 복잡도 검증 (8자 이상, 대소문자 포함)
    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).{8,}$", 
            message = "비밀번호는 8자 이상이며, 대문자와 소문자를 모두 포함해야 합니다.")
    private String password;

    // 나이 필드: null 불가 및 19-99세 범위 제한
    @NotNull(message = "나이를 입력해주세요.")
    @Min(value = 19, message = "19세 이상만 가입 가능합니다.")
    @Max(value = 99, message = "99세 이하만 가입 가능합니다.")
    private Integer age;

    // 선택적 입력 필드들 (유효성 검증 없음)
    private String mobile;  // 휴대폰 번호
    private String address; // 주소

    // DTO를 엔티티로 변환하는 메서드
    // 회원가입 요청 데이터를 실제 DB에 저장할 Member 엔티티로 변환
    public Member toEntity() {
        return Member.builder()  // 빌더 패턴 사용
                .name(name)      // 이름 설정
                .email(email)    // 이메일 설정
                .password(password)  // 비밀번호 설정
                .age(age)        // 나이 설정
                .mobile(mobile)  // 휴대폰 번호 설정
                .address(address)  // 주소 설정
                .build();        // Member 객체 생성 및 반환
    }
}
