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
import ru.clevertec.entity.Item;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;
import ru.clevertec.util.PropertiesUtil;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class FormatPdf implements Format {

    private static final float TABLE_WIDTH =
            PropertiesUtil.getYamlProperties().getCheck().getTableWidth();
    private final CustomList<Item> list;
    private final int discount;
    private final BigDecimal value;
    private final OutputStream out;

    public FormatPdf(CustomList<Item> list, int discount, BigDecimal value, OutputStream out) {
        this.list = new CustomArrayList<>(list);
        this.discount = discount;
        this.value = value;
        this.out = out;
    }

    @Override
    public void setFormat() {
        PdfWriter pdfWriter = new PdfWriter(out);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        PdfFont pdfFont;
        try (InputStream in = this.getClass().getClassLoader()
                .getResourceAsStream(PropertiesUtil.getYamlProperties().getCheck().getFont())) {
            pdfFont = PdfFontFactory.createFont(in.readAllBytes(), PdfEncodings.IDENTITY_H);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Document document = new Document(pdfDocument);
        float[] columnWidth = {TABLE_WIDTH};
        Table table = new Table(columnWidth);
        table.addCell(setTextCenter(RECEIPT));
        table.addCell(setTextCenter(
                PropertiesUtil.getYamlProperties().getCheck().getSupermarketName()));
        table.addCell(setTextCenter(
                PropertiesUtil.getYamlProperties().getCheck().getAddress()));
        table.addCell(setTextCenter(
                PropertiesUtil.getYamlProperties().getCheck().getPhone()));
        float[] columnWidth2 = {TABLE_WIDTH / 2, TABLE_WIDTH / 2};
        Table table2 = new Table(columnWidth2);
        table2.addCell(setTextLeft(CASHIER));
        table2.addCell(setTextLeft(DATE2));
        table2.addCell(setTextLeft(EMPTY));
        table2.addCell(setTextLeft(TIME2));
        float[] columnWidth3 = {TABLE_WIDTH * 0.1F, TABLE_WIDTH * 0.4F, TABLE_WIDTH * 0.18F,
                TABLE_WIDTH * 0.17F, TABLE_WIDTH * 0.15F};
        Table table3 = new Table(columnWidth3);
        table3.setFont(pdfFont);
        table3.addCell(setTextLeft(QTY));
        table3.addCell(setTextLeft(DESCRIPTION));
        table3.addCell(setTextLeft(EMPTY));
        table3.addCell(setTextRight(PRICE));
        table3.addCell(setTextRight(TOTAL));
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isPromotion() && list.get(i).getQuantity()
                    > PropertiesUtil.getYamlProperties().getCheck().getQuantityDiscount()) {
                table3.addCell(setTextLeft(String.valueOf(list.get(i).getQuantity())));
                table3.addCell(setTextLeft(list.get(i).getName()));
                table3.addCell(setTextLeft(
                        PropertiesUtil.getYamlProperties().getCheck().getSale()));
                table3.addCell(setTextRight(String.valueOf(list.get(i).getPrice()
                        .multiply(DISCOUNT_VALUE)
                        .setScale(2, RoundingMode.HALF_UP))));
                table3.addCell(setTextRight(String.valueOf(list.get(i).getPrice()
                        .multiply(DISCOUNT_VALUE)
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
        float[] columnWidth4 = {TABLE_WIDTH * 0.67F, TABLE_WIDTH * 0.33F};
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
    }

    public static Cell setTextCenter(String text) {
        return new Cell().add(text).setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.CENTER);
    }

    public static Cell setTextRight(String text) {
        return new Cell().add(text).setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.RIGHT);
    }

    public static Cell setTextLeft(String text) {
        return new Cell().add(text).setBorder(Border.NO_BORDER);
    }
}
