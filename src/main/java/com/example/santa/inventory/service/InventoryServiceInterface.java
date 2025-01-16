package com.example.santa.inventory.service;

import com.example.santa.inventory.vo.InventoryDTO;

import java.util.List;

public interface InventoryServiceInterface {
    //창고별 재고 list
    List<InventoryDTO> readInventory();

    //창고 list 선택
    List<InventoryDTO> getWarehouseList();

    //창고 column 추가
    List<InventoryDTO> addWarehouse(int warehouseId);

    //품목별 재고 list
    List<InventoryDTO> readProductInventory();
}
