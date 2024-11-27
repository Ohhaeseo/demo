package com.example.demo.model.service;

import lombok.*; // Lombok 어노테이션 자동 생성

import org.springframework.validation.annotation.Validated;

import com.example.demo.model.domain.Member; // Member 엔티티 임포트
import jakarta.validation.constraints.*; // 검증 어노테이션 추가

@Validated
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddMemberRequest {
    @NotBlank(message = "이름은 공백일 수 없습니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣]*$", message = "이름은 특수문자를 포함할 수 없습니다.")
    private String name;

    @NotBlank(message = "이메일은 공백일 수 없습니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).{8,}$", 
            message = "비밀번호는 8자 이상이며, 대문자와 소문자를 모두 포함해야 합니다.")
    private String password;

    @NotNull(message = "나이를 입력해주세요.")
    @Min(value = 19, message = "19세 이상만 가입 가능합니다.")
    @Max(value = 99, message = "99세 이하만 가입 가능합니다.")
    private Integer age;

    private String mobile;
    private String address;

    public Member toEntity() {
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
