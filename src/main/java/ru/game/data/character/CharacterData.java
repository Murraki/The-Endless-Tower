package ru.game.data.character;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CharacterData {

    private int id;
    private String name;
    private int baseHp;
    private int baseMaxHp;
    private int baseMp;
    private int baseMaxMp;
    private int baseDefense;
    private int baseStrength;
    private int baseAgility;
    private int baseIntelligence;
    private int baseAttack;

    @JsonCreator
    public CharacterData(
            @JsonProperty("id") int id,
            @JsonProperty("name") String name,
            @JsonProperty("baseHp") int baseHp,
            @JsonProperty("baseMaxHp") int baseMaxHp,
            @JsonProperty("baseMp") int baseMp,
            @JsonProperty("baseMaxMp") int baseMaxMp,
            @JsonProperty("baseDefense") int baseDefense,
            @JsonProperty("baseStrength") int baseStrength,
            @JsonProperty("baseAgility") int baseAgility,
            @JsonProperty("baseIntelligence") int baseIntelligence,
            @JsonProperty("baseAttack") int baseAttack) {

        this.id = id;
        this.name = name;
        this.baseHp = baseHp;
        this.baseMaxHp = baseMaxHp;
        this.baseMp = baseMp;
        this.baseMaxMp = baseMaxMp;
        this.baseDefense = baseDefense;
        this.baseStrength = baseStrength;
        this.baseAgility = baseAgility;
        this.baseIntelligence = baseIntelligence;
        this.baseAttack = baseAttack;
    }
}
