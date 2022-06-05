package ru.clevertec.task.race;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class RunnerRace {
    private int carNumber;
    private int trackLength;
    private final int counter;

    public RunnerRace(int counter) {
        init();
        this.counter = counter;
    }

    public int getCarNumber() {
        return carNumber;
    }

    public int getTrackLength() {
        return trackLength;
    }

    public int getCounter() {
        return counter;
    }

    public void beforeStart(CountDownLatch countDownLatch) {
        try {
            Thread.sleep(1000);
            System.out.println(countDownLatch.getCount());
            countDownLatch.countDown();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void init() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of cars: ");
        carNumber = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter track length: ");
        trackLength = Integer.parseInt(scanner.nextLine());
    }

    public static void main(String[] args) {
        RunnerRace runnerRace = new RunnerRace(3);
        CountDownLatch countDownLatch = new CountDownLatch(runnerRace.getCounter());
        for (int i = 0; i < runnerRace.getCarNumber(); i++) {
            new CarThread(runnerRace, countDownLatch);
        }
        for (int i = 0; i < runnerRace.getCounter(); i++) {
            runnerRace.beforeStart(countDownLatch);
        }
    }
}