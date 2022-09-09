package ru.clevertec.logic;

import ru.clevertec.entity.Item;
import ru.clevertec.format.FormatPdf;
import ru.clevertec.format.FormatTxt;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;
import ru.clevertec.util.PropertiesUtil;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class LogicImpl implements Logic {

    private static final int QUANTITY_DISCOUNT =
            PropertiesUtil.getYamlProperties().getCheck().getQuantityDiscount();
    private static final BigDecimal DISCOUNT =
            PropertiesUtil.getYamlProperties().getCheck().getDiscount();
    private static final int ONE_HUNDRED =
            PropertiesUtil.getYamlProperties().getCheck().getOneHundred();
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
        new FormatPdf(list, discount, value, out).setFormat();
    }
}