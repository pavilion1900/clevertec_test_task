package ru.clevertec.logic;

import ru.clevertec.entity.Item;
import ru.clevertec.format.FormatPdf;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LogicImpl implements Logic {

    private static final int QUANTITY_DISCOUNT = 5;
    private static final BigDecimal DISCOUNT = BigDecimal.valueOf(0.9);
    private static final int ONE_HUNDRED = 100;
    private final CustomList<Item> list;
    private final int discount;
    private BigDecimal value;

    public LogicImpl(CustomList<Item> list, int discount) {
        this.list = new CustomArrayList<>(list);
        this.discount = discount;
        countValue();
    }

    private void countValue() {
        value = BigDecimal.valueOf(0);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isPromotion() && list.get(i).getQuantity() > QUANTITY_DISCOUNT) {
                value = value.add(list.get(i).getPrice()
                        .multiply(DISCOUNT)
                        .setScale(2, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(list.get(i).getQuantity()))
                );
            } else {
                value = value.add(list.get(i).getPrice()
                        .multiply(new BigDecimal(ONE_HUNDRED)
                                .subtract(new BigDecimal(discount))
                                .divide(new BigDecimal(ONE_HUNDRED)))
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

//    public void printTxt() {
//        new FormatTxt(list, discount, value).setFormat();
//    }

    public void printPdf() {
        new FormatPdf(list, discount, value).setFormat();
    }
}