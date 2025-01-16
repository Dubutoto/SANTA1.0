package com.example.santa.find.service;

public interface FindServiceInterface {
    // 이메일로 아이디 찾기
    String findIdByEmail(String email);

    // 비밀번호 재설정 링크 전송
    void sendPasswordResetLink(String email);

    // 비밀번호 재설정
    void resetPassword(String token, String newPassword);
}
