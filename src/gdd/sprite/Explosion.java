package gdd.sprite;

import static gdd.Global.*;

import javax.swing.ImageIcon;

public class Explosion extends Sprite {


    public Explosion(int x, int y) {

        initExplosion(x, y);
        this.visibleFrames = 30;
    }

    private void initExplosion(int x, int y) {

        this.x = x;
        this.y = y;
        javax.swing.ImageIcon[] icons = {
            new ImageIcon("src/images/explosion.png"),
            new ImageIcon("src/images/explosion2.png")
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
    public void act() {
        animate(); // Advance animation frame
    }


}
