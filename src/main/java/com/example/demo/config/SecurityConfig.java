package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// BCryptPasswordEncoder 클래스를 가져옵니다. 비밀번호 암호화에 사용됩니다.

import org.springframework.security.crypto.password.PasswordEncoder;
// PasswordEncoder 인터페이스를 가져옵니다. 다양한 비밀번호 인코딩 방식을 정의할 수 있습니다.

import org.springframework.security.web.SecurityFilterChain;
// Spring Security에서 제공하는 SecurityFilterChain 클래스를 가져옵니다. 보안 필터 체인을 정의하는 데 사용됩니다.

@Configuration // 스프링 설정 클래스를 나타냅니다. 이 클래스는 Bean을 정의하는 데 사용됩니다.
@EnableWebSecurity // Spring Security를 활성화합니다. 보안 설정을 적용합니다.
public class SecurityConfig { // 보안 설정을 관리하는 클래스입니다.

    @Bean // 이 메서드가 Spring Bean으로 등록됨을 나타냅니다.
    // WebSecurityConfigurerAdapter 대신 SecurityFilterChain을 사용해 보안 설정을 정의합니다.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // HTTP 헤더 설정
                .headers(headers -> headers
                                .addHeaderWriter((request, response) -> {
                                    // XSS(크로스 사이트 스크립팅) 공격 방지를 위한 헤더 추가
                                    response.setHeader("X-XSS-Protection", "1; mode=block"); // XSS 보호 모드 활성화
                                })
                )
                // CSRF 보호 설정
                //.csrf(withDefaults()) // 기본 CSRF 보호 활성화(주석 처리됨)
                .csrf(csrf -> csrf.disable()) // CSRF 보호를 비활성화합니다. 필요에 따라 활성화해야 합니다.
                // 세션 관리 설정
                .sessionManagement(session -> session
                                .invalidSessionUrl("/session-expired") // 세션이 만료되었을 때 이동할 URL 설정
                                .maximumSessions(-1) // 사용자가 가질 수 있는 최대 세션 수를 1로 제한
                                .maxSessionsPreventsLogin(false) // 이미 로그인된 세션이 있으면 새로운 로그인을 차단
                );

        // HttpSecurity 설정을 바탕으로 SecurityFilterChain 객체를 생성하여 반환
        return http.build();
    }

    @Bean // 이 메서드가 Spring Bean으로 등록됨을 나타냅니다.
    public PasswordEncoder passwordEncoder() {
        // PasswordEncoder 인터페이스의 구현체로 BCryptPasswordEncoder를 반환
        // 비밀번호를 안전하게 암호화하기 위해 사용
        return new BCryptPasswordEncoder();
    }
}
