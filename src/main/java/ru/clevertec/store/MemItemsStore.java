package ru.clevertec.store;

import ru.clevertec.model.Item;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;

import java.io.*;
import java.math.*;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class MemItemsStore implements Store<Item> {
    private Map<Integer, Item> map = new LinkedHashMap<>();

    @Override
    public Map<Integer, Item> getMap() {
        return map;
    }

    @Override
    public void loadDataFromFile(String path) {
        try (BufferedReader in = new BufferedReader(
                new FileReader(path, Charset.forName("UTF-8")))) {
            String line;
            while ((line = in.readLine()) != null) {
                String[] array = line.split(";");
                if (array.length == 0 || array.length < 4) {
                    throw new IllegalArgumentException("Check file with items");
                }
                Item item = new Item(Integer.parseInt(array[0]), array[1],
                        new BigDecimal(array[2]), Boolean.parseBoolean(array[3]));
                map.put(item.getId(), item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<CustomList<Item>> findAll() {
        CustomList<Item> listOfItems = new CustomArrayList<>();
        map.values().stream()
                .forEach(listOfItems::add);
        return Optional.of(listOfItems);
    }
}