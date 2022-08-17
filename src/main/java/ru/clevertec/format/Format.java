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
    String SALE = "sale";
    String RECEIPT = String.format("%26s", "CASH RECEIPT");
    String RECEIPT2 = "CASH RECEIPT";
    String DATE = String.format("%-15s%15s%s",
            "CASHIER: №1520", "DATE: ", DATE_TIME.format(FORMATTER_DATE));
    String CASHIER2 = "CASHIER: №1520";
    String DATE2 = "DATE: " + DATE_TIME.format(FORMATTER_DATE);
    String TIME = String.format("%30s%s", "TIME: ", DATE_TIME.format(FORMATTER_TIME));
    String TIME2 = "TIME: " + DATE_TIME.format(FORMATTER_TIME);
    String LINE = "-".repeat(41);
    String HEAD = String.format("%-4s%-20s%8s%8s", "QTY", "DESCRIPTION", "PRICE", "TOTAL");
    int ONE_HUNDRED = 100;
    BigDecimal DISCOUNT_VALUE = new BigDecimal(PropertiesUtil.get("check.discount"));
    String QTY = "QTY";
    String DESCRIPTION = "DESCRIPTION";
    String PRICE = "PRICE";
    String TOTAL = "TOTAL";
    String LINE_2 = "=".repeat(41);
    String TAXABLE_TOT = "TAXABLE TOT.";

    public void setFormat();
}
