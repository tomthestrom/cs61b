package byow.Core;

import java.util.Objects;

public record GridCoords(int x, int y) {
    public static int DIRECTIONS = 4;

    public GridCoords top() {
        return new GridCoords(x, y + 1);
    }

    public GridCoords bottom() {
        return new GridCoords(x, y - 1);
    }

    public GridCoords left() {
        return new GridCoords(x - 1, y);
    }
    public GridCoords right() {
        return new GridCoords(x + 1, y);
    }

    public GridCoords[] directions() {
        GridCoords[] directions = new GridCoords[DIRECTIONS];
           directions[0] = top();
           directions[1] = bottom();
           directions[2] = left();
           directions[3] = right();

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
