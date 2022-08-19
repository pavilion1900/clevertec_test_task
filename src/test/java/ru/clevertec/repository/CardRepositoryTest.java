package ru.clevertec.repository;

import org.junit.jupiter.api.*;
import ru.clevertec.entity.Card;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

public class CardRepositoryTest {

//    private static final Integer PAGE_SIZE_DEFAULT = 20;
//    private static final Integer PAGE_DEFAULT = 0;
//    private static CardRepository cardRepository = CardRepository.getInstance();
//
//    @Test
//    void whenSaveCard() {
//        Card card = cardRepository.save(Card.builder().
//                number(1234)
//                .discount(5)
//                .build());
//        assertThat(cardRepository.findById(card.getId()).get()).isEqualTo(card);
//    }
//
//    @Test
//    void whenUpdateCard() {
//        Card card = cardRepository.save(Card.builder().
//                number(1234)
//                .discount(5)
//                .build());
//        card.setDiscount(10);
//        Card updatedCard = cardRepository.update(card);
//        assertThat(cardRepository.findById(card.getId()).get()).isEqualTo(updatedCard);
//    }
//
//    @Test
//    void whenDeleteCard() {
//        Card card = cardRepository.save(Card.builder().
//                number(1234)
//                .discount(5)
//                .build());
//        assertTrue(cardRepository.delete(card.getId()));
//    }

//    @Test
//    void whenFindByNumber() {
//        Card card = cardRepository.save(Card.builder().
//                number(1234)
//                .discount(5)
//                .build());
//        assertThat(cardRepository.findByNumber(card.getNumber()).get()).isEqualTo(card);
//    }

//    @Test
//    void whenFindAllCards() {
//        Card first = cardRepository.add(new Card(1234, 5));
//        Card second = cardRepository.add(new Card(1111, 1));
//        CustomList<Card> actualListOfCards = cardRepository
//                .findAll(PAGE_SIZE_DEFAULT, PAGE_DEFAULT);
//        CustomList<Card> expectedListOfCards = new CustomArrayList<>();
//        expectedListOfCards.add(first);
//        expectedListOfCards.add(second);
//        assertThat(actualListOfCards).isEqualTo(expectedListOfCards);
//    }
}
