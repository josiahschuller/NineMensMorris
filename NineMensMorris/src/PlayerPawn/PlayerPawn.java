package PlayerPawn;

import Core.Board;
import Core.Move;
import UI.IO;

/**
 * This class is used to represent the player in the game
 * It stores the number of pieces that haven't been deployed
 * It also stores the side of the player
 */
public abstract class PlayerPawn {
    
    // Number of pieces that haven't been deployed
    protected int nonDeployedPieces;
    protected int side;
    protected IO display;
    private String playerToken;
    protected String endingMessage;

    // Constructor
    public PlayerPawn(int side, int nonDeployedPieces, IO display, String endingMessage) {
        // The player starts with 9 pieces
        this.nonDeployedPieces = nonDeployedPieces;
        this.side = side;
        this.display = display;
        setPlayerToken();
        this.endingMessage = endingMessage;
    }
    public PlayerPawn(int side, int nonDeployedPieces, IO display) {
        // The player starts with 9 pieces
        this.nonDeployedPieces = nonDeployedPieces;
        this.side = side;
        this.display = display;
        setPlayerToken();
        this.endingMessage = "GG";
    }
    private void setPlayerToken(){
        if (side == 1){
            playerToken = "X";
        } else {
            playerToken = "$";
        }
    }

    /**
     * This method chooses which move the player plays
     * @param board - game board
     * @param millJustFormed - whether a mill was just formed or not
     * @return Move that the Game can execute
     */
    public Move playTurn(Board board, boolean millJustFormed) throws Exception {
        display.sendMessage(String.format("Player %d's turn (%s)", this.getSide(), this.getPlayerToken()));
        if (millJustFormed) {
            // If a mill was just formed, remove a piece
            return this.remove(board);
        } else if (nonDeployedPieces > 0){
            // If the player still has pieces to deploy, deploy them
            return this.deploy(board);
        } else if (nonDeployedPieces == 0 && board.getPieceCount(this) > 2){
            // If the player still has no pieces left to deploy, step or fly the pieces on the board
            return this.stepFly(board);
        } else {
            // No available move
            return null;
        }
    }

    protected abstract Move deploy(Board board) throws Exception;

    protected abstract Move stepFly(Board board) throws Exception;

    protected abstract Move remove(Board board) throws Exception;


    public void deployedPiece(){
        nonDeployedPieces--;
    }
    public int getSide(){
        return side;
    }

    public int getNonDeployedPieces() {
        return nonDeployedPieces;
    }

    public String getEndMessage(){
        return endingMessage;
    }

    public String getPlayerToken() {
        return playerToken;
    }
}
