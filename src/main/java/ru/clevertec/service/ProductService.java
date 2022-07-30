package ru.clevertec.service;

import ru.clevertec.input.Input;
import ru.clevertec.logic.Logics;
import ru.clevertec.entity.Card;
import ru.clevertec.entity.Item;
import ru.clevertec.output.Output;
import ru.clevertec.parse.ParseOrder;
import ru.clevertec.parse.ParseOrderArray;
import ru.clevertec.service.proxy.ProductServiceHandler;
import ru.clevertec.store.Store;
import ru.clevertec.task.collection.CustomList;

import java.lang.reflect.Proxy;

public class ProductService implements Service {

    @Override
    public Service getProxyService() {
        Service service = this;
        ClassLoader classLoader = service.getClass().getClassLoader();
        Class<?>[] interfaces = service.getClass().getInterfaces();
        Service proxyService = (Service) Proxy.newProxyInstance(
                classLoader, interfaces, new ProductServiceHandler(service));
        return proxyService;
    }

    @Override
    public CustomList<Card> findAllCards(Output out, Input in,
                                         Store<Item> itemStore, Store<Card> cardStore) {
        return cardStore.findAll();
    }

    @Override
    public CustomList<Item> findAllItems(Output out, Input in,
                                         Store<Item> itemStore, Store<Card> cardStore) {
        return itemStore.findAll();
    }

    @Override
    public void makeOrder(Output out, Input in, Store<Item> itemStore, Store<Card> cardStore) {
        String order = in.askStr("Enter your order ");
        String[] array = order.split(" ");
        ParseOrder parseOrder =
                new ParseOrderArray(array, itemStore.findAll(), cardStore.findAll());
        Logics logics = new Logics(parseOrder.getList(), parseOrder.getDiscount(), out);
        logics.printTxt();
        logics.printConsole();
    }
}
