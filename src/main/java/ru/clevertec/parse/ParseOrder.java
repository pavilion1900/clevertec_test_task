package ru.clevertec.parse;

import ru.clevertec.models.Item;

import java.util.List;

public interface ParseOrder {
    List<Item> getList();

    int getDiscount();
}
