package com.example.santa.incoming.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncomingDTO {
    private String incomingDate;
    private String supplierName;
    private String productName;
    private String warehouseName;
    private double price;
    private int quantity;
}