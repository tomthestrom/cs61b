package byow.Core;

import java.util.Objects;

public record GridCoords(int x, int y) {
    public static int DIRECTIONS = 4;

    private GridCoords up() {
        return new GridCoords(this.x, this.y + 1);
    }

    private GridCoords down() {
        return new GridCoords(this.x, this.y - 1);
    }

    private GridCoords left() {
        return new GridCoords(this.x - 1, this.y);
    }
    private GridCoords right() {
        return new GridCoords(this.x + 1, this.y);
    }

    public GridCoords getNextInDirection(Direction direction) {
        return switch (direction) {
            case UP -> up();
            case DOWN -> down();
            case LEFT -> left();
            case RIGHT -> right();
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GridCoords that = (GridCoords) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
