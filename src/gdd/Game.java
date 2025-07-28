package gdd;

import javax.swing.JFrame;

import gdd.scene.Scene1;
import gdd.scene.Scene2;
import gdd.scene.StageClearScene;
import gdd.scene.TitleScene;

public class Game extends JFrame {

    TitleScene titleScene;
    Scene1 scene1;
    Scene2 scene2;

    public Game() {
        titleScene = new TitleScene(this);
        scene1 = new Scene1(this);
        scene2 = new Scene2(this);
        initUI();
        loadTitle();
    }

    private void initUI() {
        setTitle("Space Invaders");
        setSize(Global.BOARD_WIDTH, Global.BOARD_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public void loadTitle() {
        getContentPane().removeAll();
        add(titleScene);
        titleScene.start();
        revalidate();
        repaint();
    }

    public void loadScene1() {
        getContentPane().removeAll();
        add(scene1);
        titleScene.stop();
        scene1.start();
        revalidate();
        repaint();
    }

    public void loadScene2() {
        getContentPane().removeAll();
        add(scene2);
        scene1.stop();
        scene2.start();
        revalidate();
        repaint();
    }

    public void showStageClear() {
        getContentPane().removeAll();
        StageClearScene stageClear = new StageClearScene(this);
        add((java.awt.Component) stageClear);
        revalidate();
        repaint();
    }
}
