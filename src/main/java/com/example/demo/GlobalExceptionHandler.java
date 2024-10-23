package com.example.demo;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    // NumberFormatException이 발생했을 때 처리
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormatException(NumberFormatException ex) {
        ModelAndView mav = new ModelAndView("error_page/article_error");
        mav.addObject("errorMessage", "잘못된 URL 매개변수입니다. 숫자를 입력해주세요.");
        return mav;
    }
}
