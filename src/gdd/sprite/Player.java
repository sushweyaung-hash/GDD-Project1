package gdd.sprite;

import static gdd.Global.*;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Player extends Sprite {

    private static final int START_X = 270;
    private static final int START_Y = 540;
    private int width;
    private int currentSpeed = 2;
    private int shotLevel = 1; // Add shotLevel field
    private static final int MAX_SPEED = 4;

    private Rectangle bounds = new Rectangle(175,135,17,32);

    public Player() {
        initPlayer();
    }

    private void initPlayer() {
        javax.swing.ImageIcon[] icons = {
            new javax.swing.ImageIcon("src/images/player.png"),
            new javax.swing.ImageIcon("src/images/player2.png")
        };
        java.awt.Image[] animFrames = new java.awt.Image[icons.length];
        for (int i = 0; i < icons.length; i++) {
            animFrames[i] = icons[i].getImage().getScaledInstance(icons[i].getIconWidth() * SCALE_FACTOR,
                    icons[i].getIconHeight() * SCALE_FACTOR,
                    java.awt.Image.SCALE_SMOOTH);
        }
        setFrames(animFrames);
        setX(START_X);
        setY(START_Y);
    }

    public int getSpeed() {
        return currentSpeed;
    }

    public int setSpeed(int speed) {
        if (speed < 1) {
            speed = 1; // Ensure speed is at least 1
        }
        if (speed > MAX_SPEED) {
            speed = MAX_SPEED; // Cap speed
        }
        this.currentSpeed = speed;
        return currentSpeed;
    }

    public void act() {
        x += dx;
        animate(); // Advance animation frame
        if (x <= 2) {
            x = 2;
        }
        if (x >= BOARD_WIDTH - 2 * width) {
            x = BOARD_WIDTH - 2 * width;
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -currentSpeed;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = currentSpeed;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
    }

    public int getShotLevel() {
        return shotLevel;
    }

    public void setShotLevel(int level) {
        if (level < 1) level = 1;
        if (level > 4) level = 4;
        this.shotLevel = level;
    }
}
