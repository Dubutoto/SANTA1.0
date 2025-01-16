package com.example.santa.find.service;

public interface EmailServiceInterface {
    void sendEmail(String to, String subject, String body);

    // HTML 이메일 전송 메서드
    void sendHtmlEmail(String to, String subject, String htmlContent);
}
