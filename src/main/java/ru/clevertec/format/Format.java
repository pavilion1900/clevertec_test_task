package ru.clevertec.format;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface Format {
    DateTimeFormatter FORMATTER_DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter FORMATTER_TIME = DateTimeFormatter.ofPattern("HH:mm:ss");
    LocalDateTime DATE_TIME = LocalDateTime.now();
    String RECEIPT = String.format("%26s", "CASH RECEIPT");
    String SUPERMARKET = String.format("%27s", "SUPERMARKET 123");
    String ADDRESS = String.format("%33s", "12, MILKYWAY Galaxy / Earth");
    String TEL = String.format("%28s", "Tel: 123-456-7890");
    String DATE = String.format("%-15s%15s%s",
            "CASHIER: №1520", "DATE: ", DATE_TIME.format(FORMATTER_DATE));
    String TIME = String.format("%30s%s", "TIME: ", DATE_TIME.format(FORMATTER_TIME));
    String LINE = "-----------------------------------------";
    String HEAD = String.format("%-4s%-20s%8s%8s", "QTY", "DESCRIPTION", "PRICE", "TOTAL");
    String LINE_2 = "=========================================";

    public void setFormat();
}
