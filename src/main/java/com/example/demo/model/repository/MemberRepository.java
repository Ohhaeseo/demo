package com.example.demo.model.repository;
// 패키지 선언: MemberRepository 인터페이스가 속한 패키지를 정의합니다.

import org.springframework.data.jpa.repository.JpaRepository;
// JpaRepository를 상속받아 기본적인 CRUD 기능을 제공받기 위한 import문입니다.

import org.springframework.stereotype.Repository;
// @Repository 어노테이션을 사용하기 위한 import문입니다.

import com.example.demo.model.domain.Member;
// Member 엔티티 클래스를 사용하기 위한 import문입니다.

@Repository
// @Repository 어노테이션: 이 인터페이스가 데이터 접근 계층의 컴포넌트임을 나타냅니다.
// 스프링이 이 인터페이스를 빈으로 등록하고 관리하게 됩니다.

public interface MemberRepository extends JpaRepository<Member, Long> {
    // JpaRepository를 상속받아 Member 엔티티에 대한 기본적인 CRUD 연산을 제공합니다.
    // 제네릭 타입으로 <Member, Long>을 사용:
    // - Member: 이 레포지토리가 다룰 엔티티 타입
    // - Long: Member 엔티티의 기본키(ID) 타입

    Member findByEmail(String email); 
    // 이메일로 회원을 조회하는 메서드입니다.
    // Spring Data JPA의 메서드 이름 규칙을 따라 자동으로 쿼리가 생성됩니다.
    // findBy + Email(필드명)의 형태로, select * from member where email = ? 쿼리가 실행됩니다.
    // 반환 타입이 Member이므로 단일 회원 정보를 반환합니다.
}
