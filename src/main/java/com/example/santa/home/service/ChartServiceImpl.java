package com.example.santa.home.service;

import com.example.santa.home.mapper.ChartMapper;
import com.example.santa.home.vo.CategoryInventoryVO;
import com.example.santa.home.vo.MonthlyInOutInvVO;
import com.example.santa.home.vo.MonthlyOrderVO;
import com.example.santa.home.vo.WarehouseInventoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChartServiceImpl implements ChartService {

    private final ChartMapper chartMapper;

    @Override
    public List<MonthlyInOutInvVO> getMonthlyInOutInvList() {
        return chartMapper.getMonthlyInOutInvList();
    }

    @Override
    public List<WarehouseInventoryVO> getWarehouseInventoryList() {
        return chartMapper.getWarehouseInventoryList();
    }

    @Override
    public List<CategoryInventoryVO> getCategoryInventoryList() {
        return chartMapper.getCategoryInventoryList();
    }

    @Override
    public List<MonthlyOrderVO> getMonthlyOrderList() {
        return chartMapper.getMonthlyOrderList();
    }

    @Override
    public int getPendingOrders() {
        return chartMapper.getPendingOrders();
    }

    @Override
    public double getTotalSalesAmount() {
        return chartMapper.getTotalSalesAmount();
    }

    @Override
    public double getWarehouseTotalAsset() {
        return chartMapper.getWarehouseTotalAsset();
    }

    @Override
    public int getTotalOrders() {
        return chartMapper.getTotalOrders();
    }
}
