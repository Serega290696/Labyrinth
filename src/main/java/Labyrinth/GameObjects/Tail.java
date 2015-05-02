package Labyrinth.GameObjects;

import Labyrinth.Draw;
import Labyrinth.Enums.DrawFigure;
import Labyrinth.GO;
import Labyrinth.Main;

/**
 * Created by Serega on 30.04.2015.
 */
public class Tail extends GO {

    public final int direction;
    public boolean toDelete = false;
    public Tail(float x, float y, float sx, float sy, int direction) {
        this.direction = direction;
        this.x = x;
        this.y = y;
        this.sx = sx;
        this.sy = sy;
        color = 1;
        figure = DrawFigure.CIRCLE;
    }

    @Override
    public void move() {

    }

    @Override
    public void update() {
        if(direction == 0 || direction == 2) {
            sx *= 0.95f;
//            sx *= 1.05f;
        }
        if(direction == 1 || direction == 3) {
            sy *= 0.95f;
            sx *= 0.95f;
//            if(sx < 0.01f) sx = 0;
//            sy *= 1.05f;
        }
        if(x - Draw.xshift < 0 || x - Draw.xshift > 100
                || y - Draw.yshift < 0
                || y - Draw.yshift > 100*Main.ratio
                || sx < 0.01f || sy < 0.01f) {
            sx = 0;
            sy = 0;
//            toDelete = true;
            Main.game.someChanged = true;
            Main.game.tail.remove(this);
//            return;
        }

    }

    @Override
    public void collision() {

    }
}
