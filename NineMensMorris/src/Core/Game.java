package Core;

import PlayerPawn.PlayerPawn;
import UI.IO;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/***
 * This is the main class of the game, it is responsible for the game loop and the game state
 */
public class Game {


    /* Attributes */
    private Board board;
    private ArrayList<PlayerPawn> players;
    private IO display;



    /* Constructors */
    // New Game
    public Game(PlayerPawn player1, PlayerPawn player2, int numberOfPieces, IO display){
        board = new Board();
        players = new ArrayList<PlayerPawn>();
        players.add(player1);
        players.add(player2);

        this.display = display;
    }


    /***
     * Game Loop
     */
    public void playGame(int delay) {
        // Game loop
        // The game loop until the ProcessPlayerTurn encounters a situation
        // where the player cannot move and thus loses is removed from the player pool
        display.display(this.board);
        while (players.size() > 1){
            // Process each player's turn
            for (int i = 0; i < players.size(); i++){
                // Display the board
//                display.display(this.board);

                // Process the player's turn
                PlayerPawn player = players.get(i);
                boolean hasMove = processPlayerTurn(player);
                try {
                    TimeUnit.SECONDS.sleep(delay);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                // If the player cannot move or has less than 3 pieces, remove them from the game
                if (!hasMove){
                    players.remove(i);
                    break; // TODO: This resets the turn counter if there is more than 2 players
                }
            }
        }

        // Display the winner, the remaining player
        this.display.sendMessage(String.format("Player %d wins!\n%s", players.get(0).getSide(), players.get(0).getEndMessage()));
    }


    /**
     * This method processes the player turn and executes the move the player makes
     * @param player - player whose turn it is
     * @return boolean of whether the player had a move or not
     */
    private boolean processPlayerTurn(PlayerPawn player) {
        // Initialise variables
        boolean hasMove=false;

        // Check if player has no undeployed pieces before any other checks
        if (player.getNonDeployedPieces() == 0){
            // Check if the player has less than 3 pieces
            if (board.getPieceCount(player) < 3){
                this.display.sendMessage(String.format("Player %d has less than 3 pieces! They are eliminated!", player.getSide()));
                return false;
            }

            // Check if the player has any moves possible
            if (!board.playerHasPossibleMoves(player)){
                this.display.sendMessage(String.format("Player %d has no possible moves! They are eliminated!", player.getSide()));
                return false;
            }
        }

        // Get move from player's turn
        boolean turnIsValid = false;
        while (!turnIsValid) {

            String message = "";

            try {
                Move move = player.playTurn(this.board, false);

                if (move != null && (move.typeOfMove() == MoveType.DEPLOY || move.typeOfMove() == MoveType.STEPFLY)) {
                    if (move.typeOfMove() == MoveType.DEPLOY) {
                        // Execute deploy move
                        Piece piece = new Piece(player.getSide(), player.getPlayerToken());
                        this.board.placePiece(move.getFinalPosition(), piece);
                        player.deployedPiece();
                        message = String.format("Player %d (%s) deployed a piece at %s", player.getSide(), player.getPlayerToken(),move.getFinalPosition());
                    } else if (move.typeOfMove() == MoveType.STEPFLY) {
                        // Execute step/fly move
                        this.board.movePiece(move.getStartPosition(), move.getFinalPosition(), player);
                        message = String.format("Player %d (%s) moved a piece from %s to %s", player.getSide(), player.getPlayerToken(), move.getStartPosition(), move.getFinalPosition());
                    }

                    // Check if a mill has just been formed
                    if (this.board.millCheck(move.getFinalPosition())) {
                        // A mill has been formed, so let the player remove a piece
                        // Display the board
                        // Get the move and execute it
                        Move removeMove = player.playTurn(this.board, true);
                        if (removeMove != null){
                            this.board.removePiece(removeMove.getRemovePosition());
                            message += "\nA mill has been formed! Player " + player.getSide() + " removed a piece at " + removeMove.getRemovePosition() + "!";
                        }else{
                            message += "\nA mill has been formed! Player " + "Opponent pieces are all in mills!";
                        }
                    }
                    hasMove = true;
                }
                turnIsValid = true;
                display.display(this.board);
                display.sendMessage(message);

            } catch (Exception e) {
                display.sendMessage(e.getMessage());
            }
        }

        return hasMove;
    }
}
