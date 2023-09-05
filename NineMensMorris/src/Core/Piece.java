package Core;

/**
 * This class is used to represent a piece
 *
 */
public class Piece {
    private int side;
    private String playerToken;

    public Piece(int side, String playerToken) {

        this.side = side;
        this.playerToken = playerToken;
        //piece starts in deploy state
    }

    public int getSide() {return side;}

    public boolean equals(Piece otherPiece) {
        if (otherPiece.side == this.side) {
            return true;
        }
        return false;
    }
    public boolean equals(int side) {
        return side == this.side;
    }

    public String toString() {
        return playerToken;
    }
}
