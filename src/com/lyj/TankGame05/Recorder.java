package com.lyj.TankGame05;


import java.io.Serializable;
import java.util.Vector;

/**
 * @author LYJ
 * @version 1.0
 */
public class Recorder implements Serializable {
    private static final long serialVersionUID = 1L; // 序列化版本号
    private int score;
    private boolean flag = false;
    private Bocchi bocchi;
    private Vector<Guitar> guitars = null;
    private Vector<FireBall> fireBalls_enemy = new Vector();
    private Vector<FireBall> fireBalls_bocchi = new Vector();
    public Recorder() {
    }

    public Recorder(int score, Bocchi bocchi, Vector<Guitar> guitars, Vector<FireBall> fireBalls_enemy, Vector<FireBall> fireBalls_bocchi) {
        this.score = score;
        this.bocchi = bocchi;
        this.guitars = guitars;
        this.fireBalls_enemy = fireBalls_enemy;
        this.fireBalls_bocchi = fireBalls_bocchi;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Bocchi getBocchi() {
        return bocchi;
    }

    public void setBocchi(Bocchi bocchi) {
        this.bocchi = bocchi;
    }

    public Vector<FireBall> getFireBalls_bocchi() {
        return fireBalls_bocchi;
    }

    public void setFireBalls_bocchi(Vector<FireBall> fireBalls_bocchi) {
        this.fireBalls_bocchi = fireBalls_bocchi;
    }

    public Vector<Guitar> getGuitars() {
        return guitars;
    }

    public void setGuitars(Vector<Guitar> guitars) {
        this.guitars = guitars;
    }

    public Vector<FireBall> getFireBalls_enemy() {
        return fireBalls_enemy;
    }

    public void setFireBalls_enemy(Vector<FireBall> fireBalls_enemy) {
        this.fireBalls_enemy = fireBalls_enemy;
    }
}
