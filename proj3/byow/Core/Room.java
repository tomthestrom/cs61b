package byow.Core;

public class Room extends AbstractGridObject implements GridSearchable {
    private boolean isConnected;
    private GridCoords source;
    private GridCoords target;
    private Room targetRoom;

    public static final int MIN_WIDTH = 4;
    public static final int MIN_HEIGHT = 4;

    public static final int WALL_WIDTH = 1;

    public Room(int xMin, int xMax, int yMin, int yMax) {
        super(xMin, xMax, yMin, yMax);
        isConnected = false;
    }

    public void setIsConnected(boolean isConnected) {
        this.isConnected = true;
    }

    public void setSource(GridCoords source) {
       this.source = source;
    }

    public void setTarget(GridCoords target) {
        this.target = target;
    }

    @Override
    public GridCoords getSource() {
        return source;
    }

    @Override
    public GridCoords getTarget() {
        return target;
    }

    @Override
    public double distanceFromSource() {
        return GridMathUtils.euclideanDistance(source, target);
    }

    public void setTargetRoom(Room targetRoom) {
        this.targetRoom = targetRoom;
    }
    public Room getTargetRoom() {
        return targetRoom;
    }

}
