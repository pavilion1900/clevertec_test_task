package ru.clevertec.service;

import org.junit.jupiter.api.Test;
import ru.clevertec.entity.Card;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class CardServiceTest {

    private static final Integer PAGE_SIZE_DEFAULT = 20;
    private static final Integer PAGE_DEFAULT = 0;
    private static Service<Card> cardService = CardService.getInstance();

    @Test
    void whenAddCard() {
        Card first = cardService.add(new Card(1234, 5));
        assertThat(cardService.findById(first.getId())).isEqualTo(first);
    }

    @Test
    void whenUpdateCard() {
        Card card = new Card(1234, 5);
        Card cardWithId = cardService.add(card);
        cardWithId.setDiscount(10);
        Card updatedCard = cardService.update(cardWithId.getId(), card);
        assertThat(cardService.findById(cardWithId.getId())).isEqualTo(updatedCard);
    }

    @Test
    void whenDeleteCard() {
        Card card = cardService.add(new Card(1234, 5));
        cardService.delete(card.getId());
//        assertTrue();
    }

//    @Test
//    void whenFindAllCards() {
//        Card first = cardService.add(new Card(1234, 5));
//        Card second = cardService.add(new Card(1111, 1));
//        CustomList<Card> actualListOfCards = cardService
//                .findAll(String.valueOf(PAGE_SIZE_DEFAULT),
//                        String.valueOf(PAGE_DEFAULT));
//        CustomList<Card> expectedListOfCards = new CustomArrayList<>();
//        expectedListOfCards.add(first);
//        expectedListOfCards.add(second);
//        assertThat(actualListOfCards).isEqualTo(expectedListOfCards);
//    }
}