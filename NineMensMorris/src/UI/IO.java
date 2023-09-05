package UI;

import Core.Board;

/**
 * Abstract class that is used to display the game board and messages to the user
 */
public abstract class IO {
    /* Constructor */
    IO() {}

    /* Methods */
    public abstract void display(Board board);

    public abstract void update(Board board);

    public abstract void sendMessage(String message);

    public abstract String getInput();

}
