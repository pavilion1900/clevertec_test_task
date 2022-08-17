package ru.clevertec.task.collection;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

public class CustomArrayListTest {

    private CustomList<Integer> list;

    @BeforeEach
    public void initData() {
        list = new CustomArrayList<>();
        list.add(22);
        list.add(33);
        list.add(44);
    }

    @AfterEach
    public void deleteData() {
        list = null;
    }

    @Test
    public void checkIteratorNext() {
        assertAll(
                () -> assertThat(list.getIterator().next()).isEqualTo(Integer.valueOf(22)),
                () -> assertThat(list.getIterator().next()).isEqualTo(Integer.valueOf(22))
        );
    }

    @Test
    public void checkIteratorNextAndHasNext() {
        CustomIterator<Integer> iterator = list.getIterator();
        assertAll(
                () -> assertTrue(iterator.hasNext()),
                () -> assertThat(iterator.next()).isEqualTo(Integer.valueOf(22)),
                () -> assertTrue(iterator.hasNext()),
                () -> assertThat(iterator.next()).isEqualTo(Integer.valueOf(33)),
                () -> assertTrue(iterator.hasNext()),
                () -> assertThat(iterator.next()).isEqualTo(Integer.valueOf(44)),
                () -> assertFalse(iterator.hasNext()),
                () -> assertFalse(iterator.hasNext())
        );
    }

    @Test
    public void checkIteratorHasNext() {
        list = new CustomArrayList<>(5);
        assertFalse(list.getIterator().hasNext());
    }

    @Test
    public void checkIteratorRemove() {
        CustomIterator<Integer> iterator = list.getIterator();
        iterator.next();
        iterator.remove();
        assertAll(
                () -> assertTrue(iterator.hasNext()),
                () -> assertThat(iterator.next()).isEqualTo(Integer.valueOf(33)),
                () -> assertTrue(iterator.hasNext()),
                () -> assertThat(iterator.next()).isEqualTo(Integer.valueOf(44)),
                () -> assertFalse(iterator.hasNext())
        );
    }

    @Test
    public void checkIteratorRemoveWithException() {
        CustomIterator<Integer> iterator = list.getIterator();
        assertThrows(IllegalStateException.class, () -> iterator.remove());
    }

    @Test
    public void checkIteratorAddBefore() {
        list.add(55);
        CustomIterator<Integer> iterator = list.getIterator();
        iterator.next();
        iterator.addBefore(100);
        assertAll(
                () -> assertTrue(iterator.hasNext()),
                () -> assertThat(iterator.next()).isEqualTo(Integer.valueOf(100)),
                () -> assertTrue(iterator.hasNext()),
                () -> assertThat(iterator.next()).isEqualTo(Integer.valueOf(33))
        );
    }

    @Test
    public void checkIteratorNextWithException() {
        list = new CustomArrayList<>(5);
        assertThrows(NoSuchElementException.class, () -> list.getIterator().next());
    }

    @Test
    public void checkIteratorNextWithException2() {
        CustomIterator<Integer> iterator = list.getIterator();
        list.add(4);
        assertThrows(ConcurrentModificationException.class, () -> iterator.next());
    }

