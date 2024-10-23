package com.example.demo.model.service;

// 패키지 선언: 이 클래스는 com.example.demo.model.service 패키지에 속해 있습니다.
// 서비스 계층에서 비즈니스 로직을 처리하는 역할을 담당합니다.

import java.util.List;
// java.util 패키지에서 List 인터페이스를 임포트합니다. 게시글 목록을 관리할 때 사용됩니다.

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
// 스프링 프레임워크에서 의존성 주입(Dependency Injection)을 위해 사용하는 어노테이션입니다.

import org.springframework.stereotype.Service;
// 스프링이 이 클래스를 서비스 레이어의 Bean으로 인식하도록 지정하는 어노테이션입니다.

import com.example.demo.model.domain.Article;
// Article 엔티티 클래스 임포트. 서비스에서 이를 사용해 데이터베이스와 상호작용합니다.

import com.example.demo.model.repository.BlogRepository;
// BlogRepository를 임포트. 게시글 데이터를 저장하고 조회하는 리포지토리입니다.

import lombok.RequiredArgsConstructor;
// Lombok의 어노테이션으로, final로 선언된 필드를 매개변수로 받는 생성자를 자동으로 생성합니다.

@Service
@RequiredArgsConstructor // 생성자 자동 생성(부분)
// @Service는 이 클래스가 서비스 계층임을 나타냅니다.
// @RequiredArgsConstructor는 final 필드를 가진 생성자를 자동으로 생성합니다.

public class BlogService {
    @Autowired // 객체 주입 자동화, 생성자 1개면 생략 가능
    // Autowired 어노테이션은 스프링 컨테이너가 BlogRepository 객체를 자동으로 주입하게 합니다.
    private final BlogRepository blogRepository; // 리포지토리 선언
    // BlogRepository 객체를 선언합니다. 데이터베이스와의 상호작용을 담당합니다.

    public List<Article> findAll() { // 게시판 전체 목록 조회
        // findAll 메서드는 BlogRepository의 findAll 메서드를 호출하여 모든 게시글을 조회합니다.
        return blogRepository.findAll();
        // 조회된 게시글 목록을 반환합니다.
    }

    public Article save(AddArticleRequest request){
        // 게시글 저장 로직을 처리하는 메서드입니다.
        // AddArticleRequest DTO를 받아 Article 엔티티로 변환 후 저장합니다.

        // DTO가 없는 경우 이곳에 직접 구현 가능
        // 아래는 DTO 없이 직접 파라미터로 title과 content를 받아 게시글을 생성하는 코드입니다.
        // public ResponseEntity<Article> addArticle(@RequestParam String title, @RequestParam String content) {
        // Article article = Article.builder()
        // .title(title)
        // .content(content)
        // .build();
        
        return blogRepository.save(request.toEntity());
        // request 객체의 toEntity() 메서드를 호출해 AddArticleRequest DTO를 Article 엔티티로 변환한 후, 이를 blogRepository의 save 메서드로 저장합니다.
        // 저장된 Article 엔티티를 반환합니다.
    }

    public void update(Long id, AddArticleRequest request) {
        Optional<Article> optionalArticle = blogRepository.findById(id); // 단일 글 조회
        optionalArticle.ifPresent(article -> { // 값이 있으면
            article.update(request.getTitle(), request.getContent()); // 값을 수정
            blogRepository.save(article); // Article 객체에 저장
        });
    }

    public Optional<Article> findById(Long id) {
        return blogRepository.findById(id);
    }

    public void delete(Long id) {
        blogRepository.deleteById(id);
    }
    
}
