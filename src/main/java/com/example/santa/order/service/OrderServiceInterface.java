package com.example.santa.order.service;

import com.example.santa.order.vo.OrderDTO;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public interface OrderServiceInterface {
    //*************************************************
    //******************** 주문 조회 ********************
    //*************************************************
    //주문 조회 list
    List<OrderDTO> readOrder();

    //주문 일자 검색
    List<OrderDTO> searchByOrderDate(String startDate, String endDate);

    //상품 검색
    List<OrderDTO> searchByProductName(String productName);

    //주문 지점 검색
    List<OrderDTO> searchByBranchName(String branchName);

    //주문 상태 검색
    List<OrderDTO> searchByOrderStatus(String orderStatus);

    //*************************************************
    //******************** 주문 승인 ********************
    //*************************************************
    //승인 대기 주문 list
    List<OrderDTO> readPendingOrders();

    //주문 승인/거절
    @Transactional
    int updateOrderStatus(int orderId, String orderStatus);

    // 승인 대기 주문, 주문 일자 검색
    List<OrderDTO> searchByPendingOrderDate(String startDate, String endDate);

    // 승인 대기 주문, 주문 상품 검색
    List<OrderDTO> searchByPendingProductName(String productName);

    // 승인 대기 주문, 주문 지점 검색
    List<OrderDTO> searchByPendingBranchName(String branchName);

    //*************************************************
    //******************** 로직 메서드 ******************
    //*************************************************
    // 날짜 검증 메서드 추가
    default boolean isValidDate(String date) {
        try {
            LocalDate.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
