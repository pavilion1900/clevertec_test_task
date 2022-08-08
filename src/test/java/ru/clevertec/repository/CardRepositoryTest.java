package ru.clevertec.repository;

import org.junit.jupiter.api.*;
import ru.clevertec.entity.Card;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

public class CardRepositoryTest {

    private static final Integer PAGE_SIZE_DEFAULT = 20;
    private static final Integer PAGE_DEFAULT = 0;
    private static CardRepository cardRepository = CardRepository.getInstance();

    @Test
    void whenAddCard() {
        Card first = cardRepository.add(new Card(1234, 5));
        Card second = cardRepository.add(new Card(1111, 1));
        assertThat(cardRepository.findById(first.getId()).get()).isEqualTo(first);
    }

    @Test
    void whenUpdateCard() {
        Card card = cardRepository.add(new Card(1234, 5));
        card.setDiscount(10);
        Card updatedCard = cardRepository.update(card);
        assertThat(cardRepository.findById(card.getId()).get()).isEqualTo(updatedCard);
    }

    @Test
    void whenDeleteCard() {
        Card card = cardRepository.add(new Card(1234, 5));
        assertTrue(cardRepository.delete(card.getId()));
    }

    @Test
    void whenFindByNumber() {
        Card card = cardRepository.add(new Card(1234, 5));
        assertThat(cardRepository.findByNumber(card.getNumber()).get()).isEqualTo(card);
    }

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
