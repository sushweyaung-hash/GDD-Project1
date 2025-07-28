package gdd.sprite;

import static gdd.Global.*;

public class Alien1 extends Enemy {

    private Bomb bomb;

    public Alien1(int x, int y) {
        super(x, y);
        setAlienImage(x, y);
    }

    private void setAlienImage(int x, int y) {
        javax.swing.ImageIcon[] icons = {
            new javax.swing.ImageIcon("src/images/alien.png"),
            new javax.swing.ImageIcon("src/images/alien2.png")
        };
        java.awt.Image[] animFrames = new java.awt.Image[icons.length];
        for (int i = 0; i < icons.length; i++) {
            animFrames[i] = icons[i].getImage().getScaledInstance(icons[i].getIconWidth() * SCALE_FACTOR,
                    icons[i].getIconHeight() * SCALE_FACTOR,
                    java.awt.Image.SCALE_SMOOTH);
        }
        setFrames(animFrames);
    }

    public void act(int direction) {
        this.y ++;
        animate(); // Advance animation frame
    }

    @Override
    public Enemy.Bomb getBomb() {
        return (Enemy.Bomb) super.getBomb();
    }

    public static class Bomb extends Enemy.Bomb {
        public Bomb(int x, int y) {
            super(x, y);
        }
        @Override
        public void act() {
            super.act();
        }
    }
}
