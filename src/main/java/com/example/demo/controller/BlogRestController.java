package com.example.demo.controller;

// 패키지 선언: 이 클래스가 속한 패키지를 정의합니다.
// com.example.demo.controller 패키지에 속해 있는 클래스임을 나타냅니다.

import com.example.demo.model.domain.Article;
import com.example.demo.model.service.AddArticleRequest;
import com.example.demo.model.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// 필요한 클래스와 라이브러리를 임포트합니다:
// - Article: 게시글을 나타내는 도메인 클래스입니다.
// - AddArticleRequest: 게시글 추가 요청을 처리하는 DTO 클래스입니다.
// - BlogService: 게시글 관련 비즈니스 로직을 처리하는 서비스입니다.
// - RequiredArgsConstructor: final 필드에 대해 생성자를 자동 생성해주는 Lombok 애노테이션입니다.
// - HttpStatus와 ResponseEntity: HTTP 응답 코드를 관리하고, 응답 데이터를 전달하기 위한 클래스들입니다.
// - RestController, GetMapping, PostMapping 등: Spring Web 관련 애노테이션입니다.

@RequiredArgsConstructor
@RestController // @Controller와 @ResponseBody를 결합한 애노테이션으로, JSON 또는 XML 형식으로 데이터를 반환하는 컨트롤러임을 나타냅니다.
public class BlogRestController {
    private final BlogService blogService;
    // BlogService 객체를 주입받습니다. 이 서비스는 게시글의 비즈니스 로직을 처리합니다.
    // @RequiredArgsConstructor에 의해 생성자가 자동으로 생성됩니다.

    // 주석 처리된 부분: 게시글을 추가하는 API입니다.
    // 이 메서드는 HTTP POST 요청을 받으며, 클라이언트로부터 게시글 데이터를 받아서 저장한 후, 저장된 게시글을 응답합니다.

    // @PostMapping("/api/articles")
    // public ResponseEntity<Article> addArticle(@ModelAttribute AddArticleRequest request) {
    //     // 요청 데이터를 받아서 서비스로 전달
    //     Article savedArticle = blogService.save(request);
    //     // HTTP 상태 코드 201 (Created)와 함께 저장된 게시글을 응답으로 반환
    //     return ResponseEntity.status(HttpStatus.CREATED)
    //                          .body(savedArticle);
    // }

    @GetMapping("/favicon.ico")
    public void favicon() {
        // 아무 작업도 하지 않음: 웹 브라우저가 자동으로 요청하는 favicon.ico 파일에 대한 응답을 처리하지 않습니다.
        // 빈 메서드를 추가하여 불필요한 에러 로그 생성을 방지합니다.
    }
}

