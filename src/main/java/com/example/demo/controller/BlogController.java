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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jakarta.servlet.http.Cookie;

@Controller // Spring MVC 컨트롤러임을 나타내는 어노테이션
public class BlogController {

    @Autowired // BlogService 빈을 자동 주입
    BlogService blogService;

    // 게시글 목록을 페이징하여 조회하는 메서드
    // page: 현재 페이지 번호 (기본값 0)
    // keyword: 검색어 (기본값 빈 문자열)
    // session: 사용자 세션 정보
    // 게시글 목록을 조회하는 메서드
    @GetMapping("/board_list") // GET 요청으로 "/board_list" URL에 매핑
    public String board_list(Model model, 
                            @RequestParam(defaultValue = "0") int page, // 쿼리 스트링에서 "page" 값. 기본값은 0.
                            @RequestParam(defaultValue = "") String keyword, // 검색 키워드. 기본값은 빈 문자열.
                            HttpSession session) { // 현재 사용자 세션을 가져옴
        String email = (String) session.getAttribute("email"); // 세션에서 사용자 이메일 정보 가져오기
        String userId = (String) session.getAttribute("userId_" + email); // 사용자별 고유 세션 ID 가져오기
        if (userId == null) { // 세션 ID가 없는 경우 (로그인되지 않음)
            return "redirect:/member_login"; // 로그인 페이지로 리다이렉트
        }
        System.out.println("세션 userId: " + userId); // 디버깅용 로그 출력
        

        int pageSize = 3; // 한 페이지당 보여줄 게시글 수
        PageRequest pageable = PageRequest.of(page, pageSize); // 페이징 처리 정보 생성
        Page<Board> list; // 페이징된 게시글 데이터를 저장할 변수

        // 검색어가 비어있는지 확인하여 전체 조회 또는 검색 결과 조회 결정
        if (keyword.isEmpty()) {
            list = blogService.findAll(pageable); // 전체 게시글 조회
        } else {
            list = blogService.searchByKeyword(keyword, pageable); // 검색 키워드를 포함하는 게시글 조회
        }

        int startNum = (page * pageSize) + 1; // 현재 페이지에서 게시글 시작 번호 계산
        // 뷰에서 사용할 데이터를 모델에 추가
        model.addAttribute("boards", list); // 게시글 목록
        model.addAttribute("totalPages", list.getTotalPages()); // 전체 페이지 수
        model.addAttribute("currentPage", page); // 현재 페이지 번호
        model.addAttribute("keyword", keyword); // 검색 키워드
        model.addAttribute("startNum", startNum); // 시작 번호
        model.addAttribute("email", email); // 로그인된 사용자 이메일

        return "board_list"; // "board_list"라는 뷰 템플릿 반환
    }

//     @GetMapping("/board_list")
// public String board_list(Model model, 
//                          @RequestParam String sessionId, 
//                          @RequestParam(defaultValue = "0") int page, 
//                          @RequestParam(defaultValue = "") String keyword, 
//                          HttpSession session, 
//                          HttpServletRequest request) {
//     // 세션 및 쿠키 확인
//     String email = (String) session.getAttribute("email");
//     String userId = (String) session.getAttribute("userId_" + email);

//     Cookie[] cookies = request.getCookies();
//     String userSessionId = null;
//     if (cookies != null) {
//         for (Cookie cookie : cookies) {
//             if ("USER_SESSION_ID".equals(cookie.getName())) {
//                 userSessionId = cookie.getValue();
//                 break;
//             }
//         }
//     }

//     // 세션 및 쿠키 검증
//     if (userSessionId == null || !userSessionId.equals(userId) || !sessionId.equals(userId)) {
//         return "redirect:/member_login";
//     }

//     System.out.println("유효한 사용자: email=" + email + ", sessionId=" + sessionId);

//     // 게시판 목록 조회
//     int pageSize = 3;
//     PageRequest pageable = PageRequest.of(page, pageSize);
//     Page<Board> list = keyword.isEmpty() ? blogService.findAll(pageable) : blogService.searchByKeyword(keyword, pageable);

//     // 모델에 데이터 추가
//     model.addAttribute("boards", list);
//     model.addAttribute("totalPages", list.getTotalPages());
//     model.addAttribute("currentPage", page);
//     model.addAttribute("keyword", keyword);
//     model.addAttribute("email", email);
//     model.addAttribute("sessionId", sessionId);
//     model.addAttribute("startNum", (page * pageSize) + 1);

//     return "board_list";
// }




