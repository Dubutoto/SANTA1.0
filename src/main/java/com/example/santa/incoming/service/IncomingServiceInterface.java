package com.example.santa.incoming.service;

import com.example.santa.incoming.vo.IncomingDTO;

import java.util.List;

public interface IncomingServiceInterface {
    List<IncomingDTO> selectAllIncoming();

    // 상품명별 입고 조회
    List<IncomingDTO> selectAllIncomingByProductName(String productName);

    // 입고 일자별 입고 조회
    List<IncomingDTO> selectAllIncomingByIncomingDate(String date1, String date2);
}
