package ru.clevertec.entity;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {

    private int id;
    private String name;
    private BigDecimal price;
    private boolean promotion;
    private int quantity;
}
