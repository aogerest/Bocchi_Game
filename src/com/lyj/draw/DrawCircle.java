package com.lyj.draw;

import javax.swing.*;
import java.awt.*;

/**
 * @author LYJ
 * @version 1.0
 */
public class DrawCircle extends JFrame{
    private MyPanel mp = null;
    public static void main(String[] args) {
        Runtime rt = Runtime.getRuntime();
        System.out.println(rt.availableProcessors());
        new DrawCircle();
    }
    public DrawCircle(){
        mp = new MyPanel();
        add(mp);
        this.setTitle("DrawCircle");
        this.setSize(400,400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}

class MyPanel extends JPanel {
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawOval(30,20,100,200);

        Image image = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/Bocchi.png"));
        g.drawImage(image,0,0,128,128,this);
        //        Image image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/working.ani"));
        //        g.drawImage(image1,0,0,128,128,this);





    }

}