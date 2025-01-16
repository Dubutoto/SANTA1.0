package com.example.santa.find.service;

import com.example.santa.find.mapper.FindMapper;
import com.example.santa.find.mapper.ResetTokenMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class FindService implements FindServiceInterface {

    @Autowired
    private FindMapper findMapper;

    @Autowired
    private ResetTokenMapper resetTokenMapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private HttpServletRequest request;

    // 발표용 비교 코드:  @Autowired vs 생성자 주입 방식
    /*
    public FindService(FindMapper findMapper, ResetTokenMapper resetTokenMapper, EmailService emailService, HttpServletRequest request) {
        this.findMapper = findMapper;
        this.resetTokenMapper = resetTokenMapper;
        this.emailService = emailService;
        this.request = request;
    }
    */

    // 이메일로 아이디 찾기
    @Override
    public String findIdByEmail(String email) {
        return findMapper.findIdByEmail(email);
    }

    // 비밀번호 재설정 링크 전송
    @Override
    public void sendPasswordResetLink(String email) {
        String userId = findMapper.findIdByEmail(email);
        if (userId == null) {
            throw new IllegalArgumentException("No account found with the provided email.");
        }

        String resetToken = UUID.randomUUID().toString();
        resetTokenMapper.insertResetToken(email, resetToken);

        // 동적으로 호스트와 포트 가져오기
        String serverUrl = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        String resetLink = serverUrl + "/find/password/reset?token=" + resetToken;

        // HTML 이메일 내용
        String htmlContent = "<div style=\"font-family: Arial, sans-serif; line-height: 1.5;\">"
                + "<h2 style=\"color: #007bff;\">Password Reset Request</h2>"
                + "<p>Click the button below to reset your password:</p>"
                + "<a href=\"" + resetLink + "\" style=\"display: inline-block; padding: 10px 20px; margin: 10px 0; color: white; background-color: #007bff; text-decoration: none; border-radius: 5px;\">Reset Password</a>"
                + "<p>If you didn’t request a password reset, you can safely ignore this email.</p>"
                + "</div>";

        // 이메일 전송
        emailService.sendHtmlEmail(email, "Password Reset Request", htmlContent);
    }


    // 비밀번호 재설정
    @Override
    public void resetPassword(String token, String newPassword) {
        // 1. 토큰으로 이메일 검색
        String email = resetTokenMapper.findEmailByToken(token);

        if (email == null) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        // 2. 비밀번호 암호화 후 업데이트
        String encryptedPassword = passwordEncoder.encode(newPassword);
        findMapper.updatePasswordByEmail(Map.of("email", email, "password", encryptedPassword));

        // 3. 토큰 삭제
        resetTokenMapper.deleteResetToken(token);
    }
}
