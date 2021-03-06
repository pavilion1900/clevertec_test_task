package ru.clevertec.store;

import ru.clevertec.model.Card;

import java.io.*;
import java.util.*;

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
                String[] array = line.split(" ");
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
    public List<Card> findAll() {
        return new ArrayList<>(map.values());
    }
}
