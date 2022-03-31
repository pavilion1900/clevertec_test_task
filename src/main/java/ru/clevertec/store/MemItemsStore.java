package ru.clevertec.store;

import ru.clevertec.model.Item;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;

import java.io.*;
import java.math.*;
import java.util.*;

public class MemItemsStore implements Store<Item> {
    private Map<Integer, Item> map = new HashMap<>();

    @Override
    public Map<Integer, Item> getMap() {
        return map;
    }

    @Override
    public void loadDataFromFile(String path) {
        try (BufferedReader in = new BufferedReader(new FileReader(path))) {
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
    public CustomList<Item> findAll() {
        Collection<Item> values = map.values();
        Iterator<Item> iterator = values.iterator();
        CustomList<Item> list = new CustomArrayList<>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }
}