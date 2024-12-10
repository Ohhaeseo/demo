package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.model.service.AddMemberRequest;
import com.example.demo.model.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.UUID; // UUID를 생성하기 위해 필요
import jakarta.servlet.http.Cookie;
import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.example.demo.model.domain.Member;



@Controller
public class MemberController {
    
    @Autowired
    private MemberService memberService; // MemberService 의존성 주입


    @GetMapping("/join_new")
    public String join_new(Model model) {
        model.addAttribute("addMemberRequest", new AddMemberRequest());
        return "join_new";
    }


    @PostMapping("/api/members")
    public String addmembers(@Valid @ModelAttribute AddMemberRequest request, 
                            BindingResult bindingResult, 
                            Model model) {
        // 유효성 검증 실패
        if (bindingResult.hasErrors()) {
            model.addAttribute("request", request); // 입력 데이터 유지
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "join_new";
        }
        
        try {
            // 회원 저장 시도
            memberService.saveMember(request);
            return "join_end";
        } catch (IllegalStateException e) {
            // 중복 회원 등의 비즈니스 예외 처리
            model.addAttribute("request", request);
            model.addAttribute("errorMessage", e.getMessage());
            return "join_new";
        }
    }



    @GetMapping("/member_login") // 로그인 페이지 연결
    public String member_login() 
    {
        return "login"; // .HTML 연결
    }


    @PostMapping("/api/login_check") // 로그인(아이디, 패스워드) 체크
    public String checkMembers(@ModelAttribute AddMemberRequest request, 
                            Model model, 
                            HttpServletRequest request2, 
                            HttpServletResponse response) {
        try {
            // 기존 세션 무효화
            HttpSession session = request2.getSession(false);
            if (session != null) {
                session.invalidate();
            }

            // 새로운 세션 생성
            session = request2.getSession(true);
            Member member = memberService.loginCheck(request.getEmail(), request.getPassword());

            // 고유한 세션 ID 생성
            String sessionId = UUID.randomUUID().toString();
            session.setAttribute("userId_" + request.getEmail(), sessionId); // 사용자별 세션 ID 저장
            session.setAttribute("email", request.getEmail()); // 사용자 이메일 저장

            // USER_SESSION_ID 쿠키 생성 및 응답에 추가
            Cookie userSessionCookie = new Cookie("USER_SESSION_ID", sessionId);
            userSessionCookie.setPath("/"); // 쿠키의 경로 설정
            userSessionCookie.setHttpOnly(true); // 클라이언트에서 자바스크립트 접근 차단
            userSessionCookie.setMaxAge(-1); // 브라우저 세션 동안 유지
            response.addCookie(userSessionCookie);

            System.out.println("로그인 성공: email=" + request.getEmail() + ", sessionId=" + sessionId);

            model.addAttribute("member", member); // 로그인 성공 시 회원 정보 전달
            return "redirect:/board_list?sessionId=" + sessionId;
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage()); // 에러 메시지 전달
            return "login"; // 로그인 실패 시 로그인 페이지로 리다이렉트
        }
    }

    


    @GetMapping("/api/logout")
    public String member_logout(HttpServletRequest request2, HttpServletResponse response) {
        try {
            HttpSession session = request2.getSession(false);
            if (session != null) {
                // 현재 세션의 사용자 정보 가져오기
                String sessionId = (String) session.getAttribute("userId_" + session.getId());
                String email = (String) session.getAttribute("email_" + session.getId());

                // 세션 속성 제거
                if (sessionId != null) {
                    session.removeAttribute("userId_" + sessionId);
                    session.removeAttribute("email_" + sessionId);
                }

                // 세션 무효화
                session.invalidate();

                // 현재 사용자의 쿠키 삭제
                Cookie cookie = new Cookie("JSESSIONID", null);
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);

                System.out.println("로그아웃 성공: userId=" + sessionId + ", email=" + email);
            }
            return "login"; // 로그인 페이지로 리다이렉트
        } catch (Exception e) {
            return "login"; // 에러 발생 시 로그인 페이지로 리다이렉트
        }
    }


    
    // 기존 코드
    // @PostMapping("/api/login_check") // 로그인(아이디, 패스워드) 체크
    // public String checkMembers(@ModelAttribute AddMemberRequest request, Model model, HttpServletRequest request2, HttpServletResponse response) 
    // {
    //     try 
    //     {
    //         HttpSession session = request2.getSession(false); // 기존 세션 가져오기(존재하지 않으면 null 반환)
    //         if (session != null) 
    //         {
    //             session.invalidate(); // 기존 세션 무효화
    //             Cookie cookie = new Cookie("JSESSIONID", null); // JSESSIONID 초기화
    //             cookie.setPath("/"); // 쿠키 경로
    //             cookie.setMaxAge(0); // 쿠키 삭제(0으로 설정)
    //             response.addCookie(cookie); // 응답으로 쿠키 전달
    //         }
    //             session = request2.getSession(true); // 새로운 세션 생성
    //         Member member = memberService.loginCheck(request.getEmail(), request.getPassword()); // 패스워드 반환
    //         String sessionId = UUID.randomUUID().toString(); // 임의의 고유 ID로 세션 생성
    //         // session.setAttribute("userId", sessionId); // 아이디 이름 설정
    //         String email = request.getEmail(); // 이메일 얻기
    //         // session.setAttribute("userId", sessionId); // 아이디 이름 설정
    //         // session.setAttribute("email", email); // 이메일 설정
    //         session.setAttribute("userId_" + email, sessionId);
    //         session.setAttribute("email", email);
            
    //         System.out.println("로그인 성공: email=" + request.getEmail());

    //         model.addAttribute("member", member); // 로그인 성공 시 회원 정보 전달
    //         return "redirect:/board_list"; // 로그인 성공 후 이동할 페이지
    //     } 
    //     catch (IllegalArgumentException e) 
    //     {
    //         model.addAttribute("error", e.getMessage()); // 에러 메시지 전달
    //         return "login"; // 로그인 실패 시 로그인 페이지로 리다이렉트
    //     }
    // }

    // @GetMapping("/api/logout") // 로그아웃 버튼 동작
    // public String member_logout(Model model, HttpServletRequest request2, HttpServletResponse response) {
    //     try {
    //         HttpSession session = request2.getSession(false); // 기존 세션 가져오기(존재하지 않으면 null 반환)
    //         if (session != null) {
    //             session.invalidate(); // 기존 세션 무효화
    //             Cookie cookie = new Cookie("JSESSIONID", null); // JSESSIONID is the default session cookie name
    //             cookie.setPath("/"); // Set the path for the cookie
    //             cookie.setMaxAge(0); // Set cookie expiration to 0 (removes the cookie)
    //             response.addCookie(cookie); // Add cookie to the response
    //         }
    //         session = request2.getSession(true); // 새로운 세션 생성
    //         System.out.println("세션 userId: " + session.getAttribute("userId")); // 초기화 후 IDE 터미널에 세션 값 출력
    //         return "login"; // 로그인 페이지로 리다이렉트
    //     } catch (IllegalArgumentException e) {
    //         model.addAttribute("error", e.getMessage()); // 에러 메시지 전달
    //         return "login"; // 로그인 실패 시 로그인 페이지로 리다이렉트
    //     }
    // }



}
