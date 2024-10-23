package com.example.demo.model.service;

import org.springframework.beans.factory.annotation.Autowired; 
// 스프링의 객체 주입을 자동으로 처리하는 어노테이션
import org.springframework.stereotype.Service; 
// 서비스 계층을 나타내는 어노테이션, 스프링 컨테이너에 이 클래스가 서비스로 등록됨
import com.example.demo.model.domain.TestDB; 
// TestDB 엔티티 클래스 임포트
import com.example.demo.model.repository.TestRepository; 
// TestRepository 인터페이스 임포트
import lombok.RequiredArgsConstructor; 
// 필드에서 final로 선언된 객체들에 대한 생성자를 자동으로 만들어주는 롬복 어노테이션

@Service 
// 이 클래스가 서비스 클래스임을 명시. 스프링이 관리하는 빈(Bean)으로 등록됨
@RequiredArgsConstructor 
// 클래스에 final 필드가 있다면, 그 필드를 초기화하는 생성자를 자동으로 생성해줌

public class TestService {

    @Autowired // testRepository 객체를 자동으로 스프링이 주입해줌 (생성자가 하나일 경우 생략 가능)
    private TestRepository testRepository; // 데이터베이스와의 통신을 담당하는 리포지토리 선언
    
    // 특정 name 값으로 TestDB 엔티티를 찾는 메서드
    public TestDB findByName(String name) {
        return (TestDB) testRepository.findByName(name); 
        // TestRepository의 findByName 메서드를 호출해 name에 해당하는 TestDB 객체를 반환함
    }
}
