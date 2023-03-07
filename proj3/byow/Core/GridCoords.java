package byow.Core;

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
}
