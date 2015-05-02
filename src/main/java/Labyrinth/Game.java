package Labyrinth;

import Labyrinth.GameObjects.Bonus;
import Labyrinth.GameObjects.GameMap;
import Labyrinth.GameObjects.Player;
import Labyrinth.GameObjects.Tail;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Serega on 28.04.2015.
 */
public class Game implements IGame {

    public GameConfiguration gameConfiguration = new GameConfiguration();

    public GameMap map = new GameMap();
    public Player player = new Player();

    ArrayList<Integer> buttons = new ArrayList<Integer>();
    ArrayList<Player> players = new ArrayList<Player>();
    public ArrayList<Tail> tail = new ArrayList<Tail>();
    public ArrayList<Bonus> bonuses = new ArrayList<Bonus>();

    public static boolean pause = false;
    public static boolean missPause = false;
    public boolean someChanged = false;
    public long beginTime = new Date().getTime();
    public float time = (float)(new Date().getTime() - beginTime)  / 1000f;
    public int integerTime;
    public float[] eventsTime;
    public float lastEventTime;
    public int numCurEvent = -1;
    public float curEvent;
    private boolean autoGenerate = true;
    private boolean cheatsOn = false;
    public boolean arrowOn = false;


    public Game() {
        clear();
        eventsTime = new float[]
                {
                        2.5f, 3.0f, 3f, 2.2f, 4, 2, 2, 1.5f, 2, 2, 1, 1, 1, 1, 2, 3, 3, 1, 1, 1, 1, 1, 1, 2, 1, 4, 1, 5, 1, (lastEventTime= (float) (0.9f+0.7f*Math.random()))
                };
        curEvent = eventsTime[0];
    }

    public void restartGame() {

    }

    public void clear() {
        players.clear();
        buttons.clear();
        tail.clear();
        Draw.xshift = 0;
        Draw.yshift = 0;
        map = new GameMap();
        players.add(player = new Player());
    }

