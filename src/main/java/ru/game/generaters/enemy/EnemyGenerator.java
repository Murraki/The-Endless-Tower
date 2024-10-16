package ru.game.generaters.enemy;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.game.components.enemy.EnemyComponent;
import ru.game.data.enemy.EnemyData;
import ru.game.enums.enemy.EnemyRaceType;
import ru.game.enums.enemy.EnemyType;
import ru.game.utils.Utils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EnemyGenerator {

    private final Map<EnemyType, List<EnemyData>> enemiesData;
    private final ObjectMapper objectMapper;

    public EnemyGenerator(Map<EnemyType, List<EnemyData>> enemiesData) {
        this.objectMapper = new ObjectMapper();
        this.enemiesData = enemiesData;
    }

    private Map<EnemyType, List<EnemyData>> convertRawData(Map<EnemyType, List<Object>> rawData) {
        return rawData.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(this::convertToEnemyData)
                                .collect(Collectors.toList())
                ));
    }

    private EnemyData convertToEnemyData(Object rawData) {
        return objectMapper.convertValue(rawData, EnemyData.class);
    }

    public EnemyComponent generateEnemy(EnemyType type, int playerLevel) {
        EnemyData data = getRandomEnemyData(type);
        return createEnemyComponent(data, type, playerLevel);
    }

    private EnemyData getRandomEnemyData(EnemyType type) {
        List<EnemyData> enemies = enemiesData.get(type);
        return enemies.get(Utils.newRandom().nextInt(enemies.size()));
    }

    private EnemyComponent createEnemyComponent(EnemyData data, EnemyType type, int playerLevel) {
        int level = calculateEnemyLevel(type, playerLevel);
        int hp = calculateStat(data.getHp(), level);
        int mp = calculateStat(data.getMp(), level);
        int attack = calculateStat(data.getAttack(), level);
        int defense = calculateStat(data.getDefense(), level);

        return new EnemyComponent(
                data,
                type,
                getRandomEnemyRace(),
                hp,
                hp,
                hp,
                mp,
                mp,
                mp,
                attack,
                defense,
                calculateStat(data.getStrength(), level),
                calculateStat(data.getAgility(), level),
                calculateStat(data.getIntelligence(), level),
                level,
                calculateExp(data.getExpReward(), level),
                calculateGold(data.getGoldReward(), level)
        );
    }

    private int calculateEnemyLevel(EnemyType type, int playerLevel) {
        switch (type) {
            case COMMON:
                return playerLevel + Utils.newRandom().nextInt(3) - 1;
            case ELITE:
                return playerLevel + Utils.newRandom().nextInt(3) + 1;
            case BOSS:
                return playerLevel + Utils.newRandom().nextInt(5) + 3;
            default:
                return playerLevel;
        }
    }

    private int calculateStat(int baseStat, int level) {
        return (int) (baseStat * (1 + (level - 1) * 0.1));
    }

    private int calculateExp(int baseExp, int level) {
        return baseExp * level;
    }

    private int calculateGold(int baseGold, int level) {
        return baseGold * level;
    }

    private EnemyRaceType getRandomEnemyRace() {
        return EnemyRaceType.values()[Utils.newRandom().nextInt(EnemyRaceType.values().length)];
    }

}
