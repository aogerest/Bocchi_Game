package com.lyj.TankGame05;

import java.awt.*;
import java.io.Serializable;

/**
 * @author LYJ
 * @version 1.0
 */
public class Bocchi extends Tank implements  Runnable, Serializable {
    private static  Image image_s = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/png/Bocchi_s.png"));
    private static  Image image_d = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/png/Bocchi_d.png"));
    private static  Image image_w = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/png/Bocchi_w.png"));
    private static  Image image_a = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/png/Bocchi_a.png"));
    public Bocchi(int x, int y, int speed) {
        super(x, y , speed, 3, 0);
    }

    public static Image getImage_d() {
        return image_d;
    }

    public static void setImage_d(Image image_d) {
        Bocchi.image_d = image_d;
    }

    public static Image getImage_s() {
        return image_s;
    }

    public static void setImage_s(Image image_s) {
        Bocchi.image_s = image_s;
    }

    public static Image getImage_w() {
        return image_w;
    }

    public static void setImage_w(Image image_w) {
        Bocchi.image_w = image_w;
    }

    public static Image getImage_a() {
        return image_a;
    }

    public static void setImage_a(Image image_a) {
        Bocchi.image_a = image_a;
    }

    @Override
    public String toString() {
        return "Bocchi{" +
                "x=" + x +
                ", y=" + y +
                ", direct=" + direct +
                ", speed=" + speed +
                ", moving=" + moving +
                ", Health=" + Health +
                ", living=" + living +
                '}';
    }

    @Override
    public void run() {

        while (this.isLiving()) {
            if ( moving != 0) {
                if(this.x + speed*((direct == 1) ? 1 : (direct == 3) ? -1 : 0)>0 && this.y + speed*((direct == 0) ? 1 : (direct == 2) ? -1 : 0)>0 && this.x + speed*((direct == 1) ? 1 : (direct == 3) ? -1 : 0)<940 && this.y + speed*((direct == 0) ? 1 : (direct == 2) ? -1 : 0)<690) {
                    this.x += speed*((direct == 1) ? 1 : (direct == 3) ? -1 : 0);
                    this.y += speed*((direct == 0) ? 1 : (direct == 2) ? -1 : 0);
                    //System.out.println(this.x+","+this.y);
                }
            }
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
