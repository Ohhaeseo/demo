package com.example.demo.model.service;

// Spring Security의 비밀번호 암호화 기능을 사용하기 위한 import
import org.springframework.security.crypto.password.PasswordEncoder;
// 스프링의 서비스 계층임을 나타내는 어노테이션을 위한 import
import org.springframework.stereotype.Service;
// 트랜잭션 처리를 위한 어노테이션 import
import org.springframework.transaction.annotation.Transactional;
// 유효성 검증을 위한 어노테이션 import
import org.springframework.validation.annotation.Validated;

// 회원 엔티티 클래스 import
import com.example.demo.model.domain.Member;
// 회원 저장소 인터페이스 import
import com.example.demo.model.repository.MemberRepository;
// 회원가입 요청 DTO import
import com.example.demo.model.service.AddMemberRequest;

// Jakarta EE의 유효성 검증 어노테이션 import
import jakarta.validation.Valid;
// Lombok의 생성자 자동 생성 어노테이션 import
import lombok.RequiredArgsConstructor;

@Service // 이 클래스를 스프링의 서비스 계층으로 표시
@Validated // 이 클래스에서 유효성 검증 기능 활성화
@RequiredArgsConstructor // final 필드에 대한 생성자 자동 생성
@Transactional // 모든 메서드에 트랜잭션 처리 적용
public class MemberService {

    // 회원 정보를 저장하고 조회하는 저장소 객체
    private final MemberRepository memberRepository;
    // 비밀번호 암호화를 위한 인코더 객체
    private final PasswordEncoder passwordEncoder;

    /**
     * 새로운 회원을 저장하는 메서드
     * @param request 회원가입 요청 정보를 담은 DTO
     * @return 저장된 회원 엔티티
     */
    public Member saveMember(@Valid AddMemberRequest request) {
        // 회원 저장 메서드
        // @param request - 회원가입 요청 정보를 담고 있는 DTO 객체
        // @return 저장된 회원(Member) 엔티티 객체
    
        validateDuplicateMember(request); 
        // 이메일 중복 여부를 검사하여 중복된 이메일이 있으면 예외를 발생시킵니다.
    
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        // 비밀번호 암호화
        // 회원가입 요청 객체에서 비밀번호를 가져와 암호화(BCrypt)하여 저장합니다.
    
        request.setPassword(encodedPassword);
        // 암호화된 비밀번호를 회원가입 요청 객체에 다시 설정합니다.
    
        return memberRepository.save(request.toEntity());
        // 요청 DTO를 엔티티로 변환한 후 데이터베이스에 저장하고 저장된 엔티티를 반환합니다.
    }
    
    /**
     * 이메일 중복을 확인하는 private 메서드
     * @param request 회원가입 요청 정보
     * @throws IllegalStateException 이메일이 이미 존재할 경우 예외 발생
     */
    private void validateDuplicateMember(AddMemberRequest request) {
        Member findMember = memberRepository.findByEmail(request.getEmail());
        // 이메일을 사용하여 데이터베이스에서 회원을 조회합니다.
    
        if (findMember != null) {
            // 동일한 이메일로 이미 가입된 회원이 존재할 경우 예외를 발생시킵니다.
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }
    
    /**
     * 로그인 검증을 수행하는 메서드
     * @param email 사용자 이메일
     * @param password 사용자 비밀번호
     * @return 인증된 회원(Member) 엔티티 객체
     * @throws IllegalArgumentException 인증 실패 시 예외 발생
     */
    public Member loginCheck(String email, String password) {
        // 로그인 검증 메서드
        // @param email - 로그인 시 입력받은 이메일
        // @param password - 로그인 시 입력받은 비밀번호
        // @return 인증에 성공한 회원(Member) 엔티티 객체
    
        Member member = memberRepository.findByEmail(email);
        // 이메일을 사용하여 데이터베이스에서 회원을 조회합니다.
    
        if (member == null) {
            // 조회 결과가 없으면 예외를 발생시킵니다.
            throw new IllegalArgumentException("회원 정보가 존재하지 않습니다.");
        }
    
        if (!passwordEncoder.matches(password, member.getPassword())) {
            // 입력된 비밀번호와 데이터베이스에 저장된 암호화된 비밀번호를 비교합니다.
            // 일치하지 않을 경우 예외를 발생시킵니다.
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    
        return member;
        // 인증이 성공하면 조회된 회원 엔티티를 반환합니다.
    }
    
    
}
