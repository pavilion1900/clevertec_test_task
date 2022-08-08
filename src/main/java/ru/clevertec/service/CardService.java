package ru.clevertec.service;

import ru.clevertec.entity.Card;
import ru.clevertec.exception.CardNotFoundException;
import ru.clevertec.repository.CardRepository;
import ru.clevertec.task.collection.CustomList;

public class CardService implements Service<Card> {

    private static final CardService INSTANCE = new CardService();
    private static final Integer PAGE_SIZE_DEFAULT = 20;
    private static final Integer PAGE_DEFAULT = 0;
    private final CardRepository cardRepository = CardRepository.getInstance();

    private CardService() {
    }

    public static CardService getInstance() {
        return INSTANCE;
    }

    @Override
    public Card add(Card card) {
        return cardRepository.add(card);
    }

    @Override
    public Card update(Integer id, Card card) {
        try {
            findById(id);
            card.setId(id);
            return cardRepository.update(card);
        } catch (CardNotFoundException e) {
            throw new CardNotFoundException(
                    String.format("Card with id %d not updated", id));
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            findById(id);
            cardRepository.delete(id);
        } catch (CardNotFoundException e) {
            throw new CardNotFoundException(
                    String.format("Card with id %d not found", id));
        }
    }

    @Override
    public CustomList<Card> findAll(String pageSizeStr, String pageStr) {
        Integer pageSize = PAGE_SIZE_DEFAULT;
        Integer page = PAGE_DEFAULT * pageSize;
        if (pageSizeStr != null) {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        if (pageStr != null) {
            page = Integer.parseInt(pageStr) * pageSize;
        }
        return cardRepository.findAll(pageSize, page);
    }

    @Override
    public Card findById(Integer id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException(
                        String.format("Card with id %d not found", id)));
    }

    public Card findByNumber(String number) {
        return cardRepository.findByNumber(Integer.parseInt(number))
                .orElseThrow(() -> new CardNotFoundException(
                        String.format("Card with number %s not found", number)));
    }
}
