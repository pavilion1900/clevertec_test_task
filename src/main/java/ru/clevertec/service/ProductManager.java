package ru.clevertec.service;

import ru.clevertec.input.Input;
import ru.clevertec.logic.Logics;
import ru.clevertec.model.Card;
import ru.clevertec.model.Item;
import ru.clevertec.output.Output;
import ru.clevertec.parse.ParseOrder;
import ru.clevertec.parse.ParseOrderArray;
import ru.clevertec.service.proxy.ProductManagerHandler;
import ru.clevertec.store.Store;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;
import ru.clevertec.validator.ValidateCardWithRegEx;
import ru.clevertec.validator.ValidateItemWithRegEx;

import java.lang.reflect.Proxy;
import java.util.Comparator;
import java.util.Optional;

public class ProductManager implements Manager {

    @Override
    public Manager getProxyManger() {
        Manager manager = this;
        ClassLoader classLoader = manager.getClass().getClassLoader();
        Class<?>[] interfaces = manager.getClass().getInterfaces();
        Manager proxyManager = (Manager) Proxy.newProxyInstance(
                classLoader, interfaces, new ProductManagerHandler(manager));
        return proxyManager;
    }

    @Override
    public CustomList<Card> findAllCards(Output out, Input in, Store itemStore, Store cardStore) {
        System.out.println("40");
        CustomList<Card> cardList = new CustomArrayList<>();
        System.out.println("50");
        Optional<CustomList<Card>> optionalOfCards = cardStore.findAll();
        if (optionalOfCards.isPresent()) {
            optionalOfCards.get().stream()
                    .sorted(Comparator.comparing(Card::getNumber))
                    .peek(cardList::add)
                    .forEach(out::println);
        }
        return cardList;
    }

    @Override
    public CustomList<Item> findAllItems(Output out, Input in, Store itemStore, Store cardStore) {
        CustomList<Item> itemList = new CustomArrayList<>();
        Optional<CustomList<Item>> optionalOfItems = itemStore.findAll();
        if (optionalOfItems.isPresent()) {
            optionalOfItems.get().stream()
                    .sorted(Comparator.comparing(Item::getId))
                    .peek(itemList::add)
                    .forEach(out::println);
        }
        return itemList;
    }

    @Override
    public void makeOrder(Output out, Input in, Store itemStore, Store cardStore) {
        new ValidateItemWithRegEx().validate();
        new ValidateCardWithRegEx().validate();
        itemStore.loadDataFromFile("src/main/resources/rightItemData.txt");
        cardStore.loadDataFromFile("src/main/resources/rightCardData.txt");
        String order = in.askStr("Enter your order ");
        String[] array = order.split(" ");
        ParseOrder parseOrder = new ParseOrderArray(array, itemStore.getMap(), cardStore.getMap());
        Logics logics = new Logics(parseOrder.getList(), parseOrder.getDiscount(), out);
        logics.printTxt();
        logics.printConsole();
    }
}
