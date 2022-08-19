package ru.clevertec.service;

import lombok.RequiredArgsConstructor;
import ru.clevertec.dto.CardDto;
import ru.clevertec.entity.Card;
import ru.clevertec.exception.RepositoryException;
import ru.clevertec.exception.ServiceException;
import ru.clevertec.mapper.CardMapper;
import ru.clevertec.repository.CardRepository;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class CardService implements Service<CardDto, Card> {

    private static final Integer PAGE_SIZE_DEFAULT = 20;
    private static final Integer PAGE_DEFAULT = 0;
    private final CardRepository repository;
    private final CardMapper mapper;

    @Override
    public CardDto save(Card card) {
        return mapper.map(repository.save(card));
    }

    @Override
    public CustomList<CardDto> findAll(String pageSizeStr, String pageStr) {
        Integer pageSize = PAGE_SIZE_DEFAULT;
        Integer page = PAGE_DEFAULT * pageSize;
        if (pageSizeStr != null) {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        if (pageStr != null) {
            page = Integer.parseInt(pageStr) * pageSize;
        }
        CustomList<CardDto> cardDtoList = new CustomArrayList<>();
        repository.findAll(pageSize, page).stream()
                .map(mapper::map)
                .forEach(cardDtoList::add);
        return cardDtoList;
    }

    @Override
    public CardDto findById(Integer id) {
        return repository.findById(id)
                .map(mapper::map)
                .orElseThrow(() -> new ServiceException(
                        String.format("Card with id %d not found", id)));
    }

    public CardDto findByNumber(String number) {
        return repository.findByNumber(Integer.parseInt(number))
                .map(mapper::map)
                .orElseThrow(() -> new ServiceException(
                        String.format("Card with number %s not found", number)));
    }

    @Override
    public CardDto update(Integer id, Card card) {
        try {
            findById(id);
            card.setId(id);
            return mapper.map(repository.update(card));
        } catch (RepositoryException e) {
            throw new ServiceException(
                    String.format("Card with id %d not updated", id));
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            findById(id);
            repository.delete(id);
        } catch (RepositoryException e) {
            throw new ServiceException(
                    String.format("Card with id %d not deleted", id));
        }
    }
}
