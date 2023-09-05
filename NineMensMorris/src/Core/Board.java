package Core;

import PlayerPawn.PlayerPawn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/***
 * This class represents the board of the game
 * It is responsible for keeping the locations of the board
 * It is responsible for checking the valid move
 * It is responsible for checking if a mill is formed
 *
 */
public class Board {
    private HashMap<String,Location> boardLocations;

    private HashMap<String, Line> boardLines;


    /**
     * Constructor
     * Creates the board of the game
     * Creates the lines of the board
     *
     *
     */
    public Board(){
        // Add all the board locations for Nine Men's Morris
        boardLocations = new HashMap<String, Location>();
        boardLocations.put("A1", new Location("A", 1, new String[]{"A4", "D1"}));
        boardLocations.put("A4", new Location("A", 4, new String[]{"A1", "A7", "B4"}));
        boardLocations.put("A7", new Location("A", 7, new String[]{"A4", "D7"}));
        boardLocations.put("B2", new Location("B", 2, new String[]{"B4", "D2"}));
        boardLocations.put("B4", new Location("B", 4, new String[]{"A4", "B2", "B6", "C4"}));
        boardLocations.put("B6", new Location("B", 6, new String[]{"B4", "D6"}));
        boardLocations.put("C3", new Location("C", 3, new String[]{"C4", "D3"}));
        boardLocations.put("C4", new Location("C", 4, new String[]{"B4", "C3", "C5"}));
        boardLocations.put("C5", new Location("C", 5, new String[]{"C4", "D5"}));
        boardLocations.put("D1", new Location("D", 1, new String[]{"A1", "D2", "G1"}));
        boardLocations.put("D2", new Location("D", 2, new String[]{"B2", "D1", "D3", "F2"}));
        boardLocations.put("D3", new Location("D", 3, new String[]{"C3", "D2", "E3"}));
        boardLocations.put("D5", new Location("D", 5, new String[]{"C5", "D6", "E5"}));
        boardLocations.put("D6", new Location("D", 6, new String[]{"B6", "D5", "D7", "F6"}));
        boardLocations.put("D7", new Location("D", 7, new String[]{"A7", "D6", "G7"}));
        boardLocations.put("E3", new Location("E", 3, new String[]{"D3", "E4"}));
        boardLocations.put("E4", new Location("E", 4, new String[]{"E3", "E5", "F4"}));
        boardLocations.put("E5", new Location("E", 5, new String[]{"D5", "E4"}));
        boardLocations.put("F2", new Location("F", 2, new String[]{"D2", "F4"}));
        boardLocations.put("F4", new Location("F", 4, new String[]{"E4", "F2", "F6", "G4"}));
        boardLocations.put("F6", new Location("F", 6, new String[]{"D6", "F4"}));
        boardLocations.put("G1", new Location("G", 1, new String[]{"D1", "G4"}));
        boardLocations.put("G4", new Location("G", 4, new String[]{"F4", "G1", "G7"}));
        boardLocations.put("G7", new Location("G", 7, new String[]{"D7", "G4"}));
        // Add all the board lines
        boardLines = new HashMap<String, Line>();
        boardLines.put("A", new Line(boardLocations.get("A1"), boardLocations.get("A4"), boardLocations.get("A7")));
        boardLines.put("B", new Line(boardLocations.get("B2"), boardLocations.get("B4"), boardLocations.get("B6")));
        boardLines.put("C", new Line(boardLocations.get("C3"), boardLocations.get("C4"), boardLocations.get("C5")));
        boardLines.put("D", new Line(boardLocations.get("D1"), boardLocations.get("D2"), boardLocations.get("D3")));
        boardLines.put("D1", new Line(boardLocations.get("D5"), boardLocations.get("D6"), boardLocations.get("D7")));
        boardLines.put("E", new Line(boardLocations.get("E3"), boardLocations.get("E4"), boardLocations.get("E5")));
        boardLines.put("F", new Line(boardLocations.get("F2"), boardLocations.get("F4"), boardLocations.get("F6")));
        boardLines.put("G", new Line(boardLocations.get("G1"), boardLocations.get("G4"), boardLocations.get("G7")));
        boardLines.put("1", new Line(boardLocations.get("A1"), boardLocations.get("D1"), boardLocations.get("G1")));
        boardLines.put("2", new Line(boardLocations.get("B2"), boardLocations.get("D2"), boardLocations.get("F2")));
        boardLines.put("3", new Line(boardLocations.get("C3"), boardLocations.get("D3"), boardLocations.get("E3")));
        boardLines.put("4", new Line(boardLocations.get("A4"), boardLocations.get("B4"), boardLocations.get("C4")));
        boardLines.put("4_1", new Line(boardLocations.get("E4"), boardLocations.get("F4"), boardLocations.get("G4")));
        boardLines.put("5", new Line(boardLocations.get("C5"), boardLocations.get("D5"), boardLocations.get("E5")));
        boardLines.put("6", new Line(boardLocations.get("B6"), boardLocations.get("D6"), boardLocations.get("F6")));
        boardLines.put("7", new Line(boardLocations.get("A7"), boardLocations.get("D7"), boardLocations.get("G7")));

    }

