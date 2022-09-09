package ru.clevertec.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.clevertec.dto.ItemDto;
import ru.clevertec.entity.Item;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    @Mapping(target = "quantity", ignore = true)
    ItemDto toDto(Item item);
}
