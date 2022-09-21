package ru.clevertec.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.dto.CardDto;
import ru.clevertec.entity.Card;
import ru.clevertec.service.CardService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardRESTController {

    private final CardService service;

    @GetMapping
    public ResponseEntity<List<CardDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardDto> findById(@PathVariable("id") int id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/number/{number}")
    public ResponseEntity<CardDto> findById(@PathVariable("number") String number) {
        return ResponseEntity.ok(service.findByNumber(number));
    }

    @PostMapping
    public ResponseEntity<CardDto> save(@RequestBody Card card) {
        return ResponseEntity.ok(service.save(card));
    }

    @PutMapping
    public ResponseEntity<CardDto> update(@RequestBody Card card) {
        return ResponseEntity.ok(service.update(card.getId(), card));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
