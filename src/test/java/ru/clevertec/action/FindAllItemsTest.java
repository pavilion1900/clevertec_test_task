package ru.clevertec.action;

import org.junit.jupiter.api.Test;
import ru.clevertec.input.Input;
import ru.clevertec.input.StubInput;
import ru.clevertec.model.Card;
import ru.clevertec.model.Item;
import ru.clevertec.output.Output;
import ru.clevertec.output.StubOutput;
import ru.clevertec.store.MemCardsStore;
import ru.clevertec.store.MemItemsStore;
import ru.clevertec.store.Store;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;

import static org.junit.jupiter.api.Assertions.*;

class FindAllItemsTest {

    @Test
    void execute() {
        Output out = new StubOutput();
        CustomList<String> inputData = new CustomArrayList<>();
        inputData.add("3-1 2-5 5-1 card-1234");
        Input in = new StubInput(inputData);
        Store<Item> itemStore = new MemItemsStore();
        Store<Card> cardStore = new MemCardsStore();
        MakeOrder order = new MakeOrder(out);
        order.execute(in, itemStore, cardStore);
        FindAllItems allItems = new FindAllItems(out);
        allItems.execute(in, itemStore, cardStore);
        String expected = String.join(System.lineSeparator(),
                "Item{id=28, description='Apple', price=1.12, promotion=true, quantity=0}",
                "Item{id=30, description='Watermelon', price=2.45, promotion=true, quantity=0}",
                "Item{id=38, description='Молоко', price=2.05, promotion=true, quantity=0}",
                "Item{id=26, description='Cherry', price=3.18, promotion=false, quantity=0}"
        );
        assertEquals(allItems.name(), "Find all items");
        assertTrue(out.toString().contains(expected));
    }
}