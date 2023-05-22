import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.awt.Font;
import java.awt.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.Arrays;
import javax.swing.*;

//level2 class
public class Level2 extends JPanel implements MouseListener, KeyListener, MouseMotionListener
{
    private Image barGreen, barYellow, back2, player, med;
    private Timer playerTimer;
    private int greenX, greenY, yellowX, yellowY, im, xPos, yPos, bulletCounter, bulletsSpawned;
    private boolean moveDown, moveUp, moveLeft, moveRight, movingLeft, moving, started, bulletFallCool, tenBullets;
    private Image[]runningLeft, runningRight, bullet;
    private JButton shoot, collect;
    private Rectangle2[] currentBorder;
    private boolean[] bulletBoolean;
    private int[] arr1,arr2,arr3,arr4,arr5,arr6,arr7,arr8,arr9,arr10;
    public Level2()
    {
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        bulletFallCool = true;
        im = 0;
        xPos = yPos = 400;
        barGreen = new ImageIcon("images/BarGreen.png").getImage();
        barYellow = new ImageIcon("images/BarYellow.png").getImage();
        back2 = new ImageIcon("images/Background2.png").getImage();
        player = new ImageIcon("images/MainStandLeft.png").getImage();
        med = new ImageIcon("images/med.png").getImage();
        movingLeft = true;
        bulletsSpawned = 0;
        currentBorder = new Rectangle2[]
        {
            new Rectangle2(0,700,69,776),
            new Rectangle2(43,590,69,776),
            new Rectangle2(43,590,177,669),
            new Rectangle2(147,537,230,610),
            new Rectangle2(198,491,231,610),
            new Rectangle2(198,491,285,558),
            new Rectangle2(261,429,285,558),
            new Rectangle2(261,429,339,501),
            new Rectangle2(314,369,341,501),
            new Rectangle2(314,369,395,448),
            new Rectangle2(368,316,395,444),
            new Rectangle2(368,316,447,391),
            new Rectangle2(447,309,476,445),
            new Rectangle2(473,361,507,445),
            new Rectangle2(0,0,1,1215),
            new Rectangle2(0,0,1215,1),
            new Rectangle2(1214,0,1215,1215),
            new Rectangle2(0,1214,1215,1215),
            new Rectangle2(602,375,709,446),
            new Rectangle2(631,320,679,375),
            new Rectangle2(710,370,732,606),
            new Rectangle2(732,531,763,606),
            new Rectangle2(764,533,794,660),
            new Rectangle2(794,588,957,660),
            new Rectangle2(955,543,980,656),
            new Rectangle2(980,540,1086,608),
            new Rectangle2(1063,300,1088,611),
            new Rectangle2(1037,299,1088,322),
            new Rectangle2(1016,251,1037,322),
            new Rectangle2(958,106,986,264),
            new Rectangle2(906,155,958,264),
            new Rectangle2(965,67,1037,168),
            new Rectangle2(1014,0,1035,168)
        };
        bullet = new Image[10];
        bulletBoolean = new boolean[10];
        for(int i = 0; i < 10; i++)
        {
            bullet[i] = new ImageIcon("images/Bullet.png").getImage();
            bulletBoolean[i] = false;
        }
        PlayerMover playerMover = new PlayerMover();
        playerTimer = new Timer(40,playerMover);
        playerTimer.start();
        bulletLocator();
        setBackground(Color.WHITE);
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(back2,0,0,800,800,null);
        //g.drawImage(barGreen,greenX, greenY,100,150,null);
        //g.drawImage(barYellow,yellowX,yellowY,100,150,null);
        g.drawImage(player, xPos, yPos, 50, 50, null);
        if(bulletBoolean[0])
        {
            bulletLocator();
            g.drawImage(bullet[0],arr1[0],arr1[1],20,10,null);
            bulletBoolean[0] = false;
        }
        if(bulletBoolean[1])
        {
            g.drawImage(bullet[1],arr2[0],arr2[1],20,10,null);
            bulletBoolean[0] = false;
        }
        if(bulletBoolean[2])
        {
            g.drawImage(bullet[2],arr3[0],arr3[1],20,10,null);
            bulletBoolean[0] = false;
        }
        if(bulletBoolean[3])
        {
            g.drawImage(bullet[3],arr4[0],arr4[1],20,10,null);
            bulletBoolean[0] = false;
        }
        if(bulletBoolean[4])
        {
            g.drawImage(bullet[4],arr5[0],arr5[1],20,10,null);
            bulletBoolean[0] = false;
        }
        if(bulletBoolean[5])
        {
            g.drawImage(bullet[5],arr6[0],arr6[1],20,10,null);
            bulletBoolean[0] = false;
        }
        if(bulletBoolean[6])
        {
            g.drawImage(bullet[6],arr7[0],arr7[1],20,10,null);
            bulletBoolean[0] = false;
        }
        if(bulletBoolean[7])
        {
            g.drawImage(bullet[7],arr8[0],arr8[1],20,10,null);
            bulletBoolean[0] = false;
        }
        if(bulletBoolean[8])
        {
            g.drawImage(bullet[8],arr9[0],arr9[1],20,10,null);
            bulletBoolean[0] = false;
        }
        if(bulletBoolean[9])
        {
            g.drawImage(bullet[9],arr10[0],arr10[1],20,10,null);
            bulletBoolean[0] = false;
        }
    }
    public void bulletLocator()
    {
        if(bulletBoolean[0])
            arr1 = giveLocation();
        if(bulletBoolean[1])
            arr2 = giveLocation();
        if(bulletBoolean[2])
            arr4 = giveLocation();
        if(bulletBoolean[3])
            arr4 = giveLocation();
        if(bulletBoolean[4])
            arr5 = giveLocation();
        if(bulletBoolean[5])
            arr6 = giveLocation();
        if(bulletBoolean[6])
            arr7 = giveLocation();
        if(bulletBoolean[7])
            arr8 = giveLocation();
        if(bulletBoolean[8])
            arr9 = giveLocation();
        if(bulletBoolean[9])
            arr10 = giveLocation();
    }
    class PlayerMover implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            int charPosY = yPos;
            int charPosX = xPos;
            if(moveDown)
            {
                charPosY+=5;
            }
            else if(moveUp)
            {
                charPosY-=5;
            }
            if(moveRight)
            {
                charPosX+=5;
            }
            else if(moveLeft)
            {
                charPosX-=5;
            }
            boolean canMove = true;
            for (int i = 0; i < currentBorder.length; i++)
            {
                if (currentBorder[i].contains(charPosX, charPosY))
                {
                    canMove = false;
                    break;
                }
            }
            if(canMove)
            {
                if(moveDown)
                {
                    yPos+=5;
                }
                else if(moveUp)
                {
                    yPos-=5;
                }
                if(moveRight)
                {
                    xPos+=5;
                }
                else if(moveLeft)
                {
                    xPos-=5;
                }
            }
            Runner rn = new Runner();
            player = rn.returnImage(im,movingLeft,moving);
            im++;
            if(im >= 8)
                im = 0;
            repaint();
            grabFocus();
        }
    }
    public int[] giveLocation()
    {
        int[]arr = new int[]
        {
            (int)(Math.random()*800),
            (int)(Math.random()*800)
        };
        boolean bulletInBounds = true;
        for (int i = 0; i < currentBorder.length; i++)
        {
            if (currentBorder[i].contains(arr[0],arr[1]))
            {
                bulletInBounds = false;
                break;
            }
        }
        if(bulletInBounds)
            return arr;
        else
            return giveLocation();
    }
    public boolean ammoCounter(Image[]bullets)
    {
        boolean tnBl = true;
        for(int i = 0; i < 10; i++)
        {
            if(bullets[i] == null)
            {
                tnBl = false;
                break;
            }
        }
        return tnBl;
    }
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyChar() == 'd' || e.getKeyChar() == 'a' || e.getKeyChar() == 's' || e.getKeyChar() == 'w'
                || e.getKeyCode() == KeyEvent.VK_SHIFT)
        {
            moving = true;
            started = true;
        }
        else
        {
            moving = false;
        }
        if (e.getKeyChar() == 'd')
        {
            moveRight = true;
            moveLeft = false;
            movingLeft = false;
        }
        if (e.getKeyChar() == 'a')
        {
            moveLeft = true;
            moveRight = false;
            movingLeft = true;
        }
        if (e.getKeyChar() == 'w')
        {
            moveUp = true;
            moveDown = false;
        }
        if (e.getKeyChar() == 's')
        {
            moveDown = true;
            moveUp = false;
        }
        repaint();
        grabFocus();
    }
    
    public void keyTyped(KeyEvent e)
    {}

    public void keyReleased(KeyEvent e)
    {
        moveRight = false;
        moveLeft = false;
        moveUp = false;
        moveDown = false;
        moving = false;
        repaint();
        grabFocus();
    }

    public void mouseClicked(MouseEvent e)
    {}

    public void mousePressed(MouseEvent e)
    {}

    public void mouseReleased(MouseEvent e)
    {}

    public void mouseEntered(MouseEvent e)
    {}

    public void mouseExited(MouseEvent e)
    {}

    public void mouseMoved(MouseEvent e)
    {}

    public void mouseDragged(MouseEvent e)
    {}
}
