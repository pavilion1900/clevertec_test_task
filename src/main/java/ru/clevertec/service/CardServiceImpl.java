package ru.clevertec.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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

    private final CardRepository repository;
    private final CardMapper mapper;

    @Override
    @Transactional
    public CardDto save(Card card) {
        return mapper.toDto(repository.save(card));
    }

    @Override
    public List<CardDto> findAll(Pageable pageable) {
        return repository.findAll(pageable).stream()
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
