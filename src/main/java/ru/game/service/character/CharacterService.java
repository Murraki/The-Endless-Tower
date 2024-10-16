package ru.game.service.character;

import lombok.Data;
import ru.game.components.CharacterComponent;
import ru.game.data.character.CharacterData;
import ru.game.data.character.ClassData;
import ru.game.data.character.RaceData;
import ru.game.enums.character.CharacterClassType;
import ru.game.enums.character.CharacterRaceType;
import ru.game.loaders.character.CharacterLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class CharacterService {

    private CharacterLoader loader;
    private SaveLoadCharacterService saveLoad;

    private Map<CharacterClassType, ClassData> classDataMap;
    private Map<CharacterRaceType, RaceData> raceDataMap;

    public CharacterService(CharacterLoader loader) {
        this.loader = loader;
        this.saveLoad = new SaveLoadCharacterService();
        loadData();
    }

    private void loadData() {
        this.classDataMap = loader.loadCharacterClasses();
        this.raceDataMap = loader.loadCharacterRace();

        if (this.classDataMap == null) {
            this.classDataMap = new HashMap<>();
        }
        if (this.raceDataMap == null) {
            this.raceDataMap = new HashMap<>();
        }
    }

    public CharacterComponent createCharacter(String name, CharacterClassType classType, CharacterRaceType raceType) {
        CharacterData baseData = new CharacterData(
                0, name, 100, 100, 50, 50, 10,
                10, 10, 10, 10
        );

        ClassData classData = classDataMap.getOrDefault(classType, new ClassData()); // Используем значение по умолчанию
        RaceData raceData = raceDataMap.getOrDefault(raceType, new RaceData()); // Используем значение по умолчанию

        int strength = baseData.getBaseStrength() + classData.getBaseStrength() + raceData.getStrengthBonus();
        int agility = baseData.getBaseAgility() + classData.getBaseAgility() + raceData.getAgilityBonus();
        int intelligence = baseData.getBaseIntelligence() + classData.getBaseIntelligence() + raceData.getIntelligenceBonus();
        int maxHp = baseData.getBaseMaxHp() + raceData.getHpBonus();

        return new CharacterComponent(
                baseData,
                List.of(classType),
                List.of(raceType),
                maxHp, maxHp, maxHp,
                baseData.getBaseMaxMp(), baseData.getBaseMaxMp(), baseData.getBaseMaxMp(),
                baseData.getBaseAttack(),
                baseData.getBaseDefense(),
                strength, agility, intelligence,
                1, 0, 0, 100, 0
        );
    }

    public void saveCharacter(CharacterComponent character) {
        saveLoad.saveCharacter(character);
    }

    public CharacterComponent loadCharacter(String characterName) {
        return saveLoad.loadCharacter(characterName);
    }

    public List<String> getSavedCharacterNames() {
        return saveLoad.getSavedCharacterNames();
    }

    public boolean deleteCharacter(String characterName) {
        return saveLoad.deleteCharacter(characterName);
    }

}
