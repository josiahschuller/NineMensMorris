package PlayerPawn;

import Core.Board;
import Core.Location;
import Core.Move;
import UI.IO;


/**
 * This class is used to represent a human player
 * Extends PlayerPawn
 *
 * @see PlayerPawn
 */
public class HumanPlayer extends PlayerPawn {
    // Constructor
    public HumanPlayer(int side, int nonDeployedPieces, IO display) {
        super(side, nonDeployedPieces, display);
    }


    /**
     * This method let user choose where to deploy a piece
     *
     * @param board
     * @return Move that the game can execute
     */
    @Override
    public Move deploy(Board board) throws Exception {
        Boolean isValid = false;
        String deployUserInput = null;

        // Send move prompt
        display.sendMessage(String.format("Deploy piece (%d pieces left)", this.getNonDeployedPieces()));

        while (!isValid) {
            // Get user input
            display.sendMessage("Location to deploy piece: ");
            deployUserInput = display.getInput().toUpperCase();

            Location deployLocation = board.getLocation(deployUserInput);
            if (deployLocation != null) {
                if (deployLocation.getPiece() == null) {
                    isValid = true;
                } else {
                    display.sendMessage("There is already a piece at this location.");
                }
            } else {
                display.sendMessage("Invalid location.");
            }
        }

        return new Move(null, deployUserInput, null);
    }

    /**
     * This method let user choose where to move a piece, and attempts to move
     * Check the number of pieces the player has on board to determine a fly or step move
     * It uses the board to check if the location is valid
     *
     * @param board
     * @return Move that the game can execute
     */
    @Override
    public Move stepFly(Board board) throws Exception {
        // Get location to move the piece from
        Boolean fromIsValid = false;
        String fromUserInput = null;

        // Send move prompt
        display.sendMessage("Move piece");

        while (!fromIsValid) {
            // Get user input
            if (board.getPieceCount(this) > 3) {
                display.sendMessage("Location of piece to move: ");
            } else {
                display.sendMessage("Location of piece to move (you can FLY): ");
            }
            fromUserInput = display.getInput().toUpperCase();

            // Interpret the location from the input
            Location fromLocation = board.getLocation(fromUserInput);
            // Check whether there is a piece that can be moved from that location
            if (fromLocation != null) {
                if (fromLocation.getPiece() != null) {
                    if (fromLocation.getPiece().getSide() == this.getSide()) {
                        fromIsValid = true;
                    } else {
                        display.sendMessage("Cannot move opponent's piece.");
                    }
                } else {
                    display.sendMessage("There is no piece at this location.");
                }
            } else {
                display.sendMessage("Invalid location.");
            }
        }


        Boolean toIsValid = false;
        String toUserInput = null;

        while (!toIsValid) {
            // Get user input
            if (board.getPieceCount(this) > 3) {
                display.sendMessage("Location to move piece to: ");
            } else {
                display.sendMessage("Location to move piece to (you can FLY): ");
            }
            toUserInput = display.getInput().toUpperCase();

            // Interpret the location from the input
            Location toLocation = board.getLocation(toUserInput);
            // Check whether there is a piece that can be moved from that location
            if (toLocation != null) {
                if (toLocation.getPiece() == null) {
                    toIsValid = true;
                } else {
                    display.sendMessage("Cannot move to a location with a piece already in it.");
                }
            } else {
                display.sendMessage("Invalid location.");
            }
        }

        return new Move(fromUserInput, toUserInput, null);
    }

    /**
     * This method let user choose what piece to remove
     * It uses the board to check if the location is valid
     *
     * @param board
     * @return Move that the game can execute
     */
    @Override
    public Move remove(Board board) throws Exception {
        Boolean isValid = false;
        String removeUserInput = null;

        // Send move prompt
        display.sendMessage("Remove piece");

        // Check if other players have non-mill pieces
        if (!board.otherPlayersHaveNonMillPieces(this.getSide())) {
            display.sendMessage("All other pieces are in a mill! You cannot remove a piece.");
            return null;
        }

        while (!isValid) {
            // Get user input
            display.sendMessage("Location of opponent's piece to remove: ");
            removeUserInput = display.getInput().toUpperCase();

            // Interpret location from user input
            Location removeLocation = board.getLocation(removeUserInput);
            // Check whether the location is a valid location to remove a piece from
            if (removeLocation != null) {
                if (removeLocation.getPiece() != null) {
                    if (removeLocation.getPiece().getSide() != this.getSide()) {
                        if (!board.millCheck(removeUserInput)) {
                            isValid = true;
                        } else {
                            display.sendMessage("Cannot remove a piece in a mill.");
                        }

                    } else {
                        display.sendMessage("Cannot remove your own piece.");
                    }
                } else {
                    display.sendMessage("There is no piece at this location.");
                }
            } else {
                display.sendMessage("Invalid location.");
            }
        }

        return new Move(null, null, removeUserInput);
    }
}
