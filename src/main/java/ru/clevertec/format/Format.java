package ru.clevertec.format;

import ru.clevertec.util.PropertiesUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface Format {

    DateTimeFormatter FORMATTER_DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter FORMATTER_TIME = DateTimeFormatter.ofPattern("HH:mm:ss");
    LocalDateTime DATE_TIME = LocalDateTime.now();
    String EMPTY = "";
    String RECEIPT = "CASH RECEIPT";
    String CASHIER = "CASHIER: №1520";
    String DATE = String.format("%-15s%15s%s",
            "CASHIER: №1520", "DATE: ", DATE_TIME.format(FORMATTER_DATE));
    String DATE2 = "DATE: " + DATE_TIME.format(FORMATTER_DATE);
    String TIME = String.format("%30s%s", "TIME: ", DATE_TIME.format(FORMATTER_TIME));
    String TIME2 = "TIME: " + DATE_TIME.format(FORMATTER_TIME);
    String DELIMITER = "=";
    int ONE_HUNDRED = PropertiesUtil.getYamlProperties().getCheck().getOneHundred();
    BigDecimal DISCOUNT_VALUE = PropertiesUtil.getYamlProperties().getCheck().getDiscount();
    String QTY = "QTY";
    String DESCRIPTION = "DESCRIPTION";
    String PRICE = "PRICE";
    String TOTAL = "TOTAL";
    String TAXABLE_TOT = "TAXABLE TOT.";

    void setFormat();
}
