package com.lyj.TankGame05;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

/**
 * @author LYJ
 * @version 1.0
 */
public class Mypanel extends JPanel implements KeyListener, Runnable {
    public static int DEFAULT_SPEED = 3;
    public static int DEFAULT_HEALTH = 1;
    public static int DEFAULT_DIRECT = 0;
    public static int BOCCHI_FIREBALL = 0;
    public static int ENEMY_FIREBALL = 1;
    //public static String SAVEPATH = "src\\gameSave.dat";

    Bocchi bocchi = null; //Bocchi
    Vector<Guitar> guitars = new Vector();

    int guitarNum = 6;

    Vector<FireBall> fireBalls_bocchi = new Vector();/////////
    Vector<FireBall> fireBalls_enemy = new Vector();

//    List<Guitar> guitars = new CopyOnWriteArrayList<>(); // 替换 Vector
//    private List<FireBall> fireBalls_bocchi = new CopyOnWriteArrayList<>();
//    private List<FireBall> fireBalls_enemy = new CopyOnWriteArrayList<>();

    BufferedImage imageFireBall_red = null;
    BufferedImage imageFireBall_blue = null;

    Recorder recorder = new Recorder();
    int score = 0;

    public Mypanel() {

        Scanner sc = new Scanner(System.in);
        File file = new File(TankGame01.SAVEPATH);
        if (!file.exists()) {
            System.out.println("没有存档，开始新游戏");
            newGame();
            System.out.println("新游戏");
        } else {
            System.out.print("（1）加载游戏 \n（2）新游戏\n输入选项：");
            int chose = sc.nextInt();
            if (chose == 1) {
                System.out.println("加载游戏");
                loadGame(TankGame01.SAVEPATH);

            } else {
                newGame();
                System.out.println("新游戏");
            }
        }

        new Thread(bocchi).start();
        Guitar.setGuitars(guitars);
        for(Guitar guitar : guitars){
            new Thread(guitar).start();
        }
        for(FireBall fireBall : fireBalls_enemy){
            new Thread(fireBall).start();
        }
        for(FireBall fireBall : fireBalls_bocchi) {
            new Thread(fireBall).start();
        }

        try {
            imageFireBall_red = ImageIO.read(Panel.class.getResourceAsStream("/png/FireBall_red.png"));
            imageFireBall_blue = ImageIO.read(Panel.class.getResourceAsStream("/png/FireBall_blue.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        new Thread(this).start();

    }
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0,0,1000,750);
        paintBocchi(g);
        paintGuitar(g);
        paintFireBall(g);
        showInfo(g);
    }

    public void showInfo(Graphics g) {
        //画出玩家成绩
        g.setColor(Color.BLACK);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);

        g.drawString("当前得分", 1020, 30);
        g.drawString(score + "", 1100, 100);
        drawTank(new Guitar(1020,60,0,0,0), g);


    }

    public void newGame() {
        bocchi = new Bocchi(100,300,5);
        System.out.println(bocchi.toString());
        for(int i = 0; i < guitarNum; i++){
            guitars.add(new Guitar(100 + i * 100,100,  DEFAULT_SPEED, DEFAULT_HEALTH, DEFAULT_DIRECT));/////////
        }
    }


    public void paintBocchi(Graphics g) {
        drawTank(bocchi, g);
    }

    public void paintGuitar(Graphics g) {
        for(Guitar guitar : guitars)
            drawTank(guitar, g);

    }
    public void paintFireBall(Graphics g) {
        for(FireBall fireBall_bocchi : fireBalls_bocchi) {
            drawFireBall(fireBall_bocchi, g);
        }

        for(FireBall fireBall_enemy : fireBalls_enemy) {
            drawFireBall(fireBall_enemy, g);
        }
    }
    public void updateBocchi() {
        if(bocchi.getHealth() <= 0) {
            bocchi.setLiving(false);
        }
    }

    public void updateGuitar() {
        Iterator<Guitar> iterator = guitars.iterator();
        while (iterator.hasNext()) {
            Guitar guitar = iterator.next();
            if (guitar.getHealth() > 0) {
                if (guitar.getAttack() == 1) {
                    //System.out.println("吉他火球");
                    FireBall fireBall = new FireBall(guitar.getX() + 13, guitar.getY() + 20, 5, guitar.getDirect(), ENEMY_FIREBALL);
                    fireBalls_enemy.add(fireBall);
                    new Thread(fireBall).start();
                    guitar.setAttack(0);
                }
            } else {
                score++;
                guitar.setLiving(false);
                iterator.remove(); // ✅ 使用 Iterator 安全删除
            }
        }
    }

    public void updateFireball() {
        Iterator<FireBall> iteratorBocchi = fireBalls_bocchi.iterator();
        while(iteratorBocchi.hasNext()) {
            FireBall fireBall = iteratorBocchi.next();
            if(fireBall.isLiving()) {
                for(Guitar guitar : guitars) {
                    if(isHit(fireBall, guitar)) {
                        guitar.setHealth(guitar.getHealth() - 1);
                        fireBall.setLiving(false);
                    }
                }
            }
            else
                iteratorBocchi.remove();
        }

        Iterator<FireBall> iteratorEnemy = fireBalls_enemy.iterator();
        while(iteratorEnemy.hasNext()) {
            FireBall fireBall_enemy = iteratorEnemy.next();
            if(fireBall_enemy.isLiving()) {
                if(isHit(fireBall_enemy, bocchi)) {
                    bocchi.setHealth(bocchi.getHealth() - 1);
                    fireBall_enemy.setLiving(false);
                }
            } else{
                iteratorEnemy.remove();
            }
        }
    }

