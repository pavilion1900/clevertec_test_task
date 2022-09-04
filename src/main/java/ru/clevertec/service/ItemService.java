package ru.clevertec.service;

import lombok.RequiredArgsConstructor;
import ru.clevertec.dto.ItemDto;
import ru.clevertec.entity.Item;
import ru.clevertec.exception.RepositoryException;
import ru.clevertec.exception.ServiceException;
import ru.clevertec.mapper.Mapper;
import ru.clevertec.repository.ItemRepository;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ItemService implements Service<ItemDto, Item> {

    private static final Integer PAGE_SIZE_DEFAULT = 20;
    private static final Integer PAGE_DEFAULT = 0;
    private final ItemRepository repository;
    private final Mapper<ItemDto, Item> mapper;

    @Override
    public ItemDto save(Item item) {
        return mapper.map(repository.save(item));
    }

    @Override
    public CustomList<ItemDto> findAll(String pageSizeStr, String pageStr) {
        Integer pageSize = PAGE_SIZE_DEFAULT;
        Integer page = PAGE_DEFAULT * pageSize;
        if (pageSizeStr != null) {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        if (pageStr != null) {
            page = Integer.parseInt(pageStr) * pageSize;
        }
        CustomList<ItemDto> itemDtoList = new CustomArrayList<>();
        repository.findAll(pageSize, page).stream()
                .map(mapper::map)
                .forEach(itemDtoList::add);
        return itemDtoList;
    }

    @Override
    public ItemDto findById(Integer id) {
        return repository.findById(id)
                .map(mapper::map)
                .orElseThrow(() -> new ServiceException(
                        String.format("Item with id %d not found", id)));
    }

    @Override
    public ItemDto update(Integer id, Item item) {
        try {
            findById(id);
            item.setId(id);
            return mapper.map(repository.update(item));
        } catch (RepositoryException e) {
            throw new ServiceException(
                    String.format("Item with id %d not updated", id));
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            findById(id);
            repository.delete(id);
        } catch (RepositoryException e) {
            throw new ServiceException(
                    String.format("Item with id %d not deleted", id));
        }
    }
}
