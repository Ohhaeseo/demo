package com.example.demo.model.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.model.domain.Member;
import com.example.demo.model.repository.MemberRepository;
import com.example.demo.model.service.AddMemberRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional // 클래스 내 모든 메서드에 트랜잭션 적용
public class MemberService {

    private final MemberRepository memberRepository; // 의존성 주입
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화

    // 회원 저장 메서드
    public Member saveMember(AddMemberRequest request) {
        validateDuplicateMember(request); // 이메일 중복 체크
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(encodedPassword); // 암호화된 비밀번호 설정
        return memberRepository.save(request.toEntity()); // 저장
    }

    // 이메일 중복 확인 메서드
    private void validateDuplicateMember(AddMemberRequest request) {
        Member findMember = memberRepository.findByEmail(request.getEmail()); // 이메일로 회원 조회
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다."); // 예외 발생
        }
    }

    public Member loginCheck(String email, String password) {
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            throw new IllegalArgumentException("회원 정보가 존재하지 않습니다.");
        }
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return member;
    }
    
}
