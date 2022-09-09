package ru.clevertec.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.dto.CardDto;
import ru.clevertec.entity.Card;
import ru.clevertec.exception.ServiceException;
import ru.clevertec.mapper.CardMapper;
import ru.clevertec.repository.CardRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    @Value("${check.pageSizeDefault}")
    private int pageSize;
    @Value("${check.pageDefault}")
    private int page;
    private final CardRepository repository;
    private final CardMapper mapper;

    @Override
    @Transactional
    public CardDto save(Card card) {
        return mapper.toDto(repository.save(card));
    }

    @Override
    public List<CardDto> findAll(String pageSizeStr, String pageStr) {
        if (pageSizeStr != null) {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        if (pageStr != null) {
            page = Integer.parseInt(pageStr);
        }
        return repository.findAll(PageRequest.of(page, pageSize)).stream()
                .map(mapper::toDto)
                .collect(toList());
    }

    @Override
    public CardDto findById(Integer id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ServiceException(
                        String.format("Card with id %d not found", id)));
    }

    @Override
    public CardDto findByNumber(String number) {
        return repository.findFirstByNumber(Integer.parseInt(number))
                .map(mapper::toDto)
                .orElseThrow(() -> new ServiceException(
                        String.format("Card with number %s not found", number)));
    }

    @Override
    @Transactional
    public CardDto update(Integer id, Card card) {
        if (repository.findById(id).isEmpty()) {
            throw new ServiceException(String.format("Card with id %d not exist", id));
        }
        card.setId(id);
        return mapper.toDto(repository.save(card));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (repository.findById(id).isEmpty()) {
            throw new ServiceException(String.format("Card with id %d not exist", id));
        }
        repository.deleteById(id);
    }
}
