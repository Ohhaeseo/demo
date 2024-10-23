package com.example.demo.model.domain;

// 패키지 선언: 이 클래스가 속한 패키지를 정의합니다.
// com.example.demo.model.domain 패키지에 속한 클래스임을 나타냅니다.

import jakarta.persistence.*; 
// JPA 관련 어노테이션을 위한 패키지를 불러옵니다. jakarta는 javax의 후속 버전입니다.

import lombok.Data; 
// Lombok의 @Data 어노테이션을 불러옵니다. 이 어노테이션은 getter, setter, toString, equals, hashCode 같은 메서드를 자동 생성해줍니다.

@Entity 
// JPA 어노테이션으로, 이 클래스가 DB 테이블과 매핑된 엔티티 클래스임을 나타냅니다.
// 즉, 이 클래스는 데이터베이스의 한 테이블을 표현합니다.

@Table(name = "testdb") 
// 이 엔티티가 매핑될 테이블의 이름을 "testdb"로 지정합니다.
// 기본적으로 클래스 이름이 테이블 이름이 되지만, 이렇게 명시적으로 설정할 수도 있습니다.

@Data 
// Lombok의 @Data 어노테이션을 사용하여 setter, getter, toString, equals, hashCode 메서드를 자동으로 생성합니다.
// 이를 통해 반복적인 코드 작성을 줄일 수 있습니다.

public class TestDB {
    @Id 
    // 이 필드가 테이블의 기본 키(Primary Key)임을 나타냅니다.
    
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    // 기본 키 값을 자동으로 생성하는 전략을 지정합니다. IDENTITY 전략은 데이터베이스가 자동으로 1씩 증가시키는 방식을 사용합니다.
    
    private Long id; 
    // 테이블의 "id" 열과 매핑되는 필드입니다. 이 필드는 Long 타입이며, 값이 없을 때 DB에서 자동으로 할당됩니다.

    @Column(nullable = true) 
    // 테이블의 "name" 열과 매핑됩니다. 이 열은 null 값을 허용하며, JPA에서 이를 명시적으로 설정합니다.
    
    private String name; 
    // "name" 열과 매핑되는 필드입니다. 이 필드는 String 타입으로, 데이터베이스에 name 값을 저장합니다.
}
