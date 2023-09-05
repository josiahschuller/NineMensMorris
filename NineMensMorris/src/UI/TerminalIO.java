package UI;

import Core.Board;
import Core.Piece;

import java.lang.StringBuilder;
import java.util.HashMap;
import java.util.Scanner;

public class TerminalIO extends IO {
    /* Constructor */
    public TerminalIO() {}

    /**
     *
     * create a hashmap to store the index of each location in the board
     * Iterate through the board and store the index of each location in the hashmap
     *
     * @param board
     */
    public void display(Board board) {
        /* Displays the game board */

        // Initialise variables
        StringBuilder[] layout = baseLayout();
        HashMap<String, Integer[]> indexDict = boardIndexDict();

        // Loop through the board and change the layout based on whether if Location has a Piece
        board.getBoardLocations().forEach((key, location) -> {
            // Check if location has a piece then modify layout if it does base on boardIndexDict
            Piece piece = location.getPiece();

            if (piece != null) {
                Integer[] index = indexDict.get(key);
                layout[index[0]].deleteCharAt(index[1]);
                layout[index[0]].insert(index[1], piece.toString());
            }
        });

        // Clear the screen (by printing empty lines
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }

        // Display the board
        System.out.println();
        System.out.println(convertStringBuilderArray(layout));
    }

    /**
     * Re-print the board using the display method
     * @param board
     */
    public void update(Board board) {
        // This function might be redundant
        display(board);
    }

    /**
     * Display a message to the console
     * @param message
     */
    public void sendMessage(String message) {
        /* Displays game message */
        System.out.println(message);
    }

    /**
     * Get input from the console
     * @return
     */
    public String getInput() {
        /* Recieves console input for the game*/
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }


    /**
     *  Initialise the base layout of the board
     * @return
     */
    private StringBuilder[] baseLayout() {
        /* The base layout of the Nine Men's Morris Board without tokens as a StringBuilder.
        * A StringBuilder is used so that strings (which are immutable in Java) can be changed at indexes. */
        return new StringBuilder[]{
                new StringBuilder("7  o-----------o-----------o\n"),
                new StringBuilder("   |           |           |\n"),
                new StringBuilder("6  |   o-------o-------o   |\n"),
                new StringBuilder("   |   |       |       |   |\n"),
                new StringBuilder("5  |   |   o---o---o   |   |\n"),
                new StringBuilder("   |   |   |       |   |   |\n"),
                new StringBuilder("4  o---o---o       o---o---o\n"),
                new StringBuilder("   |   |   |       |   |   |\n"),
                new StringBuilder("3  |   |   o---o---o   |   |\n"),
                new StringBuilder("   |   |       |       |   |\n"),
                new StringBuilder("2  |   o-------o-------o   |\n"),
                new StringBuilder("   |           |           |\n"),
                new StringBuilder("1  o-----------o-----------o\n"),
                new StringBuilder("   a   b   c   d   e   f   g")};
    }

    /**
     *  A dictionary that has indexes for a location's index in the StringBuilder Array of Base Layout.
     *         * Used to quickly assess where a Token should appear in the Layout by using the first integer to determine
     *         * the row then the second integer to determine the column.
     * @return
     */
    private HashMap<String, Integer[]> boardIndexDict() {

        HashMap<String, Integer[]> indexDict = new HashMap<String, Integer[]>();
        indexDict.put("A1", new Integer[]{12, 3});
        indexDict.put("D1", new Integer[]{12, 15});
        indexDict.put("G1", new Integer[]{12, 27});
        indexDict.put("B2", new Integer[]{10, 7});
        indexDict.put("D2", new Integer[]{10, 15});
        indexDict.put("F2", new Integer[]{10, 23});
        indexDict.put("C3", new Integer[]{8, 11});
        indexDict.put("D3", new Integer[]{8, 15});
        indexDict.put("E3", new Integer[]{8, 19});
        indexDict.put("A4", new Integer[]{6, 3});
        indexDict.put("B4", new Integer[]{6, 7});
        indexDict.put("C4", new Integer[]{6, 11});
        indexDict.put("E4", new Integer[]{6, 19});
        indexDict.put("F4", new Integer[]{6, 23});
        indexDict.put("G4", new Integer[]{6, 27});
        indexDict.put("C5", new Integer[]{4, 11});
        indexDict.put("D5", new Integer[]{4, 15});
        indexDict.put("E5", new Integer[]{4, 19});
        indexDict.put("B6", new Integer[]{2, 7});
        indexDict.put("D6", new Integer[]{2, 15});
        indexDict.put("F6", new Integer[]{2, 23});
        indexDict.put("A7", new Integer[]{0, 3});
        indexDict.put("D7", new Integer[]{0, 15});
        indexDict.put("G7", new Integer[]{0, 27});

        return indexDict;
    }


    /**
     * Convert the StringBuilder array to a single string
     * @param strings
     * @return
     */
    private String convertStringBuilderArray(StringBuilder[] strings) {
        /* Converts a layout (An array of StringBuilders) into a single String */

        // Initialise variables
        StringBuilder output = new StringBuilder();

        // Loop through array of StringBuilders and concatenate
        for (StringBuilder string : strings) {
            output.append(string);
        }

        return output.toString();
    }
}

