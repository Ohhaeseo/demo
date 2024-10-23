package com.example.demo; // 현재 패키지를 선언합니다. 이 클래스는 com.example.demo 패키지에 속합니다.

import org.springframework.boot.SpringApplication; // Spring Boot 애플리케이션을 실행하는 데 필요한 핵심 클래스를 가져옵니다.
import org.springframework.boot.autoconfigure.SpringBootApplication; // Spring Boot의 자동 설정 기능을 활성화하는 애노테이션을 가져옵니다.

@SpringBootApplication // 이 애노테이션은 Spring Boot 애플리케이션의 시작점을 나타냅니다.
                       // 이것은 @Configuration, @EnableAutoConfiguration, @ComponentScan을 결합한 것으로,
                       // 스프링 부트의 자동 설정, 컴포넌트 스캔, 추가 설정 등을 가능하게 합니다.
public class DemoApplication { 

    public static void main(String[] args) {  // Java 애플리케이션의 진입점인 main 메서드입니다.
                                              // 이 메서드는 애플리케이션이 시작될 때 JVM에 의해 호출됩니다.
        SpringApplication.run(DemoApplication.class, args); // SpringApplication의 run 메서드를 호출하여 Spring Boot 애플리케이션을 시작합니다.
                                                            // 이 메서드는 DemoApplication 클래스와 커맨드 라인 인자를 매개변수로 받습니다.
                                                            // 이를 통해 Spring 컨테이너가 생성되고, 빈(Bean)들이 등록되며, 웹 서버가 시작됩니다.
    }

}
