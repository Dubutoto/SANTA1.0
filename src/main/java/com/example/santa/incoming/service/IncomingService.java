package com.example.santa.incoming.service;

import com.example.santa.incoming.mapper.IncomingMapper;
import com.example.santa.incoming.vo.IncomingDTO;
import com.example.santa.incoming.vo.IncomingVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomingService {
    private final IncomingMapper incomingMapper;

    public List<IncomingDTO> selectAllIncoming() {

        List<IncomingDTO> list = incomingMapper.selectAllIncoming();
        System.out.println("================list===========" + list);
        return list;
    }

    // 상품명별 입고 조회
    public List<IncomingDTO> selectAllIncomingByProductName(String productName) {
        return incomingMapper.selectAllIncomingByProductName(productName);
    }

    // 입고 일자별 입고 조회
    public List<IncomingDTO> selectAllIncomingByIncomingDate(String date1, String date2) {
        return incomingMapper.selectAllIncomingByIncomingDate(date1, date2);
    }
}