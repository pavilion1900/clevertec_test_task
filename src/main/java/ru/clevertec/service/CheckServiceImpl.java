package ru.clevertec.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.entity.Card;
import ru.clevertec.entity.Item;
import ru.clevertec.logic.LogicImpl;
import ru.clevertec.parse.ParseOrder;
import ru.clevertec.parse.ParseOrderHttp;
import ru.clevertec.repository.CardRepository;
import ru.clevertec.repository.ItemRepository;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;
import ru.clevertec.util.PropertiesUtil;

import java.io.OutputStream;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CheckServiceImpl implements CheckService {

    private final ItemRepository itemRepository;
    private final CardRepository cardRepository;

    @Override
    public void calculateCheck(Map<String, String[]> map, OutputStream out) {
        String[] arrayId = map.get(PropertiesUtil.getYamlProperties().getCheck().getId());
        String[] arrayCount = map.get(PropertiesUtil.getYamlProperties().getCheck().getCount());
        String[] arrayCard = map.get(PropertiesUtil.getYamlProperties().getCheck().getCard());
        CustomList<Item> itemList = new CustomArrayList<>();
        for (String id : arrayId) {
            itemRepository.findById(Integer.parseInt(id)).ifPresent(itemList::add);
        }
        Optional<Card> card = cardRepository.findFirstByNumber(Integer.parseInt(arrayCard[0]))
                .stream().findFirst();
        if (card.isPresent()) {
            ParseOrder parseOrder =
                    new ParseOrderHttp(itemList, arrayCount, card.get().getDiscount());
            LogicImpl logicImpl =
                    new LogicImpl(parseOrder.getList(), parseOrder.getDiscount(), out);
//            logicImpl.printTxt();
            logicImpl.printPdf();
        }
    }
}
