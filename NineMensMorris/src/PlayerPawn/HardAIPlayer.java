package PlayerPawn;

import Core.Board;
import Core.Location;
import Core.Move;
import UI.IO;

import java.util.ArrayList;
import java.util.HashMap;

public class HardAIPlayer extends MediumAIPlayer{
    public HardAIPlayer(int side, int nonDeployedPieces, IO display) {
        super(side, nonDeployedPieces, display, "                                                                           ___                                                                                         \n" +
                "                                                                        .'/   \\                                                                                        \n" +
                "   .                   .                   .                   .       / /     \\                                                                                       \n" +
                " .'|                 .'|                 .'|                 .'|       | |     |                                                                                       \n" +
                "<  |                <  |                <  |                <  |       | |     |                                                                                       \n" +
                " | |            __   | |            __   | |            __   | |       |/`.   .'                                                                                       \n" +
                " | | .'''-.  .:--.'. | | .'''-.  .:--.'. | | .'''-.  .:--.'. | | .'''-. `.|   |                                                                                        \n" +
                " | |/.'''. \\/ |   \\ || |/.'''. \\/ |   \\ || |/.'''. \\/ |   \\ || |/.'''. \\ ||___|                                                                                        \n" +
                " |  /    | |`\" __ | ||  /    | |`\" __ | ||  /    | |`\" __ | ||  /    | | |/___/                                                                                        \n" +
                " | |     | | .'.''| || |     | | .'.''| || |     | | .'.''| || |     | | .'.--.                                                                                        \n" +
                " | |     | |/ /   | || |     | |/ /   | || |     | |/ /   | || |     | || |    |                                                                                       \n" +
                " | '.    | '\\ \\._,\\ '| '.    | '\\ \\._,\\ '| '.    | '\\ \\._,\\ '| '.    | '\\_\\    /                                                                                       \n" +
                " '---'   '---`--'  `\"'---'   '---`--'  `\"'---'   '---`--'  `\"'---'   '---`''--'                                                                                        \n" +
                "                   .-'''-.                          _..._                                                                                                              \n" +
                "                  '   _    \\                     .-'_..._''.                                                                                                           \n" +
                "                /   /` '.   \\                  .' .'      '.\\           _..._                _..._        __.....__  .----.     .----.  __.....__                      \n" +
                " .-.          ..   |     \\  '                 / .'                    .'     '.            .'     '.  .-''         '. \\    \\   /    .-''         '.                    \n" +
                "  \\ \\        / |   '      |  '               . '                     .   .-.   .          .   .-.   ./     .-''\"'-.  `.'   '. /'   /     .-''\"'-.  `..-,.--.           \n" +
                "   \\ \\      / /\\    \\     / /                | |                __   |  '   '  |          |  '   '  /     /________\\   |    |'    /     /________\\   |  .-. |          \n" +
                "    \\ \\    / /  `.   ` ..' _    _            | |             .:--.'. |  |   |  |          |  |   |  |                  |    ||    |                  | |  | |          \n" +
                "     \\ \\  / /      '-...-'| '  / |           . '            / |   \\ ||  |   |  |          |  |   |  \\    .-------------'.   `'   .\\    .-------------| |  | |          \n" +
                "      \\ `  /             .' | .' |            \\ '.          `\" __ | ||  |   |  |          |  |   |  |\\    '-.____...---.\\        / \\    '-.____...---| |  '-           \n" +
                "       \\  /              /  | /  |             '. `._____.-'/.'.''| ||  |   |  |          |  |   |  | `.             .'  \\      /   `.             .'| |               \n" +
                "       / /              |   `'.  |               `-.______ // /   | ||  |   |  |          |  |   |  |   `''-...... -'     '----'      `''-...... -'  | |               \n" +
                "   |`-' /               '   .'|  '/                       ` \\ \\._,\\ '|  |   |  |          |  |   |  |                                                |_|               \n" +
                "    '..'                 `-'  `--'                           `--'  `\"'--'   '--'          '--'   '--'                                                                  \n" +
                "                                                                                             ___                                                                       \n" +
                "                                                                                          .'/   \\                                                                      \n" +
                "/|             __.....__                                __  __   ___        __.....__    / /     \\                                                                     \n" +
                "||         .-''         '.                             |  |/  `.'   `.  .-''         '.  | |     |                                                                     \n" +
                "||        /     .-''\"'-.  `.             .|            |   .-.  .-.   '/     .-''\"'-.  `.| |     |                                                                     \n" +
                "||  __   /     /________\\   \\   __     .' |_           |  |  |  |  |  /     /________\\   |/`.   .'                                                                     \n" +
                "||/'__ '.|                  |.:--.'. .'     |          |  |  |  |  |  |                  |`.|   |                                                                      \n" +
                "|:/`  '. \\    .-------------/ |   \\ '--.  .-'          |  |  |  |  |  \\    .-------------' ||___|                                                                      \n" +
                "||     | |\\    '-.____...---`\" __ | |  |  |            |  |  |  |  |  |\\    '-.____...---. |/___/                                                                      \n" +
                "||\\    / ' `.             .' .'.''| |  |  |            |__|  |__|  |__| `.             .'  .'.--.                                                                      \n" +
                "|/\\'..' /    `''-...... -'  / /   | |_ |  '.'                             `''-...... -'   | |    |                                                                     \n" +
                "'  `'-'`                    \\ \\._,\\ '/ |   /                                              \\_\\    /                                                                     \n" +
                "                             `--'  `\"  `'-'                                                `''--'                                                                      ");
    }