    public void drawFireBall(FireBall fireBall, Graphics g) {
        int x = fireBall.getX();
        int y = fireBall.getY();
        BufferedImage img = fireBall.getType() == 0 ? imageFireBall_red : imageFireBall_blue;
        Graphics2D g2d = (Graphics2D) g;
        // 保存原始变换状态
        AffineTransform oldTransform = g2d.getTransform();
        try {
            // 计算旋转中心（绘制位置 + 图像中心）
            int centerX = x + img.getWidth() / 2;
            int centerY = y + img.getHeight() / 2;
            // 应用旋转
            g2d.rotate(Math.toRadians(-90 * fireBall.getDirection()), centerX, centerY);
            g2d.drawImage(img, x, y, null);
        } finally {
            // 恢复原始变换状态
            g2d.setTransform(oldTransform);
        }
    }



    public void drawTank(Tank tank, Graphics g) {
       int x = tank.getX();
       int y = tank.getY();
       int direct = tank.getDirect();
       Tank t;
       if(tank instanceof Bocchi) {
           switch (direct) {
               case 0:
                   g.drawImage(Bocchi.getImage_s(), x, y, 64,64,this);
                   break;
               case 1:
                   g.drawImage(Bocchi.getImage_d(), x, y, 64,64,this);
                   break;
               case 2:
                   g.drawImage(Bocchi.getImage_w(), x, y, 64,64,this);
                   break;
               case 3:
                   g.drawImage(Bocchi.getImage_a(), x, y, 64,64,this);
                   break;
               default:
                   System.out.println("没有处理");
           }

       } else if(tank instanceof Guitar) {
           switch (direct) {
               case 0:
                   g.drawImage(Guitar.getImage_s(), x, y, 64,64,this);
                   break;
               case 1:
                   g.drawImage(Guitar.getImage_d(), x, y, 64,64,this);
                   break;
               case 2:
                   g.drawImage(Guitar.getImage_w(), x, y, 64,64,this);
                   break;
               case 3:
                   g.drawImage(Guitar.getImage_a(), x, y, 64,64,this);
                   break;
               default:
                   System.out.println("没有处理");
           }
       }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_A) {
            bocchi.setMoving(1);
            bocchi.setDirect(3);
        } else if(e.getKeyCode() == KeyEvent.VK_D) {
            bocchi.setMoving(1);
            bocchi.setDirect(1);
        } else if(e.getKeyCode() == KeyEvent.VK_W) {
            bocchi.setMoving(1);
            bocchi.setDirect(2);
        } else if(e.getKeyCode() == KeyEvent.VK_S) {
            bocchi.setMoving(1);
            bocchi.setDirect(0);
        } else if(e.getKeyCode() == KeyEvent.VK_J) {
            FireBall fireBall = new FireBall(bocchi.getX() + 13, bocchi.getY()+ 20, 5, bocchi.getDirect(), BOCCHI_FIREBALL);
            fireBalls_bocchi.add(fireBall);
            Thread fireMove = new Thread(fireBall);
            fireMove.start();

        }
        this.repaint();
    }


    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_W) {
            bocchi.setMoving(0);
        }

        this.repaint();
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            updateGame();
            this.repaint();
        }
    }

    public void updateGame() {
        updateBocchi();
        updateGuitar();
        updateFireball();
    }

    public boolean isHit (FireBall fireBall, Tank tank) {
        int sizeX;
        int sizeY;
        if(tank instanceof Bocchi) {
            sizeX = Bocchi.getImage_s().getWidth(null);
            sizeY = Bocchi.getImage_s().getHeight(null);
        } else {
            sizeX = Guitar.getImage_s().getHeight(null);
            sizeY = Guitar.getImage_s().getHeight(null);
        }
        return fireBall.getX() - tank.getX() < sizeX && fireBall.getY() - tank.getY() < sizeY
                && fireBall.getX() - tank.getX() > 0 && fireBall.getY() - tank.getY() > 0;
    }

    public void saveGame(String filePath) {
        bocchi.setMoving(0);
        recorder.setScore(score);
        recorder.setGuitars(guitars);
        recorder.setBocchi(bocchi);
        recorder.setFireBalls_enemy(fireBalls_enemy);
        recorder.setFireBalls_bocchi(fireBalls_bocchi);
        recorder.setFlag(true);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
                oos.writeObject(recorder);
                System.out.println("保存成功！");
            } catch (IOException e) {
                e.printStackTrace();
        }

    }

    public void loadGame(String filePath) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath));
            recorder = (Recorder) ois.readObject();
            System.out.println(recorder);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (! recorder.isFlag()) {
            System.out.println("存档异常，开启新游戏");
            newGame();
            return;
        }
        bocchi = recorder.getBocchi();
        score = recorder.getScore();
        guitars = recorder.getGuitars();
        fireBalls_enemy = recorder.getFireBalls_enemy();
        fireBalls_bocchi = recorder.getFireBalls_bocchi();
        if(bocchi == null) {
            System.out.println("www");
        }
        System.out.println("加载完成" + bocchi.toString() +"分数 "+ score +
                "guitars " + guitars.size() + "enemy " + fireBalls_enemy.size()
                + "bocchi" + fireBalls_bocchi.size() );
    }
}
