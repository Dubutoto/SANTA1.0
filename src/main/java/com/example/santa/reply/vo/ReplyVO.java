package com.example.santa.reply.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data //get/set, toString코드 자동 생성
@NoArgsConstructor //기본 생성자 자동 생성
@AllArgsConstructor //모든 멤버변수 생성자 자동 생성
@Builder
public class BoardVO {
    private int boardId;
    private String boardType;
    private int authorId;
    private String title;
    private String content;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

}



