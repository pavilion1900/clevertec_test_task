package ru.clevertec.logic;

import ru.clevertec.format.*;
import ru.clevertec.entity.Item;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;

import java.io.OutputStream;
import java.math.*;

public class LogicImpl implements Logic {

    private final CustomList<Item> list;
    private final int discount;
    private BigDecimal value;
    private final OutputStream out;

    public LogicImpl(CustomList<Item> list, int discount, OutputStream out) {
        this.list = new CustomArrayList<>(list);
        this.discount = discount;
        this.out = out;
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

    @Override
    public BigDecimal getValue() {
        return value;
    }

    public void printTxt() {
        new FormatTxt(list, discount, value).setFormat();
    }

    public void printPdf() {
        new FormatPdf(list, discount, value, out).setFormat();
    }
}