    /**
     * Get the location object for a given location string and the piece
     * @param location
     * @param piece
     * @throws Exception
     */
    public void placePiece(String location, Piece piece) throws Exception {
        location = location.toUpperCase();
        getLocation(location).addPiece(piece);
        //check for mill vertically and horizontally
    }

    /**
     * Get the location object for a given location string and call the remove piece method
     * @param location
     * @throws Exception
     */
    public void removePiece(String location) throws Exception {
        // Enforce location is in the correct format

        // Remove the piece
        getLocation(location).removePiece();
    }

    /**
     * Get the location object for a given location string and transfer the piece to the new location
     *
     * @param from
     * @param to
     * @param player
     * @throws Exception
     */
    public void movePiece(String from, String to, PlayerPawn player) throws Exception {
        // Enforce the locations are in the correct format

        // Remove the piece from the original location and place it in the new one
        Piece temp = boardLocations.get(from).getPiece();

        if(!temp.equals(player.getSide())){
            throw new Exception("You can only move your own pieces");
        }

        // Check if location is already occupied
        if (getLocation(to).getPiece() != null) {
            throw new Exception("Location already occupied");
        }

        // Check if the player has more than 3 pieces and thus can only move not fly
        if (getPieceCount(player) > 3)
        {
            // They can't fly so throw an exception if destination isn't adjacent to start location
            if (!locationsAreAdjacentString(from, to)) {
                throw new Exception("You can only fly when you have 3 pieces or less");
            }
        }

        getLocation(to).addPiece(temp);
        getLocation(from).removePiece();
    }


    /***
     * Checks if it is possible to STEP from one location to another.
     * Target location must be empty and adjacent to the source location.
     * @param from
     * @param to
     * @return
     */
    public boolean canStepTo(String from, String to) {
        // Retrieve the location objects for the given locations
        Location toLocation = getLocation(to);

        // Not valid move if the target location is occupied
        if (toLocation.getPiece() != null) {
            return false;
        }

        // Not valid move if the target location is not adjacent to the source location
        String[] adjacentLocations = toLocation.getAdjacentLocations();
        boolean adjacent = false;
        for (String adjacentLocation : adjacentLocations) {
            if (adjacentLocation.equals(from)) {
                adjacent = true;
                break;
            }
        }

        return adjacent;
    }

    /**
     * Check if the adjacent locations for mill check are occupied by the same player
     * @param location
     * @return
     */
    public boolean millCheck(String location){
        String column = location.substring(0, 1);
        int row=  Integer.parseInt(location.substring(1));
        Line horizontalLine = null;
        Line verticalLine = null;
        //if location is on d line or is 4, check the row for different line
        if(column.equals("D") && row >3){

            verticalLine= boardLines.get("D1");

        } else{
            verticalLine= boardLines.get(column);
        }

        if(row == 4 && column.compareTo("C") >0){

            horizontalLine= boardLines.get("4_1");

        }else{
            horizontalLine= boardLines.get(Integer.toString(row));

        }



        return horizontalLine.isMill() || verticalLine.isMill();
    }




