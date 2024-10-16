package ru.game.data.character;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.game.enums.character.CharacterRaceType;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RaceData {

    private int id;
    private String name;
    private CharacterRaceType type;
    private int strengthBonus;
    private int agilityBonus;
    private int intelligenceBonus;
    private int hpBonus;
}
