package Core;

public class Move {

    private final String startPosition;
    private final String finalPosition;
    private final String removePosition;

    // Constructor
    public Move(String startPosition, String finalPosition, String removePosition) {
        this.startPosition = startPosition;
        this.finalPosition = finalPosition;
        this.removePosition = removePosition;
    }

    public String getStartPosition() {
        return startPosition;
    }

    public String getFinalPosition() {
        return finalPosition;
    }

    public String getRemovePosition() {
        return removePosition;
    }

    /***
     * This method determines the type of move based on the start, final and remove positions given
     *
     * @return
     */
    public MoveType typeOfMove() {
        // Determine the type of move based on the move's positions
        if (this.startPosition == null && this.finalPosition == null && this.removePosition != null) {
            return MoveType.REMOVE;
        } else if (this.startPosition == null && this.finalPosition != null && this.removePosition == null) {
            return MoveType.DEPLOY;
        } else if (this.startPosition != null && this.finalPosition != null && this.removePosition == null) {
            return MoveType.STEPFLY;
        } else {
            return null;
        }
    }
}
