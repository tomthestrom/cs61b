package byow.Core;

abstract class AbstractGridObject {
    protected int xMin;

    protected int xMax;

    protected int yMin;

    protected int yMax;

    protected AbstractGridObject(int xMin, int xMax, int yMin, int yMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
    }

    public int getxMin() {
        return xMin;
    }

    public int getxMax() {
        return xMax;
    }

    public int getyMin() {
        return yMin;
    }

    public int getyMax() {
        return yMax;
    }

    public int width() {
        return xMax - xMin;
    }

    public int height() {
        return yMax - yMin;
    }

    public int size() {
        return width() * height();
    }

    public int getXCenter() {
       return ((xMax - xMin) / 2) + xMin;
    }

    public int getYCenter() {
        return ((yMax - yMin) / 2) + yMin;
    }

    public GridCoords getCenter() {
        return new GridCoords(getXCenter(), getYCenter());
    }
}
