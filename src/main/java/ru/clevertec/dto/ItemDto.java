package ru.clevertec.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDto {

    private int id;
    private String name;
    private BigDecimal price;
    private boolean promotion;
    private int quantity;
}
