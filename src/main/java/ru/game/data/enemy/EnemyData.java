package ru.game.data.enemy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnemyData {

    private int id;
    private String name;
    private int level;
    private int hp;
    private int maxHp;
    private int mp;
    private int maxMp;
    private int attack;
    private int defense;
    private int strength;
    private int agility;
    private int intelligence;
    private int magicAttack;
    private int magicDefense;
    private int expReward;
    private int goldReward;
    private int[] skillIds;
    private int[] lootIds;
}
