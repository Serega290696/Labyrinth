package Labyrinth.GameObjects;

import Labyrinth.Enums.BlockType;
import Labyrinth.Enums.DrawFigure;
import Labyrinth.GO;
import Labyrinth.Main;
import Labyrinth.Physics;

/**
 * Created by Serega on 28.04.2015.
 */
public class Player extends GO {

    private int direction = 0;  // ^ > v <
    private int oldDirection = -1;  // ^ > v <
                                // 0 1 2 3

    private float maxSpeed = 180;
    private float speed = 80;
    public boolean immortal = false;
    public float score = 0;

    public Player() {
        figure = DrawFigure.PLAYER;
        sx = 1.6f;
        sy = 1.1f;
        x = 50;
        y = 50* Main.ratio;
        direction = GameMap.mainDirection;
        color = 8;
    }
    public Player(float x, float y, float sx, float sy) {
        figure = DrawFigure.PLAYER;
        this.sx = sx;
        this.sy = sy;
        this.x = x;
        this.y = y;
        direction = GameMap.mainDirection;
        color = 8;
    }

    @Override
    public void move() {
        switch(direction) {
            case 0:
                y -= speed / Main.fps;
                break;
            case 1:
                x += speed / Main.fps;
                break;
            case 2:
                y += speed / Main.fps;
                break;
            case 3:
                x -= speed / Main.fps;
                break;
        }
    }

    @Override
    public void update() {
        speed -= (0.03*speed) / Main.fps;
        if(speed < 0) speed = 0;
        if(speed > maxSpeed) speed -= (0.2*speed) / Main.fps;
        score += Math.pow(speed-30, 1.8f)/100/Main.fps;
        for(int i = 0; i < Main.game.map.amountH; i++) {
            for (int j = 0; j < Main.game.map.amountH; j++) {
                if (Physics.checkCollisions(this, Main.game.map.blocks[i][j])) {
                    if (Main.game.map.blocks[i][j].getType() == BlockType.BLACK || Main.game.map.blocks[i][j].isObstacle() ) {
                        System.out.println("BAX!!!");
                        if(!immortal)
                            Main.restartGame();
                    }
                    if (Main.game.map.blocks[i][j].getType() == BlockType.BONUS) {
                        getBonus();
                    }

                    if (Main.game.map.blocks[i][j].getType() == BlockType.ARROW) {
                        oldDirection = direction;
                        direction = Main.game.map.blocks[i][j].arrow;
                    }
                    else {
                        if(oldDirection != -1)
                        oldDirection = -1;
                    }

                }
            }
        }
        for(Bonus curBonus : Main.game.bonuses) {
            if(Physics.checkCollisions(this, curBonus)) {
                this.getBonus();
                Main.game.bonuses.clear();
                break;
            }
        }
        Main.game.tail.add(new Tail(x, y, sx, sy, direction));
        move();
    }

    @Override
    public void collision() {

    }

    public void setDirection(int newDirection) {
        switch(Main.game.gameConfiguration.controlMode) {
            case 1:
                if(newDirection >= 0) {
                    direction++;
                }
                else direction--;
                if(direction < 0)
                    direction = 4-Math.abs(direction)%4;
                direction %= 4;
                break;
            case 2:
                direction= newDirection;
                direction %= 4;
                break;
        }
    }

    public int getDirection() {
        return direction;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
    public float getSpeed() {
        return speed;
    }

    public void getBonus() {
        Main.game.map.tunnelWidth+=10;
        speed += 30;
    }
}
