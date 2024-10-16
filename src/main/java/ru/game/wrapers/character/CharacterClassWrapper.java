package ru.game.wrapers.character;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.game.data.character.ClassData;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacterClassWrapper {

    private List<ClassData> classes;
}
