package com.example.demo.controller;

import java.util.List;  // 올바른 List 임포트
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.domain.Article;
import com.example.demo.model.service.BlogService;

@Controller
public class BlogController {
    @Autowired
    BlogService blogService;  // 소문자로 변경

    @GetMapping("/article_list")
    public String article_list(Model model) {
        List<Article> list = blogService.findAll();  // blogService로 일관성 유지
        model.addAttribute("articles", list);
        return "article_list"; // .HTML 연결
    }
}
