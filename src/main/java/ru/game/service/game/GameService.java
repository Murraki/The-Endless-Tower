package ru.game.service.game;

import lombok.AllArgsConstructor;
import ru.game.components.CharacterComponent;
import ru.game.components.enemy.EnemyComponent;
import ru.game.enums.messenger.MessengerTextColor;
import ru.game.generaters.floor.FloorGenerator;
import ru.game.service.character.CharacterService;
import ru.game.service.menu.MenuService;
import ru.game.service.messenger.MessengerService;
import ru.game.utils.Utils;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class GameService {

    MenuService menu;
    CharacterService character;
    private FloorGenerator floorGenerator;

    private int currentFloor = 1;
    private List<EnemyComponent> currentFloorEnemies;

    public GameService(MenuService menu, CharacterService character, FloorGenerator floorGenerator) {
        this.menu = menu;
        this.character = character;
        this.floorGenerator = floorGenerator;
        this.currentFloorEnemies = new ArrayList<>();
        character.getSaveLoad().ensureSaveDirectoryExists();

    }

    public void startGame() {
        menu.menuStart();

    }

    public void welcome() {
        MessengerService
                .messenger(MessengerTextColor.BLUE)
                .text("►►► Стражник Bernard ◄◄◄")
                .newLine()
                .text("► Стоять! Ты кто такой?")
                .newLine()
                .print();
        MessengerService
                .messenger(MessengerTextColor.RED)
                .text("Нажмите ENTER чтобы начать создание персонажа.")
                .newLine()
                .print();

        Utils.newScanner().nextLine();

        menu.menuCreateCharacter();

        MessengerService
                .messenger(MessengerTextColor.BLUE)
                .text("► А ну привет {}, так ты новенький...", menu.getCurrentCharacter().getData().getName())
                .newLine()
                .text("► Ну что ж добро пожаловать ко входу в Endless Tower")
                .newLine()
                .text("► Вам предстоит отправиться в опасное путешествие в эту башню.")
                .newLine()
                .text("► Каждый этаж представляет новые испытания и монстров, которых нужно преодолеть.")
                .newLine()
                .text("► Готовы ли вы начать свое приключение?")
                .newLine()
                .print();

        MessengerService
                .messenger(MessengerTextColor.RED)
                .text("Нажмите ENTER чтобы стражник открыл дверь и вы начали свое приключение.")
                .newLine()
                .print();

        Utils.newScanner().nextLine();

    }

    public void startNewGame() {
        MessengerService.messenger(MessengerTextColor.BLUE)
                .text("Добро пожаловать в игру «Башня»!")
                .newLine()
                .text("Вам предстоит отправиться в опасное путешествие через загадочную башню.")
                .newLine()
                .text("Каждый этаж представляет новые испытания и монстров, которых нужно преодолеть.")
                .newLine()
                .text("Готовы ли вы начать свое приключение?")
                .newLine()
                .print();

        Utils.newScanner().nextLine();
        System.out.println("Starting new game...");
        welcome();
        System.out.println("Generating new floor...");
        generateNewFloor();
        System.out.println("Starting floor...");
        startFloor();

    }

    private void generateNewFloor() {
        CharacterComponent player = menu.getCurrentCharacter();
        currentFloorEnemies = floorGenerator.generateFloor(player.getLevel(), currentFloor);
    }

    private void startFloor() {
        MessengerService.messenger(MessengerTextColor.BLUE)
                .text("Вы входите на " + currentFloor + " этаж башни.")
                .newLine()
                .text("На этом этаже вас ждет " + currentFloorEnemies.size() + " врагов.")
                .newLine()
                .print();

        while (!currentFloorEnemies.isEmpty()) {
            EnemyComponent enemy = currentFloorEnemies.get(0);
            battle(enemy);
        }

        completeFloor();
    }

    private void battle(EnemyComponent enemy) {
        // Здесь будет реализована логика сражения
        // Пока что просто удалим врага из списка
        currentFloorEnemies.remove(enemy);
        MessengerService.messenger(MessengerTextColor.GREEN)
                .text("Вы победили " + enemy.getData().getName() + "!")
                .newLine()
                .print();

        if (menu.getCurrentCharacter().getCurrentHp() <= 0) {
            characterDeath();
            return;
        }
    }

    private void completeFloor() {
        currentFloor++;
        MessengerService.messenger(MessengerTextColor.GREEN)
                .text("Поздравляем! Вы прошли " + (currentFloor - 1) + " этаж башни!")
                .newLine()
                .print();

        if (currentFloor % 10 == 0) {
            // Здесь можно добавить логику для особых событий каждые 10 этажей
        }

        generateNewFloor();
        startFloor();
    }

    public void loadGame() {
        List<String> savedCharacters = character.getSavedCharacterNames();
        if (savedCharacters.isEmpty()) {
            MessengerService
                    .messenger(MessengerTextColor.RED)
                    .text("Нет сохраненных игр. Начните новую игру.")
                    .newLine()
                    .print();
            menu.menuStart();
            return;
        }

        MessengerService
                .messenger(MessengerTextColor.BLUE)
                .text("Выберите персонажа для загрузки:")
                .newLine()
                .print();

        for (int i = 0; i < savedCharacters.size(); i++) {
            MessengerService
                    .messenger(MessengerTextColor.BLUE)
                    .text((i + 1) + ". " + savedCharacters.get(i))
                    .newLine()
                    .print();
        }

        int choice = Utils.getIntInput(1, savedCharacters.size());
        String selectedCharacterName = savedCharacters.get(choice - 1);

        CharacterComponent loadedCharacter = character.loadCharacter(selectedCharacterName);
        if (loadedCharacter != null) {
            menu.setCurrentCharacter(loadedCharacter);
            MessengerService
                    .messenger(MessengerTextColor.GREEN)
                    .text("Игра успешно загружена!")
                    .newLine()
                    .text("Имя персонажа: " + menu.getCurrentCharacter().getData().getName())
                    .newLine()
                    .print();
            // Здесь можно добавить логику для продолжения игры с загруженным персонажем
        } else {
            MessengerService
                    .messenger(MessengerTextColor.RED)
                    .text("Не удалось загрузить игру. Начните новую игру.")
                    .newLine()
                    .print();
            menu.menuStart();
        }
    }

    public void saveGame() {
        CharacterComponent currentCharacter = menu.getCurrentCharacter();
        if (currentCharacter != null) {
            character.saveCharacter(currentCharacter);
            MessengerService
                    .messenger(MessengerTextColor.GREEN)
                    .text("Игра успешно сохранена!")
                    .newLine()
                    .print();
        } else {
            MessengerService
                    .messenger(MessengerTextColor.RED)
                    .text("Нет активного персонажа для сохранения.")
                    .newLine()
                    .print();
        }
    }

    public void deleteCharacter() {
        List<String> savedCharacters = character.getSavedCharacterNames();
        if (savedCharacters.isEmpty()) {
            MessengerService
                    .messenger(MessengerTextColor.RED)
                    .text("Нет сохраненных персонажей для удаления.")
                    .newLine()
                    .print();
            return;
        }

        MessengerService
                .messenger(MessengerTextColor.BLUE)
                .text("Выберите персонажа для удаления:")
                .newLine()
                .print();

        for (int i = 0; i < savedCharacters.size(); i++) {
            MessengerService
                    .messenger(MessengerTextColor.BLUE)
                    .text((i + 1) + ". " + savedCharacters.get(i))
                    .newLine()
                    .print();
        }

        int choice = Utils.getIntInput(1, savedCharacters.size());
        String selectedCharacterName = savedCharacters.get(choice - 1);

        character.getSaveLoad().logSaveDirectoryContents();

        try {
            if (character.deleteCharacter(selectedCharacterName)) {
                MessengerService
                        .messenger(MessengerTextColor.GREEN)
                        .text("Персонаж успешно удален: " + selectedCharacterName)
                        .newLine()
                        .print();
            } else {
                MessengerService
                        .messenger(MessengerTextColor.RED)
                        .text("Не удалось удалить персонажа: " + selectedCharacterName)
                        .newLine()
                        .text("Возможно, файл сохранения не существует или недоступен.")
                        .newLine()
                        .print();
            }
        } catch (Exception e) {
            MessengerService
                    .messenger(MessengerTextColor.RED)
                    .text("Произошла ошибка при удалении персонажа: " + selectedCharacterName)
                    .newLine()
                    .text("Ошибка: " + e.getMessage())
                    .newLine()
                    .print();
        }
    }

    public void characterDeath() {
        MessengerService
                .messenger(MessengerTextColor.RED)
                .text("Ваш персонаж погиб!")
                .newLine()
                .print();

        // Удаляем сохранение персонажа
        if (character.deleteCharacter(menu.getCurrentCharacter().getData().getName())) {
            MessengerService
                    .messenger(MessengerTextColor.BLUE)
                    .text("Сохранение персонажа удалено.")
                    .newLine()
                    .print();
        } else {
            MessengerService
                    .messenger(MessengerTextColor.RED)
                    .text("Не удалось удалить сохранение персонажа.")
                    .newLine()
                    .print();
        }

        // Возвращаемся в главное меню
        menu.setCurrentCharacter(null);
        menu.menuStart();
    }

}
