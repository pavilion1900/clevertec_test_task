package ru.clevertec.parse;

import ru.clevertec.entity.Item;
import ru.clevertec.task.collection.CustomList;

public interface ParseOrder {
    CustomList<Item> getList();

    int getDiscount();
}
