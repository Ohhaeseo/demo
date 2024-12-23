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
import com.example.demo.model.domain.Board;
import com.example.demo.model.repository.BoardRepository;
// BlogRepository를 임포트. 게시글 데이터를 저장하고 조회하는 리포지토리입니다.
import com.example.demo.model.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
// Lombok의 어노테이션으로, final로 선언된 필드를 매개변수로 받는 생성자를 자동으로 생성합니다.

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Service
@RequiredArgsConstructor // 생성자 자동 생성(부분)
// @Service는 이 클래스가 서비스 계층임을 나타냅니다.
// @RequiredArgsConstructor는 final 필드를 가진 생성자를 자동으로 생성합니다.

public class BlogService {
    @Autowired // 객체 주입 자동화, 생성자 1개면 생략 가능
    // Autowired 어노테이션은 스프링 컨테이너가 BlogRepository 객체를 자동으로 주입하게 합니다.
    // private final BlogRepository blogRepository; // 리포지토리 선언
    // BlogRepository 객체를 선언합니다. 데이터베이스와의 상호작용을 담당합니다.

    private final BoardRepository blogRepository; // 리포지토리 선언

    public void delete(Long id) {
        // 주어진 id의 게시글을 삭제하는 메서드입니다.
        blogRepository.deleteById(id);
        // 데이터베이스에서 해당 id의 게시글을 삭제합니다.
        // 만약 해당 id의 게시글이 없다면 아무 일도 일어나지 않습니다.
    }
    
    
    
    public List<Board> findAll() { 
        // 게시판 전체 목록 조회 메서드
        // 데이터베이스에서 모든 게시글(Board)을 조회하여 반환합니다.
        return blogRepository.findAll(); 
        // blogRepository의 findAll() 메서드를 호출하여 
        // 게시글 목록을 List<Board> 형태로 반환합니다.
    }
    
    public Optional<Board> findById(Long id) { 
        // 게시판 특정 글 조회 메서드
        // 주어진 ID를 사용하여 특정 게시글(Board)을 데이터베이스에서 조회합니다.
        // @param id - 조회할 게시글의 고유 ID
        // @return Optional<Board> - 조회된 게시글이 존재하면 해당 게시글을 포함한 Optional 객체 반환,
        // 없으면 비어 있는 Optional 객체 반환.
        return blogRepository.findById(id); 
        // blogRepository의 findById() 메서드를 호출하여 
        // ID에 해당하는 게시글을 조회하고 Optional로 감쌉니다.
    }
    

    public void update(Long id, AddArticleRequest request) {
        // 게시글 수정 메서드입니다.
        // id: 수정할 게시글의 고유 번호
        // request: 수정할 내용이 담긴 객체

        Optional<Board> optionalBoard = blogRepository.findById(id); 
        // 주어진 id로 게시글을 데이터베이스에서 찾습니다.
        // Optional은 게시글이 존재할 수도, 존재하지 않을 수도 있음을 나타냅니다.

        optionalBoard.ifPresent(board -> { 
            board.update(request.getTitle(), request.getContent(), request.getUser(), 
                        request.getNewdate(), request.getCount(), request.getLikec()); 
            blogRepository.save(board); 
            // 변경된 게시글을 데이터베이스에 저장합니다.
        });
        // 게시글이 존재하지 않으면 아무 작업도 하지 않고 메서드를 종료합니다.
    }

    public Board save(AddArticleRequest request){
        // 게시글 저장 로직을 처리하는 메서드입니다.
        // AddArticleRequest DTO를 받아 Article 엔티티로 변환 후 저장합니다.

        // DTO가 없는 경우 이곳에 직접 구현 가능
        // 아래는 DTO 없이 직접 파라미터로 title과 content를 받아 게시글을 생성하는 코드입니다.
        // public ResponseEntity<Article> addArticle(@RequestParam String title, @RequestParam String content) {
        // Article article = Article.builder()
        // .title(title)
        // .content(content)
        // .build();

        // 기본값 체크 및 초기화
        if (request.getCount() == null) request.setCount("0");
        if (request.getLikec() == null) request.setLikec("0");
        
        return blogRepository.save(request.toEntity());
        // request 객체의 toEntity() 메서드를 호출해 AddArticleRequest DTO를 Article 엔티티로 변환한 후, 이를 blogRepository의 save 메서드로 저장합니다.
        // 저장된 Article 엔티티를 반환합니다.
    }
    


    // 페이징 처리된 게시글 목록을 조회하는 메서드입니다.
    // @param pageable - 페이지 정보(페이지 번호, 페이지 크기 등)를 담고 있는 객체
    // @return Page<Board> - 페이징 처리된 게시글 목록을 반환
    public Page<Board> findAll(Pageable pageable) 
    {
        return blogRepository.findAll(pageable); // JpaRepository에서 제공하는 페이징 메서드 사용
    }

    // 키워드로 게시글을 검색하고 페이징 처리하는 메서드입니다.
    // @param keyword - 검색할 키워드 문자열
    // @param pageable - 페이지 정보(페이지 번호, 페이지 크기 등)를 담고 있는 객체
    // @return Page<Board> - 검색 결과를 페이징 처리하여 반환
    public Page<Board> searchByKeyword(String keyword, Pageable pageable) 
    {
        // findByTitleContainingIgnoreCase: 
        // - Title 필드에서 keyword를 포함하는 데이터를 검색
        // - IgnoreCase: 대소문자를 구분하지 않음
        // - Containing: SQL의 LIKE 검색과 동일 (%keyword%)
        return blogRepository.findByTitleContainingIgnoreCase(keyword, pageable);
    }
    

}

