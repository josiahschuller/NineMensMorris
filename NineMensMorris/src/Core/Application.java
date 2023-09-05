package Core;

import PlayerPawn.PlayerPawn;
import PlayerPawn.PlayerPawnFactory;
import UI.IO;
import UI.TerminalIO;

/***
 *  This class is responsible for start,load and save game
 *  it is also responsible for initialise the game and game loop
 *  Uses singleton pattern as only one instance of the application should be running
 */
public class Application {

    /* Attributes */
    private static Application instance = null;
    private Game game;
    private IO io;


    /* Constructor */
    private Application() {

    }

    /* Methods */
    public static synchronized Application getInstance()
    {
        if (instance == null) {
            instance = new Application();
        }

        return instance;
    }

    public void run() {
        // Runs the game. Only new game is implemented currently.
        newGame();

    }

    public void newGame() {
        io = new TerminalIO();
        boolean isRunning = true;
        while (isRunning){
            menu();
            String option = io.getInput();
            int numberOfPieces = 9, delay = 0;
            PlayerPawn player1 = null, player2= null;
            switch(option){
                case "1":
                    player1= PlayerPawnFactory.createHumanPlayerPawn(1, numberOfPieces, io);
                    player2 = PlayerPawnFactory.createHumanPlayerPawn(2, numberOfPieces, io);

                    break;
                case "2":
                    player1 = PlayerPawnFactory.createHumanPlayerPawn(1, numberOfPieces, io);
                    player2 = PlayerPawnFactory.createAIPlayerPawn(1, 2, numberOfPieces, io);
                    break;
                case "3":
                    player1 = PlayerPawnFactory.createHumanPlayerPawn(1, numberOfPieces, io);
                    player2 = PlayerPawnFactory.createAIPlayerPawn(2, 2, numberOfPieces, io);
                    break;
                case "4":
                    player1 = PlayerPawnFactory.createHumanPlayerPawn(1, numberOfPieces, io);
                    player2 = PlayerPawnFactory.createAIPlayerPawn(3, 2, numberOfPieces, io);
                    break;
                case "5":
                    player1 = PlayerPawnFactory.createHumanPlayerPawn(1, numberOfPieces, io);
                    player2 = PlayerPawnFactory.createRandomAIPlayerPawn(2, numberOfPieces, io);
                    break;
                case "6":
                    player1 = PlayerPawnFactory.createRandomAIPlayerPawn(1, numberOfPieces, io);
                    player2 = PlayerPawnFactory.createRandomAIPlayerPawn(2, numberOfPieces, io);
                    delay=1;
                    break;
                case "7":
                    gameRulesScreen();
                    break;
                case "8":
                    return;
            }
            if (player1 == null || player2 == null){
                io.sendMessage("Invalid option! Please try again.");
            }else{
                game = new Game(player1,player2, numberOfPieces, io);
                game.playGame(delay);
            }
        }


    }

    private void menu(){
        String menuPrompt = "   ___    __  __            _                                 _     \n" +
                "  / _ \\  |  \\/  |          ( )                               (_)    \n" +
                " | (_) | | \\  / | ___ _ __ |/ ___   _ __ ___   ___  _ __ _ __ _ ___ \n" +
                "  \\__, | | |\\/| |/ _ \\ '_ \\  / __| | '_ ` _ \\ / _ \\| '__| '__| / __|\n" +
                "    / /  | |  | |  __/ | | | \\__ \\ | | | | | | (_) | |  | |  | \\__ \\\n" +
                "   /_/   |_|  |_|\\___|_| |_| |___/ |_| |_| |_|\\___/|_|  |_|  |_|___/\n" +
                "                                                                    \n" +
                "                                                                    " + "\n" +
                "Please select a game mode:\n"+
                "1. PVP\n" +
                "2. Steven AI (Easy)\n" +
                "3. Josiah AI (Medium)\n" +
                "4. Caleb AI (Hard)\n"  +
                "5. Random AI\n" +
                "6. AI vs AI\n" +
                "7. View Game Rules\n" +
                "8. Exit\n" ;
        io.sendMessage(menuPrompt);

    }

    private void gameRulesScreen(){
        //clear the screen
        for (int i =0;i<10;i++){
            io.sendMessage("");
        }
        String rules="Game rules\n"+
                "1. The game is played with 2 players, white(x) and black($).\n" +
                "2. Each player has 9 pieces\n" +
                "3. There are 3 phase of the game\n"+
                "   3.1. Phase 1: Placing pieces\n" +
                "       3.1.1. Each player takes turn to place a piece on the board\n" +
                "       3.1.2. The player can place the piece on any empty space\n" +
                "   3.2. Phase 2: Moving pieces (this happens when theres no more deployable pieces)\n" +
                "       3.2.1. Each player takes turn to move a piece on the board\n" +
                "       3.2.2. The player can move their piece to any adjacent empty space\n" +
                "   3.3. Phase 3: Flying pieces (this happens when a player has only 3 pieces left)\n" +
                "       3.3.1. Each player takes turn to move their piece on the board\n" +
                "       3.3.2. The player can move the piece to any empty space\n" +
                "4. Mill\n"+
                "   4.1. A mill is formed when 3 pieces of the same player are in a row\n" +
                "   4.2. When a player forms a mill, the player can remove one of the opponent's piece\n" +
                "   4.3. A piece in a mill cannot be removed\n" +
                "   4.4. A player can only remove 1 piece per turn even if the player forms multiple mills\n" +
                "   4.5. A player cannot remove any piece in the turn if the opponent pieces are all in mills\n"+
                "5. Winning condition\n" +
                "   5.1. A player wins when the opponent has only 2 pieces left\n" +
                "   5.2. A player wins when the opponent player cannot move any piece\n";
        io.sendMessage(rules);
        io.sendMessage("Press any key to return to menu");
        while (true){
            io.getInput();
            return;
        }
    }

}
