package com.example.demo.model.domain;

// 패키지 선언: 이 클래스가 속한 패키지를 정의합니다.
// com.example.demo.model.domain 패키지에 속한 클래스임을 나타냅니다.

import lombok.*; // Lombok 라이브러리의 모든 어노테이션을 자동으로 가져옵니다.
import jakarta.persistence.*; // JPA 관련 어노테이션을 위한 패키지. (javax의 후속 버전입니다.)

@Getter // Lombok 어노테이션으로, 모든 필드에 대한 getter 메서드를 자동 생성합니다.
// setter는 제공하지 않아서 무분별한 필드 변경을 방지합니다.

@Entity // JPA 어노테이션으로, 이 클래스가 DB 테이블과 매핑된 엔티티 클래스임을 나타냅니다.
@Table(name = "Board") // 이 엔티티가 매핑될 테이블의 이름을 "article"로 지정합니다.
// 만약 이 어노테이션이 없으면 클래스 이름을 테이블 이름으로 사용하게 됩니다.

@NoArgsConstructor(access = AccessLevel.PROTECTED) 
// Lombok 어노테이션으로, 매개변수가 없는 기본 생성자를 자동 생성합니다.
// AccessLevel.PROTECTED는 외부에서 이 생성자를 호출하지 못하게 보호 수준을 설정하여 무분별한 객체 생성을 방지합니다.

public class Board {
    @Id // 이 필드가 테이블의 기본 키(Primary Key)임을 나타냅니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    // 기본 키 값을 자동으로 생성합니다. IDENTITY 전략은 DB가 자동으로 1씩 증가시키는 방식을 사용합니다.
    @Column(name = "id", updatable = false) 
    // 이 필드는 DB의 "id" 열과 매핑됩니다. 이 필드는 수정할 수 없도록(updatable = false) 설정되었습니다.
    private Long id;

    @Column(name = "content", nullable = false)
    // "content" 열과 매핑되며, 역시 null 값을 허용하지 않습니다.
    private String content = "";

    @Column(name = "title", nullable = false) 
    // "title" 열과 매핑되며, null 값을 허용하지 않습니다.
    private String title = "";


    @Column(name = "user", nullable = false)
    private String user = "";

    @Column(name = "newdate", nullable = false)
    private String newdate = "";

    @Column(name = "count", nullable = false)
    // private String count = "";
    private String count = "0";
    
    @Column(name = "likec", nullable = false)
    private String likec = "";

    @Builder 
    // Lombok의 @Builder 어노테이션을 사용해 빌더 패턴을 적용한 생성자를 자동으로 생성합니다.
    // 빌더 패턴은 객체 생성 시 가독성을 높이고, 필수 값과 선택 값을 분리해 유연하게 객체를 생성할 수 있습니다.

    public Board(String content, String title, String user, String newdate, String count, String likec) {
        // 생성자: Board 객체를 생성할 때 초기값을 설정합니다.
        // 매개변수로 전달된 값들을 필드에 할당하여 새로운 Board 객체를 초기화합니다.
        
        this.content = content; // 전달된 content 값을 현재 객체의 content 필드에 할당.
        this.title = title;     // 전달된 title 값을 현재 객체의 title 필드에 할당.
        this.user = user;       // 전달된 user 값을 현재 객체의 user 필드에 할당.
        this.newdate = newdate; // 전달된 newdate 값을 현재 객체의 newdate 필드에 할당.
        this.count = count;     // 전달된 count 값을 현재 객체의 count 필드에 할당.
        this.likec = likec;     // 전달된 likec 값을 현재 객체의 likec 필드에 할당.
    }
    

    public void update(String content, String title, String user, String newdate, String count, String likec) {
        // update 메서드: 기존 Board 객체의 필드 값을 새 값으로 업데이트합니다.
        // 객체의 상태를 수정하기 위해 사용됩니다.
        
        this.content = content; // 전달된 content 값으로 현재 객체의 content 필드 값을 변경.
        this.title = title;     // 전달된 title 값으로 현재 객체의 title 필드 값을 변경.
        this.user = user;       // 전달된 user 값으로 현재 객체의 user 필드 값을 변경.
        this.newdate = newdate; // 전달된 newdate 값으로 현재 객체의 newdate 필드 값을 변경.
        this.count = count;     // 전달된 count 값으로 현재 객체의 count 필드 값을 변경.
        this.likec = likec;     // 전달된 likec 값으로 현재 객체의 likec 필드 값을 변경.
    }
    
}
