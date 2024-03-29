package game2048;

import java.util.Formatter;
import java.util.Hashtable;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author TODO: YOUR NAME HERE
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private final Board _board;
    /** Current score. */
    private int _score;
    /** Maximum score so far.  Updated when game ends. */
    private int _maxScore;
    /** True iff game is ended. */
    private boolean _gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        _board = new Board(size);
        _score = _maxScore = 0;
        _gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        _board = new Board(rawValues);
        this._score = score;
        this._maxScore = maxScore;
        this._gameOver = gameOver;
    }

    /** Same as above, but gameOver is false. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore) {
        this(rawValues, score, maxScore, false);
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     * */
    public Tile tile(int col, int row) {
        return _board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return _board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (_gameOver) {
            _maxScore = Math.max(_score, _maxScore);
        }
        return _gameOver;
    }

    /** Return the current score. */
    public int score() {
        return _score;
    }

    /**
     * Set score
     * @return void
     */
    private void set_score(int newScore) {
        _score = newScore;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return _maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        _score = 0;
        _gameOver = false;
        _board.clear();
        setChanged();
    }

    /** Allow initial game board to announce a hot start to the GUI. */
    public void hotStartAnnounce() {
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        _board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     */
    public void tilt(Side side) {

        _board.setViewingPerspective(side);
        Hashtable<Tile, Boolean> MergedTiles = new Hashtable<>();

            for (int x = 0; x < _board.size(); x += 1) {
                for (int y = _board.size() - 1; y >= 0; y -= 1) {
                    Tile t = _board.tile(x, y);

                    if (t != null) {
                        int moveToRow = -1;
                        boolean shouldMerge = false;

                        for (int rowAbove = y + 1; rowAbove < _board.size(); rowAbove += 1) {
                            Tile aboveTile = _board.tile(x, rowAbove);
                            //if the tile above is null, tile t can move there
                            // continue to check if next is null or merge
                            if (aboveTile == null) {
                                moveToRow = rowAbove;
                                continue;
                            }
                            // we are merging if value equals and tile hasn't been merged
                            if (aboveTile.value() == t.value() && !MergedTiles.containsKey(aboveTile)) {
                                shouldMerge = true;
                                moveToRow = rowAbove;
                                }

                            // is not null, it can or can't merge - no need to check the other rows (tiles can either merge or take up empty space)
                            break;
                        }

                        if (shouldMerge) {
                            _board.move(x, moveToRow, t);
                            Tile movedT = _board.tile(x, moveToRow);

                            //newScore - add the value of the newly merged tile to total score;
                            int newScore = score() + movedT.value();
                            set_score(newScore);

                            MergedTiles.put(movedT, true);
                        } else if (moveToRow > - 1) {
                            _board.move(x, moveToRow, t);
                        }
                    }
            }
        }

        _board.setViewingPerspective(Side.NORTH);
        checkGameOver();
    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        _gameOver = checkGameOver(_board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     */
    public static boolean emptySpaceExists(Board b) {
        for (int i = 0; i < b.size(); i += 1) {
            for (int j = 0; j < b.size(); j += 1) {
                if (b.tile(i, j) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by this.MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        for (int i = 0; i < b.size(); i += 1) {
            for (int j = 0; j < b.size(); j += 1) {
                if (b.tile(i, j) != null && b.tile(i, j).value() == Model.MAX_PIECE) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
       if (emptySpaceExists(b)) {
            return true;
        }

        /** Go by rows, check direction bottom -> up and left -> right (start 0,0, is at bottom left) */
        for (int y = 0; y < b.size(); y += 1) {
            /** direction x - only loop till (size - 1) - (going left to right in comparisons) */
            for (int x = 0; x < b.size() - 1; x += 1) {
                /** check for a match in direction left to right (x axis) */
                if (b.tile(x, y).value() == b.tile(x + 1, y).value()) {
                    return true;
                }
                /* if not last row we check if there's a match dir up (y axis) */
                if (y != b.size() - 1 && b.tile(x, y).value() == b.tile(x, y + 1).value()) {
                   return true;
                }
            }
        }

        return false;
    }

    /** Returns the model as a string, used for debugging. */
    @Override
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    /** Returns whether two models are equal. */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    /** Returns hash code of Model’s string. */
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
