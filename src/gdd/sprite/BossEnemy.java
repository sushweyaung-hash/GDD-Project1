package gdd.sprite;

import static gdd.Global.*;

public class BossEnemy extends Sprite {

    private int hp = 100;
    private int dx = 2; // horizontal movement speed

    public BossEnemy(int x, int y) {
        this.x = x;
        this.y = y;
        loadBossAnimation();
    }

    private void loadBossAnimation() {
        javax.swing.ImageIcon[] icons = {
            new javax.swing.ImageIcon("src/images/boss.png"),
            new javax.swing.ImageIcon("src/images/boss2.png")
        };
        java.awt.Image[] animFrames = new java.awt.Image[icons.length];
        for (int i = 0; i < icons.length; i++) {
            animFrames[i] = icons[i].getImage().getScaledInstance(icons[i].getIconWidth() * 2,
                    icons[i].getIconHeight() * 2,
                    java.awt.Image.SCALE_SMOOTH);
        }
        setFrames(animFrames);
    }

    @Override
    public void act() {
        x += dx;
        animate(); // Advance animation frame
        // Bounce back at screen edges
        if (x < BORDER_LEFT || x > BOARD_WIDTH - 200) {
            dx *= -1;
        }
    }

    public void hit() {
        hp -= 1;
        if (hp <= 0) {
            hp = 0;
            die();
        }
    }

    public int getHp() {
        return hp;
    }
}
