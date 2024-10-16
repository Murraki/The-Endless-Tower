package ru.game.enums.character;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum CharacterClassType {
    WARRIOR,
    MAGE,
    BARBARIAN,
    ROGUE,
    ARCHER;

    @JsonCreator
    public static CharacterClassType fromString(String value) {
        return valueOf(value.toUpperCase());
    }

}
