package ru.game.data.character;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.game.enums.character.CharacterClassType;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClassData {

    private int id;
    private String name;
    private CharacterClassType type;
    private int baseStrength;
    private int baseAgility;
    private int baseIntelligence;
    private int baseLuck;
}
