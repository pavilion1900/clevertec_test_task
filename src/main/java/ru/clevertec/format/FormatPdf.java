package ru.clevertec.format;

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
import ru.clevertec.task.collection.CustomList;
import ru.clevertec.util.PropertiesUtil;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class FormatPdf implements Format {

//    private static final String PATH = "src/main/resources/check.pdf";
    private static final String PATH =
            "C:\\projects\\clevertec_test_task\\src\\main\\resources\\check.pdf";
    private static final float TABLE_WIDTH = 300F;
    private CustomList<Item> list;
    private int discount;
    private BigDecimal value;
    private OutputStream out;

    public FormatPdf(CustomList<Item> list, int discount, BigDecimal value) {
        this.list = list;
        this.discount = discount;
        this.value = value;
    }

    @Override
    public void setFormat() {
        try {
            PdfWriter pdfWriter = new PdfWriter(PATH);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            pdfDocument.setDefaultPageSize(PageSize.A4);
            Document document = new Document(pdfDocument);
            float[] columnWidth = {TABLE_WIDTH};
            Table table = new Table(columnWidth);
            table.addCell(setTextCenter(RECEIPT2));
            table.addCell(setTextCenter(PropertiesUtil.get("check.supermarket")));
            table.addCell(setTextCenter(PropertiesUtil.get("check.address")));
            table.addCell(setTextCenter(PropertiesUtil.get("check.phone")));
            float[] columnWidth2 = {TABLE_WIDTH / 2, TABLE_WIDTH / 2};
            Table table2 = new Table(columnWidth2);
            table2.addCell(setTextLeft(CASHIER2));
            table2.addCell(setTextLeft(DATE2));
            table2.addCell(setTextLeft(EMPTY));
            table2.addCell(setTextLeft(TIME2));
            float[] columnWidth3 = {TABLE_WIDTH * 0.1F, TABLE_WIDTH * 0.4F, TABLE_WIDTH * 0.18F,
                    TABLE_WIDTH * 0.17F, TABLE_WIDTH * 0.15F};
            Table table3 = new Table(columnWidth3);
            table3.addCell(setTextLeft(QTY));
            table3.addCell(setTextLeft(DESCRIPTION));
            table3.addCell(setTextLeft(EMPTY));
            table3.addCell(setTextRight(PRICE));
            table3.addCell(setTextRight(TOTAL));
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isPromotion() && list.get(i).getQuantity()
                        > Integer.parseInt(PropertiesUtil.get("check.quantity.discount"))) {
                    table3.addCell(setTextLeft(String.valueOf(list.get(i).getQuantity())));
                    table3.addCell(setTextLeft(list.get(i).getName()));
                    table3.addCell(setTextLeft(SALE));
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
            document.add(new Paragraph("-".repeat(73)));
            document.add(table3);
            document.add(new Paragraph("=".repeat(42)));
            document.add(table4);
            document.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
