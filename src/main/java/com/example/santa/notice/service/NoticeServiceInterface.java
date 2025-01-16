package com.example.santa.notice.service;

import com.example.santa.notice.vo.NoticeDTO;
import com.example.santa.notice.vo.NoticeVO;

import java.util.List;

public interface NoticeServiceInterface {
    List<NoticeDTO> selectAllNotice();

    int insertNotice(NoticeVO notice);

    int deleteNotice(int id);

    NoticeDTO selectByNoticeId(int noticeId);

    NoticeVO selectByNoticeId2(int noticeId);

    int updateNotice(NoticeVO notice);
}
