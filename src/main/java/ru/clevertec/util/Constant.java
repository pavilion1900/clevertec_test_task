package ru.clevertec.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Constant {

    public static final DateTimeFormatter FORMATTER_DATE
            = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter FORMATTER_TIME
            = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static final String CASHIER = "CASHIER: №1520";
    public static final LocalDateTime DATE_TIME = LocalDateTime.now();
    public static final String DATE = String.format("%-15s%15s%s",
            "CASHIER: №1520", "DATE: ", DATE_TIME.format(FORMATTER_DATE));
    public static final String DATE2 = "DATE: " + DATE_TIME.format(FORMATTER_DATE);
    public static final String DELIMITER = "=";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String EMPTY = "";
    public static final int ONE_HUNDRED = 100;
    public static final String QTY = "QTY";
    public static final String PRICE = "PRICE";
    public static final String RECEIPT = "CASH RECEIPT";
    public static final String TAXABLE_TOT = "TAXABLE TOT.";
    public static final String TIME
            = String.format("%30s%s", "TIME: ", DATE_TIME.format(FORMATTER_TIME));
    public static final String TIME2 = "TIME: " + DATE_TIME.format(FORMATTER_TIME);
    public static final String TOTAL = "TOTAL";
}
