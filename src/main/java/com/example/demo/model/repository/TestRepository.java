package com.example.demo.model.repository;

// 패키지 선언: 이 클래스가 속한 패키지를 정의합니다.
// com.example.demo.model.repository 패키지에 속한 인터페이스임을 나타냅니다.

import org.springframework.data.jpa.repository.JpaRepository;
// Spring Data JPA에서 제공하는 JpaRepository를 임포트합니다.
// JpaRepository는 기본적인 CRUD(생성, 조회, 수정, 삭제) 메서드를 제공하는 인터페이스입니다.

import org.springframework.stereotype.Repository;
// 이 인터페이스를 Spring의 레포지토리로 인식시키기 위해 사용되는 어노테이션입니다.
// 이 어노테이션은 클래스나 인터페이스가 데이터 액세스 계층에 속함을 나타냅니다.

import com.example.demo.model.domain.TestDB;
// 우리가 앞서 만든 TestDB 엔티티를 가져옵니다. 이 엔티티와 연동하여 데이터를 처리합니다.

@Repository
// Spring에서 이 인터페이스가 레포지토리임을 선언합니다. 이를 통해 Spring이 알아서 구현체를 생성하고 의존성 주입을 할 수 있습니다.

public interface TestRepository extends JpaRepository<TestDB, Long> {
// 인터페이스 선언: TestRepository는 JpaRepository를 상속받습니다.
// JpaRepository<TestDB, Long>은 TestDB 엔티티를 관리하며, 그 기본 키의 타입은 Long입니다.
// JpaRepository는 기본적인 데이터 조작 메서드를 자동으로 제공합니다 (예: save(), findAll(), delete() 등).

    // Find all TestDB entities by a name
    TestDB findByName(String name);
    // 커스텀 메서드: 이름을 기준으로 TestDB 엔티티를 찾는 메서드입니다.
    // Spring Data JPA는 메서드 이름을 기반으로 자동으로 쿼리를 생성합니다.
    // 여기서는 `name` 필드의 값을 기준으로 엔티티를 검색합니다.
}
