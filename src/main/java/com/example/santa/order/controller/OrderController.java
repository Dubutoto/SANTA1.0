package com.example.santa.order.controller;

import com.example.santa.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    //주문 현황
    @GetMapping("status")
    public String status() {
        return "order/status";
    }

    //주문 상세
    @GetMapping("details")
    public String details() {
        return "order/details";
    }

    //주문 통계
    @GetMapping("statistics")
    public String statistics() {
        return "order/statistics";
    }
}
