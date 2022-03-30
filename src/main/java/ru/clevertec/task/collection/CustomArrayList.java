package ru.clevertec.task.collection;

public class CustomArrayList<E> implements CustomList<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private boolean maxCapacity;
    private E[] container;
    private int size;
    private int modCount;

    public CustomArrayList() {
        this.container = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public CustomArrayList(int capacity) {
        this.container = (E[]) new Object[capacity];
    }

    @Override
    public void setMaxSize(int maxSize) {
        container = copyOf(container, maxSize);
        maxCapacity = true;
    }

    @Override
    public void add(E value) {
        if (grow()) {
            modCount++;
            container[size++] = value;
        }
    }

    @Override
    public void add(int index, E value) {
        checkIndex(index);
        if (container.length == size) {
            container = copyOf(container, container.length + DEFAULT_CAPACITY);
        }
        System.arraycopy(container, index, container, index + 1, size - index);
        container[index] = value;
        modCount++;
        size++;
    }

    @Override
    public void addAll(CustomList<? extends E> list) {
        E[] array = (E[]) new Object[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }
        addAll(array);
    }

    @Override
    public void addAll(E[] array) {
        int freeCapacityContainer = container.length - size;
        if (freeCapacityContainer >= array.length) {
            System.arraycopy(array, 0, container, size, array.length);
        } else {
            int oldCapacity = container.length;
            int newCapacity = oldCapacity + (array.length - freeCapacityContainer);
            container = copyOf(container, newCapacity);
            System.arraycopy(array, 0, container, size, array.length);
        }
        size += array.length;
        modCount++;
    }

    @Override
    public E set(int index, E newValue) {
        checkIndex(index);
        E oldValue = container[index];
        container[index] = newValue;
        return oldValue;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        E oldValue = container[index];
        System.arraycopy(container, index + 1, container, index, size - index - 1);
        container[size - 1] = null;
        size--;
        modCount++;
        return oldValue;
    }

    @Override
    public void clear() {
        modCount++;
        container = (E[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    @Override
    public int find(E value) {
        int result = -1;
        for (int i = 0; i < size; i++) {
            if (container[i].equals(value)) {
                result = i;
                break;
            }
        }
        return result;
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return container[index];
    }

    @Override
    public E[] toArray(E[] array) {
        return (E[]) copyOf(container, size);
    }

    @Override
    public int size() {
        return maxCapacity ? container.length : size;
    }

    @Override
    public void trim() {
        for (int i = 0; i < size; i++) {
            while (container[i] == null) {
                System.arraycopy(container, i + 1, container, i, size - i - 1);
                container[size - 1] = null;
                size--;
                modCount++;
                if (i == size) {
                    break;
                }
            }
        }
    }

    private boolean grow() {
        boolean result = true;
        if (size == container.length) {
            if (maxCapacity) {
                System.out.println("Max capacity " + container.length + " for this collection");
                result = false;
            } else {
                int oldCapacity = container.length;
                container = copyOf(container, oldCapacity * 2);
            }
        }
        return result;
    }

    private int checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index not exist");
        }
        return index;
    }

    private E[] copyOf(E[] array, int newLength) {
        E[] newArray = (E[]) new Object[newLength];
        System.arraycopy(array, 0, newArray, 0, newLength);
        return newArray;
    }

    @Override
    public CustomIterator<E> getIterator() {
        return new CustomIterator<E>() {
            private int cursor = 0;
            private int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                if (maxCapacity) {
                    size = container.length;
                }
                return cursor < size;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return container[cursor++];
            }

            @Override
            public void remove() {
                CustomArrayList.this.remove(cursor);
                expectedModCount = modCount;
            }

            @Override
            public void addBefore(E value) {
                CustomArrayList.this.add(cursor, value);
                expectedModCount = modCount;
            }

            @Override
            public void addAfter(E value) {
                CustomArrayList.this.add(cursor + 1, value);
                expectedModCount = modCount;
            }
        };
    }
}