    /**
     * Check if a location's line is one move from a mill and if that move is possible (not occupied)
     * If it is then it returns the location of where a piece needs to be moved to form a mill
     * @param location
     * @param playerSide
     * @return
     */
    public ArrayList<Location> oneMoveFromMill(String location, int playerSide) {
        // Retrieve the lines for the given location
        String column = location.substring(0, 1);
        int row =  Integer.parseInt(location.substring(1));
        Line horizontalLine = null;
        Line verticalLine = null;
        //if location is on d line or is 4, check the row for different line
        if (column.equals("D") && row >3){
            verticalLine= boardLines.get("D1");
        } else{
            verticalLine= boardLines.get(column);
        }
        if (row == 4 && column.compareTo("C") >0) {
            horizontalLine= boardLines.get("4_1");
        } else {
            horizontalLine= boardLines.get(Integer.toString(row));
        }

        // Check all the locations in the mill to see if they are occupied by the same player
        Location loc = getLocation(location);
        ArrayList<Location> locations = new ArrayList<Location>(2);
        locations.add(horizontalLine.oneMoveFromMill(playerSide));
        locations.add(verticalLine.oneMoveFromMill(playerSide));
        return locations;
    }

    /**
     * Check if a location's line is one move from a mill and if that move is possible (not occupied)
     * @param location
     * @param playerSide
     * @return
     */
    public boolean isOneMoveFromMill(String location, int playerSide, boolean isHorizontal){
        String column = location.substring(0, 1);
        int row =  Integer.parseInt(location.substring(1));
        Line horizontalLine = null;
        if (row == 4 && column.compareTo("C") >0) {
            horizontalLine= boardLines.get("4_1");
        } else {
            horizontalLine= boardLines.get(Integer.toString(row));
        }

        Line verticalLine = null;
        if (column.equals("D") && row >3){
            verticalLine= boardLines.get("D1");
        } else{
            verticalLine= boardLines.get(column);
        }
        if (!isHorizontal) {
            return verticalLine.oneMoveFromMill(playerSide) != null;
        }
        return horizontalLine.oneMoveFromMill(playerSide) != null;
    }



    /***
     * Checks if a location is found inside a line. Used to check if a piece can be
     * moved from the checked location to the line in order to make a mill as we don't
     * want to move pieces from line itself
     * @param checkedLoc
     * @param lineLoc
     * @return
     */
    public boolean isLocationInLine(Location checkedLoc, String lineLoc, boolean isHorizontal) {
        // Retrieve the lines for the given location
        String column = lineLoc.substring(0, 1);
        int row =  Integer.parseInt(lineLoc.substring(1));
        Line horizontalLine = null;

        //if location is on d line or is 4, check the row for different line
        if (row == 4 && column.compareTo("C") >0) {
            horizontalLine = boardLines.get("4_1");
        } else {
            horizontalLine = boardLines.get(Integer.toString(row));
        }

        Line verticalLine = null;

        //if location is on d line or is 4, check the row for different line
        if (column.equals("D") && row >3){
            verticalLine= boardLines.get("D1");
        } else{
            verticalLine= boardLines.get(column);
        }
        if (!isHorizontal) {
            return verticalLine.isLocationInLine(checkedLoc);
        }
        // Check if the location is in the line
        return horizontalLine.isLocationInLine(checkedLoc);
    }

    /**
     * Count the number of pieces on the board for the given player
      * @param player
     * @return
     */
    public int getPieceCount(PlayerPawn player){
        int count = 0;
        for(Location location: boardLocations.values()){
            if(location.getPiece()!=null && location.getPiece().equals(player.getSide())){
                count++;
            }
        }
        return count;
    }

    public HashMap<String,Location> getBoardLocations() {return boardLocations;}

    /**
     * Returns the location object for the given location string
     * @param location
     * @return
     */
    public Location getLocation(String location) {
        return boardLocations.get(location);
    }

