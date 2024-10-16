package ru.game.service.menu;

import lombok.Data;
import ru.game.components.CharacterComponent;
import ru.game.data.menu.MenuData;
import ru.game.enums.character.CharacterClassType;
import ru.game.enums.character.CharacterRaceType;
import ru.game.enums.messenger.MessengerTextColor;
import ru.game.service.character.CharacterService;
import ru.game.service.character.SaveLoadCharacterService;
import ru.game.service.game.GameService;
import ru.game.service.messenger.MessengerService;
import ru.game.utils.Utils;

@Data
public class MenuService {

    private MenuData data;
    private GameService game;
    private CharacterService characterService;
    private String characterName;
    private CharacterClassType characterClass;
    private CharacterRaceType characterRace;
    private CharacterComponent currentCharacter;
    private SaveLoadCharacterService saveLoadCharacter;

    public MenuService(CharacterService characterService) {
        this.characterService = characterService;
        this.saveLoadCharacter = characterService.getSaveLoad();
    }

    // Метод для установки GameService после его создания
    public void setGameService(GameService gameService) {
        this.game = gameService;
    }

    public void menuStart() {
        MessengerService
                .messenger(MessengerTextColor.CYAN)
                .text("►►► Добро пожаловать в игру The Endless Tower ◄◄◄")
                .newLine()
                .text("1. Начать новую игру")
                .newLine()
                .text("2. Загрузить игру")
                .newLine()
                .text("3. Удалить сохранение")
                .newLine()
                .text("4. Выйти из игры")
                .newLine()
                //.text("Введите число от 1 до 4: ")
                .print();


        int choice = Utils.getIntInput(1, 4);
        switch (choice) {
            case 1:
                game.startNewGame();
                break;
            case 2:
                game.loadGame();
                break;
            case 3:
                game.deleteCharacter();
                menuStart(); // Возвращаемся в главное меню после удаления
                break;
            case 4:
                System.exit(0);
                break;
        }

    }

    public void menuCreateCharacter() {
        MessengerService
                .messenger(MessengerTextColor.CYAN)
                .text("►►► Создание персонажа ◄◄◄")
                .newLine()
                .text("► Введите имя персонажа: ")
                .print();

        characterName = Utils.newScanner().nextLine();

        menuChoosingClass();
    }

    public void menuChoosingClass() {
        MessengerService
                .messenger(MessengerTextColor.CYAN)
                .text("► Выберите профессию персонажа.")
                .newLine()
                .text("На выбор 5 профессий:")
                .newLine()
                .text("1. Warrior")
                .newLine()
                .text("2. Mage")
                .newLine()
                .text("3. Barbarian")
                .newLine()
                .text("4. Rogue")
                .newLine()
                .text("5. Archer")
                .newLine()
                .text("Введите число от 1 до 5: ")
                .print();
        int choice = Utils.newScanner().nextInt();

        switch (choice) {
            case 1:
                characterClass = CharacterClassType.WARRIOR;
                break;
            case 2:
                characterClass = CharacterClassType.MAGE;
                break;
            case 3:
                characterClass = CharacterClassType.BARBARIAN;
                break;
            case 4:
                characterClass = CharacterClassType.ROGUE;
                break;
            case 5:
                characterClass = CharacterClassType.ARCHER;
                break;
            default:
                menuChoosingClass();
                return;
        }
        menuChoosingRace();
    }

    public void menuChoosingRace() {
        MessengerService
                .messenger(MessengerTextColor.CYAN)
                .text("► Выберите расу персонажа.")
                .newLine()
                .text("На выбор 3 расы:")
                .newLine()
                .text("1. Human")
                .newLine()
                .text("2. Elf")
                .newLine()
                .text("3. Dwarf")
                .newLine()
                .text("Введите число от 1 до 3: ")
                .print();
        int choice = Utils.newScanner().nextInt();

        switch (choice) {
            case 1:
                characterRace = CharacterRaceType.HUMAN;
                break;
            case 2:
                characterRace = CharacterRaceType.ELF;
                break;
            case 3:
                characterRace = CharacterRaceType.DWARF;
                break;
            default:
                menuChoosingRace();
                return;
        }

        createCharacter();
    }

    private void createCharacter() {
        System.out.println("Creating character...");
        currentCharacter = characterService.createCharacter(characterName, characterClass, characterRace);
        System.out.println("Character created successfully.");

        MessengerService
                .messenger(MessengerTextColor.GREEN)
                .text("► Персонаж успешно создан!")
                .newLine()
                .text("Имя: " + currentCharacter.getData().getName())
                .newLine()
                .text("Класс: " + currentCharacter.getClasses().get(0))
                .newLine()
                .text("Раса: " + currentCharacter.getRaces().get(0))
                .newLine()
                .text("HP: " + currentCharacter.getMaxHp())
                .newLine()
                .text("MP: " + currentCharacter.getMaxMp())
                .newLine()
                .text("Сила: " + currentCharacter.getStrength())
                .newLine()
                .text("Ловкость: " + currentCharacter.getAgility())
                .newLine()
                .text("Интеллект: " + currentCharacter.getIntelligence())
                .newLine()
                .print();

        saveCharacter();
    }

    private void saveCharacter() {

        saveLoadCharacter.saveCharacter(currentCharacter);
        MessengerService
                .messenger(MessengerTextColor.GREEN)
                .text("Персонаж успешно сохранен!")
                .newLine()
                .print();
        // TODO: добавить логику для перехода к следующему этапу игры
    }

}
