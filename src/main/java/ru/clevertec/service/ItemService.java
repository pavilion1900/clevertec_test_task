package ru.clevertec.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.clevertec.entity.Item;
import ru.clevertec.exception.RepositoryException;
import ru.clevertec.exception.ServiceException;
import ru.clevertec.repository.ItemRepository;
import ru.clevertec.task.collection.CustomList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemService implements Service<Item> {

    private static final ItemService INSTANCE = new ItemService();
    private static final Integer PAGE_SIZE_DEFAULT = 20;
    private static final Integer PAGE_DEFAULT = 0;
    private final ItemRepository itemRepository = ItemRepository.getInstance();

    public static ItemService getInstance() {
        return INSTANCE;
    }

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Item update(Integer id, Item item) {
        try {
            findById(id);
            item.setId(id);
            return itemRepository.update(item);
        } catch (RepositoryException e) {
            throw new ServiceException(
                    String.format("Item with id %d not updated", id));
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            findById(id);
            itemRepository.delete(id);
        } catch (RepositoryException e) {
            throw new ServiceException(
                    String.format("Item with id %d not deleted", id));
        }
    }

    @Override
    public CustomList<Item> findAll(String pageSizeStr, String pageStr) {
        Integer pageSize = PAGE_SIZE_DEFAULT;
        Integer page = PAGE_DEFAULT * pageSize;
        if (pageSizeStr != null) {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        if (pageStr != null) {
            page = Integer.parseInt(pageStr) * pageSize;
        }
        return itemRepository.findAll(pageSize, page);
    }

    @Override
    public Item findById(Integer id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ServiceException(
                        String.format("Item with id %d not found", id)));
    }
}
