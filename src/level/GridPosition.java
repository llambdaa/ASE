package level;

import utils.Direction;

public record GridPosition(int x, int y) {
    public GridPosition getAdjacentPosition(Direction direction) {
        return switch (direction) {
            case UP: {
                yield new GridPosition(this.x, this.y - 1);
            }
            case DOWN: {
                yield new GridPosition(this.x, this.y + 1);
            }
            case LEFT: {
                yield new GridPosition(this.x - 1, this.y);
            }
            case RIGHT: {
                yield new GridPosition(this.x + 1, this.y);
            }
        };
    }
}
