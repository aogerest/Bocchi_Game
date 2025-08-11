package com.lyj.TankGame05;

import java.io.Serializable;

/**
 * @author LYJ
 * @version 1.0
 */
public class Tank implements Serializable {
    int x;
    int y;
    int direct;
    int speed;
    int moving = 0;
    int Health = 1;
    boolean living = true;
    //private int speedy = 0;
    //private int speedx = 0;

    public Tank(int x, int y, int speed,  int health, int direct) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.direct = direct;
        this.Health = health;
    }

    public void moveUp() {
        this.y -= this.speed;
    }
    public void moveDown() {
        this.y += this.speed;
    }
    public void moveLeft() {
        this.x -= this.speed;
    }
    public void moveRight() {
        this.x += this.speed;
    }

    public int getHealth() {
        return Health;
    }

    public void setHealth(int health) {
        Health = health;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getMoving() {
        return moving;
    }

    public void setMoving(int moving) {
        this.moving = moving;
    }

    public boolean isLiving() {
        return living;
    }

    public void setLiving(boolean living) {
        this.living = living;
    }
}
