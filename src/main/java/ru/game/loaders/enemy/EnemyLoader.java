package ru.game.loaders.enemy;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.game.data.enemy.EnemyData;
import ru.game.enums.enemy.EnemyType;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnemyLoader {

    private static final String COMMON_ENEMIES_PATH = "/json/enemy/commonEnemies.json";
    private static final String ELITE_ENEMIES_PATH = "/json/enemy/eliteEnemies.json";
    private static final String BOSS_ENEMIES_PATH = "/json/enemy/bossesEnemies.json";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Map<EnemyType, List<EnemyData>> loadEnemies() {
        Map<EnemyType, List<EnemyData>> enemies = new HashMap<>();
        enemies.put(EnemyType.COMMON, loadEnemiesFromFile(COMMON_ENEMIES_PATH, "commonEnemies"));
        enemies.put(EnemyType.ELITE, loadEnemiesFromFile(ELITE_ENEMIES_PATH, "eliteEnemies"));
        enemies.put(EnemyType.BOSS, loadEnemiesFromFile(BOSS_ENEMIES_PATH, "bossesEnemies"));
        return enemies;
    }

    private List<EnemyData> loadEnemiesFromFile(String path, String rootName) {
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is == null) {
                throw new IOException("Resource not found: " + path);
            }
            Map<String, List<EnemyData>> data = objectMapper.readValue(is, new TypeReference<Map<String, List<EnemyData>>>() {});
            return data.get(rootName);
        } catch (IOException e) {
            System.err.println("Error loading enemies from " + path + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

}
