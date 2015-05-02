package Labyrinth.GameObjects;

import Labyrinth.Draw;
import Labyrinth.Enums.DrawFigure;
import Labyrinth.GO;
import Labyrinth.Main;

/**
 * Created by Serega on 01.05.2015.
 */
public class Bonus extends GO {

    private float ratio;
    public int type;
    public Bonus(float x, float y, float sx, float sy, int type) {
        this.x = x;
        this.y = y;
        this.sx = sx;
        this.sy = sy;
        this.type = type;
    }
    public Bonus(float x, float y, int type) {
        this.x = x + Draw.xshift;
        this.y = y + Draw.yshift;
        sx = 6;
        sx *= 0.5f + 0.5f * Main.game.map.tunnelWidth / Main.game.map.maxTunnelWidth;
        sy = 9;
        sy *= 0.5f + 0.5f * Main.game.map.tunnelWidth / Main.game.map.maxTunnelWidth;
        ratio = sy / sx;
        sx = sy;
        color = 9;
        figure = DrawFigure.CIRCLE;
        this.type = type;
    }

    @Override
    public void move() {

    }

    @Override
    public void update() {
        rotate+=600f/ Main.fps;
//        rotate+=200f;

    }
    public void render() {
//        if(x -sx/2 <= 100 && x + sx/2 >= 0 && y -sy/2 <= 100*Main.ratio && y + sy/2 >= 0 )
        Draw.draw(figure, x, y, sx, sy*ratio, rotate, color, opacity);
    }

    @Override
    public void collision() {

    }
}
