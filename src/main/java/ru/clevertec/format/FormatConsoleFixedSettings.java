package ru.clevertec.format;

import ru.clevertec.model.Item;
import ru.clevertec.output.Output;
import ru.clevertec.task.collection.CustomList;

import java.math.*;

public class FormatConsoleFixedSettings implements Format {
    private CustomList<Item> list;
    private int discount;
    private BigDecimal value;
    private final Output out;

    public FormatConsoleFixedSettings(CustomList<Item> list, int discount, BigDecimal value, Output out) {
        this.list = list;
        this.discount = discount;
        this.value = value;
        this.out = out;
    }

    @Override
    public void setFormat() {
        out.println(String.join(System.lineSeparator(),
                RECEIPT, SUPERMARKET, ADDRESS, TEL, DATE, TIME, LINE, HEAD));
        for (int i = 0; i < list.size(); i++) {
            String elem;
            if (list.get(i).isPromotion() && list.get(i).getQuantity() > 5) {
                elem = String.format("%-4d%-15s%5s%8.2f%8.2f",
                        list.get(i).getQuantity(),
                        list.get(i).getName(),
                        "sale",
                        list.get(i).getPrice().multiply(BigDecimal.valueOf(0.9)),
                        list.get(i).getPrice().multiply(BigDecimal.valueOf(0.9))
                                .setScale(2, RoundingMode.HALF_UP)
                                .multiply(BigDecimal.valueOf(list.get(i).getQuantity()))
                );
            } else {
                elem = String.format("%-4d%-20s%8.2f%8.2f",
                        list.get(i).getQuantity(),
                        list.get(i).getName(),
                        list.get(i).getPrice().multiply(new BigDecimal(100)
                                        .subtract(new BigDecimal(discount))
                                        .divide(new BigDecimal(100)))
                                .setScale(2, RoundingMode.HALF_UP),
                        list.get(i).getPrice().multiply(new BigDecimal(100)
                                        .subtract(new BigDecimal(discount))
                                        .divide(new BigDecimal(100)))
                                .setScale(2, RoundingMode.HALF_UP)
                                .multiply(BigDecimal.valueOf(list.get(i).getQuantity()))
                );
            }
            out.println(elem);
        }
        out.println(LINE_2);
        out.println(String.format("%-12s%28.2f", "TAXABLE TOT.", value));
    }
}