    /**
     * Returns true if the two locations are adjacent. Uses two location strings to check.
     * @param start
     * @param end
     * @return
     */
    public boolean locationsAreAdjacentString(String start, String end) throws Exception {
        try {
            // Initialise variables
            Location startLocation = getLocation(start);

            // Loop through start's adjacent locations and check if end is in them
            for (String location : startLocation.getAdjacentLocations()) {
                if (Objects.equals(location, end)) {
                    return true;
                }
            }

            // If end isn't in the adjacent locations
            return false;

        } catch (Exception e) {
            throw new Exception("Location does not exist");
        }
    }

    /**
     * Check if the given player has any possible moves by check for
     * remaining pieces on board and adjacent empty locations
     * @param player
     * @return
     */
    public boolean playerHasPossibleMoves(PlayerPawn player) {
        // Check if the player can fly
        boolean canFly = (getPieceCount(player) <= 3);

        // If the player can fly then we just need to find any empty location
        if (canFly) {
            for (Location location : boardLocations.values()) {
                // Check if the location is empty
                if (location.getPiece() == null) {
                    // Player can fly to this location
                    return true;
                }
            }
        }

        // If the player can't fly we need to loop through all locations and check
        // if any of the player's pieces have an adjacent empty location
        for (Location location : boardLocations.values()) {
            // Check if the location has one of the player's piece
            if (location.getPiece() != null && location.getPiece().equals(player.getSide())) {
                // Loop through all the adjacent locations for an empty space
                for (String adjacentLocation : location.getAdjacentLocations()) {
                    // If the adjacent location is empty
                    if (boardLocations.get(adjacentLocation).getPiece() == null) {
                        // Move is possible
                        return true;
                    }
                }
            }
        }

        // No adjacent locations to the player's pieces are empty
        return false;
    }

    /**
     * Check if the given player has any pieces that are not in a mill
     * @param playerSide
     * @return
     */
    public boolean otherPlayersHaveNonMillPieces(int playerSide) {
        // Loop through all the locations for and check if any of the other player's pieces are not in a mill
        for (Location location : boardLocations.values()) {
            // Check if the location has a piece
            if (location.getPiece() != null) {
                // Check if the piece is not the player's
                if (!location.getPiece().equals(playerSide)) {
                    // Check if the piece is not in a mill
                    if (!millCheck(location.toString())) {
                        // Other players have a non mill piece
                        return true;
                    }
                }
            }
        }

        // No other players have a non mill piece
        return false;
    }

    /**
     * Return a list of all the locations that are empty and adjacent to the given location
     * @param location
     * @return
     */
    public ArrayList<Location> getLocationsEmptyAdjacent(String location) {
        // Initialise variables
        ArrayList<Location> emptyAdjacentLocations = new ArrayList<>();

        // Loop through the adjacent locations and check if they are empty
        for (String adjacentLocation : getLocation(location).getAdjacentLocations()) {
            // Check if the location is empty
            if (getLocation(adjacentLocation).getPiece() == null) {
                // Add the location to the list
                emptyAdjacentLocations.add(getLocation(adjacentLocation));
            }
        }

        // Return the list of empty adjacent locations
        return emptyAdjacentLocations;
    }

    /**
     * Check if the given location is part of a mill
     * @param target
     * @return
     */
    public boolean isLocationPartOfMill(Location target, Location millLocation) {
        Line verticalLine = getLinesForLocation(target, true);
        Line horizontalLine = getLinesForLocation(target, false);
        return verticalLine.isLocationInLine(millLocation) || horizontalLine.isLocationInLine(millLocation);

    }

    /**
     * Returns the line for the given location
     * @param target
     * @param isVertical
     * @return
     */
    private Line getLinesForLocation(Location target, boolean isVertical) {
        String column = target.toString().substring(0, 1);
        int row=  Integer.parseInt(target.toString().substring(1));
        Line horizontalLine = null;
        Line verticalLine = null;
        //if location is on d line or is 4, check the row for different line
        if(column.equals("D") && row >3){

            verticalLine= boardLines.get("D1");

        } else{
            verticalLine= boardLines.get(column);
        }

        if(row == 4 && column.compareTo("C") >0){

            horizontalLine= boardLines.get("4_1");

        }else{
            horizontalLine= boardLines.get(Integer.toString(row));

        }
        if (isVertical) {
            return verticalLine;
        } else {
            return horizontalLine;
        }
    }
}
