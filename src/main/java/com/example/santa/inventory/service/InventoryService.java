package com.example.santa.inventory.service;

import com.example.santa.inventory.mapper.InventoryMapper;
import com.example.santa.inventory.vo.InventoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InventoryService implements InventoryServiceInterface {

    private final InventoryMapper inventoryMapper;

    //*************************************************
    //******************** 창고별 재고 조회 **************
    //*************************************************

    //창고별 재고 list
    @Override
    public List<InventoryDTO> readInventory() {
        return inventoryMapper.readInventory();
    }

    //창고 list 선택
    @Override
    public List<InventoryDTO> getWarehouseList() {
        return inventoryMapper.getWarehouseList();
    }

    //창고 column 추가
    @Override
    public List<InventoryDTO> addWarehouse(int warehouseId) {
        return inventoryMapper.addWarehouse(warehouseId);
    }

    //창고 column 삭제

    //*************************************************
    //******************** 품목별 재고 조회 **************
    //*************************************************

    //품목별 재고 list
    @Override
    public List<InventoryDTO> readProductInventory(){
        return inventoryMapper.readProductInventory();
    }

}
