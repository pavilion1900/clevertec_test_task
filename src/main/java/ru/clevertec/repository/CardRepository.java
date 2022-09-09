package ru.clevertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.entity.Card;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Integer> {

    Optional<Card> findFirstByNumber(Integer number);
}
