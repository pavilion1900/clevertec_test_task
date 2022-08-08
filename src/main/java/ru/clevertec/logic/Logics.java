package ru.clevertec.logic;

import ru.clevertec.format.*;
import ru.clevertec.entity.Item;
import ru.clevertec.task.collection.CustomList;

import java.math.*;

public class Logics {

    private CustomList<Item> list;
    private int discount;
    private BigDecimal value;

    public Logics(CustomList<Item> list, int discount) {
        this.list = list;
        this.discount = discount;
        countValue();
    }

    private void countValue() {
        value = BigDecimal.valueOf(0);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isPromotion() && list.get(i).getQuantity() > 5) {
                value = value.add(list.get(i).getPrice()
                        .multiply(BigDecimal.valueOf(0.9))
                        .setScale(2, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(list.get(i).getQuantity()))
                );
            } else {
                value = value.add(list.get(i).getPrice()
                        .multiply(new BigDecimal(100)
                                .subtract(new BigDecimal(discount))
                                .divide(new BigDecimal(100)))
                        .setScale(2, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(list.get(i).getQuantity()))
                );
            }
        }
    }

    public BigDecimal getValue() {
        return value;
    }

    public void printTxt() {
        new FormatTxt(list, discount, value).setFormat();
    }

    public void printPdf() {
        new FormatPdf(list, discount, value).setFormat();
    }
}