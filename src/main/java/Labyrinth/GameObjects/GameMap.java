package Labyrinth.GameObjects;

import Labyrinth.Draw;
import Labyrinth.Enums.BlockType;
import Labyrinth.GameConfiguration;
import Labyrinth.Main;

/**
 * Created by Serega on 28.04.2015.
 */
public class GameMap {

    public float x = 0;
    public float y = 0;
    public int amountH = 50;
    public int amountV = 50;
    public int tunnelWidth = (int) (amountH / 2.5f);
    public int maxTunnelWidth = (int) (amountH / 2.5f);
    public int delta = maxTunnelWidth;
    public int amountHAll = amountH + delta *2;
    public int amountVAll = amountV + delta *2;

    public long defTimeToChangeWidth = 500;
    public long minDefTimeToChangeWidth = 500;
    public long timeToChangeWidth = defTimeToChangeWidth;
    public static int mainDirection;
    public int buildTurnStatus = 0;
    public float blockSizeX = 100/ amountV;
    public float blockSizeY = 100/ amountV * Main.ratio;
    public MapBlock[][] blocks = new MapBlock[amountVAll][amountHAll];
    private int turn = 0;


    public GameMap() {
        x = 0;
        y = 0;
        buildTurnStatus = 0;
        mainDirection = GameConfiguration.startMainDirection;
        for(int i = 0; i < amountHAll; i++) {
            for (int j = 0; j < amountVAll; j++) {
                float coordX = x+blockSizeX/2 + (i-delta) * 100/ amountH;
                float coordY = y+blockSizeY/2  + (j-delta) * 100/ amountV * Main.ratio;
                    blocks[i][j] = createBlock(coordX+blockSizeX/2, coordY+blockSizeY/2, blockSizeX, blockSizeY, BlockType.BLACK, i, j);
            }

        }
        int j;
        if(mainDirection == 0 || mainDirection == 2) {
            j = (int) (delta + amountH *0.3);
            for(int i = 0; i < amountHAll; i++) {
                blocks[i][j].setType(BlockType.FRINGE_RED);
            }
            j = (int) (delta + amountH *0.3 + tunnelWidth);
            for(int i = 0; i < amountHAll; i++) {
                blocks[i][j].setType(BlockType.FRINGE_RED);
            }
        }
        if(mainDirection == 1 || mainDirection == 3) {
            j = (int) (delta + amountH *0.3);
            for(int i = 0; i < amountHAll; i++) {
                blocks[j][i].setType(BlockType.FRINGE_RED);
            }
            j = (int) (delta + amountH *0.3 + tunnelWidth);
            for(int i = 0; i < amountHAll; i++) {
                blocks[j][i].setType(BlockType.FRINGE_RED);
            }
        }
        for(int i = 0; i < amountVAll; i++) {// ^  V
            for(j = 0; j < amountHAll; j++) {
                if(blocks[i][j].getType() == BlockType.FRINGE_RED) {
                    while(j < amountHAll-1 && blocks[i][j+1].getType() != BlockType.FRINGE_RED) {
                        j++;
                        blocks[i][j].setType(BlockType.WHITE);
                    }
                    if(j < amountHAll-1)
                        j++;
                    while(j < amountHAll-1 && blocks[i][j+1].getType() != BlockType.FRINGE_RED) {
                        j++;
                        blocks[i][j].setType(BlockType.BLACK);
                    }
                }
            }
        }
        for(j = 0; j < amountHAll; j++) {// ^  V
            for(int i = 0; i < amountVAll; i++) {
                if(blocks[i][j].getType() == BlockType.FRINGE_RED) {
                    while(i < amountVAll-1 && blocks[i+1][j].getType() != BlockType.FRINGE_RED) {
                        i++;
                        blocks[i][j].setType(BlockType.WHITE);
                    }
                    if(i < amountVAll-1)
                        i++;
                    while(i < amountVAll-1 && blocks[i+1][j].getType() != BlockType.FRINGE_RED) {
                        i++;
                        blocks[i][j].setType(BlockType.BLACK);
                    }
                }
            }
        }
    }
    public void update() {
        if(Main.game.integerTime <= 20)
            defTimeToChangeWidth = (6 + (40 - Main.game.integerTime)/2) * 100;
        if(maxTunnelWidth < tunnelWidth)
            tunnelWidth = maxTunnelWidth;
        move();
        if(timeToChangeWidth <= 0) {
            timeToChangeWidth = defTimeToChangeWidth;
            tunnelWidth--;

        }
        else {
            timeToChangeWidth -= Main.delay;
        }
        for(int i = 0; i < amountHAll; i++) {
            for(int j = 0; j < amountVAll; j++) {
                if((i == delta-1) && blocks[i][j].getType() == BlockType.WHITE && j >= 4 && Main.game.arrowOn) {
                    if((blocks[i][j-2].getType() == BlockType.FRINGE_RED && Math.random() < 0.02f)
                            || (blocks[i][j-3].getType() == BlockType.FRINGE_RED && Math.random() < 0.01f)
                            || (blocks[i][j-4].getType() == BlockType.FRINGE_RED && Math.random() < 0.01f))
                        if(blocks[i+1][j].arrow < 0 && blocks[i+2][j].arrow < 0 && blocks[i+3][j].arrow < 0 && blocks[i+4][j].arrow < 0){
                            blocks[i][j].setArrow((int) (Math.random()*4), 3);
                            System.out.print("ARROW [" + i + "] [" + j + "] ");
                        }
                }
                blocks[i][j].update();
            }
        }
    }

