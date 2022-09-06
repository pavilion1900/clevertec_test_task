package ru.clevertec.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.dto.CardDto;
import ru.clevertec.entity.Card;
import ru.clevertec.exception.RepositoryException;
import ru.clevertec.exception.ServiceException;
import ru.clevertec.mapper.Mapper;
import ru.clevertec.repository.CardRepository;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;
import ru.clevertec.util.PropertiesUtil;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository repository;
    private final Mapper<CardDto, Card> mapper;

    @Override
    @Transactional
    public CardDto save(Card card) {
        return mapper.map(repository.save(card));
    }

    @Override
    public CustomList<CardDto> findAll(String pageSizeStr, String pageStr) {
        int pageSize = PropertiesUtil.getYamlProperties().getCheck().getPageSizeDefault();
        int page = PropertiesUtil.getYamlProperties().getCheck().getPageDefault() * pageSize;
        if (pageSizeStr != null) {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        if (pageStr != null) {
            page = Integer.parseInt(pageStr) * pageSize;
        }
        CustomList<CardDto> cardDtoList = new CustomArrayList<>();
        repository.findAll(PageRequest.of(page, pageSize)).stream()
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

    @Override
    public CardDto findByNumber(String number) {
        return repository.findFirstByNumber(Integer.parseInt(number))
                .map(mapper::map)
                .orElseThrow(() -> new ServiceException(
                        String.format("Card with number %s not found", number)));
    }

    @Override
    @Transactional
    public CardDto update(Integer id, Card card) {
        try {
            findById(id);
            card.setId(id);
            return mapper.map(repository.save(card));
        } catch (RepositoryException e) {
            throw new ServiceException(
                    String.format("Card with id %d not updated", id));
        }
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        try {
            findById(id);
            repository.deleteById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(
                    String.format("Card with id %d not deleted", id));
        }
    }
}
