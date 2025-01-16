package com.example.santa.home.vo;

import lombok.Data;

@Data
public class MonthlyOrderVO {
    private int month;       // 1 ~ 12
    private int orderCount;  // 월별 주문 건수
}
