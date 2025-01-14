package com.example.santa.outgoing.service;

import com.example.santa.outgoing.mapper.OutgoingMapper;
import com.example.santa.outgoing.vo.OutgoingDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OutgoingService {
    private final OutgoingMapper outgoingMapper;

    public List<OutgoingDTO> findAllOutgoing(){
        List <OutgoingDTO> outgoings = outgoingMapper.findAllOutgoing();
        System.out.println("outgoings >>>>>>>>>>>> " + outgoings);
        return outgoings;
    }

    // 2. 지점별 출고 조회
    public List<OutgoingDTO> findOutgoingByBranchName(@Param("branchName") String branchName){
        return outgoingMapper.findOutgoingByBranchName(branchName);
    }

    // 3. 카테고리별 출고 조회
    public List<OutgoingDTO> findOutgoingByCategory(@Param("category") String category){
        return outgoingMapper.findOutgoingByCategory(category);
    }

    // 4. 날짜별 출고 조회
    public List<OutgoingDTO> findOutgoingByDate(@Param("startDate") String startDate,
                                                @Param("endDate") String endDate){
        return outgoingMapper.findOutgoingByDate(startDate,endDate);
    }
}
