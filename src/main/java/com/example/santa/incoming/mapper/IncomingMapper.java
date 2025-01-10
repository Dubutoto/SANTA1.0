package com.example.santa.incoming.mapper;

import com.example.santa.incoming.vo.IncomingDTO;
import com.example.santa.incoming.vo.IncomingVO;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@Mapper
public interface IncomingMapper {
    // 입고 조회
    List<IncomingDTO> selectAllIncoming();

    // 상품명별 입고 조회
    List<IncomingDTO> selectAllIncomingByProductName(String productName);

    // 입고 일자별 입고 조회
    List<IncomingDTO> selectAllIncomingByIncomingDate(String date1, String date2);
}
