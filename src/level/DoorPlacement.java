package level;

import utils.Direction;

public record DoorPlacement(GridPosition position, Direction facing) {
    public DoorPlacement(int x, int y, Direction facing) {
        this(new GridPosition(x, y), facing);
    }
}
