package com.lyj.TankGame05;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author LYJ
 * @version 1.0
 */
public class TankGame01 extends JFrame {
    Mypanel mypanel = null;
    public static String SAVEPATH = "src\\gameSave.dat";
    public static void main(String[] args) {
        new TankGame01();
    }

    public TankGame01() {
        mypanel = new Mypanel();
        this.add(mypanel);
        this.addKeyListener(mypanel);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.out.println("游戏关闭");
                mypanel.saveGame(SAVEPATH);
                System.out.println("游戏保存成功");
                System.exit(0);
            }
        });
        this.setSize(1300,750);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
