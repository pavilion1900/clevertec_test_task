package ru.clevertec.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Item implements Serializable {
    private int id;
    private String name;
    private BigDecimal price;
    private boolean promotion;
    private int quantity;

    public Item(int id, String name, BigDecimal price, boolean promotion, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.promotion = promotion;
        this.quantity = quantity;
    }

    public Item(int id, String name, BigDecimal price, boolean promotion) {
        this(id, name, price, promotion, 0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isPromotion() {
        return promotion;
    }

    public void setPromotion(boolean promotion) {
        this.promotion = promotion;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return id == item.id
                && promotion == item.promotion
                && quantity == item.quantity
                && Objects.equals(name, item.name)
                && Objects.equals(price, item.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, promotion, quantity);
    }

    @Override
    public String toString() {
        return "Item{"
                + "id=" + id
                + ", description='" + name + '\''
                + ", price=" + price
                + ", promotion=" + promotion
                + ", quantity=" + quantity
                + '}';
    }
}