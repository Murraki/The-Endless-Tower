package ru.game.wrapers.character;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.game.data.character.RaceData;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacterRaceWrapper {

    private List<RaceData> races;
}
