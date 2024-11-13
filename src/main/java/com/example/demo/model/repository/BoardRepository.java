package com.example.demo.model.repository;
// 패키지 선언: BlogRepository 인터페이스가 속해 있는 패키지를 정의합니다. 
// 이 경우 com.example.demo.model.repository 패키지에 속해 있습니다.


import org.springframework.data.jpa.repository.JpaRepository;
// 스프링 데이터 JPA의 JpaRepository 인터페이스를 가져옵니다. 
// 이 인터페이스는 기본적인 CRUD 작업을 포함하여 다양한 JPA 관련 메서드를 제공합니다.

import org.springframework.stereotype.Repository;
// @Repository 어노테이션을 사용하기 위해 가져옵니다. 
// 이는 해당 인터페이스가 데이터 접근 객체(DAO)임을 나타냅니다.

import com.example.demo.model.domain.Board;
// 이 엔터티는 데이터베이스의 테이블과 매핑된 클래스입니다.

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Repository
// @Repository 어노테이션: 이 인터페이스가 스프링 컨텍스트에서 
// 데이터베이스에 접근하는 객체로 사용된다는 것을 나타냅니다.
// 스프링은 이 어노테이션을 통해 런타임에 예외 처리를 위한 트랜잭션을 관리합니다.

// public interface BlogRepository extends JpaRepository<Article, Long>{
// // BlogRepository는 JpaRepository를 확장(상속)하여 Article 엔터티에 대한 데이터베이스 접근 메서드를 자동으로 제공합니다.
// // 첫 번째 제네릭 타입인 Article은 이 레포지토리가 관리할 엔터티 클래스입니다.
// // 두 번째 제네릭 타입인 Long은 Article 엔터티의 기본 키(Primary Key) 타입입니다.
// // 이 인터페이스는 스프링에 의해 자동으로 구현되며, 별도의 구현 없이 CRUD 작업을 사용할 수 있습니다.
// }

// public interface BoardRepository extends JpaRepository<Board, Long>{
//     // List<Article> findAll();
// }

public interface BoardRepository extends JpaRepository<Board, Long>{
Page<Board> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
    

