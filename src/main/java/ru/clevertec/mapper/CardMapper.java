package ru.clevertec.mapper;

import org.springframework.stereotype.Component;
import ru.clevertec.dto.CardDto;
import ru.clevertec.entity.Card;

@Component
public class CardMapper implements Mapper<CardDto, Card> {

    @Override
    public CardDto map(Card card) {
        return CardDto.builder()
                .id(card.getId())
                .number(card.getNumber())
                .discount(card.getDiscount())
                .build();
    }
}
