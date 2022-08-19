package ru.clevertec.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.clevertec.entity.Card;

import static org.assertj.core.api.Assertions.*;

class CardServiceTest {

//    private static final Integer PAGE_SIZE_DEFAULT = 20;
//    private static final Integer PAGE_DEFAULT = 0;
//    @Autowired
//    @Qualifier("cardService")
//    private static Service<Card> cardService;
//
//    @Test
//    void whenAddCard() {
//        Card card = cardService.save(Card.builder()
//                .number(1234)
//                .discount(5)
//                .build());
//        assertThat(cardService.findById(card.getId())).isEqualTo(card);
//    }
//
//    @Test
//    void whenUpdateCard() {
//        Card card = cardService.save(Card.builder()
//                .number(1234)
//                .discount(5)
//                .build());
//        Card cardWithId = cardService.save(card);
//        cardWithId.setDiscount(10);
//        Card updatedCard = cardService.update(cardWithId.getId(), card);
//        assertThat(cardService.findById(cardWithId.getId())).isEqualTo(updatedCard);
//    }
//
//    @Test
//    void whenDeleteCard() {
//        Card card = cardService.save(Card.builder()
//                .number(1234)
//                .discount(5)
//                .build());
//        cardService.delete(card.getId());
////        assertTrue();
//    }

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