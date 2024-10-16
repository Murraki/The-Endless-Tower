package ru.game.service.character;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.game.components.CharacterComponent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SaveLoadCharacterService {

    private static final Logger LOGGER = Logger.getLogger(SaveLoadCharacterService.class.getName());
    private static final String SAVE_DIRECTORY = "saves";
    private static final String FILE_EXTENSION = ".json";

    public SaveLoadCharacterService() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public void saveCharacter(CharacterComponent character) {
        try {
            Files.createDirectories(Paths.get(SAVE_DIRECTORY));
            String fileName = SAVE_DIRECTORY + File.separator + character.getData().getName() + FILE_EXTENSION;
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(fileName), character);
            System.out.println("Персонаж успешно сохранен в файл: " + fileName);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении персонажа: " + e.getMessage());
        }
    }

    public CharacterComponent loadCharacter(String characterName) {
        try {
            String fileName = SAVE_DIRECTORY + File.separator + characterName + FILE_EXTENSION;
            ObjectMapper mapper = new ObjectMapper();
            CharacterComponent character = mapper.readValue(new File(fileName), CharacterComponent.class);
            System.out.println("Персонаж успешно загружен из файла: " + fileName);
            return character;
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке персонажа: " + e.getMessage());
            return null;
        }
    }

    public List<String> getSavedCharacterNames() {
        List<String> characterNames = new ArrayList<>();
        File saveDir = new File(SAVE_DIRECTORY);
        if (saveDir.exists() && saveDir.isDirectory()) {
            File[] files = saveDir.listFiles((dir, name) -> name.endsWith(FILE_EXTENSION));
            if (files != null) {
                for (File file : files) {
                    String name = file.getName();
                    characterNames.add(name.substring(0, name.length() - FILE_EXTENSION.length()));
                }
            }
        }
        return characterNames;
    }

    public boolean deleteCharacter(String characterName) {
        Path path = Paths.get(SAVE_DIRECTORY, characterName + FILE_EXTENSION);
        try {
            if (Files.exists(path)) {
                Files.delete(path);
                LOGGER.info("Character file deleted: " + path);
                return true;
            } else {
                LOGGER.warning("Character file not found: " + path);
                return false;
            }
        } catch (IOException e) {
            LOGGER.severe("Error deleting character file: " + path + ". Error: " + e.getMessage());
            return false;
        }
    }

    public void ensureSaveDirectoryExists() {
        File directory = new File(SAVE_DIRECTORY);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                LOGGER.info("Save directory created: " + SAVE_DIRECTORY);
            } else {
                LOGGER.severe("Failed to create save directory: " + SAVE_DIRECTORY);
            }
        }
    }

    public void logSaveDirectoryContents() {
        File directory = new File(SAVE_DIRECTORY);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                LOGGER.info("Contents of save directory:");
                for (File file : files) {
                    LOGGER.info(file.getName());
                }
            } else {
                LOGGER.info("Save directory is empty or cannot be read.");
            }
        } else {
            LOGGER.warning("Save directory does not exist or is not a directory.");
        }
    }

}
