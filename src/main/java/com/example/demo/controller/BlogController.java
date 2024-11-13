package com.example.demo.controller;

// 패키지 선언: 이 클래스가 속한 패키지 경로를 명시합니다. 
// com.example.demo.controller 패키지에 속해 있는 클래스임을 나타냅니다.

import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.domain.Board;
import com.example.demo.model.domain.Article;
import com.example.demo.model.service.AddArticleRequest;
import com.example.demo.model.service.BlogService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


// 필요한 클래스와 인터페이스를 임포트: 
// - List: 게시글 목록을 다루기 위한 Java 리스트 클래스
// - Autowired: 의존성 주입을 위한 Spring 애노테이션
// - Controller: 이 클래스가 컨트롤러임을 나타냅니다.
// - Model: View에 데이터를 전달하기 위한 인터페이스
// - GetMapping: HTTP GET 요청을 처리하는 메서드를 정의
// - PostMapping: HTTP POST 요청을 처리하는 메서드를 정의
// - Article: 게시글 객체를 나타내는 도메인 클래스
// - AddArticleRequest: 게시글 추가 요청 데이터를 처리하기 위한 서비스 클래스
// - BlogService: 게시글 비즈니스 로직을 처리하는 서비스

@Controller
public class BlogController {
    // @Controller 애노테이션: 이 클래스가 Spring MVC 컨트롤러임을 나타냅니다. 
    // 이 컨트롤러는 사용자의 요청을 처리하고 그에 따른 응답을 반환합니다.

    @Autowired
    BlogService blogService;  
    // @Autowired: Spring이 자동으로 BlogService 객체를 주입해줍니다.
    // blogService는 게시글과 관련된 비즈니스 로직을 처리하는 서비스입니다.

    // @GetMapping("/board_list") // 새로운 게시판 링크 지정
    // public String board_list(Model model) {
    //     List<Board> list = blogService.findAll(); // 게시판 전체 리스트
    //     model.addAttribute("boards", list); // 모델에 추가
    //     return "board_list"; // .HTML 연결
    // }

    // 게시글 목록을 가져오는 메서드
    // @GetMapping("/board_list")
    // public String board_list(Model model) {
    //     // @GetMapping: HTTP GET 요청이 /article_list 경로로 들어오면 이 메서드가 호출됩니다.
    //     // 게시글 목록을 가져와서 사용자에게 보여주는 역할을 합니다.

    //     List<Board> list = blogService.findAll();  
    //     // blogService.findAll(): 서비스 계층에서 모든 게시글을 가져옵니다.
    //     // findAll() 메서드는 DB에서 모든 게시글을 조회해 List<Article> 형태로 반환합니다.

    //     model.addAttribute("boards", list);  
    //     // model.addAttribute(): 가져온 게시글 목록을 "articles"라는 이름으로 모델에 추가합니다.
    //     // 이는 뷰(HTML 파일)에서 사용할 수 있도록 데이터를 전달하는 역할을 합니다.

    //     return "board_list";  
    //     // "article_list"라는 이름의 Thymeleaf 템플릿을 반환합니다.
    //     // 이 템플릿은 src/main/resources/templates/article_list.html 파일에 대응합니다.
    // }

    

    @GetMapping("/board_view/{id}") // 게시판 링크 지정
    public String board_view(Model model, @PathVariable Long id) {
    Optional<Board> list = blogService.findById(id); // 선택한 게시판 글
    if (list.isPresent()) 
    {
        model.addAttribute("boards", list.get()); // 존재할 경우 실제 Article 객체를 모델에 추가
    } 
    else 
    {
    // 처리할 로직 추가 (예: 오류 페이지로 리다이렉트, 예외 처리 등)
        return "/error_page/article_error"; // 오류 처리 페이지로 연결
    }
        return "board_view"; // .HTML 연결
    }

    // @GetMapping("api/board_edit/{id}") // 게시판 링크 지정
    // public String board_edit(Model model, @PathVariable Long id) {
    // Optional<Board> list = blogService.findById(id); // 선택한 게시판 글
    // if (list.isPresent()) 
    // {
    //     model.addAttribute("boards", list.get()); // 존재할 경우 실제 Article 객체를 모델에 추가
    // } 
    // else 
    // {
    // // 처리할 로직 추가 (예: 오류 페이지로 리다이렉트, 예외 처리 등)
    //     return "/error_page/board_error"; // 오류 처리 페이지로 연결
    // }
    //     return "board_edit"; // .HTML 연결
    // }

     

