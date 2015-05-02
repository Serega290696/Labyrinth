package Labyrinth.GameObjects;

import Labyrinth.Draw;
import Labyrinth.Enums.BlockType;
import Labyrinth.Enums.DrawFigure;
import Labyrinth.GO;
import Labyrinth.Main;

/**
 * Created by Serega on 28.04.2015.
 */
public class MapBlock extends GO {

    private BlockType type = BlockType.WHITE;
    public int value = (int) (Math.random()*4);
    public final int i;
    public final int j;
    private boolean isObstacle = false;
    public int number = 1;
    private boolean likeBlack = false;
    public int arrow = -1;

    public MapBlock(float coordX, float coordY, float sizeX, float sizeY, BlockType type, int i, int j) {
        this.i = i;
        this.j = j;
        x = coordX;
        y = coordY;
        sx = sizeX;
        sy = sizeY;
        figure = DrawFigure.RECT;
        color = 0;
//        defaultColor = 6;
        this.type = type;
    }

    @Override
    public void move()  {

    }

    @Override
    public void update() {
        setColorType();
        if(((Main.game.map.mainDirection == 0 &&  Main.game.player.getY()+10 < this.getY()) || (Main.game.map.mainDirection == 2 &&  Main.game.player.getY()-10 > this.getY()))) {
            if(this.type == BlockType.FRINGE_RED && (Math.random() < 0.5f || Math.abs(Main.game.player.getY() - this.getY()) < 5*Main.ratio)) {
                if(j +1 < Main.game.map.amountHAll)
                    if(Main.game.map.blocks[i][j+1].getType() != BlockType.FRINGE_RED) {
                        this.type = BlockType.BLACK;
                    }
                if(j -1 > 0)
                    if(Main.game.map.blocks[i][j-1].getType() != BlockType.FRINGE_RED) {
                        this.type = BlockType.BLACK;
                    }
            }
            else if(this.type == BlockType.WHITE && (i >= 0 && i < Main.game.map.amountVAll) && (j > 0 && j < Main.game.map.amountHAll-1)) {
                if(Main.game.map.blocks[i][j+1].getType() == BlockType.BLACK || Main.game.map.blocks[i][j-1].getType() == BlockType.BLACK)
                    Main.game.map.blocks[i][j].setType(BlockType.FRINGE_RED);
            }
        }
        else if(((Main.game.map.mainDirection == 0 &&  Main.game.player.getY()+10 >= this.getY()) || (Main.game.map.mainDirection == 2 &&  Main.game.player.getY()-10 <= this.getY()))){
            if(i != 0 && i != Main.game.map.amountVAll && j != 0 && j != Main.game.map.amountHAll && getType() == BlockType.WHITE)
                createNewLine();
        }
        if(((Main.game.map.mainDirection == 1 &&  Main.game.player.getX()-10 > this.getX()) || (Main.game.map.mainDirection == 3 &&  Main.game.player.getX()+10 < this.getX()))) {
            if(this.type == BlockType.FRINGE_RED && (Math.random() < 0.5f || Math.abs(Main.game.player.getX() - this.getX()) < 5)) {
                if(i +1 < Main.game.map.amountVAll)
                    if(Main.game.map.blocks[i+1][j].getType() != BlockType.FRINGE_RED) {
                        this.type = BlockType.BLACK;
                    }
                if(i -1 > 0)
                    if(Main.game.map.blocks[i-1][j].getType() != BlockType.FRINGE_RED) {
                        this.type = BlockType.BLACK;
                    }
            }
            else if(this.type == BlockType.WHITE && (i > 0 && i < Main.game.map.amountVAll-1) && (j >= 0 && j < Main.game.map.amountHAll)) {
                if(Main.game.map.blocks[i+1][j].getType() == BlockType.BLACK || Main.game.map.blocks[i-1][j].getType() == BlockType.BLACK)
                    Main.game.map.blocks[i][j].setType(BlockType.FRINGE_RED);
            }
        }
        else if(((Main.game.map.mainDirection == 1 &&  Main.game.player.getX()-10 < this.getX()) || (Main.game.map.mainDirection == 3 &&  Main.game.player.getX()+10 > this.getX()))){
            if(i != 0 && i != Main.game.map.amountVAll && j != 0 && j != Main.game.map.amountHAll && getType() == BlockType.WHITE)
                createNewLine();
        }






    }
    public void createNewLine() {/*
//        System.out.println(x + "   " + (y - Main.game.player.getY()));
        GameMap mapT = Main.game.map;
        number = mapT.maxTunnelWidth - mapT.tunnelWidth + 1;
//        for(int i=0; i < mapT.amountVAll; i++) {
//            for(int j=0; j < number; j++) {
//                if(mapT.blocks[i][j].getType() == BlockType.FRINGE_RED) {
//
//                }
//            }
//        }
        if (number > 1)
            if (mapT.blocks[i][j - 1].getType() == BlockType.FRINGE_RED) {
                this.setIsObstacle(true);
            }
        if (number > 2)
            if (mapT.blocks[i][j - 1].isObstacle()) {
                for (int k = 1; k < number; k++) {
                    if (mapT.blocks[i][j - k].getType() == BlockType.FRINGE_RED) {
//                        this.likeBlack = true;
                        this.setIsObstacle(true);
                        for(int p = k; p > 0; p--)
                            mapT.blocks[i][j - p].likeBlack = true;
                    }
//                    mapT.blocks[i][j - k].likeBlack = true;
                }
                this.likeBlack = false;
            }
        switch (mapT.mainDirection) {
            case 0:
                if(Math.random() < (1f - (float)((float) mapT.timeToChangeWidth / mapT.defTimeToChangeWidth))/(float)mapT.amountV*2) {
                    if (j - number > 0)
                        if (mapT.blocks[i][j - number].getType() == BlockType.FRINGE_RED) {
                            this.setIsObstacle(true);

//                            System.out.println((1f - (float) ((float) mapT.timeToChangeWidth / mapT.defTimeToChangeWidth)) / (float) mapT.amountV);
//                            System.out.println((float) mapT.timeToChangeWidth +"/"+ mapT.defTimeToChangeWidth);
                        }
//                    if (j + 1 < mapT.amountHAll)
//                        if (mapT.blocks[i][j + 1].getType() == BlockType.FRINGE_RED)
//                            this.setIsObstacle(true);
                }
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }*/
    }

