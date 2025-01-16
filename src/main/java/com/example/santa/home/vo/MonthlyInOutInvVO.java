package com.example.santa.home.vo;

import lombok.Data;

@Data
public class MonthlyInOutInvVO {
    private int month;
    private int inbound;     // 월별 총 입고량
    private int outbound;    // 월별 총 출고량
    private int inventory;   // 월별 총 재고량
}
