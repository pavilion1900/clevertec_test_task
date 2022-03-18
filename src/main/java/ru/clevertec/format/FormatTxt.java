package ru.clevertec.format;

import ru.clevertec.model.Item;

import java.io.*;
import java.math.*;
import java.util.List;

public class FormatTxt implements Format {
    private List<Item> list;
    private int discount;
    private BigDecimal value;

    public FormatTxt(List<Item> list, int discount, BigDecimal value) {
        this.list = list;
        this.discount = discount;
        this.value = value;
    }

    @Override
    public void setFormat() {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
                "src/main/resources/Check.txt")))) {
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
                                    .multiply(BigDecimal.valueOf(item.getQuantity())));
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
            out.printf("%-12s%28.2f\n", "TAXABLE TOT.", value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}