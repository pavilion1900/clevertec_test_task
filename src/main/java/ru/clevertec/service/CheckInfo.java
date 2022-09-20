package ru.clevertec.service;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.entity.Card;
import ru.clevertec.entity.Item;
import ru.clevertec.task.collection.CustomList;

import java.math.BigDecimal;

@Data
@Builder
public class CheckInfo {

    private final CustomList<Item> itemList;
    private final Card card;
    private final BigDecimal value;
}
