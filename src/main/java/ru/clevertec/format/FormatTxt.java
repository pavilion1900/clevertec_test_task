package ru.clevertec.format;

import ru.clevertec.entity.Item;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;

public class FormatTxt implements Format {

    //    private static final String PATH = "src/main/resources/check.txt";
    private static final String PATH =
            "C:\\projects\\clevertec_test_task\\src\\main\\resources\\txt\\check.txt";
    private static final String ENCODING = "UTF-8";
    private static final String SUPERMARKET_NAME = "SUPERMARKET 123";
    private static final String ADDRESS = "12, MILKYWAY Galaxy / Earth";
    private static final String PHONE = "Tel: 123-456-7890";
    private static final int QUANTITY_DISCOUNT = 5;
    private static final String SALE = "sale";
    private final CustomList<Item> list;
    private final int discount;
    private final BigDecimal value;

    public FormatTxt(CustomList<Item> list, int discount, BigDecimal value) {
        this.list = new CustomArrayList<>(list);
        this.discount = discount;
        this.value = value;
    }

    @Override
    public void setFormat() {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(PATH,
                Charset.forName(ENCODING))))) {
            out.println(String.join(System.lineSeparator(),
                    String.format("%26s", RECEIPT),
                    String.format("%27s", SUPERMARKET_NAME),
                    String.format("%33s", ADDRESS),
                    String.format("%28s", PHONE),
                    DATE, TIME, DELIMITER.repeat(42),
                    String.format("%-4s%-20s%8s%8s", QTY, DESCRIPTION, PRICE, TOTAL)));
            for (int i = 0; i < list.size(); i++) {
                String elem;
                if (list.get(i).isPromotion() && list.get(i).getQuantity()
                        > QUANTITY_DISCOUNT) {
                    elem = String.format("%-4d%-15s%5s%8.2f%8.2f",
                            list.get(i).getQuantity(),
                            list.get(i).getName(),
                            SALE,
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
            out.println(DELIMITER.repeat(42));
            out.printf("%-12s%28.2f\n", TAXABLE_TOT, value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
