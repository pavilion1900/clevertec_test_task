package ru.clevertec.mapper;

import org.springframework.stereotype.Component;
import ru.clevertec.dto.ItemDto;
import ru.clevertec.entity.Item;

@Component
public class ItemMapper implements Mapper<ItemDto, Item> {

    @Override
    public ItemDto map(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .promotion(item.isPromotion())
                .build();
    }
}
