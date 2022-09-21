package ru.clevertec.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.dto.ItemDto;
import ru.clevertec.entity.Item;
import ru.clevertec.exception.ServiceException;
import ru.clevertec.mapper.ItemMapper;
import ru.clevertec.repository.ItemRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository repository;
    private final ItemMapper mapper;

    @Override
    @Transactional
    public ItemDto save(Item item) {
        return mapper.toDto(repository.save(item));
    }

    @Override
    public List<ItemDto> findAll(Pageable pageable) {
        return repository.findAll(pageable).stream()
                .map(mapper::toDto)
                .collect(toList());
    }

    @Override
    public ItemDto findById(Integer id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ServiceException(
                        String.format("Item with id %d not found", id)));
    }

    @Override
    @Transactional
    public ItemDto update(Integer id, Item item) {
        if (repository.findById(id).isEmpty()) {
            throw new ServiceException(String.format("Item with id %d not exist", id));
        }
        item.setId(id);
        return mapper.toDto(repository.save(item));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (repository.findById(id).isEmpty()) {
            throw new ServiceException(String.format("Item with id %d not exist", id));
        }
        repository.deleteById(id);
    }
}
