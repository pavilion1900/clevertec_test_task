package ru.clevertec.task.race;

public class Car {
    private final String name;
    private final int speed;

    public Car(String name, int speed) {
        this.name = name;
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Car{"
                + "speed=" + speed
                + '}';
    }
}
