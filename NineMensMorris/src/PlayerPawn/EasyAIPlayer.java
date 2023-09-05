package PlayerPawn;

import Core.Board;
import Core.Location;
import Core.Move;
import Core.Piece;
import UI.IO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class EasyAIPlayer extends PlayerPawn {
    /* Constructors */
    public EasyAIPlayer(int side, int nonDeployedPieces, IO display) {
        super(side, nonDeployedPieces, display,"How do you even lose to this?");
    }
    public EasyAIPlayer(int side, int nonDeployedPieces, IO display, String taunt) {
        super(side, nonDeployedPieces, display, taunt);
    }


    /* Methods */

    /**
     * Method for deciding which location to deploy piece to
     * @param board
     * @return
     */
    protected Move deploy(Board board){
        // Randomly select open location to deploy piece
        ArrayList<Location> validLocations = getEmptyLocations(board);
        return deployAux(validLocations);

    }

    /**
     * Helper function for deploying
     * Uses random heuristic to select a location to deploy
     * @param validLocations
     * @return
     */
    protected Move deployAux(ArrayList<Location> validLocations){
        // Randomly select location from valid locations
        Random random = new Random();
        int randomIndex = random.nextInt(validLocations.size());
        Location randomLocation = validLocations.get(randomIndex);
        return new Move(null, randomLocation.toString(), null);
    }

    /**
     * Method for deciding which piece to move and where to move it
     * Uses random heuristic to select a piece to move and a location to move it to
     * @param board
     * @return
     */
    protected Move stepFly(Board board) {
        // Randomly move piece
        // Iterate through all locations and find all valid pieces to move
        Random random = new Random();
        ArrayList<Location> validLocations = getPlayerPieceLocations(board);

        // Randomly choose from valid locations to move
        Boolean foundValidMove = false;
        int pieceCount = board.getPieceCount(this);

        // Change behavior based on whether it is possible to fly
        if (pieceCount == 3) {
            int randomIndex = random.nextInt(validLocations.size());
            Location randomLocation = validLocations.get(randomIndex);
            ArrayList<Location> validMoves = getEmptyLocations(board);
            randomIndex = random.nextInt(validMoves.size());
            Location randomMove = validMoves.get(randomIndex);
            return new Move(randomLocation.toString(), randomMove.toString(), null);
        }

        while (!foundValidMove) {
            int randomIndex = random.nextInt(validLocations.size());
            Location randomLocation = validLocations.get(randomIndex);
            ArrayList<Location> validMoves = board.getLocationsEmptyAdjacent(randomLocation.toString());
            if (validMoves.size() > 0) {
                int randomMoveIndex = random.nextInt(validMoves.size());
                Location randomMove = validMoves.get(randomMoveIndex);
                foundValidMove = true;
                return new Move(randomLocation.toString(), randomMove.toString(), null);
            }
        }

        // Base case
        return null;
    }

    /**
     * Method for deciding which piece to remove
     * Uses random heuristic to select a piece to remove
     * @param board
     * @return
     */
    protected Move remove(Board board) {
        // Randomly select piece to remove
        // Find all valid locations with a piece to remove
        Random random = new Random();
        ArrayList<Location> validLocations = getOpponentPieceLocations(board);

        // Randomly select piece from valid locations
        if (validLocations.size() > 0) {
            int randomIndex = random.nextInt(validLocations.size());
            Location randomLocation = validLocations.get(randomIndex);
            return new Move(null, null, randomLocation.toString());
        }
        return null;
    }

    /**
     * Helper function for getting all empty locations on the board
     * Protected which allows for subclasses to use the helper functions
     * @param board
     * @return
     */
    protected ArrayList<Location> getEmptyLocations(Board board ){
        HashMap<String, Location> locations = board.getBoardLocations();
        ArrayList<Location> emptyLocations = new ArrayList<>();
        for (Location location: locations.values()){
            if (location.getPiece()==null){
                emptyLocations.add(location);
            }
        }
        return emptyLocations;
    }

    /**
     * Helper function for getting all locations with a piece on the board
     * Protected which allows for subclasses to use the helper functions
     * @param board
     * @return
     */
    protected ArrayList<Location> getPlayerPieceLocations(Board board){
        HashMap<String, Location> locations = board.getBoardLocations();
        ArrayList<Location> playerPieceLocations = new ArrayList<>();
        for (Location location: locations.values()){
            if (location.getPiece()!=null && location.getPiece().getSide()==this.side){
                playerPieceLocations.add(location);
            }
        }
        return playerPieceLocations;
    }

    /**
     * Helper function for getting all locations with an opponent's piece on the board
     * Protected which allows for subclasses to use the helper functions
     * @param board
     * @return
     */
    protected ArrayList<Location> getOpponentPieceLocations(Board board){
        HashMap<String, Location> locations = board.getBoardLocations();
        ArrayList<Location> opponentPieceLocations = new ArrayList<>();
        for (Location location: locations.values()){
            if (location.getPiece()!=null && location.getPiece().getSide()!=this.side){
                opponentPieceLocations.add(location);
            }
        }
        return opponentPieceLocations;
    }
}
