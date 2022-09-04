package ru.clevertec.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.entity.Card;
import ru.clevertec.entity.Item;
import ru.clevertec.logic.LogicImpl;
import ru.clevertec.parse.ParseOrder;
import ru.clevertec.parse.ParseOrderHttp;
import ru.clevertec.repository.CardRepository;
import ru.clevertec.repository.ItemRepository;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;

import java.io.OutputStream;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckServiceImpl implements CheckService {

    private static final String ID = "id";
    private static final String COUNT = "count";
    private static final String CARD = "card";
    private final ItemRepository itemRepository;
    private final CardRepository cardRepository;

    @Override
    public void calculateCheck(Map<String, String[]> map, OutputStream out) {
        String[] arrayId = map.get(ID);
        String[] arrayCount = map.get(COUNT);
        String[] arrayCard = map.get(CARD);
        CustomList<Item> itemList = new CustomArrayList<>();
        for (String id : arrayId) {
            itemRepository.findById(Integer.parseInt(id)).ifPresent(itemList::add);
        }
        Optional<Card> card = cardRepository.findByNumber(Integer.parseInt(arrayCard[0]));
        if (card.isPresent()) {
            ParseOrder parseOrder =
                    new ParseOrderHttp(itemList, arrayCount, card.get().getDiscount());
            LogicImpl logicImpl =
                    new LogicImpl(parseOrder.getList(), parseOrder.getDiscount(), out);
            logicImpl.printTxt();
            logicImpl.printPdf();
        }
    }
}
