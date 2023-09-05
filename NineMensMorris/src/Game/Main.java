package Game;

import Core.Application;

/**
 * This class is the entry point of the application
 */
public class Main {
    /* Runs the application */

    public static void main(String[] args) {
        Application app = Application.getInstance();
        app.run();
    }
}