    public void getInput() {
        int currentButton;
        switch(gameConfiguration.controlMode) {
            case 1:
                currentButton = Keyboard.KEY_LEFT;
                if(Keyboard.isKeyDown(currentButton)){
                    if(!isClicked(currentButton)) {
                        player.setDirection(-1);
                        buttons.add(currentButton);
                    }
                }
                else {
                    if (buttons.contains(currentButton)) {
                        buttons.remove(new Integer(currentButton));
                    }
                }

                currentButton = Keyboard.KEY_RIGHT;
                if(Keyboard.isKeyDown(currentButton)){
                    if(!isClicked(currentButton)) {
                        player.setDirection(1);
                        buttons.add(currentButton);
                    }
                }
                else {
                    if (buttons.contains(currentButton)) {
                        buttons.remove(new Integer(currentButton));
                    }
                }

                currentButton = Keyboard.KEY_ESCAPE;
                if(Keyboard.isKeyDown(currentButton)){
                    if(!isClicked(currentButton)) {
                        pause = !pause;
                        buttons.add(currentButton);
                    }
                }
                else {
                    if (buttons.contains(currentButton)) {
                        buttons.remove(new Integer(currentButton));
                    }
                }
                currentButton = Keyboard.KEY_BACK;
                if(Keyboard.isKeyDown(currentButton)){
                    if(!isClicked(currentButton)) {
                        Draw.hide = !Draw.hide;
                        buttons.add(currentButton);
                    }
                }
                else {
                    if (buttons.contains(currentButton)) {
                        buttons.remove(new Integer(currentButton));
                    }
                }
                currentButton = Keyboard.KEY_SPACE;
                if(Keyboard.isKeyDown(currentButton)){
                    if(!isClicked(currentButton)) {
                        arrowOn = !arrowOn;
                        buttons.add(currentButton);
                    }
                }
                else {
                    if (buttons.contains(currentButton)) {
                        buttons.remove(new Integer(currentButton));
                    }
                }
                break;
            case 2:
                currentButton = Keyboard.KEY_UP;
                if(Keyboard.isKeyDown(currentButton)){
                    if(!isClicked(currentButton)) {
                        player.setDirection(0);
                        buttons.add(currentButton);
                    }
                }
                else {
                    if (buttons.contains(currentButton)) {
                        buttons.remove(new Integer(currentButton));
                    }
                }
                currentButton = Keyboard.KEY_RIGHT;
                if(Keyboard.isKeyDown(currentButton)){
                    if(!isClicked(currentButton)) {
                        player.setDirection(1);
                        buttons.add(currentButton);
                    }
                }
                else {
                    if (buttons.contains(currentButton)) {
                        buttons.remove(new Integer(currentButton));
                    }
                }
                currentButton = Keyboard.KEY_DOWN;
                if(Keyboard.isKeyDown(currentButton)){
                    if(!isClicked(currentButton)) {
                        player.setDirection(2);
                        buttons.add(currentButton);
                    }
                }
                else {
                    if (buttons.contains(currentButton)) {
                        buttons.remove(new Integer(currentButton));
                    }
                }

                currentButton = Keyboard.KEY_LEFT;
                if(Keyboard.isKeyDown(currentButton)){
                    if(!isClicked(currentButton)) {
                        player.setDirection(3);
                        buttons.add(currentButton);
                    }
                }
                else {
                    if (buttons.contains(currentButton)) {
                        buttons.remove(new Integer(currentButton));
                    }
                }

                currentButton = Keyboard.KEY_ESCAPE;
                if(Keyboard.isKeyDown(currentButton)){
                    if(!isClicked(currentButton)) {
                        pause = !pause;
                        buttons.add(currentButton);
                    }
                }
                else {
                    if (buttons.contains(currentButton)) {
                        buttons.remove(new Integer(currentButton));
                    }
                }
                currentButton = Keyboard.KEY_BACK;
                if(Keyboard.isKeyDown(currentButton)){
                    if(!isClicked(currentButton)) {
                        Draw.hide = !Draw.hide;
                        buttons.add(currentButton);
                    }
                }
                else {
                    if (buttons.contains(currentButton)) {
                        buttons.remove(new Integer(currentButton));
                    }
                }
                currentButton = Keyboard.KEY_SPACE;
                if(Keyboard.isKeyDown(currentButton)){
                    if(!isClicked(currentButton)) {
                        arrowOn = !arrowOn;
                        buttons.add(currentButton);
                    }
                }
                else {
                    if (buttons.contains(currentButton)) {
                        buttons.remove(new Integer(currentButton));
                    }
                }
                break;
        }
        currentButton = Keyboard.KEY_1;
        if(Keyboard.isKeyDown(currentButton)){
            if(!isClicked(currentButton)) {
                gameConfiguration.controlMode = 1;
                buttons.add(currentButton);
            }
        }
        else {
            if (buttons.contains(currentButton)) {
                buttons.remove(new Integer(currentButton));
            }
        }
        currentButton = Keyboard.KEY_2;
        if(Keyboard.isKeyDown(currentButton)){
            if(!isClicked(currentButton)) {
                gameConfiguration.controlMode = 2;
                buttons.add(currentButton);
            }
        }
        else {
            if (buttons.contains(currentButton)) {
                buttons.remove(new Integer(currentButton));
            }
        }
        currentButton = Keyboard.KEY_3;
        if(Keyboard.isKeyDown(currentButton)){
            if(!isClicked(currentButton)) {
                cheatsOn = !cheatsOn;
                buttons.add(currentButton);
            }
        }
        else {
            if (buttons.contains(currentButton)) {
                buttons.remove(new Integer(currentButton));
            }
        }
        if(cheatsOn){
            currentButton = Keyboard.KEY_UP;
            if (Keyboard.isKeyDown(currentButton)) {
                if (!isClicked(currentButton)) {
                    player.setSpeed(player.getSpeed() + 20);
                    buttons.add(currentButton);
                }
            } else {
                if (buttons.contains(currentButton)) {
                    buttons.remove(new Integer(currentButton));
                }
            }
            currentButton = Keyboard.KEY_DOWN;
            if (Keyboard.isKeyDown(currentButton)) {
                if (!isClicked(currentButton)) {
                    player.setSpeed(player.getSpeed() - 40);
                    buttons.add(currentButton);
                }
            } else {
                if (buttons.contains(currentButton)) {
                    buttons.remove(new Integer(currentButton));
                }
            }

            currentButton = Keyboard.KEY_O;
            if (Keyboard.isKeyDown(currentButton)) {
                if (!isClicked(currentButton)) {
                    missPause = true;
                    buttons.add(currentButton);
                }
            } else {
                if (buttons.contains(currentButton)) {
                    buttons.remove(new Integer(currentButton));
                }
            }


            currentButton = Keyboard.KEY_NUMPAD4;
            if (Keyboard.isKeyDown(currentButton)) {
                if (!isClicked(currentButton)) {
                    setTurn(-1);
                    buttons.add(currentButton);
                }
            } else {
                if (buttons.contains(currentButton)) {
                    buttons.remove(new Integer(currentButton));
                }
            }
            currentButton = Keyboard.KEY_NUMPAD6;
            if (Keyboard.isKeyDown(currentButton)) {
                if (!isClicked(currentButton)) {
                    setTurn(1);
                    buttons.add(currentButton);
                }
            } else {
                if (buttons.contains(currentButton)) {
                    buttons.remove(new Integer(currentButton));
                }
            }
            currentButton = Keyboard.KEY_NUMPAD8;
            if (Keyboard.isKeyDown(currentButton)) {
                if (!isClicked(currentButton)) {
                    setTurn(2);
                    buttons.add(currentButton);
                }
            } else {
                if (buttons.contains(currentButton)) {
                    buttons.remove(new Integer(currentButton));
                }
            }
            currentButton = Keyboard.KEY_W;
            if (Keyboard.isKeyDown(currentButton)) {
                if (!isClicked(currentButton)) {
                    autoGenerate = !autoGenerate;
                    buttons.add(currentButton);
                }
            } else {
                if (buttons.contains(currentButton)) {
                    buttons.remove(new Integer(currentButton));
                }
            }
            currentButton = Keyboard.KEY_I;
            if (Keyboard.isKeyDown(currentButton)) {
                if (!isClicked(currentButton)) {
                    player.immortal = !player.immortal;
                    buttons.add(currentButton);
                }
            } else {
                if (buttons.contains(currentButton)) {
                    buttons.remove(new Integer(currentButton));
                }
            }
            currentButton = Keyboard.KEY_Q;
            if (Keyboard.isKeyDown(currentButton)) {
                if (!isClicked(currentButton)) {
                    player.getBonus();
                    buttons.add(currentButton);
                }
            } else {
                if (buttons.contains(currentButton)) {
                    buttons.remove(new Integer(currentButton));
                }
            }
        }
    }

