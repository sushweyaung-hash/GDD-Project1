package gdd.scene;

import static gdd.Global.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import gdd.AudioPlayer;
import gdd.Game;

public class TitleScene extends JPanel {

    private int frame = 0;
    private Image image;
    private AudioPlayer audioPlayer;
    private final Dimension d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    private Timer timer;
    private Game game;

    public TitleScene(Game game) {
        this.game = game;
        // initBoard();
        // initTitle();
    }

    private void initBoard() {

    }

    public void start() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.black);

        timer = new Timer(1000 / 60, new GameCycle());
        timer.start();

        initTitle();
        initAudio();
    }

    public void stop() {
        try {
            if (timer != null) {
                timer.stop();
            }

            if (audioPlayer != null) {
                audioPlayer.stop();
            }
        } catch (Exception e) {
            System.err.println("Error closing audio player.");
        }
    }

    private void initTitle() {
        var ii = new ImageIcon(IMG_TITLE);
        image = ii.getImage();

    }

    private void initAudio() {
        try {
            String filePath = "src/audio/title.wav";
            audioPlayer = new AudioPlayer(filePath);

            audioPlayer.play();
        } catch (Exception e) {
            System.err.println("Error with playing sound.");
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

        g.drawImage(image, 0, -80, d.width, d.height, this);

        if (frame % 60 < 30) {
            g.setColor(Color.red);
        } else {
            g.setColor(Color.white);
        }

        g.setFont(g.getFont().deriveFont(32f));
        String text = "Press Enter to Start and Space to Stage 2";
        int stringWidth = g.getFontMetrics().stringWidth(text);
        int x = (d.width - stringWidth) / 2;
        g.drawString(text, x, 600);

        // Team and member names
        g.setColor(Color.orange);
        g.setFont(g.getFont().deriveFont(18f));
        String team = "Team: ORANGE";
        int teamWidth = g.getFontMetrics().stringWidth(team);
        g.drawString(team, (d.width - teamWidth) / 2, 640);

        g.setColor(Color.white);
        g.setFont(g.getFont().deriveFont(14f));
        String member1 = "Nan Ei Shwe Sin Hlaing (6530231)";
        String member2 = "Hsu Shwe Yaung (6611665)";
        int m1Width = g.getFontMetrics().stringWidth(member1);
        int m2Width = g.getFontMetrics().stringWidth(member2);
        g.drawString(member1, (d.width - m1Width) / 2, 660);
        g.drawString(member2, (d.width - m2Width) / 2, 680);

        Toolkit.getDefaultToolkit().sync();
    }

    private void update() {
        frame++;
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

        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("Title.keyPressed: " + e.getKeyCode());
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_SPACE) {
                // Load Stage 2 directly for testing
                game.loadScene2();
            } else if (key == KeyEvent.VK_ENTER) {
                // Load Stage 1 (normal flow)
                game.loadScene1();
            }
        }
    }
}
