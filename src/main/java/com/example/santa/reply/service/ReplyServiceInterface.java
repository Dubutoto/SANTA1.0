package com.example.santa.reply.service;

import com.example.santa.reply.vo.ReplyVO;

import java.util.List;

public interface ReplyServiceInterface {
    List<ReplyVO> selectReplyByBoardId(int boardId);

    // 댓글 추가
    int insertReply(ReplyVO replyVO);

    // 댓글 수정
    int updateReply(ReplyVO replyVO);

    // 댓글 삭제
    int deleteReply(int replyId);
}
