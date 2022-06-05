package ru.clevertec.task.race;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class CarThread extends Thread {
    private static final Logger LOG = LoggerFactory.getLogger(CarThread.class.getName());
    private final RunnerRace runnerRace;
    private final CountDownLatch countDownLatch;

    public CarThread(RunnerRace runnerRace, CountDownLatch countDownLatch) {
        this.runnerRace = runnerRace;
        this.countDownLatch = countDownLatch;
        this.start();
    }

    @Override
    public void run() {
        try {
            countDownLatch.await();
            action();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void action() {
        int speed = new Random().ints(100, 150)
                .findFirst().getAsInt();
        Car car = new Car("#" + speed, speed);
        int trackLength = runnerRace.getTrackLength();
        while (trackLength > 0) {
            try {
                Thread.sleep(1000);
                trackLength -= car.getSpeed();
                LOG.debug("Car {} left to finish {}", car.getName(), trackLength);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}