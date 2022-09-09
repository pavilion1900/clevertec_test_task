package ru.clevertec.controller;

import lombok.RequiredArgsConstructor;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardRESTController {

    private final CardService service;

    @GetMapping
    public List<CardDto> findAll(HttpServletRequest request) {
        return service.findAll(request.getParameter("pageSize"), request.getParameter("page"));
    }

    @GetMapping("/{id}")
    public CardDto findById(@PathVariable("id") int id) {
        return service.findById(id);
    }

    @GetMapping("/number/{number}")
    public CardDto findById(@PathVariable("number") String number) {
        return service.findByNumber(number);
    }

    @PostMapping
    public CardDto save(@RequestBody Card card) {
        return service.save(card);
    }

    @PutMapping
    public CardDto update(@RequestBody Card card) {
        return service.update(card.getId(), card);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
