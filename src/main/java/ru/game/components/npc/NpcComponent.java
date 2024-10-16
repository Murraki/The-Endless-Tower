package ru.game.components.npc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.game.data.npc.NpcData;
import ru.game.enums.npc.NpcType;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NpcComponent {

    private NpcData data;

    private List<NpcType> types;
}
