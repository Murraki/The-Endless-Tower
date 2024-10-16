package ru.game.generaters.floor;

import ru.game.components.enemy.EnemyComponent;
import ru.game.enums.enemy.EnemyType;
import ru.game.generaters.enemy.EnemyGenerator;
import ru.game.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class FloorGenerator {

    private final EnemyGenerator enemyGenerator;

    public FloorGenerator(EnemyGenerator enemyGenerator) {
        this.enemyGenerator = enemyGenerator;
    }

    public List<EnemyComponent> generateFloor(int playerLevel, int floorNumber) {
        List<EnemyComponent> enemies = new ArrayList<>();
        int enemyCount = calculateEnemyCount(floorNumber);

        for (int i = 0; i < enemyCount; i++) {
            EnemyType enemyType = getEnemyType(floorNumber, i == enemyCount - 1);
            enemies.add(enemyGenerator.generateEnemy(enemyType, playerLevel));
        }

        return enemies;
    }

    private int calculateEnemyCount(int floorNumber) {
        return 3 + Utils.newRandom().nextInt(3) + (floorNumber / 5);
    }

    private EnemyType getEnemyType(int floorNumber, boolean isLast) {
        if (floorNumber % 10 == 0 && isLast) {
            return EnemyType.BOSS;
        } else if (Utils.newRandom().nextDouble() < 0.2 + (floorNumber * 0.01)) {
            return EnemyType.ELITE;
        } else {
            return EnemyType.COMMON;
        }
    }
}
