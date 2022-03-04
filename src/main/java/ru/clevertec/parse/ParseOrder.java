package ru.clevertec.parse;

import ru.clevertec.model.Item;

import java.util.List;

public interface ParseOrder {
    List<Item> getList();

    int getDiscount();
}
