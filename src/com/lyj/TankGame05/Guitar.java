package com.lyj.TankGame05;

import java.awt.*;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author LYJ
 * @version 1.0
 */
public class Guitar extends Tank implements Runnable{
    int attack = 0;
    int timeChangeMoving = 0;
    int timeChangeAttacking = 0;
    static Vector<Guitar> guitars = null;

    private static Image image_s = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/png/Guitar_s.png"));
    private static Image image_d = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/png/Guitar_d.png"));
    private static Image image_w = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/png/Guitar_a.png"));
    private static Image image_a = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/png/Guitar_w.png"));
    public Guitar(int x, int y, int speed, int health, int direct) {
        super(x, y, speed, health, direct);
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    @Override
    public void run() {


        while (this.isLiving()) {
            if(timeChangeMoving-- <= 0){
                this.setDirect((int) (ThreadLocalRandom.current().nextInt(4)));
                this.setMoving((int) (ThreadLocalRandom.current().nextInt(2)));
                timeChangeMoving = (int) (ThreadLocalRandom.current().nextInt(100)) + 50;

            }

            if(timeChangeAttacking-- <= 0){
                attack = (int) (ThreadLocalRandom.current().nextInt(2));
                timeChangeAttacking = (int) (ThreadLocalRandom.current().nextInt(100)) + 50;
            }

            if ( moving != 0) {
                if(!collideWall() && !collideGuitars()) {
                    this.x += speed*((direct == 1) ? 1 : (direct == 3) ? -1 : 0);
                    this.y += speed*((direct == 0) ? 1 : (direct == 2) ? -1 : 0);
                    //System.out.println(this.x+","+this.y);
                }
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        System.out.println("guitar is dead");
    }
//    public boolean runable() {
//        if(this.x + speed*((direct == 1) ? 1 : (direct == 3) ? -1 : 0)<0
//                || this.y + speed*((direct == 0) ? 1 : (direct == 2) ? -1 : 0)<0
//                || this.x + speed*((direct == 1) ? 1 : (direct == 3) ? -1 : 0)>940
//                || this.y + speed*((direct == 0) ? 1 : (direct == 2) ? -1 : 0)>690) {
//            return false;
//        }
//        return true;
//    }
    public boolean collideWall() {
        return this.x + speed*((direct == 1) ? 1 : (direct == 3) ? -1 : 0)<0
                || this.y + speed*((direct == 0) ? 1 : (direct == 2) ? -1 : 0)<0
                || this.x + speed*((direct == 1) ? 1 : (direct == 3) ? -1 : 0)>940
                || this.y + speed*((direct == 0) ? 1 : (direct == 2) ? -1 : 0)>690;
    }
    public boolean collideGuitars() {
        //Iterator<Guitar> iterator = guitars.iterator();
        Iterator<Guitar> iterator = guitars.iterator();
        while (iterator.hasNext()) {
            Guitar guitar = iterator.next();
            if (guitar != this) {
                if(Math.abs(guitar.x - this.x - speed*((direct == 1) ? 1 : (direct == 3) ? -1 : 0)) < image_s.getWidth(null)
                        && Math.abs(guitar.y - this.y - speed*((direct == 0) ? 1 : (direct == 2) ? -1 : 0)) < image_s.getHeight(null)) {
                   return true;
                }
            }
        }
        return false;
    }

    public static Vector<Guitar> getGuitars() {
        return guitars;
    }

    public static void setGuitars(Vector<Guitar> guitars) {
        Guitar.guitars = guitars;
    }

    public static Image getImage_s() {
        return image_s;
    }

    public static void setImage_s(Image image_s) {
        Guitar.image_s = image_s;
    }

    public static Image getImage_d() {
        return image_d;
    }

    public static void setImage_d(Image image_d) {
        Guitar.image_d = image_d;
    }

    public static Image getImage_w() {
        return image_w;
    }

    public static void setImage_w(Image image_w) {
        Guitar.image_w = image_w;
    }

    public static Image getImage_a() {
        return image_a;
    }

    public static void setImage_a(Image image_a) {
        Guitar.image_a = image_a;
    }
}
