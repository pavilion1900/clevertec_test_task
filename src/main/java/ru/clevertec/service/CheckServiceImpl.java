package ru.clevertec.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.entity.Card;
import ru.clevertec.entity.Item;
import ru.clevertec.repository.CardRepository;
import ru.clevertec.repository.ItemRepository;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CheckServiceImpl implements CheckService {

    @Value("${check.discount}")
    private final BigDecimal discount;
    @Value("${check.oneHundred}")
    private final int oneHundred;
    @Value("${check.quantityDiscount}")
    private final int quantityDiscount;
    @Value("${check.id}")
    private final String id;
    @Value("${check.count}")
    private final String count;
    @Value("${check.card}")
    private final String card;
    private final ItemRepository itemRepository;
    private final CardRepository cardRepository;

    @Override
    public CheckInfo calculateCheck(Map<String, String[]> map) {
        String[] arrayId = map.get(id);
        String[] arrayCount = map.get(count);
        String[] arrayCard = map.get(card);
        CustomList<Item> itemList = new CustomArrayList<>();
        for (int i = 0; i < arrayId.length; i++) {
            int itemId = Integer.parseInt(arrayId[i]);
            int itemCount = Integer.parseInt(arrayCount[i]);
            itemRepository.findById(itemId)
                    .map(item -> {
                        item.setQuantity(itemCount);
                        return item;
                    })
                    .ifPresent(itemList::add);
        }
        Optional<Card> card = cardRepository.findFirstByNumber(Integer.parseInt(arrayCard[0]))
                .stream().findFirst();
        BigDecimal value = countValue(itemList, card.get().getDiscount());
        return new CheckInfo(itemList, card.get(), value);
    }

    private BigDecimal countValue(CustomList<Item> itemList, int discount) {
        BigDecimal value = BigDecimal.valueOf(0);
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).isPromotion() && itemList.get(i).getQuantity()
                    > quantityDiscount) {
                value = value.add(itemList.get(i).getPrice()
                        .multiply(this.discount)
                        .setScale(2, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(itemList.get(i).getQuantity()))
                );
            } else {
                value = value.add(itemList.get(i).getPrice()
                        .multiply(new BigDecimal(oneHundred)
                                .subtract(new BigDecimal(discount))
                                .divide(new BigDecimal(oneHundred)))
                        .setScale(2, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(itemList.get(i).getQuantity()))
                );
            }
        }
        return value;
    }
}
