package com.example.santa.home.service;

import com.example.santa.home.vo.CategoryInventoryVO;
import com.example.santa.home.vo.MonthlyInOutInvVO;
import com.example.santa.home.vo.MonthlyOrderVO;
import com.example.santa.home.vo.WarehouseInventoryVO;

import java.util.List;

public interface ChartService {

    List<MonthlyInOutInvVO> getMonthlyInOutInvList();

    List<WarehouseInventoryVO> getWarehouseInventoryList();

    List<CategoryInventoryVO> getCategoryInventoryList();

    List<MonthlyOrderVO> getMonthlyOrderList();


    int getPendingOrders();               // 주문 대기 수
    double getTotalSalesAmount();         // 총 판매 금액
    double getWarehouseTotalAsset();      // 창고 총 자산 금액
    int getTotalOrders();                 // 총 주문 수


}
