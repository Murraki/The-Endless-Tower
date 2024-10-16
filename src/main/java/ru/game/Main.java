package ru.game;

import ru.game.initializers.GameInitializer;
import ru.game.service.game.GameService;

public class Main {
    public static void main(String[] args) {
        GameInitializer gameInitializer = new GameInitializer();
        gameInitializer.initialize();

        GameService gameService = gameInitializer.getGameService();
        gameService.startGame();
    }
}