    /**
     * Method to deploy a piece
     * 1. try to form mill
     * 2. try to block opponent's mill
     * 3. deploy in location with the most adjacent pieces
     * 4. deploy in location with the most empty adjacent locations
     * 5. deploy in random location
     * @param board
     * @return
     */
    @Override
    protected Move deploy(Board board) {
        //find all empty locations
        HashMap<String, Location> locations = board.getBoardLocations();
        ArrayList<Location> emptyLocations = getEmptyLocations(board);
        ArrayList<Location> existingLocations = getPlayerPieceLocations(board);
        ArrayList<Location> opponentLocations = getOpponentPieceLocations(board);

        //try to form mill
        Location millLocation = millForm(existingLocations, board);
        if (millLocation != null) {
            return new Move(null, millLocation.toString(), null);
        }
        //try to block opponent's mill
        Location deployLocation = blockMillForm(opponentLocations, board);
        if (deployLocation != null) {
            return new Move(null, deployLocation.toString(), null);
        }

        //deploy in adjacent location
        for (Location location: existingLocations){
            ArrayList<Location> possibleLocation = board.getLocationsEmptyAdjacent(location.toString());
            for (Location possible: possibleLocation){
                if (possible.getPiece()==null){
                    return new Move(null, possible.toString(), null);
                }
            }
        }
        //deploy in location that has the most existing adjacent locations
        int max = 0;
        Location maxLocation = null;
        for (Location location:emptyLocations){
            int count =0;
            for (String adjLoc : location.getAdjacentLocations()){
                if (locations.get(adjLoc).equals(this.side)){
                   count++;
                }
            }
            if (count >max){
                max= count;
                maxLocation = location;
            }
        }
        if (maxLocation != null){
            return new Move(null, maxLocation.toString(), null);
        }

        //deploy in location that has the most adjacent locations
        int emptyMax = 0;
        ArrayList<Location> emptyMaxLocations = new ArrayList<>();
        for (Location location:emptyLocations){
            int count =0;
            for (String adjLoc : location.getAdjacentLocations()){
                if (locations.get(adjLoc).getPiece()==null){
                    count++;
                }
            }
            if (count >=emptyMax){
                emptyMax= count;
            }
        }

        for (Location location:emptyLocations){
            int count =0;
            for (String adjLoc : location.getAdjacentLocations()){
                if (locations.get(adjLoc).getPiece()==null){
                    count++;
                }
            }
            if (count ==emptyMax){
                emptyMaxLocations.add(location);
            }
        }

        //deploy in random location
        return deployAux(emptyLocations);
    }

    /**
     * Method to move a piece
     * Uses medium strategy
     * @param board
     * @return
     */
    @Override
    protected Move stepFly(Board board){

        return super.stepFly(board);
    }

    /**
     * Method to remove a piece
     * 1. try to remove opponent's piece that can form multiple mills in the next move
     * 2. try to remove opponent's piece that can form mill in the next move
     * 3. randomly remove opponent's piece
     * @param board
     * @return
     */
    @Override
    protected Move remove(Board board) {
        HashMap<String, Location> locations = board.getBoardLocations();
        ArrayList<Location> opponentLocations = getOpponentPieceLocations(board);
        Location removeLocation = null;
        //check if opponent pieces can form mill
        for (Location location: opponentLocations){
            if (board.isOneMoveFromMill(location.toString(), this.side,true) && board.isOneMoveFromMill(location.toString(), this.side, false)){
                return new Move(null, null, location.toString());
            }else if (board.isOneMoveFromMill(location.toString(), this.side,true) || board.isOneMoveFromMill(location.toString(), this.side,false)){
                removeLocation = location;
            }
        }
        if (removeLocation != null){
            return new Move(null, null, removeLocation.toString());
        }

        return super.remove(board);
    }



}
