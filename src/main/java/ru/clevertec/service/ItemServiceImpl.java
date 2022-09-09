package ru.clevertec.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.dto.ItemDto;
import ru.clevertec.entity.Item;
import ru.clevertec.exception.RepositoryException;
import ru.clevertec.exception.ServiceException;
import ru.clevertec.mapper.Mapper;
import ru.clevertec.repository.ItemRepository;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;
import ru.clevertec.util.PropertiesUtil;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository repository;
    private final Mapper<ItemDto, Item> mapper;

    @Override
    @Transactional
    public ItemDto save(Item item) {
        return mapper.map(repository.save(item));
    }

    @Override
    public CustomList<ItemDto> findAll(String pageSizeStr, String pageStr) {
        int pageSize = PropertiesUtil.getYamlProperties().getCheck().getPageSizeDefault();
        int page = PropertiesUtil.getYamlProperties().getCheck().getPageDefault() * pageSize;
        if (pageSizeStr != null) {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        if (pageStr != null) {
            page = Integer.parseInt(pageStr) * pageSize;
        }
        CustomList<ItemDto> itemDtoList = new CustomArrayList<>();
        repository.findAll(PageRequest.of(page, pageSize)).stream()
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
    @Transactional
    public ItemDto update(Integer id, Item item) {
        try {
            findById(id);
            item.setId(id);
            return mapper.map(repository.save(item));
        } catch (RepositoryException e) {
            throw new ServiceException(
                    String.format("Item with id %d not updated", id));
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
                    String.format("Item with id %d not deleted", id));
        }
    }
}
