package gdd.scene;

import static gdd.Global.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.Timer;

import gdd.AudioPlayer;
import gdd.Game;
import gdd.SpawnDetails;
import gdd.powerup.MultiShot;
import gdd.powerup.SpeedUp;
import gdd.sprite.Alien1;
import gdd.sprite.AlienZigZag;
import gdd.sprite.BossEnemy;
import gdd.sprite.Enemy;
import gdd.sprite.Explosion;
import gdd.sprite.Player;
import gdd.sprite.Shot;

public class Scene2 extends JPanel {

    private int frame = 0;
    private final Dimension d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    private Timer timer;
    private AudioPlayer audioPlayer;
    private final Game game;
    private Player player;
    private ArrayList<Shot> shots;
    private ArrayList<Explosion> explosions;
    private BossEnemy boss;
    private boolean inGame = true;
    private String message = "You Win!";
    private long startTime;
    private int score = 0; // Add score variable
    private HashMap<Integer, SpawnDetails> spawnMap = new HashMap<>();
    private ArrayList<Alien1> alien1s = new ArrayList<>();
    private ArrayList<AlienZigZag> zigzags = new ArrayList<>();
    private ArrayList<SpeedUp> speedUps = new ArrayList<>();
    private ArrayList<MultiShot> multiShots = new ArrayList<>();
    private ArrayList<Enemy.Bomb> bombs = new ArrayList<>();

    public Scene2(Game game) {
        this.game = game;
    }

    public void start() {
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        loadSpawnDetails();
        player = new Player();
        boss = null;
        shots = new ArrayList<>();
        explosions = new ArrayList<>();
        timer = new Timer(1000 / 60, new GameCycle());
        timer.start();
        startTime = System.currentTimeMillis();
        initAudio();
    }

    public void stop() {
        if (timer != null) {
            timer.stop();
        }
        try {
            if (audioPlayer != null) {
                audioPlayer.stop();
            }
        } catch (Exception e) {
            System.err.println("Error closing audio");
        }
    }

    private void initAudio() {
        try {
            audioPlayer = new AudioPlayer("src/audio/title.wav");
            audioPlayer.play();
        } catch (Exception e) {
            System.err.println("Scene2 audio error: " + e.getMessage());
        }
    }

    private void loadSpawnDetails() {
        spawnMap.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("src/levels/level2.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("frame")) continue; // skip header/empty
                String[] parts = line.split(",");
                if (parts.length < 4) continue;
                int frame = Integer.parseInt(parts[0].trim());
                String type = parts[1].trim();
                int x = Integer.parseInt(parts[2].trim());
                int y = Integer.parseInt(parts[3].trim());
                spawnMap.put(frame, new SpawnDetails(type, x, y));
            }
        } catch (Exception e) {
            System.err.println("Error loading level2 CSV: " + e.getMessage());
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, d.width, d.height);