    public boolean likeBlack() {
        return likeBlack = true;
    }
    public boolean isObstacle() {
        return isObstacle;
    }
    public void setIsObstacle(boolean isObstacle) {
        this.isObstacle = isObstacle;
    }
    public void setArrow(int arrowSide, int length) {
        if(type == BlockType.BLACK || arrowSide < 0 || length <= 0)
            arrow = -1;
        else {
            arrow = arrowSide;
            type = BlockType.ARROW;
            if(length > 1) {
                for (int p = 1; p < length; p++) {
                    switch (Main.game.map.mainDirection) {
                        case 0:
                            Main.game.map.blocks[i + p][j].setArrow(arrowSide, 1);
                            break;
                        case 1:
                            Main.game.map.blocks[i][j - p].setArrow(arrowSide, 1);
                            break;
                        case 2:
                            Main.game.map.blocks[i - p][j].setArrow(arrowSide, 1);
                            break;
                        case 3:
                            Main.game.map.blocks[i][j + p].setArrow(arrowSide, 1);
                            break;
                    }
                }
            }
        }
    }

    private void setColorType() {
        switch(type) {
            case WHITE: color = 0;
                break;
//            case RED: color = 1;
//                break;
            case BLACK: color = 8;
                break;
            case FRINGE_RED: color = 1;
                break;
//            case FRINGE_BLACK: color = 5;
//                break;
            case ARROW: color = 10;
                break;
            default:
        }
        if(isObstacle()) {
            color = 5;
        }
        if(likeBlack) {
            color = 8;
        }
    }

    public void render() {
        Draw.draw(figure, x, y, sx, sy, rotate, color, opacity);
    }

    @Override
    public void collision() {

    }

    public void setType(BlockType type) {
        this.type = type;
    }

    public BlockType getType() {
        return type;
    }
}
