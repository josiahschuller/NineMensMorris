package Core;

public class Line {
    /***
     * This class is responsible for storing the 3 locations that make up a line
     * It is also responsible for checking if a mile is formed by the 3 locations
     *
     */
    private Location[] locations;

    public Line(Location start, Location middle, Location end){
        //the board will line up the locations in the correct order
        locations = new Location[3];
        locations[0] = start;
        locations[1] = middle;
        locations[2] = end;
    }

    /**
     * This method checks if a mill is formed by the 3 adjacent locations
     * @return
     */
    public boolean isMill(){
        //check 3 locations equal each other
        for(Location l : locations){
            if(l.getPiece() == null){
                return false;
            }
        }
        //check if all 3 locations are the same

        for (Location l: locations){
            if (!l.getPiece().equals(locations[0].getPiece())){
                return false;
            }
        }
        return true;
    }

    /**
     * This method checks if a mill can be formed in one move
     * It does this by checking if there is an empty spot in the line
     * @param playerSide
     * @return
     */
    public Location oneMoveFromMill(int playerSide) {
        // Iterate through the locations and see if there is an available spot
        // that will create a mill if a piece of the playerSide is moved there
        Location emptySpot = null;
        for (Location location : locations) {
            if (location.getPiece() == null) {
                if (emptySpot != null) {
                    // There are two empty spots, so no mill can be formed in one move
                    return null;
                }
                else {
                    emptySpot = location;
                }
            } else {
                // Check if the piece is the same side as the player
                if (!location.getPiece().equals(playerSide)) {
                    // If not same side, then no mill can be formed in one move
                    return null;
                }
            }
        }

        // Passes all checks so mill is possible
        return emptySpot;
    }

    /**
     * The method checks if a location is in the line
     * @param location
     * @return
     */
    public boolean isLocationInLine(Location location){
        for(Location l : locations){
            if(l.equals(location)){
                return true;
            }
        }
        return false;
    }
}
