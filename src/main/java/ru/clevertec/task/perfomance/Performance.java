package ru.clevertec.task.perfomance;

import java.util.List;

public class Performance<T> {
    private static final int ONE_MIL = 1_000_000;
    private Factory<T> factory;
    private int amountElements;
    private T value;

    public Performance(Factory<T> factory, int amountElements, T value) {
        this.factory = factory;
        this.amountElements = amountElements;
        this.value = value;
    }

    public Factory<T> getFactory() {
        return factory;
    }

    public int getAmountElements() {
        return amountElements;
    }

    private List<T> createNewListWithElements() {
        List<T> list = factory.createCollection();
        for (int i = 0; i < amountElements; i++) {
            list.add(value);
        }
        return list;
    }

    public long addNElementsToEnd() {
        List<T> list = factory.createCollection();
        long start = System.nanoTime();
        for (int i = 0; i < amountElements; i++) {
            list.add(value);
        }
        long finish = System.nanoTime();
        return (finish - start) / ONE_MIL;
    }

    public long addNElementsToHead() {
        List<T> list = createNewListWithElements();
        long start = System.nanoTime();
        for (int i = 0; i < amountElements; i++) {
            list.add(0, value);
        }
        long finish = System.nanoTime();
        return (finish - start) / ONE_MIL;
    }

    public long addNElementsToMiddle() {
        List<T> list = createNewListWithElements();
        long start = System.nanoTime();
        for (int i = 0; i < amountElements; i++) {
            list.add((list.size() / 2), value);
        }
        long finish = System.nanoTime();
        return (finish - start) / ONE_MIL;
    }

    public long getLastElementNTimes() {
        List<T> list = createNewListWithElements();
        long start = System.nanoTime();
        for (int i = 0; i < amountElements; i++) {
            list.get(list.size() - 1);
        }
        long finish = System.nanoTime();
        return (finish - start) / ONE_MIL;
    }

    public long getFirstElementNTimes() {
        List<T> list = createNewListWithElements();
        long start = System.nanoTime();
        for (int i = 0; i < amountElements; i++) {
            list.get(0);
        }
        long finish = System.nanoTime();
        return (finish - start) / ONE_MIL;
    }

    public long getMiddleElementNTimes() {
        List<T> list = createNewListWithElements();
        long start = System.nanoTime();
        for (int i = 0; i < amountElements; i++) {
            list.get(list.size() / 2);
        }
        long finish = System.nanoTime();
        return (finish - start) / ONE_MIL;
    }

    public long removeLastElementNTimes() {
        List<T> list = createNewListWithElements();
        long start = System.nanoTime();
        for (int i = 0; i < amountElements; i++) {
            list.remove(list.size() - 1);
        }
        long finish = System.nanoTime();
        return (finish - start) / ONE_MIL;
    }

    public long removeFirstElementNTimes() {
        List<T> list = createNewListWithElements();
        long start = System.nanoTime();
        for (int i = 0; i < amountElements; i++) {
            list.remove(0);
        }
        long finish = System.nanoTime();
        return (finish - start) / ONE_MIL;
    }

    public long removeMiddleElementNTimes() {
        List<T> list = createNewListWithElements();
        long start = System.nanoTime();
        for (int i = 0; i < amountElements; i++) {
            list.remove(list.size() / 2);
        }
        long finish = System.nanoTime();
        return (finish - start) / ONE_MIL;
    }
}
