package gdd.sprite;

import static gdd.Global.*;

import javax.swing.ImageIcon;

public class Enemy extends Sprite {

    private Bomb bomb;

    public Enemy(int x, int y) {
        initEnemy(x, y);
        bomb = new Bomb(x, y);
    }

    private void initEnemy(int x, int y) {
        this.x = x;
        this.y = y;
        var ii = new ImageIcon(IMG_ENEMY);
        var scaledImage = ii.getImage().getScaledInstance(ii.getIconWidth() * SCALE_FACTOR,
                ii.getIconHeight() * SCALE_FACTOR,
                java.awt.Image.SCALE_SMOOTH);
        setImage(scaledImage);
    }

    @Override
    public void act() {
        act(0);
    }

    public void act(int direction) {
        this.x += direction;
    }

    public Bomb getBomb() {
        return bomb;
    }

    public static class Bomb extends Sprite {
        private boolean destroyed;
        private int frame = 0;
        private javax.swing.ImageIcon[] frames;

        public Bomb(int x, int y) {
            initBomb(x, y);
        }

        private void initBomb(int x, int y) {
            setDestroyed(true);
            this.x = x;
            this.y = y;
            // Prepare animation frames (if you have multiple images, add them here)
            frames = new javax.swing.ImageIcon[1];
            frames[0] = new javax.swing.ImageIcon("src/images/bomb.png");
            setImage(frames[0].getImage());
        }

        @Override
        public void act() {
            if (!destroyed) {
                this.y += 2; // Move bomb down
            }
        }

        public void setDestroyed(boolean destroyed) {
            this.destroyed = destroyed;
        }

        public boolean isDestroyed() {
            return destroyed;
        }

        public void animate() {
            // If you have multiple frames, cycle them here
            // Example: frame = (frame + 1) % frames.length;
            // setImage(frames[frame].getImage());
        }
    }
}
