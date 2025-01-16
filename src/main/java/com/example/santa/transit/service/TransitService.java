package com.example.santa.transit.service;

import com.example.santa.transit.mapper.TransitMapper;
import com.example.santa.transit.vo.TransitDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransitService implements TransitServiceInterface {
    private final TransitMapper transitMapper;

    @Override
    public List<TransitDTO> findAllTransit() {
        List <TransitDTO> transits = transitMapper.findAllTransit();
        System.out.println("transits >>>>>>>>>>>> " + transits);
        return transits;
    }

    @Override
    public void updateTransit(List<Integer> transitIds) {
        transitMapper.updateTransitStatus(transitIds);
    }

    @Override
    public void rejectTransit(List<Integer> transitIds){
        transitMapper.updateTransitStatusRejection(transitIds);
    }

    @Scheduled(cron = "0 0 11 * * *")
    @Override
    public void updateTransitSchedule(List<Integer> transitIds) {
        transitMapper.updateTransitStatus(transitIds);
    }
}
