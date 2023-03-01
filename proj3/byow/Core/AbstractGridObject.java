package byow.Core;

abstract class AbstractGridObject {
    protected int xMin;

    protected int xMax;

    protected int yMin;

    protected int yMax;

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
}
