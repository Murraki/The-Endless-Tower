package ru.game.initializers;

import lombok.Data;
import ru.game.data.enemy.EnemyData;
import ru.game.enums.enemy.EnemyType;
import ru.game.generaters.enemy.EnemyGenerator;
import ru.game.generaters.floor.FloorGenerator;
import ru.game.loaders.character.CharacterLoader;
import ru.game.loaders.enemy.EnemyLoader;
import ru.game.service.character.CharacterService;
import ru.game.service.game.GameService;
import ru.game.service.menu.MenuService;

import java.util.List;
import java.util.Map;

@Data
public class GameInitializer {

    private EnemyGenerator enemyGenerator;
    private FloorGenerator floorGenerator;
    private GameService gameService;
    private MenuService menuService;
    private CharacterService characterService;

    public void initialize() {
        System.out.println("Initializing game components...");
        initializeEnemyGenerator();
        initializeFloorGenerator();
        initializeServices();
        System.out.println("Game components initialized successfully.");
    }

    private void initializeEnemyGenerator() {
        EnemyLoader enemyLoader = new EnemyLoader();
        Map<EnemyType, List<EnemyData>> enemiesData = enemyLoader.loadEnemies();
        this.enemyGenerator = new EnemyGenerator(enemiesData);
    }

    private void initializeFloorGenerator() {
        this.floorGenerator = new FloorGenerator(enemyGenerator);
    }

    private void initializeServices() {
        CharacterLoader characterLoader = new CharacterLoader();
        this.characterService = new CharacterService(characterLoader);
        this.menuService = new MenuService(characterService);
        this.gameService = new GameService(menuService, characterService, floorGenerator);
        menuService.setGameService(gameService);
    }

}
