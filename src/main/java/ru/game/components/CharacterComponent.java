package ru.game.components;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.game.data.character.CharacterData;
import ru.game.enums.character.CharacterClassType;
import ru.game.enums.character.CharacterRaceType;

import java.util.List;

@Data
@NoArgsConstructor
public class CharacterComponent {

    private CharacterData data;
    private List<CharacterClassType> classes;
    private List<CharacterRaceType> races;

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
    private int currentExp;
    private int expNextLevel;
    private int gold;

    @JsonCreator
    public CharacterComponent(
            @JsonProperty("data") CharacterData data,
            @JsonProperty("classes") List<CharacterClassType> classes,
            @JsonProperty("races") List<CharacterRaceType> races,
            @JsonProperty("hp") int hp,
            @JsonProperty("maxHp") int maxHp,
            @JsonProperty("currentHp") int currentHp,
            @JsonProperty("mp") int mp,
            @JsonProperty("maxMp") int maxMp,
            @JsonProperty("currentMp") int currentMp,
            @JsonProperty("attack") int attack,
            @JsonProperty("defense") int defense,
            @JsonProperty("strength") int strength,
            @JsonProperty("agility") int agility,
            @JsonProperty("intelligence") int intelligence,
            @JsonProperty("level") int level,
            @JsonProperty("exp") int exp,
            @JsonProperty("currentExp") int currentExp,
            @JsonProperty("expNextLevel") int expNextLevel,
            @JsonProperty("gold") int gold) {

        this.data = data;
        this.classes = classes;
        this.races = races;
        this.hp = hp;
        this.maxHp = maxHp;
        this.currentHp = currentHp;
        this.mp = mp;
        this.maxMp = maxMp;
        this.currentMp = currentMp;
        this.attack = attack;
        this.defense = defense;
        this.strength = strength;
        this.agility = agility;
        this.intelligence = intelligence;
        this.level = level;
        this.exp = exp;
        this.currentExp = currentExp;
        this.expNextLevel = expNextLevel;
        this.gold = gold;
    }
}
