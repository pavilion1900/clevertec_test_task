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
import ru.clevertec.dto.ItemDto;
import ru.clevertec.entity.Item;
import ru.clevertec.service.ItemService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemRESTController {

    private final ItemService service;

    @GetMapping
    public List<ItemDto> findAll(HttpServletRequest request) {
        return service.findAll(request.getParameter("pageSize"), request.getParameter("page"));
    }

    @GetMapping("/{id}")
    public ItemDto findById(@PathVariable("id") int id) {
        return service.findById(id);
    }

    @PostMapping
    public ItemDto save(@RequestBody Item item) {
        return service.save(item);
    }

    @PutMapping
    public ItemDto update(@RequestBody Item item) {
        return service.update(item.getId(), item);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
