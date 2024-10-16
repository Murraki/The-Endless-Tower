package ru.game.components.enemy;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.game.data.enemy.EnemyData;
import ru.game.enums.enemy.EnemyRaceType;
import ru.game.enums.enemy.EnemyType;

@Data
@AllArgsConstructor
public class EnemyComponent {

    private EnemyData data;
    private EnemyType type;
    private EnemyRaceType raceType;

    private int hp;
    private int maxHp;
    private int currentHp;
    private int mp;
    private int maxMp;
    private int currentMp;
    private int attack;
    private int defense;
    private int strength;
    private int agility;
    private int intelligence;
    private int level;
    private int exp;
    private int gold;
}