    // @PutMapping("/api/board_edit/{id}")
    // public String updateArticle(@PathVariable Long id, @ModelAttribute AddArticleRequest request) 
    // {
    //     // @PutMapping: HTTP PUT 요청을 처리하여 게시글을 수정합니다
    //     // @PathVariable: URL의 id를 매개변수로 받습니다
    //     // @ModelAttribute: 폼 데이터를 AddArticleRequest 객체로 변환합니다
        
    //     blogService.update(id, request); // 게시글을 업데이트합니다
    //     return "redirect:/board_list"; // 수정 완료 후 목록 페이지로 리다이렉트합니다
    // }

    @PutMapping("/api/board_edit/{id}")
    public String updateBoard(@PathVariable Long id, @ModelAttribute AddArticleRequest request) 
    {
        // @PutMapping: HTTP PUT 요청을 처리하여 게시글을 수정합니다
        // @PathVariable: URL의 id를 매개변수로 받습니다
        // @ModelAttribute: 폼 데이터를 AddArticleRequest 객체로 변환합니다
        
        blogService.update(id, request); // 게시글을 업데이트합니다
        return "redirect:/board_list"; // 수정 완료 후 목록 페이지로 리다이렉트합니다
    }


    /*// 게시글 목록을 가져오는 메서드
    @GetMapping("/article_list")
    public String article_list(Model model) {
        // @GetMapping: HTTP GET 요청이 /article_list 경로로 들어오면 이 메서드가 호출됩니다.
        // 게시글 목록을 가져와서 사용자에게 보여주는 역할을 합니다.

        List<Article> list = blogService.findAll();  
        // blogService.findAll(): 서비스 계층에서 모든 게시글을 가져옵니다.
        // findAll() 메서드는 DB에서 모든 게시글을 조회해 List<Article> 형태로 반환합니다.

        model.addAttribute("articles", list);  
        // model.addAttribute(): 가져온 게시글 목록을 "articles"라는 이름으로 모델에 추가합니다.
        // 이는 뷰(HTML 파일)에서 사용할 수 있도록 데이터를 전달하는 역할을 합니다.

        return "article_list";  
        // "article_list"라는 이름의 Thymeleaf 템플릿을 반환합니다.
        // 이 템플릿은 src/main/resources/templates/article_list.html 파일에 대응합니다.
    }

    

    // 게시글 추가 메서드
    @PostMapping("/api/articles")
    public String addArticle(@ModelAttribute AddArticleRequest request) {
        // @PostMapping: HTTP POST 요청이 /api/articles 경로로 들어오면 이 메서드가 호출됩니다.
        // @ModelAttribute: HTML 폼에서 제출된 데이터를 AddArticleRequest 객체로 매핑합니다.
        // AddArticleRequest는 게시글의 제목과 내용을 포함한 데이터 요청을 처리하는 DTO입니다.

        blogService.save(request);
        // blogService.save(): 요청 데이터를 전달해 새로운 게시글을 저장합니다.
        // 서비스 계층에서 DB에 게시글을 저장하는 로직을 처리합니다.

        return "redirect:/article_list";  
        // 게시글이 저장된 후 사용자를 게시글 목록 페이지로 리디렉션합니다.
        // "redirect:"는 HTTP 리다이렉션을 의미하며, 새로운 요청으로 /article_list 경로로 이동시킵니다.
    }

    
    @GetMapping("/article_edit/{id}") // URL 경로에서 게시글 ID를 변수로 받습니다
    public String article_edit(Model model, @PathVariable Long id) {
        // @GetMapping: /article_edit/{id} 경로로 들어오는 GET 요청을 처리합니다
        // @PathVariable: URL 경로의 {id} 부분을 Long 타입의 id 매개변수로 받습니다
        
        Optional<Article> list = blogService.findById(id); 
        // blogService.findById(): 주어진 id로 게시글을 조회합니다
        // Optional<Article>을 반환하여 게시글이 존재하지 않을 수 있음을 표현합니다
        
        if (list.isPresent()) 
        {
            model.addAttribute("article", list.get()); 
            // 게시글이 존재하면 모델에 "article"이라는 이름으로 추가합니다
            // list.get()으로 Optional에서 실제 Article 객체를 꺼냅니다
        } 
        else 
        {
            return "/error_page/article_error"; 
            // 게시글이 존재하지 않으면 에러 페이지로 이동합니다
        }
        return "article_edit"; // article_edit.html 템플릿을 렌더링합니다
    }

    @PutMapping("/api/article_edit/{id}")
    public String updateArticle(@PathVariable Long id, @ModelAttribute AddArticleRequest request) 
    {
        // @PutMapping: HTTP PUT 요청을 처리하여 게시글을 수정합니다
        // @PathVariable: URL의 id를 매개변수로 받습니다
        // @ModelAttribute: 폼 데이터를 AddArticleRequest 객체로 변환합니다
        
        blogService.update(id, request); // 게시글을 업데이트합니다
        return "redirect:/article_list"; // 수정 완료 후 목록 페이지로 리다이렉트합니다
    }

    @DeleteMapping("/api/article_delete/{id}")
    public String deleteArticle(@PathVariable Long id) {
        // @DeleteMapping: HTTP DELETE 요청을 처리하여 게시글을 삭제합니다
        // @PathVariable: URL의 id를 매개변수로 받습니다
        
        blogService.delete(id); // 해당 id의 게시글을 삭제합니다
        return "redirect:/article_list"; // 삭제 완료 후 목록 페이지로 리다이렉트합니다
    }*/

