package PlayerPawn;

import UI.IO;

public class PlayerPawnFactory {

    /**
     * Creates a human player pawn
     * @param side
     * @param nonDeployedPieces
     * @param display
     * @return PlayerPawn
     */
    public static PlayerPawn createHumanPlayerPawn(int side, int nonDeployedPieces, IO display) {
        return new HumanPlayer(side, nonDeployedPieces, display);
    }

    /**
     * Creates an AI player pawn based on the level
     * Level 1: Easy AI
     * Level 2: Medium AI
     * Level 3 or higher: Hard AI
     * @param level
     * @param side
     * @param nonDeployedPieces
     * @param display
     * @return
     */
    public static PlayerPawn createAIPlayerPawn(int level, int side, int nonDeployedPieces, IO display) {
        //check level
        if (level == 1) {
            return new EasyAIPlayer(side,nonDeployedPieces, display);
        } else if (level == 2) {
            return new MediumAIPlayer(side,nonDeployedPieces, display);
        }
        return new HardAIPlayer(side,nonDeployedPieces, display);

    }

    /**
     * Randomly generates a level and creates an AI player pawn based on the level
     * @param side
     * @param nonDeployedPieces
     * @param display
     * @return
     */
    public static PlayerPawn createRandomAIPlayerPawn(int side, int nonDeployedPieces, IO display) {
        int level = (int) (Math.random() * 3) + 1;
        return createAIPlayerPawn(level, side, nonDeployedPieces, display);
    }
}
