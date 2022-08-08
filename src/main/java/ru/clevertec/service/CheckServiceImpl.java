package ru.clevertec.service;

import ru.clevertec.entity.Card;
import ru.clevertec.entity.Item;
import ru.clevertec.logic.Logics;
import ru.clevertec.parse.ParseOrder;
import ru.clevertec.parse.ParseOrderHttp;
import ru.clevertec.service.proxy.CheckServiceHandler;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;

import java.lang.reflect.Proxy;
import java.util.Map;

public class CheckServiceImpl implements CheckService {

    private static final CheckService INSTANCE = new CheckServiceImpl().getProxyCheckService();
    private static final String ID = "id";
    private static final String COUNT = "count";
    private static final String CARD = "card";
    private static ItemService itemService = ItemService.getInstance();
    private static CardService cardService = CardService.getInstance();

    private CheckServiceImpl() {
    }

    public static CheckService getInstance() {
        return INSTANCE;
    }

    @Override
    public CheckService getProxyCheckService() {
        CheckService checkService = this;
        ClassLoader classLoader = checkService.getClass().getClassLoader();
        Class<?>[] interfaces = checkService.getClass().getInterfaces();
        CheckService proxyCheckService = (CheckService) Proxy.newProxyInstance(
                classLoader, interfaces, new CheckServiceHandler(checkService));
        return proxyCheckService;
    }

    @Override
    public void calculateCheck(Map<String, String[]> map) {
        String[] arrayId = map.get(ID);
        String[] arrayCount = map.get(COUNT);
        String[] arrayCard = map.get(CARD);
        CustomList<Item> itemList = new CustomArrayList<>();
        for (int i = 0; i < arrayId.length; i++) {
            Item item = itemService.findById(Integer.parseInt(arrayId[i]));
            itemList.add(item);
        }
        Card card = cardService.findByNumber(arrayCard[0]);
        ParseOrder parseOrder = new ParseOrderHttp(itemList, arrayCount, card.getDiscount());
        Logics logics = new Logics(parseOrder.getList(), parseOrder.getDiscount());
        logics.printTxt();
        logics.printPdf();
    }
}