    // 게시글 수정 화면으로 이동하는 매핑
    @GetMapping("/board_edit/{id}")
    public String board_edit(Model model, @PathVariable Long id) {
        Optional<Board> board = blogService.findById(id);
        if (board.isPresent()) {
            model.addAttribute("board", board.get());
            return "board_edit"; // 수정 페이지
        } else {
            return "/error_page/board_error"; // 오류 페이지로 연결
        }
    }

    // @PutMapping("/api/board_edit/{id}")
    // public String updateBoard(@PathVariable Long id, @ModelAttribute AddArticleRequest request) 
    // {
    //     // @PutMapping: HTTP PUT 요청을 처리하여 게시글을 수정합니다
    //     // @PathVariable: URL의 id를 매개변수로 받습니다
    //     // @ModelAttribute: 폼 데이터를 AddArticleRequest 객체로 변환합니다
        
    //     blogService.update(id, request); // 게시글을 업데이트합니다
    //     return "redirect:/board_list"; // 수정 완료 후 목록 페이지로 리다이렉트합니다
    // }

    //글쓰기 맵핑
    @GetMapping("/board_write")
    public String board_write() {
    return "board_write";
    }

    @PostMapping("/api/boards") // 글쓰기 게시판 저장
    public String addboards(@ModelAttribute AddArticleRequest request) {
    blogService.save(request);
    return "redirect:/board_list"; // .HTML 연결
    }

    // @GetMapping("/board_list") // 새로운 게시판 링크 지정
    // public String board_list(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "") String keyword) {
    // PageRequest pageable = PageRequest.of(page, 3); // 한 페이지의 게시글 수
    // Page<Board> list; // Page를 반환

    // if (keyword.isEmpty()) 
    // {
    //     list = blogService.findAll(pageable); // 기본 전체 출력(키워드 x)
    // } 
    // else 
    // {
    //     list = blogService.searchByKeyword(keyword, pageable); // 키워드로 검색
    // }
    // model.addAttribute("boards", list); // 모델에 추가
    // model.addAttribute("totalPages", list.getTotalPages()); // 페이지 크기
    // model.addAttribute("currentPage", page); // 페이지 번호
    // model.addAttribute("keyword", keyword); // 키워드
    //     return "board_list"; // .HTML 연결
    // }

    @DeleteMapping("/api/board_delete/{id}")
    public String deleteBoard(@PathVariable Long id) {
        // @DeleteMapping: HTTP DELETE 요청을 처리하여 게시글을 삭제합니다
        // @PathVariable: URL의 id를 매개변수로 받습니다
        
        blogService.delete(id); // 해당 id의 게시글을 삭제합니다
        return "redirect:/board_list"; // 삭제 완료 후 목록 페이지로 리다이렉트합니다
    }

    @GetMapping("/board_list") // 새로운 게시판 링크 지정
    public String board_list(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "") String keyword) {
        int pageSize = 3;  // 한 페이지에 표시할 게시글 수
        PageRequest pageable = PageRequest.of(page, pageSize);
        Page<Board> list; // Page를 반환

        if (keyword.isEmpty()) {
            list = blogService.findAll(pageable); // 기본 전체 출력(키워드 x)
        } else {
            list = blogService.searchByKeyword(keyword, pageable); // 키워드로 검색
        }

        int startNum = (page * pageSize) + 1; // 현재 페이지에서의 시작 글 번호
        model.addAttribute("boards", list); // 모델에 게시글 목록 추가
        model.addAttribute("totalPages", list.getTotalPages()); // 총 페이지 수
        model.addAttribute("currentPage", page); // 현재 페이지 번호
        model.addAttribute("keyword", keyword); // 키워드
        model.addAttribute("startNum", startNum); // 시작 번호

        return "board_list"; // 연결할 HTML 템플릿
    }



    
}




