package com.example.demo.controller;

import com.example.demo.model.domain.Article;
import com.example.demo.model.service.AddArticleRequest;
import com.example.demo.model.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController // @Controller + @ResponseBody

public class BlogRestController {
    private final BlogService blogService; // 필드명 수정

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) { // @ModelAttribute -> @RequestBody
        Article saveArticle = blogService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(saveArticle);
    }

    @GetMapping("/favicon.ico")
    public void favicon() {
    // 아무 작업도 하지 않음
    }
}
