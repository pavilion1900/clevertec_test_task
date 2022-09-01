package ru.clevertec.util;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Check {

    private float tableWidth;
    private String supermarketName;
    private String address;
    private String phone;
    private int quantityDiscount;
    private BigDecimal discount;
    private String encoding;
    private String sale;
    private String font;
}
