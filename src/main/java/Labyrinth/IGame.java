package Labyrinth;

import Labyrinth.GameObjects.Player;

/**
 * Created by Serega on 28.04.2015.
 */
public interface IGame {
    public Player player = null;

    public void restartGame();
    public void clear();
    public void getInput();
    public void update();
    public void render();
    public void script();
    public void delObj(GO go);

}
