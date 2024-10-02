// package com.example.demo;

// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;

// @Controller // 컨트롤러 어노테이션 명시
// public class DemoController 
// { 
//     @GetMapping("/hello") // 전송 방식 GET
//     public String hello(Model model) 
//     {
//         model.addAttribute("data", " 방갑습니다."); // model 설정
//         return "hello"; // hello.html 연결
//     }
// }

// 이 클래스가 속한 패키지를 선언합니다.
package com.example.demo;

// Spring Framework에서 제공하는 Controller 애노테이션을 임포트합니다.
import org.springframework.stereotype.Controller;
// Spring의 Model 인터페이스를 임포트합니다. 이는 뷰에 데이터를 전달하는 데 사용됩니다.
import org.springframework.ui.Model;
// HTTP GET 요청을 특정 메서드에 매핑하기 위한 애노테이션을 임포트합니다.
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


// 이 클래스가 Spring MVC의 컨트롤러임을 나타냅니다.
// 이 애노테이션은 Spring에게 이 클래스가 웹 요청을 처리할 수 있음을 알려줍니다.
@Controller

// DemoController 클래스를 정의합니다.
public class DemoController {
    
    // "/hello" URL로 들어오는 HTTP GET 요청을 이 메서드가 처리하도록 매핑합니다.
    @GetMapping("/hello")
    // hello 메서드를 정의합니다. Model 객체를 파라미터로 받습니다.
    public String hello(Model model) {
        // Model 객체에 "data"라는 이름으로 " 방갑습니다." 문자열을 추가합니다.
        // 이 데이터는 뷰(여기서는 hello.html)에서 사용할 수 있습니다.
        model.addAttribute("data", " 방갑습니다.");
        
        // "hello"라는 문자열을 반환합니다. 
        // 이는 ViewResolver에 의해 처리되어 "hello.html" 템플릿을 찾아 렌더링합니다.
        return "hello";
    }

    @GetMapping("/hello2")
    public String hello2(Model model2) {
        model2.addAttribute("data2", "홍길동님.");
        model2.addAttribute("data3", "방갑습니다.");
        model2.addAttribute("data4", "오늘.");
        model2.addAttribute("data5", "날씨는.");
        model2.addAttribute("data6", "매우 좋습니다.");
        return "hello2";
    }

    @GetMapping("/about_detailed")
    public String about() {
    return "about_detailed";
    }

    @GetMapping("/thymeleaf_test1")
    public String thymeleaf_test1(Model model) 
    {
        model.addAttribute("data1", "<h2> 방갑습니다 </h2>");
        model.addAttribute("data2", "태그의 속성 값");
        model.addAttribute("link", 01);
        model.addAttribute("name", "홍길동");
        model.addAttribute("para1", "001");
        model.addAttribute("para2", 002);
    return "thymeleaf_test1";
    }

    
}



// @Controller
// public class DemoController {

//     @GetMapping("/hello2")
//     public String hello2(Model model) {
//         model.addAttribute("data", "이것은 Hello2 페이지입니다.");
//         return "hello2"; // hello2.html 파일을 반환
//     }
// }

