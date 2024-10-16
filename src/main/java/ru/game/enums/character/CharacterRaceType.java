package ru.game.enums.character;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum CharacterRaceType {
    HUMAN,
    ELF,
    DWARF;

    @JsonCreator
    public static CharacterRaceType fromString(String value) {
        return valueOf(value.toUpperCase());
    }

}
