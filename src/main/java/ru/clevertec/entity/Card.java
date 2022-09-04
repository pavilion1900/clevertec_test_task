package ru.clevertec.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {

    private int id;
    private int number;
    private int discount;
}
