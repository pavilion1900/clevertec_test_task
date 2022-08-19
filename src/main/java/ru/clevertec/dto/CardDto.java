package ru.clevertec.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardDto {

    private int id;
    private int number;
    private int discount;
}
