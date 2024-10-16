package ru.game.utils;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@UtilityClass
public class RndUtil {

    public boolean calc(int chance, int limit) {
        return chance > ThreadLocalRandom.current().nextInt(limit + 1);
    }

    public int get(int bound) {
        return ThreadLocalRandom.current().nextInt(bound + 1);
    }

    public int get(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public boolean getBoolean() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    public double getDouble() {
        return ThreadLocalRandom.current().nextDouble();
    }

    public <T> T get(List<T> list) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("Список не должен быть пустым");
        }
        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }

    public <T> T get(T[] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Массив не должен быть пустым");
        }
        return array[ThreadLocalRandom.current().nextInt(array.length)];
    }

}
