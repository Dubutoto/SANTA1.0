package com.example.santa.home.controller;

import com.example.santa.home.service.ChartService;
import com.example.santa.home.vo.MonthlyInOutInvVO;
import com.example.santa.home.vo.MonthlyOrderVO;
import com.example.santa.home.vo.WarehouseInventoryVO;
import com.example.santa.home.vo.CategoryInventoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.DecimalFormat;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChartController {

    private final ChartService chartService;

    @GetMapping("/home")
    public String home(Model model) {
        // DB에서 차트 데이터 조회
        List<MonthlyInOutInvVO> monthlyInOutInvList = chartService.getMonthlyInOutInvList();
        List<WarehouseInventoryVO> warehouseInventoryList = chartService.getWarehouseInventoryList();
        List<CategoryInventoryVO> categoryInventoryList = chartService.getCategoryInventoryList();
        List<MonthlyOrderVO> monthlyOrderList = chartService.getMonthlyOrderList();

        // '월별 주문량'의 총합
        int totalOrders = 0;
        if (monthlyOrderList != null) {
            totalOrders = monthlyOrderList.stream()
                    .mapToInt(MonthlyOrderVO::getOrderCount)
                    .sum();
        }

        // Model에 담기
        model.addAttribute("monthlyInOutInvList", monthlyInOutInvList);
        model.addAttribute("warehouseInventoryList", warehouseInventoryList);
        model.addAttribute("categoryInventoryList", categoryInventoryList);
        model.addAttribute("monthlyOrderList", monthlyOrderList);

        // 카드
        int pendingOrders = chartService.getPendingOrders();
        double totalSalesAmount = chartService.getTotalSalesAmount();
        double warehouseTotalAsset = chartService.getWarehouseTotalAsset();


        // DecimalFormat으로 포맷팅하고 "원" 추가
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedTotalSalesAmount = decimalFormat.format(totalSalesAmount) + " 원";
        String formattedWarehouseTotalAsset = decimalFormat.format(warehouseTotalAsset) + " 원";

        model.addAttribute("pendingOrders", pendingOrders);
        model.addAttribute("formattedTotalSalesAmount", formattedTotalSalesAmount);
        model.addAttribute("formattedWarehouseTotalAsset", formattedWarehouseTotalAsset);
        model.addAttribute("totalOrders", totalOrders);

        // 합계 계산 결과를 별도 변수에 담아 View로 전달
        model.addAttribute("totalOrders", totalOrders);

        // (4) home.html로 이동
        return "home/home";
    }
}

