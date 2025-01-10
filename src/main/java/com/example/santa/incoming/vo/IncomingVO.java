package com.example.santa.incoming.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncomingVO {
    private int incoming_id;
    private int warehouse_id;
    private int product_id;
    private int supplier_id;
    private int quantity;
    private LocalDateTime incoming_date;
}