    @Test
    public void checkMaxSize() {
        list = new CustomArrayList<>();
        list.setMaxSize(3);
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);
        assertThat(list.size()).isEqualTo(3);
    }

    @Test
    public void checkMaxSize2() {
        list = new CustomArrayList<>();
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);
        list.setMaxSize(3);
        assertThat(list.size()).isEqualTo(3);
    }

    @Test
    public void checkAdd() {
        list = new CustomArrayList<>();
        list.add(null);
        list.add(10);
        assertAll(
                () -> assertThat(list.size()).isEqualTo(2),
                () -> assertNull(list.get(0)),
                () -> assertThat(list.get(1)).isEqualTo(Integer.valueOf(10))
        );
    }

    @Test
    public void checkAddMoreThanDefaultCapacity() {
        list.add(11);
        list.add(52);
        list.add(61);
        list.add(72);
        list.add(83);
        list.add(95);
        list.add(105);
        list.add(112);
        list.add(122);
        assertThat(list.size()).isEqualTo(12);
    }

    @Test
    public void checkAddWithIndex() {
        list = new CustomArrayList<>();
        list.add(null);
        list.add(10);
        list.add(1, 100);
        assertAll(
                () -> assertThat(list.size()).isEqualTo(3),
                () -> assertThat(list.get(1)).isEqualTo(Integer.valueOf(100))
        );
    }

    @Test
    public void checkAddAllCollection() {
        CustomList<Integer> customList = new CustomArrayList<>();
        customList.add(70);
        customList.add(80);
        list.addAll(customList);
        assertAll(
                () -> assertThat(list.size()).isEqualTo(5),
                () -> assertThat(list.get(1)).isEqualTo(Integer.valueOf(33)),
                () -> assertThat(list.get(4)).isEqualTo(Integer.valueOf(80))
        );
    }

    @Test
    public void checkAddAllArray() {
        Integer[] array = {70, 80};
        list.addAll(array);
        assertAll(
                () -> assertThat(list.size()).isEqualTo(5),
                () -> assertThat(list.get(1)).isEqualTo(Integer.valueOf(33)),
                () -> assertThat(list.get(4)).isEqualTo(Integer.valueOf(80))
        );
    }

    @Test
    public void checkSet() {
        assertAll(
                () -> assertThat(list.set(1, 55)).isEqualTo(Integer.valueOf(33)),
                () -> assertThat(list.get(1)).isEqualTo(Integer.valueOf(55)),
                () -> assertThat(list.size()).isEqualTo(3)
        );
    }

    @Test
    public void checkSetByIncorrectIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(5, 22));
    }

    @Test
    public void checkRemoveAndSize() {
        assertAll(
                () -> assertThat(list.size()).isEqualTo(3),
                () -> assertThat(list.remove(1)).isEqualTo(Integer.valueOf(33)),
                () -> assertThat(list.size()).isEqualTo(2)
        );

    }

    @Test
    public void checkRemoveByIncorrectIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(5));
    }

    @Test
    public void checkRemoveAndGet() {
        list.remove(1);
        assertAll(
                () -> assertThat(list.get(0)).isEqualTo(Integer.valueOf(22)),
                () -> assertThat(list.get(1)).isEqualTo(Integer.valueOf(44))
        );

    }

    @Test
    public void checkClear() {
        list.clear();
        assertThat(list.size()).isEqualTo(0);
    }

    @Test
    public void checkFind() {
        list.add(88);
        list.add(44);
        assertAll(
                () -> assertThat(list.find(44)).isEqualTo(2),
                () -> assertThat(list.find(4444)).isEqualTo(-1)
        );
    }

    @Test
    public void checkFind2() {
        list.add(null);
        list.add(60);
        list.add(null);
        list.add(70);
        assertAll(
                () -> assertThat(list.find(null)).isEqualTo(3),
                () -> assertThat(list.size()).isEqualTo(7),
                () -> assertThat(list.find(4444)).isEqualTo(-1)
        );
    }

    @Test
    public void checkGetByCorrectIndex() {
        assertThat(list.get(0)).isEqualTo(Integer.valueOf(22));
    }

    @Test
    public void checkGetByIncorrectIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(5));
    }

    @Test
    public void checkToArray() {
        Integer[] array = list.toArray(new Integer[list.size()]);
        assertAll(
                () -> assertThat(array.length).isEqualTo(3),
                () -> assertThat(array[0]).isEqualTo(Integer.valueOf(22)),
                () -> assertThat(array[1]).isEqualTo(Integer.valueOf(33))
        );
    }

    @Test
    public void checkSize() {
        assertThat(list.size()).isEqualTo(3);
    }

    @Test
    public void checkTrim() {
        list.add(null);
        list.add(null);
        list.add(null);
        list.trim();
        assertAll(
                () -> assertThat(list.size()).isEqualTo(3),
                () -> assertThat(list.get(0)).isEqualTo(Integer.valueOf(22)),
                () -> assertThat(list.get(1)).isEqualTo(Integer.valueOf(33)),
                () -> assertThat(list.get(2)).isEqualTo(Integer.valueOf(44))
        );
    }
}