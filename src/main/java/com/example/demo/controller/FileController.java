package com.example.demo.controller;
// 패키지 선언: 이 클래스가 속한 패키지를 지정합니다.

import org.springframework.beans.factory.annotation.Value;
// @Value 어노테이션을 사용하기 위해 가져옵니다.

import org.springframework.stereotype.Controller;
// @Controller 어노테이션을 사용하기 위해 가져옵니다.

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
// 요청 매핑 및 요청 파라미터를 처리하기 위한 어노테이션을 사용하기 위해 가져옵니다.

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
// 리다이렉트 시 데이터를 전달하기 위해 사용합니다.

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
// 파일 및 경로 관련 작업을 위해 가져옵니다.

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
// 파일 쓰기 및 입출력 예외 처리를 위해 가져옵니다.

import java.util.List;
// 컬렉션(List)을 사용하기 위해 가져옵니다.

@Controller
// 이 클래스가 Spring MVC의 컨트롤러임을 나타냅니다.
public class FileController {
    
    @Value("${spring.servlet.multipart.location}")
    // 애플리케이션 설정 파일(application.properties 또는 application.yml)에 정의된 업로드 경로를 주입합니다.
    private String uploadFolder;

    @PostMapping("/upload-email")
    // "/upload-email" 경로로 들어오는 POST 요청을 처리하는 메서드임을 나타냅니다.
    public String uploadEmail(
            @RequestParam("email") String email,
            // 요청 파라미터에서 "email" 값을 받아옵니다.
            @RequestParam("subject") String subject,
            // 요청 파라미터에서 "subject" 값을 받아옵니다.
            @RequestParam("message") String message,
            // 요청 파라미터에서 "message" 값을 받아옵니다.
            RedirectAttributes redirectAttributes) {
            // 리다이렉트 시 플래시 속성으로 데이터를 전달하기 위해 사용합니다.
        try {
            // 업로드 폴더의 절대 경로를 가져옵니다.
            Path uploadPath = Paths.get(uploadFolder).toAbsolutePath();
            if (!Files.exists(uploadPath)) {
                // 업로드 폴더가 존재하지 않으면 디렉토리를 생성합니다.
                Files.createDirectories(uploadPath);
            }

            // 이메일에서 특수문자를 제거하고 파일명에 사용할 수 있도록 변환합니다.
            String sanitizedEmail = email.replaceAll("[^a-zA-Z0-9]", "_");
            // 파일 경로를 설정합니다. 예: /uploadFolder/user_email.txt
            Path filePath = uploadPath.resolve(sanitizedEmail + ".txt");

            // 동일한 파일명이 존재할 경우 파일명에 숫자를 붙여서 중복을 피합니다.
            int count = 1;
            while (Files.exists(filePath)) {
                // 파일명이 이미 존재하면 숫자를 증가시켜 새로운 파일명을 생성합니다.
                filePath = uploadPath.resolve(sanitizedEmail + "_" + count + ".txt");
                count++;
            }

            // 파일에 이메일 내용(제목과 메시지)을 저장합니다.
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
                writer.write("메일제목: " + subject);
                writer.newLine(); // 줄 바꿈
                writer.write("요청메시지:");
                writer.newLine(); // 줄 바꿈
                writer.write(message);
            }

            // 저장된 파일들과 비교하여 동일한 내용의 파일이 있는지 확인합니다.
            boolean isDuplicate = false;
            for (int i = 0; i < count; i++) {
                // 각 파일명을 생성합니다.
                Path existingFilePath = uploadPath.resolve(sanitizedEmail + (i == 0 ? "" : "_" + i) + ".txt");
                if (Files.exists(existingFilePath)) {
                    // 파일이 존재하면 내용을 읽어옵니다.
                    List<String> existingFileContent = Files.readAllLines(existingFilePath);
                    // 파일 내용에서 제목과 메시지를 비교합니다.
                    boolean isEmailSame = existingFileContent.contains("메일제목: " + subject);
                    boolean isMessageSame = existingFileContent.contains("요청메시지:") && existingFileContent.contains(message);
                    
                    // 이메일, 제목, 메시지가 모두 같으면 중복으로 간주합니다.
                    if (isEmailSame && isMessageSame) {
                        isDuplicate = true;
                        break; // 중복이 발견되면 반복을 중지합니다.
                    }
                }
            }

            if (isDuplicate) {
                // 중복된 내용이 존재하면 에러 메시지를 설정하고 에러 페이지로 이동합니다.
                redirectAttributes.addFlashAttribute("error", "동일한 이메일, 제목, 메시지가 이미 존재합니다.");
                return "/error_page/article_error";
            }

            // 성공 메시지를 설정합니다.
            redirectAttributes.addFlashAttribute("message", "메일내용이 성공적으로 업로드되었습니다!");
        } catch (IOException e) {
            // 입출력 예외가 발생한 경우
            e.printStackTrace(); // 오류 스택 트레이스를 출력합니다.
            // 에러 메시지를 설정하고 에러 페이지로 이동합니다.
            redirectAttributes.addFlashAttribute("error", "업로드 중 오류가 발생했습니다: " + e.getMessage());
            return "/error_page/article_error";
        }
        // 업로드가 성공적으로 완료되면 "upload_end" 뷰를 반환합니다.
        return "upload_end";
    }

    
}