    // 게시글 상세 조회 메서드
    @GetMapping("/board_view/{id}") // GET 요청으로 "/board_view/{id}" URL에 매핑
    public String board_view(Model model, @PathVariable Long id, HttpSession session) { // URL 경로의 "id"를 파라미터로 받음
        String email = (String) session.getAttribute("email"); // 세션에서 이메일 가져오기
        Optional<Board> list = blogService.findById(id); // ID로 게시글을 조회
        if (list.isPresent()) { // 게시글이 존재하는 경우
            model.addAttribute("boards", list.get()); // 조회된 게시글 정보를 모델에 추가
            model.addAttribute("email", email); // 사용자 이메일 추가
        } else { // 게시글이 없는 경우
            return "/error_page/article_error"; // 에러 페이지로 이동
        }
        return "board_view"; // "board_view"라는 뷰 템플릿 반환
    }

    // 게시글 작성 페이지로 이동
    @GetMapping("/board_write") // GET 요청으로 "/board_write" URL에 매핑
    public String board_write(Model model, HttpSession session) {
        String email = (String) session.getAttribute("email"); // 세션에서 사용자 이메일 가져오기
        if (email == null) { // 이메일이 없으면 (로그인되지 않음)
            return "redirect:/member_login"; // 로그인 페이지로 리다이렉트
        }
        model.addAttribute("email", email); // 사용자 이메일을 모델에 추가
        return "board_write"; // "board_write"라는 뷰 템플릿 반환
    }

    // 게시글 저장
    @PostMapping("/api/boards") // POST 요청으로 "/api/boards" URL에 매핑
    public String addboards(@ModelAttribute AddArticleRequest request, HttpSession session) {
        String email = (String) session.getAttribute("email"); // 세션에서 사용자 이메일 가져오기
        if (email == null) { // 로그인되지 않은 경우
            return "redirect:/member_login"; // 로그인 페이지로 리다이렉트
        }
        request.setUser(email); // 작성자 정보를 게시글 요청 객체에 설정
        blogService.save(request); // 게시글 저장 로직 호출
        return "redirect:/board_list"; // 게시글 목록 페이지로 리다이렉트
    }

    // 게시글 수정 페이지로 이동
    @GetMapping("/board_edit/{id}") // GET 요청으로 "/board_edit/{id}" URL에 매핑
    public String board_edit(Model model, @PathVariable Long id) {
        Optional<Board> board = blogService.findById(id); // ID로 게시글 조회
        if (board.isPresent()) { // 게시글이 존재하는 경우
            model.addAttribute("board", board.get()); // 조회된 게시글을 모델에 추가
            return "board_edit"; // "board_edit"라는 뷰 템플릿 반환
        } else { // 게시글이 없는 경우
            return "/error_page/board_error"; // 에러 페이지로 이동
        }
    }

    // 게시글 수정
    @PutMapping("/api/board_edit/{id}") // PUT 요청으로 "/api/board_edit/{id}" URL에 매핑
    public String updateBoard(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
        blogService.update(id, request); // 게시글 수정 로직 호출
        return "redirect:/board_list"; // 게시글 목록 페이지로 리다이렉트
    }

    // 게시글 삭제
    @DeleteMapping("/api/board_delete/{id}") // DELETE 요청으로 "/api/board_delete/{id}" URL에 매핑
    public String deleteBoard(@PathVariable Long id) {
        blogService.delete(id); // 게시글 삭제 로직 호출
        return "redirect:/board_list"; // 게시글 목록 페이지로 리다이렉트
    }

}
