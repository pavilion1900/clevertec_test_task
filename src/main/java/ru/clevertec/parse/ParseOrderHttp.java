package ru.clevertec.parse;

import ru.clevertec.entity.Item;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;

public class ParseOrderHttp implements ParseOrder {

    private final CustomList<Item> itemList;
    private final String[] arrayCount;
    private final int discount;

    public ParseOrderHttp(CustomList<Item> itemList, String[] arrayCount, int discount) {
        this.itemList = new CustomArrayList<>(itemList);
        this.arrayCount = arrayCount;
        this.discount = discount;
    }

    @Override
    public CustomList<Item> getList() {
        for (int i = 0; i < itemList.size(); i++) {
            int quantity = Integer.parseInt(arrayCount[i]);
            itemList.get(i).setQuantity(quantity);
        }
        return itemList;
    }

    @Override
    public int getDiscount() {
        return discount;
    }
}
