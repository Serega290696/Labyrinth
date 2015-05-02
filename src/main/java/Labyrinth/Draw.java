package Labyrinth;

import Labyrinth.Enums.DrawFigure;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.*;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Serega on 28.04.2015.
 */
public class Draw {
    public static float xshift = 0;
    public static float yshift = 0;
//    private static final float Pi = 180f;

    private static boolean antiAlias = true;
    static Font awtFont1 = new Font("Times New Roman", Font.BOLD, 20);
    static Font awtFont2 = new Font("Times New Roman", Font.BOLD, 30);
    static Font dsCrystal = new Font("DS Crystal", Font.BOLD, 76);
    private static TrueTypeFont font1_1 = new TrueTypeFont(awtFont1, antiAlias);
    private static TrueTypeFont font1_2 = new TrueTypeFont(awtFont2, antiAlias);
    private static TrueTypeFont font2 = new TrueTypeFont(awtFont2, antiAlias);
    private static TrueTypeFont font3 = new TrueTypeFont(dsCrystal, antiAlias);

    private static final String CONTENT_PATH = "content/";
    private static final String TEXTURE_PATH = CONTENT_PATH+"images/";
    private static final String SOUND_PATH = CONTENT_PATH+"music/";
    private static final String FONTS_PATH = CONTENT_PATH+"fonts/";


    public static Texture fon1;
    public static boolean hide = false;
//    public static Texture fon3;
    private static Audio fonSound1;

    public static void draw(DrawFigure figure, float x, float y, float sx, float sy, float rotate, int color, float opacity) {

        glDisable(GL_TEXTURE_2D);
        float fringe = 30;
        if(Main.game.player.x - xshift < fringe && Main.game.player.getDirection() == 3)
            xshift = Main.game.player.x -fringe;
        if(Main.game.player.x - xshift > (100-fringe) && Main.game.player.getDirection() == 1)
            xshift = Main.game.player.x -(100-fringe);
        if(Main.game.player.y - yshift < fringe*Main.ratio && Main.game.player.getDirection() == 0)
            yshift = Main.game.player.y -fringe*Main.ratio;
        if(Main.game.player.y - yshift > (100-fringe)*Main.ratio && Main.game.player.getDirection() == 2)
            yshift = Main.game.player.y -(100-fringe)*Main.ratio;
        xshift = Main.game.player.x - 50;
        yshift = Main.game.player.y - 50*Main.ratio;

        x -= xshift;
        y -= yshift;
//        x += 50;
//        y += 50;
//        Main.em = (int) ( Main.defEm *Main.game.map.amountH / Main.game.map.amountHAll * 0.9f);
        x *= Main.em;
        y *= Main.em;

        sx *= Main.em;
        sy *= Main.em;
        switch(figure) {
            case RECT:
                rect(x, y, sx, sy, rotate, color, opacity);
                break;
            case PLAYER:
//                rect(x, y, sx, sy, rotate, color, opacity);
                float tSV = sy/2;
                if(Main.game.player.getDirection() == 0) {
                    triangle(x, y - sy / 2 - tSV / 2 + 1, tSV, sx, 90, color, opacity);
                    rect(x, y, sx, sy, rotate, color, opacity);
                }
                if(Main.game.player.getDirection() == 1) {
                    triangle(x+sy/2+tSV/2 -1, y, tSV, sx, 0, color, opacity);
                    rect(x, y, sx, sy, 90, color, opacity);
                }
                if(Main.game.player.getDirection() == 2) {
                    triangle(x, y+sy/2+tSV/2 -1, tSV, sx, -90, color, opacity);
                    rect(x, y, sx, sy, 180, color, opacity);
                }
                if(Main.game.player.getDirection() == 3) {
                    triangle(x-sy/2-tSV/2 +1, y, tSV, sx, 180, color, opacity);
                    rect(x, y, sx, sy, -90, color, opacity);
                }
                break;
            case TRIANGLE: triangle(x, y, sx, sy, rotate, color, opacity);
                break;
            case CIRCLE: circle(x, y, sx, sy, color, opacity, rotate);
                break;
            case FON:
                break;
            default:
                break;
        }
    }



