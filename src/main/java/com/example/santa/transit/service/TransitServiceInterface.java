package com.example.santa.transit.service;

import com.example.santa.transit.vo.TransitDTO;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public interface TransitServiceInterface {
    List<TransitDTO> findAllTransit();

    void updateTransit(List<Integer> transitIds);

    void rejectTransit(List<Integer> transitIds);

    // 매일 오전 10시에 실행
    @Scheduled(cron = "0 00 11 * * *")
    void updateTransitSchedule(List<Integer> transitIds);
}
