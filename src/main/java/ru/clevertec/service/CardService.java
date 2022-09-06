package ru.clevertec.service;

import ru.clevertec.dto.CardDto;
import ru.clevertec.entity.Card;

public interface CardService extends Service<CardDto, Card> {

    CardDto findByNumber(String number);
}
