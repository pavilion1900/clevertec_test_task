package ru.clevertec.task.collection;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(Integer.valueOf(22), list.getIterator().next());
        assertEquals(Integer.valueOf(22), list.getIterator().next());
    }

    @Test
    public void checkIteratorNextAndHasNext() {
        CustomIterator<Integer> iterator = list.getIterator();
        assertTrue(iterator.hasNext());
        assertEquals(Integer.valueOf(22), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(Integer.valueOf(33), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(Integer.valueOf(44), iterator.next());
        assertFalse(iterator.hasNext());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void checkIteratorHasNext() {
        list = new CustomArrayList<>(5);
        assertFalse(list.getIterator().hasNext());
    }

    @Test
    public void checkIteratorRemove() {
        CustomIterator<Integer> iterator = list.getIterator();
        assertTrue(iterator.hasNext());
        assertEquals(Integer.valueOf(22), iterator.next());
        iterator.remove();
        assertTrue(iterator.hasNext());
        assertEquals(Integer.valueOf(33), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(Integer.valueOf(44), iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void checkIteratorRemoveWithException() {
        CustomIterator<Integer> iterator = list.getIterator();
        assertThrows(IllegalStateException.class, () -> iterator.remove());
    }

    @Test
    public void checkIteratorAddBeforeAndAddAfter() {
        list.add(55);
        CustomIterator<Integer> iterator = list.getIterator();
        assertTrue(iterator.hasNext());
        assertEquals(Integer.valueOf(22), iterator.next());
        iterator.addBefore(100);
        assertTrue(iterator.hasNext());
        assertEquals(Integer.valueOf(100), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(Integer.valueOf(33), iterator.next());
        iterator.addAfter(200);
        assertTrue(iterator.hasNext());
        assertEquals(Integer.valueOf(44), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(Integer.valueOf(200), iterator.next());
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
        assertEquals(3, list.size());
    }

    @Test
    public void checkMaxSize2() {
        list = new CustomArrayList<>();
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);
        list.setMaxSize(3);
        assertEquals(3, list.size());
    }

    @Test
    public void checkAdd() {
        list = new CustomArrayList<>();
        list.add(null);
        list.add(10);
        assertEquals(2, list.size());
        assertNull(list.get(0));
        assertEquals(Integer.valueOf(10), list.get(1));
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
        assertEquals(12, list.size());
    }

    @Test
    public void checkAddWithIndex() {
        list = new CustomArrayList<>();
        list.add(null);
        list.add(10);
        list.add(1, 100);
        assertEquals(3, list.size());
        assertEquals(Integer.valueOf(100), list.get(1));
    }

    @Test
    public void checkAddAllCollection() {
        CustomList<Integer> customList = new CustomArrayList<>();
        customList.add(70);
        customList.add(80);
        list.addAll(customList);
        assertEquals(5, list.size());
        assertEquals(Integer.valueOf(33), list.get(1));
        assertEquals(Integer.valueOf(80), list.get(4));
    }

    @Test
    public void checkAddAllArray() {
        Integer[] array = {70, 80};
        list.addAll(array);
        assertEquals(5, list.size());
        assertEquals(Integer.valueOf(33), list.get(1));
        assertEquals(Integer.valueOf(80), list.get(4));
    }

    @Test
    public void checkSet() {
        assertEquals(Integer.valueOf(33), list.set(1, 55));
        assertEquals(Integer.valueOf(55), list.get(1));
        assertEquals(3, list.size());
    }

    @Test
    public void checkSetByIncorrectIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(5, 22));
    }

    @Test
    public void checkRemoveAndSize() {
        assertEquals(3, list.size());
        assertEquals(Integer.valueOf(33), list.remove(1));
        assertEquals(2, list.size());
    }

    @Test
    public void checkRemoveByIncorrectIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(5));
    }

    @Test
    public void checkRemoveAndGet() {
        list.remove(1);
        assertEquals(Integer.valueOf(22), list.get(0));
        assertEquals(Integer.valueOf(44), list.get(1));
    }

    @Test
    public void checkClear() {
        list.clear();
        assertEquals(0, list.size());
    }

    @Test
    public void checkFind() {
        list.add(88);
        list.add(44);
        assertEquals(2, list.find(44));
        assertEquals(-1, list.find(4444));
    }

    @Test
    public void checkFind2() {
        list.add(null);
        list.add(60);
        list.add(null);
        list.add(70);
        assertEquals(3, list.find(null));
        assertEquals(7, list.size());
        assertEquals(-1, list.find(4444));
    }

    @Test
    public void checkGetByCorrectIndex() {
        assertEquals(Integer.valueOf(22), list.get(0));
    }

    @Test
    public void checkGetByIncorrectIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(5));
    }

    @Test
    public void checkToArray() {
        Integer[] array = list.toArray(new Integer[list.size()]);
        assertEquals(3, array.length);
        assertEquals(Integer.valueOf(22), array[0]);
        assertEquals(Integer.valueOf(33), array[1]);
    }

    @Test
    public void checkSize() {
        assertEquals(3, list.size());
    }

    @Test
    public void checkTrim() {
        list.add(null);
        list.add(null);
        list.add(null);
        list.trim();
        assertEquals(3, list.size());
        assertEquals(Integer.valueOf(22), list.get(0));
        assertEquals(Integer.valueOf(33), list.get(1));
        assertEquals(Integer.valueOf(44), list.get(2));
    }
}