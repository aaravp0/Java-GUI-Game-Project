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
class Level2 extends JPanel implements MouseListener, KeyListener, MouseMotionListener
{
    private Image barGreen, barYellow, back2, player;
    private Timer playerTimer;
    private int greenX, greenY, yellowX, yellowY, im;
    private boolean moveDown, moveUp, moveLeft, moveRight, movingLeft, moving, started;
    public Level2()
    {
        im = 1;
        barGreen = new ImageIcon("images/BarGreen.png").getImage();
        barYellow = new ImageIcon("images/BarYellow.png").getImage();
        back2 = new ImageIcon("images/Background2.png").getImage();
        player = new ImageIcon("images/MainStandLeft.png").getImage();
        movingLeft = true;
        PlayerMover playerMover = new PlayerMover();
        playerTimer = new Timer(20,playerMover);
        playerTimer.start();
        setBackground(Color.WHITE);
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(back2,0,0,800,800,null);
        //g.drawImage(barGreen,greenX, greenY,100,150,null);
        //g.drawImage(barYellow,yellowX,yellowY,100,150,null);
        g.drawImage(player, 400, 400, 75, 75, null);
    }
    class PlayerMover implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if (moving)
            {
                im++;
                String ims = "images/";
                if (!movingLeft)
                {
                    if (im >= 8)
                        im = 1;
                    ims += "Right" + im + ".png";
                }
                else
                {
                    if (im >= 8)
                        im = 1;
                    ims += "Run" + im + ".png";
                }
                player = new ImageIcon(ims).getImage();
            }
            else
            {
                if (movingLeft)
                    player = new ImageIcon("images/MainStandLeft2.png").getImage();
                else
                    player = new ImageIcon("images/MainStand2.png").getImage();
            }
            repaint();
            grabFocus();
        }
    }
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyChar() == 'd' || e.getKeyChar() == 'a' || e.getKeyChar() == 's' || e.getKeyChar() == 'w'
                || e.getKeyCode() == KeyEvent.VK_SHIFT)
        {
            playerTimer.start();
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
    {}

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
