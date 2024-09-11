package com.example.demo; //현재 폴더 위치

import org.springframework.boot.SpringApplication; // 스프링 핵심 클래스
import org.springframework.boot.autoconfigure.SpringBootApplication; // 자동설정기능 활성화

@SpringBootApplication //시작점을 의미함. 애노테이션(스프링부트APP 명시, 하위다양한설정을자동등록)
public class DemoApplication { 

	public static void main(String[] args) {  // 메인메서드(프로그램 시작점)
		SpringApplication.run(DemoApplication.class, args); // run 메서드로 실행
	}

}
