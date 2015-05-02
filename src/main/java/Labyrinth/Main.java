package Labyrinth;

/**
 * Created by Serega on 28.04.2015.
 */

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Serega on 03.03.2015.
 */
public class Main {


    public static final String GAME_TITLE = "Labyrinth";
    public static Game game;

    public static final int delay = 15;
    public static final int fps = (int) (1000 / delay);
    public static final int updatePS = (int) (40);
    public static long lastRenderTime = 0;

    public static final float ratio = 0.8f;
    public static final int dWidth = 1000;
    public static final int dHeight = (int) (dWidth * ratio);
    public static int defEm = dWidth / 100;
    public static int em = dWidth / 100;


    public static void main(String[] args) {
        initDisplay();
        initGL();

        Draw.init();

        initGame();

        gameLoop();

        cleanUp();

    }


    public static void restartGame() {
        initGame();
    }

    private static void initDisplay() {

        try {
            Display.setDisplayMode(new DisplayMode(dWidth, dHeight));
            Display.create();
            Display.setVSyncEnabled(true);
            Display.setTitle(GAME_TITLE);

            Keyboard.create();
            Mouse.create();
            Mouse.setGrabbed(false);
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

    }
    public static void mouseGrabbed(boolean mouseGrabbed) {
        Mouse.setGrabbed(mouseGrabbed);
    }

    private static void initGL() {
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);

        GL11.glClearDepth(1);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glViewport(0, 0, dWidth, dHeight);
        glMatrixMode(GL_MODELVIEW);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GL11.glOrtho(0, dWidth, dHeight, 0, dWidth, -dWidth);
        glMatrixMode(GL_MODELVIEW);
        glClearColor(0.0f, 0.0f, 0.002f, 1);
    }

    private static void initGame() {
        game = new Game();
    }

    private static void gameLoop() {
        while (!Display.isCloseRequested()) {
            getInput();
                    update();
            render();
            if (Keyboard.isKeyDown(Keyboard.KEY_R))
                restartGame();
        }
    }

    private static void getInput() {
        game.getInput();
    }

    private static void update() {
        game.update();
    }

    private static void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glLoadIdentity();

        game.render();

        Display.update();

        Display.sync(fps);
    }

    public static void cleanUp() {
        Display.destroy();
        Keyboard.destroy();
        Mouse.destroy();
    }


}
