package gdd.sprite;

import static gdd.Global.*;

import javax.swing.ImageIcon;

public class Shot extends Sprite {

    private static final int H_SPACE = 20;
    private static final int V_SPACE = 1;

    private int dx = 0;
    private int dy = -20; // Default: straight up

    public Shot() {
    }

    public Shot(int x, int y) {

        initShot(x, y);
    }

    public Shot(int x, int y, int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
        initShot(x, y);
    }

    private void initShot(int x, int y) {
        javax.swing.ImageIcon[] icons = {
            new ImageIcon("src/images/shot.png"),
            new ImageIcon("src/images/shot2.png")
        };
        java.awt.Image[] animFrames = new java.awt.Image[icons.length];
        for (int i = 0; i < icons.length; i++) {
            animFrames[i] = icons[i].getImage().getScaledInstance(icons[i].getIconWidth() * SCALE_FACTOR,
                    icons[i].getIconHeight() * SCALE_FACTOR, 
                    java.awt.Image.SCALE_SMOOTH);
        }
        setFrames(animFrames);
        setX(x); // Use player's X directly
        setY(y); // Use player's Y directly
    }

    @Override
    public void act() {
        animate(); // Advance animation frame
        x += dx;
        y += dy;
    }
}
