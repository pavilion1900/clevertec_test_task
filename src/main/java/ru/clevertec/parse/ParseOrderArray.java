package ru.clevertec.parse;

import ru.clevertec.entity.Card;
import ru.clevertec.entity.Item;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;

import java.util.Map;
import java.util.stream.Collectors;

public class ParseOrderArray implements ParseOrder {

    private final String[] args;
    private final CustomList<Item> itemList;
    private final CustomList<Card> cardList;
    private int discount;

    public ParseOrderArray(
            String[] args, CustomList<Item> itemList, CustomList<Card> cardList) {
        this.args = args;
        this.itemList = new CustomArrayList<>(itemList);
        this.cardList = new CustomArrayList<>(cardList);
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
                Map<Integer, Card> mapCards = cardList.stream()
                        .collect(Collectors.toMap(Card::getNumber, value -> value));
                if (mapCards.containsKey(Integer.parseInt(array[1]))) {
                    Card card = mapCards.get(Integer.parseInt(array[1]));
                    discount = card.getDiscount();
                }
                continue;
            }
            int itemId = Integer.parseInt(array[0]);
            Map<Integer, Item> mapItems = itemList.stream()
                    .collect(Collectors.toMap(Item::getId, value -> value));
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
