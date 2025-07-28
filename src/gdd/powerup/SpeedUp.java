package gdd.powerup;

import gdd.sprite.Player;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


public class SpeedUp extends PowerUp {

    public SpeedUp(int x, int y) {
        super(x, y);
        loadAnimatedImages();
    }

    private void loadAnimatedImages() {
        javax.swing.ImageIcon[] icons = {
            new javax.swing.ImageIcon("src/images/powerup-s.png"),
            new javax.swing.ImageIcon("src/images/powerup-s2.png")
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
        this.y += 2; // Move down by 2 pixel each frame
    }

    public void upgrade(Player player) {
        // Upgrade the player with speed boost
        player.setSpeed(player.getSpeed() + 1); // Increase player's speed by 1 (was 4)
        this.die(); // Remove the power-up after use
    }

}
