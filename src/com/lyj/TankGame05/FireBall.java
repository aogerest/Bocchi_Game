package com.lyj.TankGame05;

import java.io.Serializable;

/**
 * @author LYJ
 * @version 1.0
 */
public class FireBall implements Runnable, Serializable {
    int x;
    int y;
    int speed;
    int direction;
    int type;
    private boolean isLiving = true;
    int speedx = 0;
    int speedy = 0;

    //private static BufferedImage imageFireBall_red = null;
    //private static BufferedImage imageFireBall_blue = null;

    FireBall(int x, int y, int speed, int direction, int type) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.direction = direction;
        this.type = type;
        switch (direction) {
            case 0:
                speedy = speed;
                break;
            case 1:
                speedx = speed;

                break;
            case 2:
                speedy = -speed;
                break;
            case 3:
                speedx = -speed;
                break;
            default:
        }


    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isLiving() {
        return isLiving;
    }

    public void setLiving(boolean living) {
        isLiving = living;
    }

    @Override
    public void run() {
        while(isLiving) {
            this.x += speedx;
            this.y += speedy;
            if(!(x>0 && y>0 && x<1000 && y<750)) {
                this.isLiving = false;
                break;
            }
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        //System.out.println("火球进程消失");
    }
}
