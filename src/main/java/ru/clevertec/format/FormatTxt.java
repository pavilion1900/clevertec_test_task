package ru.clevertec.format;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.clevertec.entity.Item;
import ru.clevertec.service.CheckInfo;
import ru.clevertec.service.CheckService;
import ru.clevertec.task.collection.CustomList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FormatTxt implements Format {

    @Value("${check.pathTxtCheck}")
    private final String pathTxtCheck;
    @Value("${check.encoding}")
    private final String encoding;
    @Value("${check.supermarketName}")
    private final String supermarketName;
    @Value("${check.address}")
    private final String address;
    @Value("${check.phone}")
    private final String phone;
    @Value("${check.quantityDiscount}")
    private final int quantityDiscount;
    @Value("${check.sale}")
    private final String sale;
    private final CheckService service;

    @Override
    @SneakyThrows
    public ResponseEntity<byte[]> setFormat(Map<String, String[]> map) {
        CheckInfo checkInfo = service.calculateCheck(map);
        CustomList<Item> list = checkInfo.getItemList();
        int discount = checkInfo.getCard().getDiscount();
        BigDecimal value = checkInfo.getValue();
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(pathTxtCheck,
                Charset.forName(encoding))))) {
            out.println(String.join(System.lineSeparator(),
                    String.format("%26s", RECEIPT),
                    String.format("%27s", supermarketName),
                    String.format("%33s", address),
                    String.format("%28s", phone),
                    DATE, TIME, DELIMITER.repeat(42),
                    String.format("%-4s%-20s%8s%8s", QTY, DESCRIPTION, PRICE, TOTAL)));
            for (int i = 0; i < list.size(); i++) {
                String elem;
                if (list.get(i).isPromotion() && list.get(i).getQuantity()
                        > quantityDiscount) {
                    elem = String.format("%-4d%-15s%5s%8.2f%8.2f",
                            list.get(i).getQuantity(),
                            list.get(i).getName(),
                            sale,
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
        }
        byte[] content = Files.readAllBytes(Path.of(pathTxtCheck));
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .contentLength(content.length)
                .body(content);
    }
}
