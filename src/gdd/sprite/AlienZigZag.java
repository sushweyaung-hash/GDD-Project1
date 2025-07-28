package gdd.sprite;

import static gdd.Global.*;

public class AlienZigZag extends Enemy {

    private int dx = 2; // horizontal speed
    private int dy = 1; // vertical speed

    public AlienZigZag(int x, int y) {
        super(x, y);
        setAlienZigZagImage(x, y);
    }

    private void setAlienZigZagImage(int x, int y) {
        javax.swing.ImageIcon[] icons = {
            new javax.swing.ImageIcon("src/images/alienzigzag.png"),
            new javax.swing.ImageIcon("src/images/alienzigzag2.png")
        };
        java.awt.Image[] animFrames = new java.awt.Image[icons.length];
        for (int i = 0; i < icons.length; i++) {
            animFrames[i] = icons[i].getImage().getScaledInstance(icons[i].getIconWidth() * SCALE_FACTOR,
                    icons[i].getIconHeight() * SCALE_FACTOR,
                    java.awt.Image.SCALE_SMOOTH);
        }
        setFrames(animFrames);
    }

    @Override
    public void act(int unusedDirection) {
        x += dx;
        y += dy;
        animate(); // Advance animation frame
        if (x <= BORDER_LEFT || x >= BOARD_WIDTH - ALIEN_WIDTH - BORDER_RIGHT) {
            dx *= -1;
        }
    }
}
