package ru.clevertec.parse;

import ru.clevertec.model.*;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;

import java.util.Map;

public class ParseOrderArray implements ParseOrder {
    private String[] args;
    private Map<Integer, Item> mapItems;
    private Map<Integer, Card> mapCards;
    private int discount;

    public ParseOrderArray(
            String[] args, Map<Integer, Item> mapItems, Map<Integer, Card> mapCards) {
        this.args = args;
        this.mapItems = mapItems;
        this.mapCards = mapCards;
    }

    @Override
    public CustomList<Item> getList() {
        CustomList<Item> list = new CustomArrayList<>();
        if (args.length == 0) {
            throw new IllegalArgumentException("Order is empty");
        }
        for (String elem : args) {
            String[] array = elem.split("-");
            if (array.length < 2) {
                throw new IllegalArgumentException("Check input data");
            }
            if (array[0].equals("card")) {
                if (mapCards.containsKey(Integer.parseInt(array[1]))) {
                    Card card = mapCards.get(Integer.parseInt(array[1]));
                    discount = card.getDiscount();
                }
                continue;
            }
            int itemId = Integer.parseInt(array[0]);
            if (!mapItems.containsKey(itemId)) {
                throw new IllegalArgumentException(
                        String.format("Product with id %d does not exist", itemId));
            }
            Item item = mapItems.get(itemId);
            int quantity = Integer.parseInt(array[1]);
            item.setQuantity(quantity);
            list.add(item);
        }
        return list;
    }

    @Override
    public int getDiscount() {
        return discount;
    }
}
