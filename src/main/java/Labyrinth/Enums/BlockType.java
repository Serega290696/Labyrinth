package Labyrinth.Enums;

/**
 * Created by Serega on 28.04.2015.
 */
public enum BlockType {


    WHITE, BLACK,
    ARROW, BONUS,
    FRINGE_RED,
    ;

    public boolean isFringe() {
        if (
                this == FRINGE_RED
                )
            return true;

        return false;
    }
}
