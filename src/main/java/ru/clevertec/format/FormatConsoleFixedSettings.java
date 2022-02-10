package ru.clevertec.format;

import ru.clevertec.models.Item;
import ru.clevertec.output.Output;

import java.math.*;
import java.util.List;

public class FormatConsoleFixedSettings implements Format {
    private List<Item> list;
    private int discount;
    private BigDecimal value;
    private final Output out;

    public FormatConsoleFixedSettings(List<Item> list, int discount, BigDecimal value, Output out) {
        this.list = list;
        this.discount = discount;
        this.value = value;
        this.out = out;
    }

    @Override
    public void setFormat() {
        out.println(String.join(System.lineSeparator(),
                RECEIPT, SUPERMARKET, ADDRESS, TEL, DATE, TIME, LINE, HEAD));
        for (Item item : list) {
            String elem;
            if (item.isPromotion() && item.getQuantity() > 5) {
                elem = String.format("%-4d%-15s%5s%8.2f%8.2f",
                        item.getQuantity(),
                        item.getName(),
                        "sale",
                        item.getPrice().multiply(BigDecimal.valueOf(0.9)),
                        item.getPrice().multiply(BigDecimal.valueOf(0.9))
                                .setScale(2, RoundingMode.HALF_UP)
                                .multiply(BigDecimal.valueOf(item.getQuantity()))
                );
            } else {
                elem = String.format("%-4d%-20s%8.2f%8.2f",
                        item.getQuantity(),
                        item.getName(),
                        item.getPrice().multiply(new BigDecimal(100)
                                        .subtract(new BigDecimal(discount))
                                        .divide(new BigDecimal(100)))
                                .setScale(2, RoundingMode.HALF_UP),
                        item.getPrice().multiply(new BigDecimal(100)
                                        .subtract(new BigDecimal(discount))
                                        .divide(new BigDecimal(100)))
                                .setScale(2, RoundingMode.HALF_UP)
                                .multiply(BigDecimal.valueOf(item.getQuantity()))
                );
            }
            out.println(elem);
        }
        out.println(LINE_2);
        out.println(String.format("%-12s%28.2f", "TAXABLE TOT.", value));
    }
}