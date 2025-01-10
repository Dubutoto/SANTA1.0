package com.example.santa.inventory.controller;

import com.example.santa.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    //창고별 재고 관리
    @GetMapping("warehouseManagement")
    public String warehouseManagement(Model model) {

        return "inventory/warehouseManagement";
    }

    //품목별 재고 관리
    @GetMapping("categoryManagement")
    public String categoryManagement(Model model) {

        return "inventory/categoryManagement";
    }

    //재고 이력 관리
    @GetMapping("historyManagement")
    public String historyManagement(Model model) {

        return "inventory/historyManagement";
    }

    //창고 재고
    @GetMapping("statistics")
    public String statistics(Model model) {

        return "inventory/statistics";
    }


}