    public boolean isClicked(final int CURRENT_BUTTON) {
        for(int button : buttons) {
            if(button == CURRENT_BUTTON) {
                return true;
            }
        }
        return false;
    }


    public void update() {
        if(pause && !missPause) {
            return;
        }
        else if(pause && missPause) {
            missPause = false;
        }
        map.update();
        for(Player player : players) {
            player.update();
        }
        for(Bonus bonus: bonuses) {
            bonus.update();
        }
        someChanged = true;
        while(someChanged) {
            someChanged = false;
            for(Tail curTailPart : tail) {
                curTailPart.update();
                if(someChanged)
                    break;
            }
        }
        script();
    }

    public void render() {
        map.render();
        for(Player player : players) {
            player.render();
        }
        for(Tail curTailPart : tail) {
            curTailPart.render();
        }
        for(Bonus bonus: bonuses) {
            bonus.render();
        }
        Draw.gameInterface();
    }

    public void script() {
        time = (float)(new Date().getTime() - beginTime) / 1000f;
        if((int)time != integerTime) {
            integerTime = (int)(time);
            System.out.print("\n" + integerTime + ". " + player.getDirection());
            System.out.println("     \t\t\t-- bs: " + map.buildTurnStatus + "   mDir: " + map.mainDirection + "   T: " + map.getTurn() + ". TunnelWidth: " + map.tunnelWidth);
        }

        curEvent -= 1f / Main.fps;
        if(curEvent <= 0) {
            int turnSide = (int) (1 - (int)(Math.random() * 2) *2);
            if(Math.random() < 0.2)
                turnSide = 2;
            if(autoGenerate)
                map.setTurn(turnSide);
            System.out.print("!e: " + numCurEvent + " ");
            numCurEvent++;
            if(numCurEvent >= eventsTime.length) {
                numCurEvent = eventsTime.length-2;
            }
            curEvent = eventsTime[numCurEvent];
        }


    }

    public void setTurn(int a) {
        map.setTurn(a);
    }
    public void delObj(GO go) {

    }
}
