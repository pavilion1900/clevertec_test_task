package ru.clevertec.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.dto.CardDto;
import ru.clevertec.entity.Card;

@Mapper(componentModel = "spring")
public interface CardMapper {

    CardDto toDto(Card card);
}
