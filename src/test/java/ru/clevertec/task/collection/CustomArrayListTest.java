package ru.clevertec.task.collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CustomArrayListTest {

    private CustomList<Integer> list;

    @Before
    public void initData() {
        list = new CustomArrayList<>();
        list.add(22);
        list.add(33);
        list.add(44);
    }

    @Test
    public void checkIteratorNext() {
        Assert.assertEquals(Integer.valueOf(22), list.getIterator().next());
        Assert.assertEquals(Integer.valueOf(22), list.getIterator().next());
    }

    @Test
    public void checkIteratorNextAndHasNext() {
        CustomIterator<Integer> iterator = list.getIterator();
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(Integer.valueOf(22), iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(Integer.valueOf(33), iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(Integer.valueOf(44), iterator.next());
        Assert.assertFalse(iterator.hasNext());
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void checkIteratorHasNext() {
        list = new CustomArrayList<>(5);
        Assert.assertFalse(list.getIterator().hasNext());
    }

    @Test
    public void checkIteratorRemove() {
        CustomIterator<Integer> iterator = list.getIterator();
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(Integer.valueOf(22), iterator.next());
        iterator.remove();
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(Integer.valueOf(44), iterator.next());
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void checkIteratorAddBeforeAndAddAfter() {
        list.add(55);
        CustomIterator<Integer> iterator = list.getIterator();
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(Integer.valueOf(22), iterator.next());
        iterator.addBefore(100);
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(Integer.valueOf(100), iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(Integer.valueOf(33), iterator.next());
        iterator.addAfter(200);
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(Integer.valueOf(44), iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(Integer.valueOf(200), iterator.next());
    }

    @Test(expected = NoSuchElementException.class)
    public void checkIteratorNextWithException() {
        list = new CustomArrayList<>(5);
        list.getIterator().next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void checkIteratorNextWithException2() {
        CustomIterator<Integer> iterator = list.getIterator();
        list.add(4);
        iterator.next();
    }

    @Test
    public void checkMaxSize() {
        list = new CustomArrayList<>();
        list.setMaxSize(3);
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);
        Assert.assertEquals(3, list.size());
    }

    @Test
    public void checkMaxSize2() {
        list = new CustomArrayList<>();
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);
        list.setMaxSize(3);
        Assert.assertEquals(3, list.size());
    }

    @Test
    public void checkAdd() {
        list = new CustomArrayList<>();
        list.add(null);
        list.add(10);
        Assert.assertEquals(2, list.size());
        Assert.assertNull(list.get(0));
        Assert.assertEquals(Integer.valueOf(10), list.get(1));
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
        Assert.assertEquals(12, list.size());
    }

    @Test
    public void checkAddWithIndex() {
        list = new CustomArrayList<>();
        list.add(null);
        list.add(10);
        list.add(1, 100);
        Assert.assertEquals(3, list.size());
        Assert.assertEquals(Integer.valueOf(100), list.get(1));
    }

    @Test
    public void checkAddAllCollection() {
        CustomList<Integer> customList = new CustomArrayList<>();
        customList.add(70);
        customList.add(80);
        list.addAll(customList);
        Assert.assertEquals(5, list.size());
        Assert.assertEquals(Integer.valueOf(33), list.get(1));
        Assert.assertEquals(Integer.valueOf(80), list.get(4));
    }

    @Test
    public void checkAddAllArray() {
        Integer[] array = {70, 80};
        list.addAll(array);
        Assert.assertEquals(5, list.size());
        Assert.assertEquals(Integer.valueOf(33), list.get(1));
        Assert.assertEquals(Integer.valueOf(80), list.get(4));
    }

    @Test
    public void checkSet() {
        Assert.assertEquals(Integer.valueOf(33), list.set(1, 55));
        Assert.assertEquals(Integer.valueOf(55), list.get(1));
        Assert.assertEquals(3, list.size());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void checkSetByIncorrectIndex() {
        list.set(5, 22);
    }

    @Test
    public void checkRemoveAndSize() {
        Assert.assertEquals(3, list.size());
        Assert.assertEquals(Integer.valueOf(33), list.remove(1));
        Assert.assertEquals(2, list.size());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void checkRemoveByIncorrectIndex() {
        list.remove(5);
    }

    @Test
    public void checkRemoveAndGet() {
        list.remove(1);
        Assert.assertEquals(Integer.valueOf(22), list.get(0));
        Assert.assertEquals(Integer.valueOf(44), list.get(1));
    }

    @Test
    public void checkClear() {
        list.clear();
        Assert.assertEquals(0, list.size());
    }

    @Test
    public void checkFind() {
        list.add(88);
        list.add(44);
        Assert.assertEquals(2, list.find(44));
        Assert.assertEquals(-1, list.find(4444));
    }

    @Test
    public void checkFind2() {
        list.add(null);
        list.add(60);
        list.add(null);
        list.add(70);
        Assert.assertEquals(3, list.find(null));
        Assert.assertEquals(7, list.size());
        Assert.assertEquals(-1, list.find(4444));
    }

    @Test
    public void checkGetByCorrectIndex() {
        Assert.assertEquals(Integer.valueOf(22), list.get(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void checkGetByIncorrectIndex() {
        list.get(5);
    }

    @Test
    public void checkToArray() {
        Integer[] array = list.toArray(new Integer[list.size()]);
        Assert.assertEquals(3, array.length);
        Assert.assertEquals(Integer.valueOf(22), array[0]);
        Assert.assertEquals(Integer.valueOf(33), array[1]);
    }

    @Test
    public void checkSize() {
        Assert.assertEquals(3, list.size());
    }

    @Test
    public void checkTrim() {
        list.add(null);
        list.add(null);
        list.add(null);
        list.trim();
        Assert.assertEquals(3, list.size());
        Assert.assertEquals(Integer.valueOf(22), list.get(0));
        Assert.assertEquals(Integer.valueOf(33), list.get(1));
        Assert.assertEquals(Integer.valueOf(44), list.get(2));
    }
}