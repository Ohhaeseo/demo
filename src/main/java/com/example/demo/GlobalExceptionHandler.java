package com.example.demo;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
// @ControllerAdvice 어노테이션: 전역적으로 예외를 처리하는 클래스임을 나타냅니다.
// 이 클래스의 메서드들은 모든 컨트롤러에서 발생하는 예외에 대해 적용됩니다.
public class GlobalExceptionHandler {

    // NumberFormatException이 발생했을 때 처리하는 메서드
    @ExceptionHandler(NumberFormatException.class)
    // @ExceptionHandler 어노테이션: 이 메서드가 NumberFormatException을 처리함을 나타냅니다.
    // 예를 들어, URL에 숫자가 아닌 값이 들어왔을 때 이 예외가 발생할 수 있습니다.
    public ModelAndView handleNumberFormatException(NumberFormatException ex) {
        // ModelAndView 객체를 생성합니다. 뷰 이름은 "error_page/article_error"로 설정됩니다.
        // 이는 에러 메시지를 표시할 HTML 페이지의 위치를 나타냅니다.
        ModelAndView mav = new ModelAndView("error_page/article_error");
        
        // ModelAndView 객체에 에러 메시지를 추가합니다.
        // 이 메시지는 뷰(HTML 페이지)에서 ${errorMessage}로 접근할 수 있습니다.
        mav.addObject("errorMessage", "잘못된 URL 매개변수입니다. 숫자를 입력해주세요.");
        
        // 설정된 ModelAndView 객체를 반환합니다.
        // 이를 통해 사용자에게 에러 페이지와 메시지가 표시됩니다.
        return mav;
    }
}
