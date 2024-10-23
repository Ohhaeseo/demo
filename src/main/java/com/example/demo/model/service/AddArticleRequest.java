package com.example.demo.model.service;

// 패키지 선언: 이 클래스는 com.example.demo.model.service 패키지에 속해있음을 나타냅니다.
// 서비스 레이어에 관련된 기능을 처리하는 클래스임을 의미합니다.

import lombok.*; // 어노테이션 자동 생성
// Lombok 라이브러리를 사용하여 반복되는 코드 (getter, setter, 생성자 등)를 자동으로 생성합니다.

import com.example.demo.model.domain.Article;
// AddArticleRequest 클래스에서 Article 엔티티를 사용하기 위해 Article 클래스를 임포트합니다.

@NoArgsConstructor // 기본 생성자 추가
// Lombok 어노테이션으로, 매개변수가 없는 기본 생성자를 자동으로 생성해 줍니다.

@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가
// 모든 필드(title, content)를 인자로 받는 생성자를 자동으로 생성해 줍니다.

@Data // getter, setter, toString, equals 등 자동 생성
// @Data는 Lombok에서 제공하는 강력한 어노테이션으로, getter, setter, toString, equals, hashCode 등을 자동으로 생성해줍니다.

public class AddArticleRequest {
// 게시글 작성 요청을 처리하기 위한 DTO(Data Transfer Object) 클래스입니다.

    private String title;
    // 게시글의 제목을 저장할 필드입니다.

    private String content;
    // 게시글의 내용을 저장할 필드입니다.

    // 불필요한 데이터 노출을 방지하기 위해 필요한 필드만 사용
    public Article toEntity() {
    // 사용자가 입력한 title과 content를 바탕으로 Article 엔티티 객체를 생성하는 메서드입니다.
    // 엔티티 변환을 담당하는 역할을 합니다.

        return Article.builder()  // Article 엔티티를 빌더 패턴을 사용해 생성합니다.
            .title(title)         // 이 요청 객체의 title 값을 Article의 title에 설정합니다.
            .content(content)     // 이 요청 객체의 content 값을 Article의 content에 설정합니다.
            .build();             // Article 객체를 생성합니다.
    }
}
