package game;

public class BoardSpot {
    private int row;
    private int col;

    public BoardSpot() {
        row = 0;
        col = 0;
    }

    public BoardSpot(int rowVal, int columnVal) {
        row = rowVal;
        col = columnVal;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean equals (Object other) {

        if ( other instanceof BoardSpot ) {
            BoardSpot o = (BoardSpot) other;
            return row == o.row && col == o.col;
        } else {
            return false;
        }
    }
}
