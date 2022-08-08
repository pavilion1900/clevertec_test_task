package ru.clevertec.entity;

import java.util.Objects;

public class Card {

    private int id;
    private int number;
    private int discount;

    public Card(int id, int number, int discount) {
        this.id = id;
        this.number = number;
        this.discount = discount;
    }

    public Card(int number, int discount) {
        this(0, number, discount);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Card card = (Card) o;
        return number == card.number && discount == card.discount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, discount);
    }

    @Override
    public String toString() {
        return "Card{"
                + "id=" + id
                + ", number=" + number
                + ", discount=" + discount
                + '}';
    }
}
