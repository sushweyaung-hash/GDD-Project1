package gdd.powerup;

import gdd.sprite.Player;

public class MultiShot extends PowerUp {

    public MultiShot(int x, int y) {
        super(x, y);
        loadAnimatedImages();
    }

    private void loadAnimatedImages() {
        javax.swing.ImageIcon[] icons = {
            new javax.swing.ImageIcon("src/images/powerup-m.png"),
            new javax.swing.ImageIcon("src/images/powerup-m2.png")
        };
        java.awt.Image[] animFrames = new java.awt.Image[icons.length];
        for (int i = 0; i < icons.length; i++) {
            animFrames[i] = icons[i].getImage().getScaledInstance(icons[i].getIconWidth(),
                    icons[i].getIconHeight(),
                    java.awt.Image.SCALE_SMOOTH);
        }
        setFrames(animFrames);
    }

    @Override
    public void act() {
        animate(); // Advance animation frame
        this.y += 2;
    }

    @Override
    public void upgrade(Player player) {
        int currentLevel = player.getShotLevel();
        if (currentLevel < 4) {
            player.setShotLevel(currentLevel + 1);
        }
        this.die();
    }
}
