package byow.Core;

import java.util.Objects;

public record GridCoords(int x, int y) {
    public static int DIRECTIONS = 4;

    private GridCoords top() {
        return new GridCoords(x, y + 1);
    }

    private GridCoords bottom() {
        return new GridCoords(x, y - 1);
    }

    private GridCoords left() {
        return new GridCoords(x - 1, y);
    }
    private GridCoords right() {
        return new GridCoords(x + 1, y);
    }

    public GridCoords getNextInDirection(Direction direction) {
        return switch (direction) {
            case UP -> top();
            case DOWN -> bottom();
            case LEFT -> left();
            default -> right();
        };
    }

    public GridCoords[] directions() {
        GridCoords[] directions = new GridCoords[Direction.values().length];

        int i = 0;
        for (Direction direction: Direction.values()) {
            directions[i] = getNextInDirection(direction);
            i++;
        }
           return directions;
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
