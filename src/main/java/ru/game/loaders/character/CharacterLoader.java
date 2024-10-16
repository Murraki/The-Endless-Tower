package ru.game.loaders.character;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.game.data.character.ClassData;
import ru.game.data.character.RaceData;
import ru.game.enums.character.CharacterClassType;
import ru.game.enums.character.CharacterRaceType;
import ru.game.wrapers.character.CharacterClassWrapper;
import ru.game.wrapers.character.CharacterRaceWrapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CharacterLoader {

    private static final String[] CLASS_JSON_PATHS = {
            "/json/character/classes.json",
            "/ru/game/json/character/classes.json",
            "classes.json"
    };

    private static final String[] RACE_JSON_PATHS = {
            "/json/character/races.json",
            "/ru/game/json/character/races.json",
            "races.json"
    };

    public Map<CharacterClassType, ClassData> loadCharacterClasses() {
        for (String path : CLASS_JSON_PATHS) {
            try (InputStream classStream = getClass().getResourceAsStream(path)) {
                if (classStream != null) {
                    ObjectMapper mapper = new ObjectMapper();
                    CharacterClassWrapper wrapper = mapper.readValue(classStream, CharacterClassWrapper.class);
                    if (wrapper.getClasses() != null) {
                        return wrapper.getClasses().stream()
                                .collect(Collectors.toMap(ClassData::getType, c -> c));
                    }
                }
            } catch (IOException e) {
                System.err.println("Ошибка при загрузке класса персонажа из " + path + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
        System.err.println("Не удалось найти файл класса персонажа");
        return new HashMap<>();
    }

    public Map<CharacterRaceType, RaceData> loadCharacterRace() {
        for (String path : RACE_JSON_PATHS) {
            try (InputStream raceStream = getClass().getResourceAsStream(path)) {
                if (raceStream != null) {
                    ObjectMapper mapper = new ObjectMapper();
                    CharacterRaceWrapper wrapper = mapper.readValue(raceStream, CharacterRaceWrapper.class);
                    if (wrapper.getRaces() != null) {
                        return wrapper.getRaces().stream()
                                .collect(Collectors.toMap(RaceData::getType, r -> r));
                    }
                }
            } catch (IOException e) {
                System.err.println("Ошибка при загрузке расы персонажа из " + path + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
        System.err.println("Не удалось найти файл расы персонажа");
        return new HashMap<>();
    }
}
