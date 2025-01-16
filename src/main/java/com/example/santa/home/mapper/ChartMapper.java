package com.example.santa.home.mapper;

import com.example.santa.home.vo.CategoryInventoryVO;
import com.example.santa.home.vo.MonthlyInOutInvVO;
import com.example.santa.home.vo.MonthlyOrderVO;
import com.example.santa.home.vo.WarehouseInventoryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChartMapper {

    //  월별 입/출/재고량
    List<MonthlyInOutInvVO> getMonthlyInOutInvList();

    //  창고별 재고량
    List<WarehouseInventoryVO> getWarehouseInventoryList();

    //  카테고리별 재고량
    List<CategoryInventoryVO> getCategoryInventoryList();

    //  월별 주문량
    List<MonthlyOrderVO> getMonthlyOrderList();

    // 카드 인터페이스
    int getPendingOrders();
    double getTotalSalesAmount();
    double getWarehouseTotalAsset();
    int getTotalOrders();
}
