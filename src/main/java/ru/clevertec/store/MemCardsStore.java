package ru.clevertec.store;

import ru.clevertec.model.Card;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MemCardsStore implements Store<Card> {
    private Map<Integer, Card> map = new HashMap<>();

    @Override
    public Map<Integer, Card> getMap() {
        return map;
    }

    @Override
    public void loadDataFromFile(String path) {
        try (BufferedReader in = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = in.readLine()) != null) {
                String[] array = line.split(";");
                if (array.length == 0 || array.length < 2) {
                    throw new IllegalArgumentException("Check file with cards");
                }
                Card card = new Card(Integer.parseInt(array[0]), Integer.parseInt(array[1]));
                map.put(card.getNumber(), card);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CustomList<Card> findAll() {
        Collection<Card> values = map.values();
        Iterator<Card> iterator = values.iterator();
        CustomList<Card> list = new CustomArrayList<>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }
}
