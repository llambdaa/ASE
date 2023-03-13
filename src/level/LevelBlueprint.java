package level;

import utils.Direction;

import java.util.List;
import java.util.Map;

public record LevelBlueprint(RoomGrid grid, Map<GridPosition, List<Direction>> graph) {
}
