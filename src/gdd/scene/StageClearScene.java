package gdd.scene;

import static gdd.Global.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import gdd.Game;

public class StageClearScene extends JPanel {

    private final Game game;
    private Timer timer;
    private int frame = 0;

    public StageClearScene(Game game) {
        this.game = game;
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
    }

    public void start() {
        setFocusable(true);
        requestFocusInWindow();
        timer = new Timer(1000 / 60, new GameCycle());
        timer.start();
    }

    public void stop() {
        if (timer != null) timer.stop();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawMessage(g);
    }

    private void drawMessage(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        String msg = "Stage 1 Clear!";
        int textWidth = g.getFontMetrics().stringWidth(msg);
        g.drawString(msg, (BOARD_WIDTH - textWidth) / 2, BOARD_HEIGHT / 2);
    }

    private void update() {
        frame++;
        if (frame >= 180) { // ~3 seconds at 60fps
            stop();
            game.loadScene2();
        }
    }

    private class GameCycle implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            update();
            repaint();
        }
    }
}