    public void move() {
        shift();
    }
    public void shift() {
        boolean isShiftN = true;
        while(isShiftN) {
            boolean isShift = false;
            isShiftN = false;
            if (x - Draw.xshift >= blockSizeX) {
                isShift = true;
                isShiftN = true;
                    for (int i = 0; i < amountHAll; i++) {
                        for (int j = amountVAll - 1; j > 0; j--) {
                            blocks[i][j].setType(blocks[i][j - 1].getType());
                            blocks[i][j].setArrow(blocks[i][j - 1].arrow, 1);
                            blocks[i][j].setIsObstacle(blocks[i][j - 1].isObstacle());
                        }
                    }
//                }
                x -= blockSizeX;
            }
            if (x - Draw.xshift <= -blockSizeX) {
                isShift = true;
                isShiftN = true;
//                for (int n = 0; n < Math.abs((int) (x - Draw.xshift) / blockSizeX); n++) {
                    for (int i = 0; i < amountHAll; i++) {
                        for (int j = 0; j < amountVAll - 1; j++) {
                            blocks[i][j].setType(blocks[i][j + 1].getType());
                            blocks[i][j].setArrow(blocks[i][j + 1].arrow, 1);
                            blocks[i][j].setIsObstacle(blocks[i][j + 1].isObstacle());
                        }
                    }
//                }
                x += blockSizeX;
            }
            if (y - Draw.yshift >= blockSizeY) {
                isShift = true;
                isShiftN = true;
                    for (int i = amountHAll - 1; i > 0; i--) {
                        for (int j = 0; j < amountVAll; j++) {
                            blocks[i][j].setType(blocks[i - 1][j].getType());
                            blocks[i][j].setArrow(blocks[i - 1][j].arrow, 1);
                            blocks[i][j].setIsObstacle(blocks[i - 1][j].isObstacle());
                        }
                    }
//                }
                y -= blockSizeY;
            }
            if (y - Draw.yshift <= -blockSizeY) {
                isShift = true;
                isShiftN = true;
//                for (int n = 0; n < Math.abs((int) (y - Draw.yshift) / blockSizeY); n++) {
                    for (int i = 0; i < amountHAll - 1; i++) {
                        for (int j = 0; j < amountVAll; j++) {
                            blocks[i][j].setType(blocks[i + 1][j].getType());
                            blocks[i][j].setArrow(blocks[i + 1][j].arrow, 1);
                            blocks[i][j].setIsObstacle(blocks[i+1][j].isObstacle());
                        }
                    }
//                }
                y += blockSizeY;
            }
            if(turn != 0 && isShift) {
                System.out.print("  Turn! mainDir: " + mainDirection + ". Turn: " + turn + "\n");
                generate(mainDirection, turn);
            }
            if(turn == 0) {
                buildTurnStatus = 0;
            }
        }

        for(int i = 0; i < amountHAll; i++) {
            for (int j = amountVAll-1; j >= 0; j--) {
                float coordX = x+blockSizeX/2 + (j-delta) * 100/ amountH;
                float coordY = y+blockSizeY/2 + (i-delta) * 100/ amountV * Main.ratio;
                blocks[i][j].x = coordX;
                blocks[i][j].y = coordY;
            }
        }
    }
    private void generate(int curDirection, int turn) {
        while(buildTurnStatus <= tunnelWidth + 2) {
            int im = 0;
            int jm = 0;
            switch (curDirection) {
                case 0: {
                    switch (turn) {
                        case 1: {
                            im = tunnelWidth + 2 - buildTurnStatus;
                            if (buildTurnStatus == 0) {
                                for (int j = 0; j < amountHAll; j++) {
                                    boolean done = false;
                                    if (blocks[im][j].getType() == BlockType.FRINGE_RED) {
                                        while (j < amountHAll - 1 && !done) {
                                            j++;
                                            if (blocks[im][j].getType() == BlockType.FRINGE_RED) {
                                                done = true;
                                                while (j < amountHAll - 1) {
                                                    j++;
                                                    blocks[im][j].setType(BlockType.FRINGE_RED);
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (buildTurnStatus > 0 && buildTurnStatus < tunnelWidth+1) {
                                for (int j = 0; j < amountHAll; j++) {
                                    if (blocks[im][j].getType() == BlockType.FRINGE_RED) {
                                        while (j < amountHAll - 1) {
                                            j++;
                                            blocks[im][j].setType(BlockType.WHITE);
                                        }
                                    }
                                }
                            } else if (buildTurnStatus == tunnelWidth + 1) {
                                for (int j = 0; j < amountHAll; j++) {
                                    if (blocks[im][j].getType() == BlockType.FRINGE_RED) {
                                        while (j < amountHAll - 1) {
                                            j++;
                                            blocks[im][j].setType(BlockType.FRINGE_RED);
                                        }
                                    }
                                }
                            } else if (buildTurnStatus > tunnelWidth) {
                                for (int j = 0; j < amountHAll; j++) {
                                    blocks[im][j].setType(BlockType.BLACK);

                                }
                                mainDirection = 1;
                                this.turn = 0;
                            }
                            buildTurnStatus++;
//                        printLabyrinth(buildTurnStatus);
                            break;
                        }
                        case -1: {
                            //      ^  <
                            int i = tunnelWidth + 2 - buildTurnStatus;
                            //                if(random() < 0.2f) {
                            if (buildTurnStatus == 0 && mainDirection == 3) {
                                break;
                            }
                            if (buildTurnStatus == 0 && mainDirection != 3) {
                                for (int j = amountHAll - 1; j > 0; j--) {
                                    boolean done = false;
                                    if (blocks[i][j].getType() == BlockType.FRINGE_RED) {
                                        while (j > 0 && !done) {
                                            j--;
                                            if (blocks[i][j].getType() == BlockType.FRINGE_RED) {
                                                done = true;
                                                while (j > 0) {
                                                    j--;
                                                    blocks[i][j].setType(BlockType.FRINGE_RED);
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (buildTurnStatus > 0 && buildTurnStatus < tunnelWidth+1) {
                                for (int j = amountHAll - 1; j >= 0; j--) {
                                    if (blocks[i][j].getType() == BlockType.FRINGE_RED) {
                                        while (j > 0) {
                                            j--;
                                            blocks[i][j].setType(BlockType.WHITE);
                                        }
                                    }
                                }
                            }

                            if (buildTurnStatus == tunnelWidth + 1) {
                                for (int j = amountHAll - 1; j >= 0; j--) {
                                    if (blocks[i][j].getType() == BlockType.FRINGE_RED) {
                                        while (j > 0) {
                                            j--;
                                            blocks[i][j].setType(BlockType.FRINGE_RED);
                                        }
                                    }
                                }
//                            buildTurnStatus++;
                            } else if (buildTurnStatus > tunnelWidth) {
                                for (int j = amountHAll - 1; j >= 0; j--) {
                                    blocks[i][j].setType(BlockType.BLACK);

                                }
                                mainDirection = 3;
                                this.turn = 0;
                            }
                            buildTurnStatus++;
                            break;
                        }
                        case 2: {
                            im = tunnelWidth + 2 - buildTurnStatus;
                            if (buildTurnStatus == 0) {
                                boolean tmp = false;
                                for (int j = 0; j < amountHAll; j++) {
                                    if (blocks[im][j].getType() == BlockType.FRINGE_RED && !tmp)
                                        tmp = true;
                                    else if (blocks[im][j].getType() == BlockType.FRINGE_RED && tmp)
                                        tmp = false;
                                    if (!tmp)
                                        blocks[im][j].setType(BlockType.FRINGE_RED);
                                }
                            } else if (buildTurnStatus > 0 && buildTurnStatus < tunnelWidth+1) {
                                for (int j = 0; j < amountHAll; j++) {
                                    blocks[im][j].setType(BlockType.WHITE);
                                }
                            } else if (buildTurnStatus == tunnelWidth + 1) {
                                for (int j = 0; j < amountHAll; j++) {
                                    blocks[im][j].setType(BlockType.FRINGE_RED);
                                }
                            } else if (buildTurnStatus > tunnelWidth +1) {
                                for (int j = 0; j < amountHAll; j++) {
                                    blocks[im][j].setType(BlockType.BLACK);
                                }
                                boolean isTopFringeExist = false;
                                boolean isBottomFringeExist = false;
                                Player playerT = Main.game.player;
                                for (int i = 0; i < amountVAll; i++) {
                                    for (int j = 0; j < amountVAll; j++) {
                                        MapBlock curBlock = blocks[i][j];
                                        if (curBlock.getType() == BlockType.FRINGE_RED && playerT.getDirection() % 2 == 1) {
                                            if (curBlock.getX() - curBlock.getSx() <= playerT.getX() &&
                                                    curBlock.getX() + curBlock.getSx() >= playerT.getX()) {
                                                if (curBlock.getY() < playerT.getY()) {
                                                    isTopFringeExist = true;
                                                } else if (curBlock.getY() > playerT.getY()) {
                                                    isBottomFringeExist = true;
                                                }
                                                System.out.println(isTopFringeExist + " <> " + isTopFringeExist + " <> ");
                                            }
                                        }
                                    }
                                }
                                if (isTopFringeExist && isBottomFringeExist) {
                                    mainDirection = Main.game.player.getDirection();
                                    this.turn = 0;
                                }
                            }
                            buildTurnStatus++;
                            break;
                        }
                    }
                }
                break;
                case 1:
                    switch (turn) {
                        case 1: {
                            jm = amountHAll - 0 - 1 - (tunnelWidth + 2 - buildTurnStatus);
                            if (buildTurnStatus == 0) {
                                for (int i = 0; i < amountHAll; i++) {
                                    System.out.println("1" + blocks[i][jm].getType());
                                    boolean done = false;
                                    if (blocks[i][jm].getType() == BlockType.FRINGE_RED) {
                                        while (i < amountHAll - 1 && !done) {
                                            System.out.println("3");
                                            i++;
                                            if (blocks[i][jm].getType() == BlockType.FRINGE_RED) {
                                                done = true;
                                                while (i < amountHAll - 1) {
                                                    System.out.println("5");
                                                    i++;
                                                    blocks[i][jm].setType(BlockType.FRINGE_RED);
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (buildTurnStatus > 0 && buildTurnStatus < tunnelWidth+1) {
                                for (int i = 0; i < amountHAll; i++) {
                                    if (blocks[i][jm].getType() == BlockType.FRINGE_RED) {
                                        while (i < amountVAll - 1) {
                                            i++;
                                            blocks[i][jm].setType(BlockType.WHITE);
                                        }
                                    }
                                }
                            }

                            if (buildTurnStatus == tunnelWidth + 1) {
                                for (int i = 0; i < amountHAll; i++) {
                                    if (blocks[i][jm].getType() == BlockType.FRINGE_RED) {
                                        while (i < amountHAll - 1) {
                                            i++;
                                            blocks[i][jm].setType(BlockType.FRINGE_RED);
                                        }
                                    }
                                }
                            } else if (buildTurnStatus > tunnelWidth) {
                                for (int i = 0; i < amountHAll; i++) {
                                    blocks[i][jm].setType(BlockType.BLACK);
                                }
                                mainDirection = 2;
                                this.turn = 0;
                            }
                            buildTurnStatus++;
                        }
                    break;
                        case -1: {
                            //      >  ^
                            jm = amountHAll - 1 - (tunnelWidth + 2 - buildTurnStatus);
                            if (buildTurnStatus == 0) {
                                for (int i = amountHAll - 1; i >= 0; i--) {
                                    System.out.println("1" + blocks[i][jm].getType());
                                    boolean done = false;
                                    if (blocks[i][jm].getType() == BlockType.FRINGE_RED) {
                                        while (i > 0 && !done) {
                                            System.out.println("3");
                                            i--;
                                            if (blocks[i][jm].getType() == BlockType.FRINGE_RED) {
                                                done = true;
                                                while (i > 0) {
                                                    System.out.println("5");
                                                    i--;
                                                    blocks[i][jm].setType(BlockType.FRINGE_RED);
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (buildTurnStatus > 0 && buildTurnStatus < tunnelWidth+1) {
                                for (int i = amountHAll - 1; i >= 0; i--) {
                                    if (blocks[i][jm].getType() == BlockType.FRINGE_RED) {
                                        while (i > 0) {
                                            i--;
                                            blocks[i][jm].setType(BlockType.WHITE);
                                        }
                                    }
                                }
                            }

                            if (buildTurnStatus == tunnelWidth + 1) {
                                for (int i = amountHAll - 1; i >= 0; i--) {
                                    if (blocks[i][jm].getType() == BlockType.FRINGE_RED) {
                                        while (i > 0) {
                                            i--;
                                            blocks[i][jm].setType(BlockType.FRINGE_RED);
                                        }
                                    }
                                }
                            } else if (buildTurnStatus > tunnelWidth) {
                                for (int i = amountHAll - 1; i >= 0; i--) {
                                    blocks[i][jm].setType(BlockType.BLACK);
                                }
                                mainDirection = 0;
                                this.turn = 0;
                            }
                            buildTurnStatus++;
                        }
                        break;
                        case 2: {
                            jm = amountHAll - 0 - 1 - (tunnelWidth + 2 - buildTurnStatus);
                            if (buildTurnStatus == 0 && mainDirection == 2) {
                                break;
                            }
                            if (buildTurnStatus == 0 && mainDirection != 2) {
                                boolean tmp = false;
                                for (int i = 0; i < amountVAll; i++) {
                                    if (blocks[i][jm].getType() == BlockType.FRINGE_RED && !tmp)
                                        tmp = true;
                                    else if (blocks[i][jm].getType() == BlockType.FRINGE_RED && tmp)
                                        tmp = false;
                                    if (!tmp)
                                        blocks[i][jm].setType(BlockType.FRINGE_RED);
                                }
                            } else if (buildTurnStatus > 0 && buildTurnStatus < tunnelWidth +1) {
                                for (int i = 0; i < amountVAll; i++) {
                                    blocks[i][jm].setType(BlockType.WHITE);
                                }
                            }

                            if (buildTurnStatus == tunnelWidth + 1) {
                                for (int i = 0; i < amountVAll; i++) {
                                    blocks[i][jm].setType(BlockType.FRINGE_RED);
                                }
                            } else if (buildTurnStatus > tunnelWidth) {
                                for (int i = 0; i < amountVAll; i++) {
                                    blocks[i][jm].setType(BlockType.BLACK);
                                }
                                boolean isTopFringeExist = false;
                                boolean isBottomFringeExist = false;
                                Player playerT = Main.game.player;
                                for (int i = 0; i < amountVAll; i++) {
                                    for (int j = 0; j < amountVAll; j++) {
                                        MapBlock curBlock = blocks[i][j];
                                        if (curBlock.getType() == BlockType.FRINGE_RED && playerT.getDirection() % 2 == 0) {
                                            if (curBlock.getY() - curBlock.getSy() <= playerT.getY() &&
                                                    curBlock.getY() + curBlock.getSy() >= playerT.getY()) {
                                                if (curBlock.getX() < playerT.getX()) {
                                                    isTopFringeExist = true;
                                                } else if (curBlock.getX() > playerT.getX()) {
                                                    isBottomFringeExist = true;
                                                }
                                            }
                                        }
                                    }
                                }
                                if (isTopFringeExist && isBottomFringeExist) {
                                    mainDirection = Main.game.player.getDirection();
                                    this.turn = 0;
                                }
                            }
                            buildTurnStatus++;
                        }
                        break;
                    }
                    break;
                case 2:
                    switch (turn) {
                        case -1: {
                            im = amountVAll - 1 - (tunnelWidth + 2 - buildTurnStatus);
                            if (buildTurnStatus == 0) {
                                for (int j = 0; j < amountHAll; j++) {
                                    boolean done = false;
                                    if (blocks[im][j].getType() == BlockType.FRINGE_RED) {
                                        while (j < amountHAll - 1 && !done) {
                                            j++;
                                            if (blocks[im][j].getType() == BlockType.FRINGE_RED) {
                                                done = true;
                                                while (j < amountHAll - 1) {
                                                    j++;
                                                    blocks[im][j].setType(BlockType.FRINGE_RED);
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (buildTurnStatus > 0 && buildTurnStatus < tunnelWidth+1) {
                                for (int j = 0; j < amountHAll; j++) {
                                    if (blocks[im][j].getType() == BlockType.FRINGE_RED) {
                                        while (j < amountHAll - 1) {
                                            j++;
                                            blocks[im][j].setType(BlockType.WHITE);
                                        }
                                    }
                                }
                            } else if (buildTurnStatus == tunnelWidth + 1) {
                                for (int j = 0; j < amountHAll; j++) {
                                    if (blocks[im][j].getType() == BlockType.FRINGE_RED) {
                                        while (j < amountHAll - 1) {
                                            j++;
                                            blocks[im][j].setType(BlockType.FRINGE_RED);
                                        }
                                    }
                                }
                            } else if (buildTurnStatus > tunnelWidth) {
                                for (int j = 0; j < amountHAll; j++) {
                                    blocks[im][j].setType(BlockType.BLACK);

                                }
                                mainDirection = 1;
                                this.turn = 0;
                            }
                            buildTurnStatus++;
                            break;
                        }
                        case 1: {
                            //      ^  <
                            int i = amountVAll - 1 - (tunnelWidth + 2 - buildTurnStatus);
                            //                if(random() < 0.2f) {
                            if (buildTurnStatus == 0 && mainDirection == 3) {
                                break;
                            }
                            if (buildTurnStatus == 0 && mainDirection != 3) {
                                for (int j = amountHAll - 1; j > 0; j--) {
                                    boolean done = false;
                                    if (blocks[i][j].getType() == BlockType.FRINGE_RED) {
                                        while (j > 0 && !done) {
                                            j--;
                                            if (blocks[i][j].getType() == BlockType.FRINGE_RED) {
                                                done = true;
                                                while (j > 0) {
                                                    j--;
                                                    blocks[i][j].setType(BlockType.FRINGE_RED);
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (buildTurnStatus > 0 && buildTurnStatus < tunnelWidth+1) {
                                for (int j = amountHAll - 1; j >= 0; j--) {
                                    if (blocks[i][j].getType() == BlockType.FRINGE_RED) {
                                        while (j > 0) {
                                            j--;
                                            blocks[i][j].setType(BlockType.WHITE);
                                        }
                                    }
                                }
                            }

                            if (buildTurnStatus == tunnelWidth + 1) {
                                for (int j = amountHAll - 1; j >= 0; j--) {
                                    if (blocks[i][j].getType() == BlockType.FRINGE_RED) {
                                        while (j > 0) {
                                            j--;
                                            blocks[i][j].setType(BlockType.FRINGE_RED);
                                        }
                                    }
                                }
                            } else if (buildTurnStatus > tunnelWidth+1) {
                                for (int j = amountHAll - 1; j >= 0; j--) {
                                    blocks[i][j].setType(BlockType.BLACK);

                                }
                                mainDirection = 3;
                                this.turn = 0;
                            }
                            buildTurnStatus++;
                            break;
                        }
                        case 2: {
                            im = amountVAll - 1 - (tunnelWidth + 2 - buildTurnStatus);
                            if (buildTurnStatus == 0) {
                                boolean tmp = false;
                                for (int j = 0; j < amountHAll; j++) {
                                    if (blocks[im][j].getType() == BlockType.FRINGE_RED && !tmp)
                                        tmp = true;
                                    else if (blocks[im][j].getType() == BlockType.FRINGE_RED && tmp)
                                        tmp = false;
                                    if (!tmp)
                                        blocks[im][j].setType(BlockType.FRINGE_RED);
                                }
                            } else if (buildTurnStatus > 0 && buildTurnStatus < tunnelWidth+1) {
                                for (int j = 0; j < amountHAll; j++) {
                                    blocks[im][j].setType(BlockType.WHITE);
                                }
                            } else if (buildTurnStatus == tunnelWidth + 1) {
                                for (int j = 0; j < amountHAll; j++) {
                                    blocks[im][j].setType(BlockType.FRINGE_RED);
                                }
                            } else if (buildTurnStatus > tunnelWidth) {
                                for (int j = 0; j < amountHAll; j++) {
                                    blocks[im][j].setType(BlockType.BLACK);
                                }
                                boolean isTopFringeExist = false;
                                boolean isBottomFringeExist = false;
                                Player playerT = Main.game.player;
                                for (int i = 0; i < amountVAll; i++) {
                                    for (int j = 0; j < amountVAll; j++) {
                                        MapBlock curBlock = blocks[i][j];
                                        if (curBlock.getType() == BlockType.FRINGE_RED && playerT.getDirection() % 2 == 1) {
                                            if (curBlock.getX() - curBlock.getSx() <= playerT.getX() &&
                                                    curBlock.getX() + curBlock.getSx() >= playerT.getX()) {
                                                if (curBlock.getY() < playerT.getY()) {
                                                    isTopFringeExist = true;
                                                } else if (curBlock.getY() > playerT.getY()) {
                                                    isBottomFringeExist = true;
                                                }
                                            }
                                        }
                                    }
                                }
                                if (isTopFringeExist && isBottomFringeExist) {
                                    mainDirection = Main.game.player.getDirection();
                                    this.turn = 0;
                                }
                            }
                            buildTurnStatus++;
                            break;
                        }
                    }
                    break;
                case 3:
                    switch (turn) {
                        case -1: {
                            //      <
                            jm = tunnelWidth + 2 - buildTurnStatus;
                            if (buildTurnStatus == 0) {
                                for (int i = 0; i < amountHAll; i++) {
                                    System.out.println("1" + blocks[i][jm].getType());
                                    boolean done = false;
                                    if (blocks[i][jm].getType() == BlockType.FRINGE_RED) {
                                        while (i < amountHAll - 1 && !done) {
                                            System.out.println("3");
                                            i++;
                                            if (blocks[i][jm].getType() == BlockType.FRINGE_RED) {
                                                done = true;
                                                while (i < amountHAll - 1) {
                                                    System.out.println("5");
                                                    i++;
                                                    blocks[i][jm].setType(BlockType.FRINGE_RED);
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (buildTurnStatus > 0 && buildTurnStatus < tunnelWidth+1) {
                                for (int i = 0; i < amountHAll; i++) {
                                    if (blocks[i][jm].getType() == BlockType.FRINGE_RED) {
                                        while (i < amountVAll - 1) {
                                            i++;
                                            blocks[i][jm].setType(BlockType.WHITE);
                                        }
                                    }
                                }
                            }

                            if (buildTurnStatus == tunnelWidth + 1) {
                                for (int i = 0; i < amountHAll; i++) {
                                    if (blocks[i][jm].getType() == BlockType.FRINGE_RED) {
                                        while (i < amountHAll - 1) {
                                            i++;
                                            blocks[i][jm].setType(BlockType.FRINGE_RED);
                                        }
                                    }
                                }
                            } else if (buildTurnStatus > tunnelWidth) {
                                for (int i = 0; i < amountHAll; i++) {
                                    blocks[i][jm].setType(BlockType.BLACK);
                                }
                                mainDirection = 2;
                                this.turn = 0;
                            }
                            buildTurnStatus++;
                            break;
                        }
                        case 1: {
                            //      <  ^
                            jm = tunnelWidth + 2 - buildTurnStatus;
                            if (buildTurnStatus == 0) {
                                for (int i = amountHAll - 1; i >= 0; i--) {
                                    System.out.println("1" + blocks[i][jm].getType());
                                    boolean done = false;
                                    if (blocks[i][jm].getType() == BlockType.FRINGE_RED) {
                                        while (i > 0 && !done) {
                                            System.out.println("3");
                                            i--;
                                            if (blocks[i][jm].getType() == BlockType.FRINGE_RED) {
                                                done = true;
                                                while (i > 0) {
                                                    System.out.println("5");
                                                    i--;
                                                    blocks[i][jm].setType(BlockType.FRINGE_RED);
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (buildTurnStatus > 0 && buildTurnStatus < tunnelWidth+1) {
                                for (int i = amountHAll - 1; i >= 0; i--) {
                                    if (blocks[i][jm].getType() == BlockType.FRINGE_RED) {
                                        while (i > 0) {
                                            i--;
                                            blocks[i][jm].setType(BlockType.WHITE);
                                        }
                                    }
                                }
                            }

                            if (buildTurnStatus == tunnelWidth + 1) {
                                for (int i = amountHAll - 1; i >= 0; i--) {
                                    if (blocks[i][jm].getType() == BlockType.FRINGE_RED) {
                                        while (i > 0) {
                                            i--;
                                            blocks[i][jm].setType(BlockType.FRINGE_RED);
                                        }
                                    }
                                }
                            } else if (buildTurnStatus > tunnelWidth) {
                                for (int i = amountHAll - 1; i >= 0; i--) {
                                    blocks[i][jm].setType(BlockType.BLACK);
                                }
                                mainDirection = 0;
                                this.turn = 0;
                            }
                            buildTurnStatus++;
                            break;
                        }
                        case 2: {
                            //      >  ^v
                            jm = tunnelWidth + 2 - buildTurnStatus;
                            if (buildTurnStatus == 0 && mainDirection == 2) {
                                break;
                            }
                            if (buildTurnStatus == 0 && mainDirection != 2) {
                                boolean tmp = false;
                                for (int i = 0; i < amountVAll; i++) {
                                    if (blocks[i][jm].getType() == BlockType.FRINGE_RED && !tmp)
                                        tmp = true;
                                    else if (blocks[i][jm].getType() == BlockType.FRINGE_RED && tmp)
                                        tmp = false;
                                    if (!tmp)
                                        blocks[i][jm].setType(BlockType.FRINGE_RED);
                                }
                            } else if (buildTurnStatus > 0 && buildTurnStatus < tunnelWidth+1) {
                                for (int i = 0; i < amountVAll; i++) {
                                    blocks[i][jm].setType(BlockType.WHITE);
                                }
                            }

                            if (buildTurnStatus == tunnelWidth + 1) {
                                for (int i = 0; i < amountVAll; i++) {
                                    blocks[i][jm].setType(BlockType.FRINGE_RED);
                                }
                            } else if (buildTurnStatus > tunnelWidth) {
                                for (int i = 0; i < amountVAll; i++) {
                                    blocks[i][jm].setType(BlockType.BLACK);
                                }
                                boolean isTopFringeExist = false;
                                boolean isBottomFringeExist = false;
                                Player playerT = Main.game.player;
                                for (int i = 0; i < amountVAll; i++) {
                                    for (int j = 0; j < amountVAll; j++) {
                                        MapBlock curBlock = blocks[i][j];
                                        if (curBlock.getType() == BlockType.FRINGE_RED && playerT.getDirection() % 2 == 0) {
                                            if (curBlock.getY() - curBlock.getSy() <= playerT.getY() &&
                                                    curBlock.getY() + curBlock.getSy() >= playerT.getY()) {
                                                if (curBlock.getX() < playerT.getX()) {
                                                    isTopFringeExist = true;
                                                } else if (curBlock.getX() > playerT.getX()) {
                                                    isBottomFringeExist = true;
                                                }
                                            }
                                        }
                                    }
                                }
                                if (isTopFringeExist && isBottomFringeExist) {
                                    mainDirection = Main.game.player.getDirection();
                                    this.turn = 0;
                                }
                            }
                            buildTurnStatus++;
                            break;
                        }
                    }
                    break;
            }
            if(buildTurnStatus == tunnelWidth / 2 && (Math.random() < 0.1f + 0.5f * (1 - (tunnelWidth / maxTunnelWidth))  )) {
                System.out.println("Hello!!!! " + buildTurnStatus);
                float shiftValueX = buildTurnStatus * blockSizeX;
                float shiftValueY = buildTurnStatus * blockSizeY;
                switch(mainDirection) {
                    case 0:
                        Main.game.bonuses.add(new Bonus(50, 0 - shiftValueY, 0));
                        break;
                    case 1:
                        Main.game.bonuses.add(new Bonus(100 + shiftValueX, 50*Main.ratio, 0));
                        break;
                    case 2:
                        Main.game.bonuses.add(new Bonus(50, 100*Main.ratio + shiftValueY, 0));
                        break;
                    case 3:
                        Main.game.bonuses.add(new Bonus(0 - shiftValueX, 50*Main.ratio, 0));
                        break;
                }

            }
        }
        if (turn == 2) {
            switch(curDirection) {
                case 0:{
                    if (buildTurnStatus > tunnelWidth +1) {
                        boolean isTopFringeExist = false;
                        boolean isBottomFringeExist = false;
                        Player playerT = Main.game.player;
                        for (int i = 0; i < amountVAll; i++) {
                            for (int j = 0; j < amountVAll; j++) {
                                MapBlock curBlock = blocks[i][j];
                                if (curBlock.getType() == BlockType.FRINGE_RED && playerT.getDirection() % 2 == 1) {
                                    if (curBlock.getX() - curBlock.getSx() <= playerT.getX() &&
                                            curBlock.getX() + curBlock.getSx() >= playerT.getX()) {
                                        if (curBlock.getY() < playerT.getY()) {
                                            isTopFringeExist = true;
                                        } else if (curBlock.getY() > playerT.getY()) {
                                            isBottomFringeExist = true;
                                        }
                                    }
                                }
                            }
                        }
                        if (isTopFringeExist && isBottomFringeExist) {
                            mainDirection = Main.game.player.getDirection();
                            this.turn = 0;
                        }
                    }
                    buildTurnStatus++;
                    break;
                }
                case 1:{
                    //      >  ^v
                    if (buildTurnStatus > tunnelWidth) {
                        boolean isTopFringeExist = false;
                        boolean isBottomFringeExist = false;
                        Player playerT = Main.game.player;
                        for (int i = 0; i < amountVAll; i++) {
                            for (int j = 0; j < amountVAll; j++) {
                                MapBlock curBlock = blocks[i][j];
                                if (curBlock.getType() == BlockType.FRINGE_RED && playerT.getDirection() % 2 == 0) {
                                    if (curBlock.getY() - curBlock.getSy() <= playerT.getY() &&
                                            curBlock.getY() + curBlock.getSy() >= playerT.getY()) {
                                        if (curBlock.getX() < playerT.getX()) {
                                            isTopFringeExist = true;
                                        } else if (curBlock.getX() > playerT.getX()) {
                                            isBottomFringeExist = true;
                                        }
                                    }
                                }
                            }
                        }
                        if (isTopFringeExist && isBottomFringeExist) {
                            mainDirection = Main.game.player.getDirection();
                            this.turn = 0;
                        }
                    }
                    buildTurnStatus++;
                }
                    break;
                case 2:
                {
                    if (buildTurnStatus > tunnelWidth) {
                        boolean isTopFringeExist = false;
                        boolean isBottomFringeExist = false;
                        Player playerT = Main.game.player;
                        for (int i = 0; i < amountVAll; i++) {
                            for (int j = 0; j < amountVAll; j++) {
                                MapBlock curBlock = blocks[i][j];
                                if (curBlock.getType() == BlockType.FRINGE_RED && playerT.getDirection() % 2 == 1) {
                                    if (curBlock.getX() - curBlock.getSx() <= playerT.getX() &&
                                            curBlock.getX() + curBlock.getSx() >= playerT.getX()) {
                                        if (curBlock.getY() < playerT.getY()) {
                                            isTopFringeExist = true;
                                        } else if (curBlock.getY() > playerT.getY()) {
                                            isBottomFringeExist = true;
                                        }
                                    }
                                }
                            }
                        }
                        if (isTopFringeExist && isBottomFringeExist) {
                            mainDirection = Main.game.player.getDirection();
                            this.turn = 0;
                        }
                    }
                    buildTurnStatus++;
                    break;
                }
                case 3:
                    if (buildTurnStatus > tunnelWidth) {
                        boolean isTopFringeExist = false;
                        boolean isBottomFringeExist = false;
                        Player playerT = Main.game.player;
                        for (int i = 0; i < amountVAll; i++) {
                            for (int j = 0; j < amountVAll; j++) {
                                MapBlock curBlock = blocks[i][j];
                                if (curBlock.getType() == BlockType.FRINGE_RED && playerT.getDirection() % 2 == 0) {
                                    if (curBlock.getY() - curBlock.getSy() <= playerT.getY() &&
                                            curBlock.getY() + curBlock.getSy() >= playerT.getY()) {
                                        if (curBlock.getX() < playerT.getX()) {
                                            isTopFringeExist = true;
                                        } else if (curBlock.getX() > playerT.getX()) {
                                            isBottomFringeExist = true;
                                        }
                                    }
                                }
                            }
                        }
                        if (isTopFringeExist && isBottomFringeExist) {
                            mainDirection = Main.game.player.getDirection();
                            this.turn = 0;
                        }
                    }
                    break;
            }
        }

    }

    private void printLabyrinth(int buildTurnStatus) {
        System.out.println(buildTurnStatus);
        for(int i = 0; i < amountVAll; i++) {
            System.out.println();
            for(int j = 0; j < amountHAll; j++) {
                if(blocks[i][j].getType() == BlockType.BLACK)
                    System.out.print("-");
                else if(blocks[i][j].getType() == BlockType.WHITE)
                    System.out.print("0");
                else if(blocks[i][j].getType() == BlockType.FRINGE_RED)
                    System.out.print("x");
                else
                    System.out.print("#");
            }
        }
    }


    public void render() {
        for(int i = 0; i < amountHAll; i++) {
            for (int j = 0; j < amountVAll; j++) {
                blocks[i][j].render();
            }
        }
    }
    private MapBlock createBlock(float coordX, float coordY, float sizeX, float sizeY, BlockType type, int i, int j) {
        return new MapBlock(coordX, coordY, sizeX, sizeY, type, i, j);
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }
    public int getTurn() {
        return turn;
    }
}
