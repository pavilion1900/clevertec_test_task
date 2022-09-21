package ru.clevertec.format;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static ru.clevertec.util.Constant.CASHIER;
import static ru.clevertec.util.Constant.DATE2;
import static ru.clevertec.util.Constant.DELIMITER;
import static ru.clevertec.util.Constant.DESCRIPTION;
import static ru.clevertec.util.Constant.EMPTY;
import static ru.clevertec.util.Constant.ONE_HUNDRED;
import static ru.clevertec.util.Constant.PRICE;
import static ru.clevertec.util.Constant.QTY;
import static ru.clevertec.util.Constant.RECEIPT;
import static ru.clevertec.util.Constant.TAXABLE_TOT;
import static ru.clevertec.util.Constant.TIME2;
import static ru.clevertec.util.Constant.TOTAL;

@Component
@RequiredArgsConstructor
public class FormatPdf implements Format {

    @Value("${check.address}")
    private final String address;
    @Value("${check.discountValue}")
    private final BigDecimal discountValue;
    @Value("${check.quantityDiscount}")
    private final int quantityDiscount;
    @Value("${check.pathFont}")
    private final String pathFont;
    @Value("${check.pathPdfCheck}")
    private final String pathPdfCheck;
    @Value("${check.phone}")
    private final String phone;
    @Value("${check.sale}")
    private final String sale;
    @Value("${check.supermarketName}")
    private final String supermarketName;
    @Value("${check.tableWidth}")
    private final float tableWidth;
    private final CheckService service;

    @Override
    @SneakyThrows
    public ResponseEntity<byte[]> setFormat(Map<String, String[]> map) {
        CheckInfo checkInfo = service.calculateCheck(map);
        CustomList<Item> list = checkInfo.getItemList();
        int discount = checkInfo.getCard().getDiscount();
        BigDecimal value = checkInfo.getValue();
        PdfWriter pdfWriter = new PdfWriter(pathPdfCheck);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        PdfFont pdfFont = PdfFontFactory.createFont(pathFont, PdfEncodings.IDENTITY_H);
        Document document = new Document(pdfDocument);
        float[] columnWidth = {tableWidth};
        Table table = new Table(columnWidth);
        table.addCell(setTextCenter(RECEIPT));
        table.addCell(setTextCenter(supermarketName));
        table.addCell(setTextCenter(address));
        table.addCell(setTextCenter(phone));
        float[] columnWidth2 = {tableWidth / 2, tableWidth / 2};
        Table table2 = new Table(columnWidth2);
        table2.addCell(setTextLeft(CASHIER));
        table2.addCell(setTextLeft(DATE2));
        table2.addCell(setTextLeft(EMPTY));
        table2.addCell(setTextLeft(TIME2));
        float[] columnWidth3 = {tableWidth * 0.1F, tableWidth * 0.4F, tableWidth * 0.18F,
                tableWidth * 0.17F, tableWidth * 0.15F};
        Table table3 = new Table(columnWidth3);
        table3.setFont(pdfFont);
        table3.addCell(setTextLeft(QTY));
        table3.addCell(setTextLeft(DESCRIPTION));
        table3.addCell(setTextLeft(EMPTY));
        table3.addCell(setTextRight(PRICE));
        table3.addCell(setTextRight(TOTAL));
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isPromotion() && list.get(i).getQuantity()
                    > quantityDiscount) {
                table3.addCell(setTextLeft(String.valueOf(list.get(i).getQuantity())));
                table3.addCell(setTextLeft(list.get(i).getName()));
                table3.addCell(setTextLeft(sale));
                table3.addCell(setTextRight(String.valueOf(list.get(i).getPrice()
                        .multiply(discountValue)
                        .setScale(2, RoundingMode.HALF_UP))));
                table3.addCell(setTextRight(String.valueOf(list.get(i).getPrice()
                        .multiply(discountValue)
                        .setScale(2, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(list.get(i).getQuantity())))));
            } else {
                table3.addCell(setTextLeft(String.valueOf(list.get(i).getQuantity())));
                table3.addCell(setTextLeft(list.get(i).getName()));
                table3.addCell(setTextLeft(EMPTY));
                table3.addCell(setTextRight(String.valueOf(list.get(i).getPrice()
                        .multiply(new BigDecimal(ONE_HUNDRED)
                                .subtract(new BigDecimal(discount))
                                .divide(new BigDecimal(ONE_HUNDRED)))
                        .setScale(2, RoundingMode.HALF_UP))));
                table3.addCell(setTextRight(String.valueOf(list.get(i).getPrice()
                        .multiply(new BigDecimal(ONE_HUNDRED)
                                .subtract(new BigDecimal(discount))
                                .divide(new BigDecimal(ONE_HUNDRED)))
                        .setScale(2, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(list.get(i).getQuantity())))));
            }
        }
        float[] columnWidth4 = {tableWidth * 0.67F, tableWidth * 0.33F};
        Table table4 = new Table(columnWidth4);
        table4.addCell(setTextLeft(TAXABLE_TOT));
        table4.addCell(setTextRight(String.valueOf(value)));
        document.add(table);
        document.add(table2);
        document.add(new Paragraph(DELIMITER.repeat(42)));
        document.add(table3);
        document.add(new Paragraph(DELIMITER.repeat(42)));
        document.add(table4);
        document.close();
        byte[] content = Files.readAllBytes(Path.of(pathPdfCheck));
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(content.length)
                .body(content);
    }

    private static Cell setTextCenter(String text) {
        return new Cell().add(text).setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.CENTER);
    }

    private static Cell setTextRight(String text) {
        return new Cell().add(text).setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.RIGHT);
    }

    private static Cell setTextLeft(String text) {
        return new Cell().add(text).setBorder(Border.NO_BORDER);
    }
}