        if (inGame) {
            drawStars(g);
            drawPlayer(g);
            drawBoss(g);
            drawAliens(g);
            drawPowerUps(g);
            drawBombing(g);
            drawShots(g);
            drawExplosions(g);
            drawHUD(g);
        } else {
            drawGameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawStars(Graphics g) {
        g.setColor(Color.white);
        for (int i = 0; i < 50; i++) {
            int x = (i * 15 + frame) % BOARD_WIDTH;
            int y = (i * 30 + frame * 2) % BOARD_HEIGHT;
            g.fillRect(x, y, 2, 2);
        }
    }

    private void drawPlayer(Graphics g) {
        if (player.isVisible()) {
            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }
    }

    private void drawBoss(Graphics g) {
        if (boss != null && boss.isVisible()) {
            g.drawImage(boss.getImage(), boss.getX(), boss.getY(), this);

            // Draw boss HP bar
            int hp = boss.getHp();
            g.setColor(Color.red);
            g.fillRect(100, 20, 500, 20);
            g.setColor(Color.green);
            g.fillRect(100, 20, hp * 5, 20);
            g.setColor(Color.white);
            g.drawRect(100, 20, 500, 20);
            g.drawString("Boss HP: " + hp, 100, 15);
        }
    }

    private void drawAliens(Graphics g) {
        for (Alien1 a : alien1s) {
            if (a.isVisible()) g.drawImage(a.getImage(), a.getX(), a.getY(), this);
        }
        for (AlienZigZag z : zigzags) {
            if (z.isVisible()) g.drawImage(z.getImage(), z.getX(), z.getY(), this);
        }
    }

    private void drawPowerUps(Graphics g) {
        for (SpeedUp s : speedUps) {
            if (s.isVisible()) g.drawImage(s.getImage(), s.getX(), s.getY(), this);
        }
        for (MultiShot m : multiShots) {
            if (m.isVisible()) g.drawImage(m.getImage(), m.getX(), m.getY(), this);
        }
    }

    private void drawShots(Graphics g) {
        for (Shot s : shots) {
            if (s.isVisible()) {
                g.drawImage(s.getImage(), s.getX(), s.getY(), this);
            }
        }
    }

    private void drawExplosions(Graphics g) {
        for (Explosion e : explosions) {
            if (e.isVisible()) {
                g.drawImage(e.getImage(), e.getX(), e.getY(), this);
                e.visibleCountDown();
            }
        }
    }

    private void drawBombing(Graphics g) {
        ArrayList<Enemy.Bomb> toRemove = new ArrayList<>();
        for (Enemy.Bomb bomb : bombs) {
            if (!bomb.isDestroyed() && bomb.isVisible()) {
                bomb.animate();
                bomb.act();
                g.drawImage(bomb.getImage(), bomb.getX(), bomb.getY(), this);
                if (bomb.getY() > BOARD_HEIGHT) {
                    bomb.setDestroyed(true);
                    toRemove.add(bomb);
                }
            } else {
                toRemove.add(bomb);
            }
        }
        bombs.removeAll(toRemove);
    }

    private void drawHUD(Graphics g) {
        // HUD background
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(580, 0, 140, 100);

        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.setColor(Color.yellow);
        g.drawString("Stage 2", 20, 30);

        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.setColor(Color.white);
        g.drawString("Score: " + score, 590, 15); // Display score

        g.drawString("Speed", 590, 35);
        g.setColor(Color.gray);
        g.fillRect(590, 40, 100, 10);
        g.setColor(Color.green);
        int speed = Math.min(player.getSpeed(), 10);
        g.fillRect(590, 40, speed * 10, 10);

        g.setColor(Color.white);
        g.drawString("Shot", 590, 60);
        g.setColor(Color.gray);
        g.fillRect(590, 65, 100, 10);
        g.setColor(Color.cyan);
        int shotLevel = Math.min(player.getShotLevel(), 4);
        g.fillRect(590, 65, shotLevel * 25, 10);
    }


    private void drawGameOver(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        g.setColor(Color.white);
        g.setFont(g.getFont().deriveFont(32f));
        g.drawString(message, 250, BOARD_HEIGHT / 2);
    }

    private void update() {
        frame++;

        // TODO: Consider using a more robust stage end condition. Currently, stage ends after 5 minutes or boss defeat.
        if (System.currentTimeMillis() - startTime > 300000) { // After 5 minutes
            stop();
            message = "Boss Defeated!";
            inGame = false;
        }

        player.act();

        if (boss != null) {
            boss.act();
            if (boss.getHp() <= 0) {
                boss.die();
                score += 1000; // Bonus for defeating boss
                inGame = false;
            }
        }

        ArrayList<Shot> removeList = new ArrayList<>();
        for (Shot s : shots) {
            s.setY(s.getY() - 10); // TODO: This overrides Shot's own act() movement, which may cause confusion.
            if (s.getY() < 0) {
                s.die();
                removeList.add(s);
                continue;
            }

            // Boss collision (existing)
            if (boss != null && s.collidesWith(boss) && boss.isVisible()) {
                boss.hit();
                score += 100; // Increment score when boss is hit
                s.die();
                explosions.add(new Explosion(boss.getX() + 40, boss.getY() + 40));
                if (boss.getHp() <= 0) {
                    boss.die();
                    score += 1000; // Bonus for defeating boss
                    inGame = false;
                }
                removeList.add(s);
                continue;
            }

            // Alien1 collision
            for (Alien1 a : alien1s) {
                if (a.isVisible() && s.collidesWith(a)) {
                    a.die();
                    explosions.add(new Explosion(a.getX(), a.getY()));
                    s.die();
                    removeList.add(s);
                    score += 100;
                    break;
                }
            }
            // AlienZigZag collision
            for (AlienZigZag z : zigzags) {
                if (z.isVisible() && s.collidesWith(z)) {
                    z.die();
                    explosions.add(new Explosion(z.getX(), z.getY()));
                    s.die();
                    removeList.add(s);
                    score += 150;
                    break;
                }
            }
        }
        shots.removeAll(removeList);

        // Bomb dropping logic for aliens
        for (Alien1 a : alien1s) {
            if (a.isVisible()) {
                Enemy.Bomb bomb = a.getBomb();
                if (bomb.isDestroyed() && Math.random() < 0.005) { // Random drop
                    bomb.setDestroyed(false);
                    bomb.setX(a.getX() + 20);
                    bomb.setY(a.getY() + 40);
                    bombs.add(bomb);
                }
            }
        }
        for (AlienZigZag z : zigzags) {
            if (z.isVisible()) {
                Enemy.Bomb bomb = z.getBomb();
                if (bomb.isDestroyed() && Math.random() < 0.005) { // Random drop
                    bomb.setDestroyed(false);
                    bomb.setX(z.getX() + 20);
                    bomb.setY(z.getY() + 40);
                    bombs.add(bomb);
                }
            }
        }
        // Bomb collision with player
        ArrayList<Enemy.Bomb> bombsToRemove = new ArrayList<>();
        for (Enemy.Bomb bomb : bombs) {
            if (!bomb.isDestroyed() && bomb.isVisible() && bomb.collidesWith(player)) {
                var ii = new javax.swing.ImageIcon(IMG_EXPLOSION);
                player.setImage(ii.getImage());
                player.setDying(true);
                bomb.setDestroyed(true);
                bombsToRemove.add(bomb);
            }
        }
        bombs.removeAll(bombsToRemove);

        // Spawn logic from CSV
        // TODO: If multiple spawns are scheduled for the same frame, only one will be spawned. Consider supporting multiple spawns per frame.
        SpawnDetails sd = spawnMap.get(frame);
        if (sd != null) {
            switch (sd.type) {
                case "Alien1":
                    alien1s.add(new Alien1(sd.x, sd.y));
                    break;
                case "AlienZigZag":
                    zigzags.add(new AlienZigZag(sd.x, sd.y));
                    break;
                case "PowerUp-SpeedUp":
                    speedUps.add(new SpeedUp(sd.x, sd.y));
                    break;
                case "PowerUp-MultiShot":
                    multiShots.add(new MultiShot(sd.x, sd.y));
                    break;
                case "Boss":
                    if (boss == null) boss = new BossEnemy(sd.x, sd.y);
                    break;
            }
        }
        // Update aliens
        for (Alien1 a : alien1s) a.act();
        for (AlienZigZag z : zigzags) z.act(0);
        // Update powerups
        for (SpeedUp s : speedUps) s.act();
        for (MultiShot m : multiShots) m.act();
        // Powerup collision with player
        for (SpeedUp s : speedUps) {
            if (s.isVisible() && s.collidesWith(player)) s.upgrade(player);
        }
        for (MultiShot m : multiShots) {
            if (m.isVisible() && m.collidesWith(player)) m.upgrade(player);
        }
    }

    private void doGameCycle() {
        update();
        repaint();
    }

    private class GameCycle implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            doGameCycle();
        }
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
            if (e.getKeyCode() == KeyEvent.VK_SPACE && inGame && shots.size() < 4) {
                int shotLevel = player.getShotLevel();
                int x = player.getX();
                int y = player.getY();
                if (shotLevel >= 4) {
                    // 3-way shot: straight, left, right
                    shots.add(new Shot(x, y, 0, -10)); // straight
                    shots.add(new Shot(x, y, -5, -9)); // left
                    shots.add(new Shot(x, y, 5, -9)); // right
                } else {
                    shots.add(new Shot(x, y));
                }
            }
        }
    }
}
