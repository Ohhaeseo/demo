package com.example.demo.controller;

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
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Controller // Spring MVC 컨트롤러임을 나타내는 어노테이션
public class BlogController {

    @Autowired // BlogService 빈을 자동 주입
    BlogService blogService;

    // 게시글 목록을 페이징하여 조회하는 메서드
    // page: 현재 페이지 번호 (기본값 0)
    // keyword: 검색어 (기본값 빈 문자열)
    // session: 사용자 세션 정보
    @GetMapping("/board_list")
    public String board_list(Model model, @RequestParam(defaultValue = "0") int page, 
                           @RequestParam(defaultValue = "") String keyword, HttpSession session) {
        String userId = (String) session.getAttribute("userId"); // 세션에서 사용자 ID 가져오기
        String email = (String) session.getAttribute("email"); // 세션에서 이메일 가져오기
        if (userId == null) { // 로그인하지 않은 경우
            return "redirect:/member_login"; // 로그인 페이지로 리다이렉트
        }
        System.out.println("세션 userId: " + userId);

        int pageSize = 3; // 한 페이지당 보여줄 게시글 수
        PageRequest pageable = PageRequest.of(page, pageSize); // 페이징 정보 생성
        Page<Board> list;

        // 검색어 유무에 따른 게시글 조회
        if (keyword.isEmpty()) {
            list = blogService.findAll(pageable); // 전체 게시글 조회
        } else {
            list = blogService.searchByKeyword(keyword, pageable); // 검색어로 게시글 검색
        }

        int startNum = (page * pageSize) + 1; // 게시글 시작 번호 계산
        // 모델에 필요한 데이터 추가
        model.addAttribute("boards", list);
        model.addAttribute("totalPages", list.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("startNum", startNum);
        model.addAttribute("email", email);

        return "board_list"; // board_list 뷰 반환
    }

    // 게시글 상세 내용을 조회하는 메서드
    // id: 게시글 번호
    @GetMapping("/board_view/{id}")
    public String board_view(Model model, @PathVariable Long id, HttpSession session) {
        String email = (String) session.getAttribute("email"); // 세션에서 이메일 가져오기
        Optional<Board> list = blogService.findById(id); // ID로 게시글 조회
        if (list.isPresent()) { // 게시글이 존재하는 경우
            model.addAttribute("boards", list.get());
            model.addAttribute("email", email);
        } else { // 게시글이 없는 경우
            return "/error_page/article_error"; // 에러 페이지로 이동
        }
        return "board_view"; // board_view 뷰 반환
    }

    // 게시글 작성 페이지로 이동하는 메서드
    @GetMapping("/board_write")
    public String board_write(Model model, HttpSession session) {
        String email = (String) session.getAttribute("email"); // 세션에서 이메일 가져오기
        if (email == null) { // 로그인하지 않은 경우
            return "redirect:/member_login"; // 로그인 페이지로 리다이렉트
        }
        model.addAttribute("email", email);
        return "board_write"; // board_write 뷰 반환
    }

    // 새로운 게시글을 저장하는 메서드
    @PostMapping("/api/boards")
    public String addboards(@ModelAttribute AddArticleRequest request, HttpSession session) {
        String email = (String) session.getAttribute("email"); // 세션에서 이메일 가져오기
        if (email == null) { // 로그인하지 않은 경우
            return "redirect:/member_login"; // 로그인 페이지로 리다이렉트
        }
        request.setUser(email); // 작성자 정보 설정
        blogService.save(request); // 게시글 저장
        return "redirect:/board_list"; // 게시글 목록으로 리다이렉트
    }

    // 게시글 수정 페이지로 이동하는 메서드
    @GetMapping("/board_edit/{id}")
    public String board_edit(Model model, @PathVariable Long id) {
        Optional<Board> board = blogService.findById(id); // ID로 게시글 조회
        if (board.isPresent()) { // 게시글이 존재하는 경우
            model.addAttribute("board", board.get());
            return "board_edit"; // board_edit 뷰 반환
        } else { // 게시글이 없는 경우
            return "/error_page/board_error"; // 에러 페이지로 이동
        }
    }

    // 게시글을 수정하는 메서드
    @PutMapping("/api/board_edit/{id}")
    public String updateBoard(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
        blogService.update(id, request); // 게시글 수정
        return "redirect:/board_list"; // 게시글 목록으로 리다이렉트
    }

    // 게시글을 삭제하는 메서드
    @DeleteMapping("/api/board_delete/{id}")
    public String deleteBoard(@PathVariable Long id) {
        blogService.delete(id); // 게시글 삭제
        return "redirect:/board_list"; // 게시글 목록으로 리다이렉트
    }

    // ---------- 주석 처리된 이전 코드들 ----------

    /*
    // 이전 게시판 목록 관련 코드
    @GetMapping("/board_list")
    public String board_list(Model model) {
        List<Board> list = blogService.findAll();
        model.addAttribute("boards", list);
        return "board_list";
    }

    // 이전 게시글 목록 관련 코드
    @GetMapping("/article_list")
    public String article_list(Model model) {
        List<Article> list = blogService.findAll();
        model.addAttribute("articles", list);
        return "article_list";
    }

    // 이전 게시글 수정 관련 코드
    @GetMapping("api/board_edit/{id}")
    public String board_edit(Model model, @PathVariable Long id) {
        Optional<Board> list = blogService.findById(id);
        if (list.isPresent()) {
            model.addAttribute("boards", list.get());
        } else {
            return "/error_page/board_error";
        }
        return "board_edit";
    }

    // 이전 게시글 작성 관련 코드
    @GetMapping("/board_write")
    public String board_write() {
        return "board_write";
    }

    @PostMapping("/api/boards")
    public String addboards(@ModelAttribute AddArticleRequest request) {
        blogService.save(request);
        return "redirect:/board_list";
    }

    // 이전 게시판 페이징 관련 코드
    @GetMapping("/board_list")
    public String board_list(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "") String keyword) {
        PageRequest pageable = PageRequest.of(page, 3);
        Page<Board> list;

        if (keyword.isEmpty()) {
            list = blogService.findAll(pageable);
        } else {
            list = blogService.searchByKeyword(keyword, pageable);
        }
        model.addAttribute("boards", list);
        model.addAttribute("totalPages", list.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "board_list";
    }

    // 이전 게시글 CRUD 관련 코드
    @PostMapping("/api/articles")
    public String addArticle(@ModelAttribute AddArticleRequest request) {
        blogService.save(request);
        return "redirect:/article_list";
    }

    @GetMapping("/article_edit/{id}")
    public String article_edit(Model model, @PathVariable Long id) {
        Optional<Article> list = blogService.findById(id);
        if (list.isPresent()) {
            model.addAttribute("article", list.get());
        } else {
            return "/error_page/article_error";
        }
        return "article_edit";
    }

    @PutMapping("/api/article_edit/{id}")
    public String updateArticle(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
        blogService.update(id, request);
        return "redirect:/article_list";
    }

    @DeleteMapping("/api/article_delete/{id}")
    public String deleteArticle(@PathVariable Long id) {
        blogService.delete(id);
        return "redirect:/article_list";
    }
    */
}
