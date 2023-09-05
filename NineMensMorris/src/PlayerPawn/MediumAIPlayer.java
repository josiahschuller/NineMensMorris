package PlayerPawn;

import Core.Board;
import Core.Location;
import Core.Move;
import Core.Piece;
import UI.IO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MediumAIPlayer extends EasyAIPlayer {
    /* Constructors */
    public MediumAIPlayer(int side, int nonDeployedPieces, IO display) {
        super(side, nonDeployedPieces, display, "You can do better than that!");
    }
    public MediumAIPlayer(int side, int nonDeployedPieces, IO display, String taunt) {
        super(side, nonDeployedPieces, display, taunt);
    }


    /**
     * Method for deciding which piece to move and where to move it
     * 1. Try to form a mill if possible
     * 2. Try to block opponent from forming a mill if possible
     * 3. Randomly select a piece to move and a location to move it to using EasyAIPlayer's heuristic
     * @param board
     * @return
     */
    @Override
    protected Move deploy(Board board) {
        // Initialise variables
        Random random = new Random();
        ArrayList<Location> myPieces = getPlayerPieceLocations(board);
        ArrayList<Location> opponentPieces = getOpponentPieceLocations(board);

        // Heuristic 1 - Move piece to form mill if possible
        // Find all locations that are one move away from forming a mill
        // and check if any of them are available to move to
        Location millLocation = millForm(myPieces, board);
        if (millLocation != null) {
            return new Move(null, millLocation.toString(), null);
        }

        // Heuristic 2 - Move piece to block opponent from forming mill if possible
        // Find all locations that are one move away from forming a mill for an opponent
        Location deployLocation = blockMillForm(opponentPieces, board);
        if (deployLocation != null) {
            return new Move(null, deployLocation.toString(), null);
        }

        // Heuristic 3 - Randomly select open location to deploy piece
        return super.deploy(board);
    }

    /**
     * Helper function for checking if a mill can be formed in one move given a list of locations
     * Returns the location that can form a mill if possible
     * @param locations
     * @param board
     * @return
     */
    protected Location millForm(ArrayList<Location> locations, Board board){
        for (Location location: locations){
            ArrayList<Location> possibleLocation = board.oneMoveFromMill(location.toString(), this.side);
            if (possibleLocation!=null){
                for (Location possible: possibleLocation){
                    if (possible!=null){
                        return possible;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Helper function for checking if a mill can be blocked in one move given a list of locations
     * Returns the location that can block a mill if possible
     * @param opponentPieces
     * @param board
     * @return
     */
    protected Location blockMillForm(ArrayList<Location> opponentPieces, Board board){
        for (Location location : opponentPieces) {
            // Find all locations that can make a mill in one move
            ArrayList<Location> millMoves = board.oneMoveFromMill(location.toString(), location.getPiece().getSide());
            for (Location targetLoc : millMoves) {
                // Check if millMove is a valid location
                if (targetLoc != null) {
                    return targetLoc;
                }
            }
        }
        return null;
    }

    /**
     * Method for deciding which piece to move and where to move it
     * 1. Try to move piece to form a mill if possible
     * 2. Try to move piece to block opponent from forming a mill if possible
     * 3. Randomly select a piece to move and a location to move it to using EasyAIPlayer's heuristic
     * @param board
     * @return
     */
    protected Move stepFly(Board board) {
        // Initialise variables
        Random random = new Random();
        ArrayList<Location> myPieces = new ArrayList<>(9);
        ArrayList<Location> opponentPieces = new ArrayList<>(9);
        HashMap<String, Location> locations = board.getBoardLocations();
        int pieceCount = board.getPieceCount(this);

        // Iterate through all locations and find all valid pieces to move
        for (Location location : locations.values()) {
            Piece locPiece = location.getPiece();
            if (locPiece != null) {
                if (locPiece.getSide() == this.side) {
                    myPieces.add(location);
                }
                else {
                    opponentPieces.add(location);
                }
            }
        }

        // Heuristic 1 - Move piece to form mill if possible
        // Check each piece location if it is one move away from making
        // a mill. If so, move it there if possible
        for (Location location : myPieces) {
            // Check if piece is one move away from making a mill and iterate
            // through them to see if any piece is available to move to this location
            ArrayList<Location> millMoves = board.oneMoveFromMill(location.toString(), this.side);
            for (int i = 0; i < millMoves.size(); i++) {
                // Check if millMove is a valid location
                Location targetLoc = millMoves.get(i);
                if (targetLoc != null) {
                    // Check if able to fly and change behavior accordingly
                    if (pieceCount == 3) {
                        // Just randomly select a piece that's not in the mill
                        for (Location pieceToMove: myPieces) {
                            // Change check depending on whether it is the horizontal or vertical mill
                            if (i == 0)
                            {
                                if (board.isLocationInLine(pieceToMove, location.toString(), true))
                                {
                                    return new Move(pieceToMove.toString(), targetLoc.toString(), null);
                                }
                            }
                            else {
                                if (board.isLocationInLine(pieceToMove, location.toString(), false))
                                {
                                    return new Move(pieceToMove.toString(), targetLoc.toString(), null);
                                }
                            }
                        }
                    }
                    else {
                        // Iterate through all pieces and check if any are able to step to this location
                        //exlude the piece that is in the mill
                        for (Location pieceToMove : myPieces) {
                            if (!board.isLocationPartOfMill(pieceToMove, targetLoc) && board.canStepTo(pieceToMove.toString(), targetLoc.toString())) {
                                return new Move(pieceToMove.toString(), targetLoc.toString(), null);
                            }
                        }
                    }
                }
            }
        }


        // Heuristic 2 - Block an opponent from creating a mill
        // Loop through opponent pieces and check if any are one move away from making a mill
        // If so, move a piece to block it if possible
        for (Location location : opponentPieces) {
            // Check if piece is one move away from making a mill and iterate
            // through them to see if any piece is available to move to this location
            ArrayList<Location> millMoves = board.oneMoveFromMill(location.toString(), location.getPiece().getSide());
            for (Location targetLoc : millMoves) {
                // Check if millMove is a valid location
                if (targetLoc != null) {
                    // Check if able to fly and change behavior accordingly
                    if (pieceCount == 3) {
                        // Just randomly select a piece that's not in the mill
                        int randomIndex = random.nextInt(myPieces.size());
                        Location randomLocation = myPieces.get(randomIndex);
                        return new Move(randomLocation.toString(), targetLoc.toString(), null);
                    } else {
                        // Iterate through all pieces and check if any are able to step to this location
                        for (Location pieceToMove : myPieces) {
                            if (board.canStepTo(pieceToMove.toString(), targetLoc.toString())) {
                                return new Move(pieceToMove.toString(), targetLoc.toString(), null);
                            }
                        }
                    }
                }
            }
        }

        // Heuristic 3 - Randomly move piece
        // Randomly choose from valid locations to move
        return super.stepFly(board);
    }


}