    public static void draw(DrawFigure figure, float x, float y, float sx, float sy) {
        draw(figure, x, y, sx, sy, 0, 0, 1);
    }
    public static void rect(float x, float y, float sx, float sy, float rotate, float opacity) {
        rect(x, y, sx, sy, rotate, 0);
    }
    public static void chooseColor(int color, float opacity) {
        switch(color) {
            case 0:
                glColor4f(0.8f, 0.8f, 0.8f, opacity);
                break;
            case 1:
                glColor4f(1.0f, 0.0f, 0.0f, opacity);
                glColor4f(0.733f, 0.223f, 0.168f, opacity);
                break;
            case 2:
                glColor4f(0.478f, 0.737f, 0.345f, opacity);
                break;
            case 3:
                glColor4f(0.247f, 0.494f, 1.0f, opacity);
                break;
            case 4:
                glColor4f(0.9f, 0.2f, 0.2f, opacity);
                break;
            case 5:
                glColor4f(0.0f, 1.0f, 0.0f, opacity);
                break;
            case 6:
                glColor4f(0f, 0.0f, 1.0f, opacity);
                break;
            case 7:
                glColor4f(0f, 0.0f, 0.0f, opacity);
                break;
            case 8:
                glColor4f(0.1f, 0.1f, 0.1f, opacity);
                break;
            case 9:
                glColor4f(0.435f, 0.168f, 0.733f, opacity);
                break;
            case 10:
                glColor4f(0f, 0.423f, 1f, opacity);
                break;
        }
    }
    public static void rect(float x, float y, float sx, float sy, float rotate, int color, float opacity) {
//        gl
        glPushMatrix();
        {
            chooseColor(color, opacity);
            glTranslatef(x, y, 0);
            glRotatef((float) (-rotate), 0, 0, 1);
            glDisable(GL_POLYGON_SMOOTH);
//            glColor4f(0.0f, 1.0f, 0.0f, opacity);
            glBegin(GL_QUADS);
            {
                glVertex2f(-sx / 2, -sy / 2);
                glVertex2f(-sx/2, sy/2);
                glVertex2f(sx / 2, sy / 2);
                glVertex2f(sx/2,-sy/2);
            }
            glEnd();
        }
        glPopMatrix();
    }
    public static void circle(float x, float y, float sx, float sy, int color, float opacity, float rotate) {
        glPushMatrix();
        {
            chooseColor(color, opacity);
            glTranslatef(x, y, 0);
            glRotatef((float) (-rotate), 0, 0, 1);
                glBegin(GL_POLYGON);
                {
                    int amountPoints = (int) (20 * sx);
                    for (int i = 0; i <= amountPoints; i++) {
                        glVertex2d(sx / 2 * Math.cos((float) i / amountPoints * 2 * Math.PI), sy / 2 * Math.sin((float) i / amountPoints * 2 * Math.PI));
                    }
                }
                glEnd();
//            }
        }
        glPopMatrix();
    }
    public static void gameInterface() {
        glEnable(GL_TEXTURE_2D);
        font1_2.drawString(50, 40,
                "Score: " +
                        String.valueOf((int) Main.game.player.score),
                org.newdawn.slick.Color.white);
        font1_1.drawString(50, 70,
                "Speed: " +
                        String.valueOf((int) Main.game.player.getSpeed()),
                org.newdawn.slick.Color.white);
        if(!hide) {
            font1_1.drawString(Main.dWidth - 300, Main.dHeight - 130,
                    "Control: (left) < , (right:) >",
                    org.newdawn.slick.Color.white);
            font1_1.drawString(Main.dWidth - 300, Main.dHeight - 100,
                    "pause/cont.: Esc",
                    org.newdawn.slick.Color.white);
            font1_1.drawString(Main.dWidth - 300, Main.dHeight - 70,
                    "Hide: backspace",
                    org.newdawn.slick.Color.white);
            font1_1.drawString(Main.dWidth - 400, Main.dHeight - 40,
                    "On arrow(not recommended): space",
                    org.newdawn.slick.Color.white);
        }
    }
    public static void triangle(float x, float y, float sx, float sy, float rotate, int color, float opacity) {
        glPushMatrix();
        {

            float side = sx;
            chooseColor(color, opacity);
            glTranslatef(x, y, 0);
            glRotatef(-rotate, 0, 0, 1);
            glEnable(GL_POLYGON_SMOOTH);
            glBegin(GL_TRIANGLES);
            {
                glVertex2f(-sx / 2, -sy / 2);
                glVertex2f(-sx / 2, sy / 2);
                glVertex2f((float) ((Math.pow(3, 0.5f) - 1) / 2 * sx), 0);
            }
            glEnd();
        }
        glPopMatrix();
    }
    public static void musicPlay() {
        fonSound1.playAsSoundEffect(1.0f, 1.0f, false);
    }
    public static void init() {
//        try {
//            fon1 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(TEXTURE_PATH + "fon/fon1.png"));
//            fon3 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(TEXTURE_PATH + "fon/fon2.png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        initMusic();
    }
    public static void initMusic() {
        try {
            fonSound1 = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream(SOUND_PATH + "1.wav"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Draw.musicPlay();
    }
}
