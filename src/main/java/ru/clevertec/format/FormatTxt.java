package ru.clevertec.format;

import ru.clevertec.entity.Item;
import ru.clevertec.task.collection.CustomList;
import ru.clevertec.util.PropertiesUtil;

import java.io.*;
import java.math.*;
import java.nio.charset.Charset;

public class FormatTxt implements Format {

    //    private static final String PATH = "src/main/resources/check.txt";
    private static final String PATH =
            "C:\\projects\\clevertec_test_task\\src\\main\\resources\\check.txt";
    private CustomList<Item> list;
    private int discount;
    private BigDecimal value;

    public FormatTxt(CustomList<Item> list, int discount, BigDecimal value) {
        this.list = list;
        this.discount = discount;
        this.value = value;
    }

    @Override
    public void setFormat() {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
                PATH, Charset.forName("UTF-8"))))) {
            out.println(String.join(System.lineSeparator(),
                    RECEIPT,
                    String.format("%27s", PropertiesUtil.get("check.supermarket")),
                    String.format("%33s", PropertiesUtil.get("check.address")),
                    String.format("%28s", PropertiesUtil.get("check.phone")),
                    DATE, TIME, LINE, HEAD));
            for (int i = 0; i < list.size(); i++) {
                String elem;
                if (list.get(i).isPromotion() && list.get(i).getQuantity()
                        > Integer.parseInt(PropertiesUtil.get("check.quantity.discount"))) {
                    elem = String.format("%-4d%-15s%5s%8.2f%8.2f",
                            list.get(i).getQuantity(),
                            list.get(i).getName(),
                            "sale",
                            list.get(i).getPrice().multiply(DISCOUNT_VALUE)
                                    .setScale(2, RoundingMode.HALF_UP),
                            list.get(i).getPrice().multiply(DISCOUNT_VALUE)
                                    .setScale(2, RoundingMode.HALF_UP)
                                    .multiply(BigDecimal.valueOf(list.get(i).getQuantity())));
                } else {
                    elem = String.format("%-4d%-20s%8.2f%8.2f",
                            list.get(i).getQuantity(),
                            list.get(i).getName(),
                            list.get(i).getPrice().multiply(new BigDecimal(ONE_HUNDRED)
                                            .subtract(new BigDecimal(discount))
                                            .divide(new BigDecimal(ONE_HUNDRED)))
                                    .setScale(2, RoundingMode.HALF_UP),
                            list.get(i).getPrice().multiply(new BigDecimal(ONE_HUNDRED)
                                            .subtract(new BigDecimal(discount))
                                            .divide(new BigDecimal(ONE_HUNDRED)))
                                    .setScale(2, RoundingMode.HALF_UP)
                                    .multiply(BigDecimal.valueOf(list.get(i).getQuantity()))
                    );
                }
                out.println(elem);
            }
            out.println(LINE_2);
            out.printf("%-12s%28.2f\n", TAXABLE_TOT, value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}