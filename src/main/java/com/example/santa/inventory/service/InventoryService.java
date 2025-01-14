package com.example.santa.inventory.service;

import com.example.santa.inventory.mapper.InventoryMapper;
import com.example.santa.inventory.vo.InventoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryMapper inventoryMapper;

    //*************************************************
    //******************** 창고별 재고 조회 **************
    //*************************************************

    //창고별 재고 list

    //상품명 검색

    //창고 row 추가

    //*************************************************
    //******************** 품목별 재고 조회 **************
    //*************************************************

    //품목별 재고 list
    public List<InventoryDTO> readProductInventory(){
        return inventoryMapper.readProductInventory();
    }

}
