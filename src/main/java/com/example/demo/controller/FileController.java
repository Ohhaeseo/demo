package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Controller
public class FileController {
    
    @Value("${spring.servlet.multipart.location}") // properties 등록된 설정(경로) 주입
    private String uploadFolder;

    @PostMapping("/upload-email")
    public String uploadEmail( // 이메일, 제목, 메시지를 전달받음
            @RequestParam("email") String email,
            @RequestParam("subject") String subject,
            @RequestParam("message") String message,
            RedirectAttributes redirectAttributes) {
        try {
            Path uploadPath = Paths.get(uploadFolder).toAbsolutePath();
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String sanitizedEmail = email.replaceAll("[^a-zA-Z0-9]", "_");
            Path filePath = uploadPath.resolve(sanitizedEmail + ".txt");

            // 동일한 파일이 존재할 경우 다른 이름으로 저장
            int count = 1;
            while (Files.exists(filePath)) {
                filePath = uploadPath.resolve(sanitizedEmail + "_" + count + ".txt");
                count++;
            }

            // 파일 저장
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
                writer.write("메일제목: " + subject);
                writer.newLine();
                writer.write("요청메시지:");
                writer.newLine();
                writer.write(message);
            }

            // 저장 후 동일한 파일 내용이 있는지 확인
            boolean isDuplicate = false;
            for (int i = 0; i < count; i++) {
                Path existingFilePath = uploadPath.resolve(sanitizedEmail + (i == 0 ? "" : "_" + i) + ".txt");
                if (Files.exists(existingFilePath)) {
                    List<String> existingFileContent = Files.readAllLines(existingFilePath);
                    boolean isEmailSame = existingFileContent.contains("메일제목: " + subject);
                    boolean isMessageSame = existingFileContent.contains("요청메시지:") && existingFileContent.contains(message);
                    
                    // 이메일, 제목, 메시지가 모두 같을 때만 중복으로 간주
                    if (isEmailSame && isMessageSame) {
                        isDuplicate = true;
                        break;
                    }
                }
            }

            if (isDuplicate) {
                redirectAttributes.addFlashAttribute("error", "동일한 이메일, 제목, 메시지가 이미 존재합니다.");
                return "/error_page/article_error";
            }

            redirectAttributes.addFlashAttribute("message", "메일내용이 성공적으로 업로드되었습니다!");
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "업로드 중 오류가 발생했습니다: " + e.getMessage());
            return "/error_page/article_error";
        }
        return "upload_end";
    }
}
