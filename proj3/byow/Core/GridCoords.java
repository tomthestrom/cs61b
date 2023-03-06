package byow.Core;

public record GridCoords(int x, int y) {
    public GridCoords coordAbove() {
        return new GridCoords(x, y + 1);
    }

    public GridCoords coordDown() {
        return new GridCoords(x, y + 1);
    }

    public GridCoords coordLeft() {
        return new GridCoords(x - 1, y);
    }
    public GridCoords coordRight() {
        return new GridCoords(x + 1, y);
    }
}
