package Core;

/**
 * This class is used to represent a location on the board
 * It stores the piece that is on the location
 *
 */
public class Location {


    private Piece piece;
    private boolean inMill;
    private String column;
    private int row;
    private String[] adjacentLocations;


    public Location(String column, int row, String[] adjacentLocations){
        this.column = column;
        this.row = row;
        this.adjacentLocations = adjacentLocations;
        piece = null;
        inMill = false;
    }

    /**
     * Add a piece to the location if it is empty
     * throws error if location is not empty
     * @param piece
     * @throws Exception
     */
    public void addPiece(Piece piece) throws Exception {
        if (this.piece != null){
            throw new Exception("Location already occupied");
        }
        this.piece = piece;
    }

    /**
     * Remove a piece from the location if it is not empty
     * throws error if location is empty
     * @throws Exception
     */
    public void removePiece() throws Exception {
        if (piece == null){
            throw new Exception("Location is empty");
        }
        piece = null;
    }
    public Piece getPiece() {
        return piece;
    }

    public String[] getAdjacentLocations() {
        return this.adjacentLocations;
    }

    public String toString(){
        return String.format("%s%d",column,row);
    }

    public boolean equals(int side){
        if (piece == null){
            return false;
        }
        return piece.equals(side);
    }
}
