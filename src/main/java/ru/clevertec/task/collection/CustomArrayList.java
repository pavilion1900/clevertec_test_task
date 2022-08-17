package ru.clevertec.task.collection;

import java.util.Objects;
import java.util.stream.Stream;

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
        maxCapacity = true;
        container = copyOf(container, maxSize);
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
        if (value == null) {
            for (int i = 0; i < size; i++) {
                if (container[i] == null) {
                    result = i;
                    break;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (value.equals(container[i])) {
                    result = i;
                    break;
                }
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
    public <T> T[] toArray(T[] array) {
        System.arraycopy(container, 0, array, 0, size);
        return array;
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

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof CustomList)) {
            return false;
        }

        final int expectedModCount = modCount;
        boolean equal = (o.getClass() == CustomArrayList.class)
                ? equalsCustomArrayList((CustomArrayList<?>) o)
                : equalsRange((CustomList<?>) o, 0, size);

        checkForComodification(expectedModCount);
        return equal;
    }

    boolean equalsRange(CustomList<?> other, int from, int to) {
        final Object[] es = container;
        if (to > es.length) {
            throw new ConcurrentModificationException();
        }
        var oit = other.getIterator();
        for (; from < to; from++) {
            if (!oit.hasNext() || !Objects.equals(es[from], oit.next())) {
                return false;
            }
        }
        return !oit.hasNext();
    }

    private boolean equalsCustomArrayList(CustomArrayList<?> other) {
        final int otherModCount = other.modCount;
        final int s = size;
        boolean equal = (s == other.size);
        if (equal) {
            final Object[] otherEs = other.container;
            final Object[] es = container;
            if (s > es.length || s > otherEs.length) {
                throw new ConcurrentModificationException();
            }
            for (int i = 0; i < s; i++) {
                if (!Objects.equals(es[i], otherEs[i])) {
                    equal = false;
                    break;
                }
            }
        }
        other.checkForComodification(otherModCount);
        return equal;
    }

    private void checkForComodification(final int expectedModCount) {
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }

    @Override
    public int hashCode() {
        int expectedModCount = modCount;
        int hash = hashCodeRange(0, size);
        checkForComodification(expectedModCount);
        return hash;
    }

    int hashCodeRange(int from, int to) {
        final Object[] es = container;
        if (to > es.length) {
            throw new ConcurrentModificationException();
        }
        int hashCode = 1;
        for (int i = from; i < to; i++) {
            Object e = es[i];
            hashCode = 31 * hashCode + (e == null ? 0 : e.hashCode());
        }
        return hashCode;
    }

    public Stream<E> stream() {
        E[] array = (E[]) new Object[size];
        System.arraycopy(container, 0, array, 0, size);
        return Stream.of(array);
    }

    private boolean grow() {
        boolean result = true;
        if (size == container.length) {
            if (maxCapacity) {
                System.out.println("Max capacity " + container.length + " for this collection");
                result = false;
            } else {
                int newCapacity = container.length * 2;
                container = copyOf(container, newCapacity);
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

    private E[] copyOf(E[] original, int newLength) {
        E[] copy = (E[]) new Object[newLength];
        if (maxCapacity) {
            System.arraycopy(original, 0, copy, 0, newLength);
        } else {
            System.arraycopy(original, 0, copy, 0, size);
        }
        return copy;
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
                if (cursor == 0) {
                    throw new IllegalStateException();
                }
                CustomArrayList.this.remove(cursor - 1);
                cursor--;
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
