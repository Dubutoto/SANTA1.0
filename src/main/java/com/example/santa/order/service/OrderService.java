package com.example.santa.order.service;


import com.example.santa.order.mapper.OrderMapper;
import com.example.santa.order.vo.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderServiceInterface {

    private final OrderMapper orderMapper;

    //*************************************************
    //******************** 주문 조회 ********************
    //*************************************************
    //주문 조회 list
    @Override
    public List<OrderDTO> readOrder(){
        return orderMapper.readOrder();
    }

    //주문 일자 검색
    @Override
    public List<OrderDTO> searchByOrderDate(String startDate, String endDate) {
        if (startDate == null || endDate == null || startDate.isEmpty() || endDate.isEmpty()) {
            throw new IllegalArgumentException("시작일자와 종료일자를 입력해야 합니다.");
        }

        if (!isValidDate(startDate) || !isValidDate(endDate)) {
            throw new IllegalArgumentException("잘못된 날짜 형식입니다.");
        }

        return orderMapper.searchByOrderDate(startDate, endDate);
    }

    //상품 검색
    @Override
    public List<OrderDTO> searchByProductName(String productName) {
        return orderMapper.searchByProductName(productName);
    }

    //주문 지점 검색
    @Override
    public List<OrderDTO> searchByBranchName(String branchName) {
        return orderMapper.searchByBranchName(branchName);
    }

    //주문 상태 검색
    @Override
    public List<OrderDTO> searchByOrderStatus(String orderStatus) {
        return orderMapper.searchByOrderStatus(orderStatus);
    }

    //*************************************************
    //******************** 주문 승인 ********************
    //*************************************************
    //승인 대기 주문 list
    @Override
    public List<OrderDTO> readPendingOrders(){
        return orderMapper.readPendingOrders();
    }

    //주문 승인/거절
    @Transactional
    @Override
    public int updateOrderStatus(int orderId, String orderStatus) {
        //orderIdFind() 해서 재고량과 주문량 비교해서 처리한 값 넘겨주기
        System.out.println(orderStatus);

        //orderStatus가 승인이면 출고 대기 등록 / 거절이면 그냥 상태 update
        // 주문 상태 업데이트
        int updateResult = orderMapper.updateOrderStatus(orderId, orderStatus);
        if (updateResult <= 0) {
            throw new RuntimeException("주문 상태 업데이트 실패");
        }

        //주문 승인 시 출고 테이블에 추가(출고 대기 상태로, 주문 상태가 '승인'인 경우)
        if (orderStatus.equals("승인")) {
            int outgoingResult = orderMapper.insertOutgoingPending(orderId);
            if (outgoingResult <= 0) {
                throw new RuntimeException("출고 대기 등록 실패");
            }
        }

        System.out.println("주문 처리 완료");

        return updateResult;
    }

    // 승인 대기 주문, 주문 일자 검색
    @Override
    public List<OrderDTO> searchByPendingOrderDate(String startDate, String endDate) {
        if (startDate == null || endDate == null || startDate.isEmpty() || endDate.isEmpty()) {
            throw new IllegalArgumentException("시작일자와 종료일자를 입력해야 합니다.");
        }

        if (!isValidDate(startDate) || !isValidDate(endDate)) {
            throw new IllegalArgumentException("잘못된 날짜 형식입니다.");
        }

        return orderMapper.searchByPendingOrderDate(startDate, endDate);
    }

    // 승인 대기 주문, 주문 상품 검색
    @Override
    public List<OrderDTO> searchByPendingProductName(String productName) {
        return orderMapper.searchByPendingProductName(productName);
    }

    // 승인 대기 주문, 주문 지점 검색
    @Override
    public List<OrderDTO> searchByPendingBranchName(String branchName) {
        return orderMapper.searchByPendingBranchName(branchName);
    }

}
