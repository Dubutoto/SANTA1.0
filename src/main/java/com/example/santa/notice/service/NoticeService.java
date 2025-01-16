package com.example.santa.notice.service;

import com.example.santa.notice.mapper.NoticeMapper;
import com.example.santa.notice.vo.NoticeDTO;
import com.example.santa.notice.vo.NoticeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService implements NoticeServiceInterface {

    final private NoticeMapper noticeMapper;

    @Override
    public List<NoticeDTO> selectAllNotice() {
        return noticeMapper.selectAllNotice();
    }

    @Override
    public int insertNotice(NoticeVO notice) {
        System.out.println("========insertNotice======" + notice);
        return noticeMapper.insertNotice(notice);
    }

    @Override
    public int deleteNotice(int id) {
        System.out.println("========noticeDelete======" + id);
        return noticeMapper.deleteNotice(id);
    }

    @Override
    public NoticeDTO selectByNoticeId(int noticeId) {
        System.out.println("========NoticeServiceSelectByNoticeId=======" + noticeId);
        return noticeMapper.selectByNoticeId(noticeId);
    }

    @Override
    public NoticeVO selectByNoticeId2(int noticeId) {
        System.out.println("========NoticeServiceSelectByNoticeId2=======" + noticeId);
        return noticeMapper.selectByNoticeId2(noticeId);
    }
    @Override
    public int updateNotice(NoticeVO notice) {
        System.out.println("=======updateNoticeService======" + notice);
        return noticeMapper.updateNotice(notice);
    }

}
