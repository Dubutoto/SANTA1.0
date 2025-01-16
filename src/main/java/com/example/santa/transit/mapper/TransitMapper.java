package com.example.santa.transit.mapper;

import com.example.santa.transit.vo.TransitDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TransitMapper {
    // 1. 전체 배송 조회
    List<TransitDTO> findAllTransit();

    // 2. 배송 승인시 상태 업데이트
    void updateTransitStatus(@Param("transitIds") List<Integer> transitIds);

    // 3. 배송 거절시 상태 업데이트
    void updateTransitStatusRejection(@Param("transitIds") List<Integer> transitIds);

    // 배송 ID 목록으로 창고 및 지점의 위도/경도 조회
    List<TransitDTO> getCoordinatesForTransit(@Param("transitIds") List<Integer> transitIds);

    // Scheduler
    void updateTransitSchedule(@Param("transitIds") List<Integer> transitIds);
}
