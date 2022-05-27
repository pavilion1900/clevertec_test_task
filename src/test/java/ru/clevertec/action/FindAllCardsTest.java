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

class FindAllCardsTest {

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
        FindAllCards allCards = new FindAllCards(out);
        allCards.execute(in, itemStore, cardStore);
        String expected = String.join(System.lineSeparator(),
                "Card{number=1234, discount=5}",
                "Card{number=1111, discount=1}",
                "Card{number=2222, discount=20}",
                "Card{number=1, discount=9}"
        );
        assertEquals(allCards.name(), "Find all cards");
        assertTrue(out.toString().contains(expected));
    }
}