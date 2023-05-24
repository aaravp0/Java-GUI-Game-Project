import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

//level2 class
public class Level2 extends JPanel implements MouseListener, KeyListener, MouseMotionListener
{
    private Image barGreen, barYellow, back2, player, med, bulletMove, countdownImage;
    private Timer playerTimer;
    private int greenX, greenY, yellowX, yellowY, im, xPos, yPos, bl, numBullets, bLocX, bLocY, xClick, yClick, countdownInt, greenHealth, health;
    private boolean moveDown, moveUp, moveLeft, moveRight, movingLeft, moving, bulletStop, bulletShow, countdown, greenSpawn, countRestarted, shot;
    private Image[] bullet, countDown;
    private JButton shoot, collect;
    private Rectangle2[] currentBorder;
    private boolean[] bulletBoolean;
    private int[] arr1,arr2,arr3,arr4,arr5,arr6,arr7,arr8,arr9,arr10, blArr, gArrX, gArrY, gGoTo;
    private double mag;

    private final static int BARNEY_X_SPEED = 3;
    private final static int BARNEY_Y_SPEED = 3;

    //add listeners, components, and set values to variables
    public Level2()
    {
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        greenSpawn = false;
        bulletStop = false;
        blArr = new int[10];
        bl = 0;
        im = 0;
        health = 250;
        xPos = yPos = bLocX = bLocY = 400;
        barGreen = new ImageIcon("images/GreenStand.png").getImage();
        barYellow = new ImageIcon("images/BarYellow.png").getImage();
        back2 = new ImageIcon("images/Background2.png").getImage();
        player = new ImageIcon("images/MainStandLeft.png").getImage();
        med = new ImageIcon("images/med.png").getImage();
        bulletMove = new ImageIcon("images/Bullet.png").getImage();
        countdownImage = new ImageIcon("images/Ten.png").getImage();
        bulletShow = false;
        movingLeft = true;
        countdown = true;
        countdownInt = 1000;
        gArrX = new int[5];
        gArrY = new int[5];
        gGoTo = new int[2];
        greenHealth = 500;
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
        countDown = new Image[]
        {
            new ImageIcon("One.png").getImage(),
            new ImageIcon("Two.png").getImage(),
            new ImageIcon("Three.png").getImage(),
            new ImageIcon("Four.png").getImage(),
            new ImageIcon("Five.png").getImage(),
            new ImageIcon("Six.png").getImage(),
            new ImageIcon("Seven.png").getImage(),
            new ImageIcon("Eight.png").getImage(),
            new ImageIcon("Nine.png").getImage(),
            new ImageIcon("Ten.png").getImage()
        };
        bullet = new Image[10];
        bulletBoolean = new boolean[10];
        for(int i = 0; i < 10; i++)
        {
            bullet[i] = new ImageIcon("images/Bullet.png").getImage();
            blArr[i] = 0;
        }
        PlayerMover playerMover = new PlayerMover();
        playerTimer = new Timer(40,playerMover);
        playerTimer.start();
        bulletLocator();
        setBackground(Color.WHITE);
    }
    //paints the images in level 2
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(back2,0,0,800,800,null);
        
        if(bulletBoolean[0])
            g.drawImage(bullet[0],arr1[0],arr1[1],20,10,null);
        if(bulletBoolean[1])
            g.drawImage(bullet[1],arr2[0],arr2[1],20,10,null);
        if(bulletBoolean[2])
            g.drawImage(bullet[2],arr3[0],arr3[1],20,10,null);
        if(bulletBoolean[3])
            g.drawImage(bullet[3],arr4[0],arr4[1],20,10,null);
        if(bulletBoolean[4])
            g.drawImage(bullet[4],arr5[0],arr5[1],20,10,null);
        if(bulletBoolean[5])
            g.drawImage(bullet[5],arr6[0],arr6[1],20,10,null);
        if(bulletBoolean[6])
            g.drawImage(bullet[6],arr7[0],arr7[1],20,10,null);
        if(bulletBoolean[7])
            g.drawImage(bullet[7],arr8[0],arr8[1],20,10,null);
        if(bulletBoolean[8])
            g.drawImage(bullet[8],arr9[0],arr9[1],20,10,null);
        if(bulletBoolean[9])
        {
            g.drawImage(bullet[9],arr10[0],arr10[1],20,10,null);
            bulletStop = true;
        }
        if(bulletShow)
            g.drawImage(bulletMove, bLocX, bLocY, 20,10,null);
        g.drawImage(player, xPos, yPos, 50, 50, null);
        g.setColor(new Color(197, 167, 119));
        g.fillRect(xPos-10,yPos-19,58,20);
        g.setColor(Color.GREEN);
        g.fillRect(xPos-6,yPos-15,(int)(health/5),12);
        if(countdown)
            g.drawImage(countdownImage,350, 50, 100, 100, null);
        if(greenSpawn)
        {
            g.drawImage(barGreen,greenX, greenY,75,125,null);
            g.setColor(new Color(197, 167, 119));
            g.fillRect(greenX-15,greenY-15,108,20);
            g.setColor(Color.RED);
            g.fillRect(greenX-11,greenY-11,(int)(greenHealth/5),12);
        }
    }
    //gives the random locations to the bullets for the first time
    public void bulletLocator()
    {
        arr1 = giveLocation();
        arr2 = giveLocation();
        arr3 = giveLocation();
        arr4 = giveLocation();
        arr4 = giveLocation();
        arr5 = giveLocation();
        arr6 = giveLocation();
        arr7 = giveLocation();
        arr8 = giveLocation();
        arr9 = giveLocation();
        arr10 = giveLocation();
    }
    //the timer class for level 2
    class PlayerMover implements ActionListener
    {
        //what happens every time the timer calls the class
        public void actionPerformed(ActionEvent e)
        {
            if(countdown)
            {
                countdownInt-=4;
                countdownImage = countDown[(int)(countdownInt/100)];
                if(countdownInt == 0)
                    countdown = false;
            }
            if(bulletStop)
            {
                if(bulletBoolean[0])
                    bulletBoolean[0] = collectBullet(arr1[0], arr1[1], 0);
                if(bulletBoolean[1])
                    bulletBoolean[1] = collectBullet(arr2[0], arr2[1], 1);
                if(bulletBoolean[2])
                    bulletBoolean[2] = collectBullet(arr3[0], arr3[1], 2);
                if(bulletBoolean[3])
                    bulletBoolean[3] = collectBullet(arr4[0], arr4[1], 3);
                if(bulletBoolean[4])
                    bulletBoolean[4] = collectBullet(arr5[0], arr5[1], 4);
                if(bulletBoolean[5])
                    bulletBoolean[5] = collectBullet(arr6[0], arr6[1], 5);
                if(bulletBoolean[6])
                    bulletBoolean[6] = collectBullet(arr7[0], arr7[1], 6);
                if(bulletBoolean[7])
                    bulletBoolean[7] = collectBullet(arr8[0], arr8[1], 7);
                if(bulletBoolean[8])
                    bulletBoolean[8] = collectBullet(arr9[0], arr9[1], 8);
                if(bulletBoolean[9])
                    bulletBoolean[9] = collectBullet(arr10[0], arr10[1], 9);
                if(!bulletBoolean[0])
                {
                    blArr[0]+=4;
                    if(blArr[0] == 500)
                    {
                        blArr[0] = 0;
                        arr1 = giveLocation();
                        bulletBoolean[0] = true;
                    }
                }
                if(!bulletBoolean[1])
                {
                    blArr[1]+=4;
                    if(blArr[1] == 500)
                    {
                        blArr[1] = 0;
                        arr2 = giveLocation();
                        bulletBoolean[1] = true;
                    }
                }
                if(!bulletBoolean[2])
                {
                    blArr[2]+=4;
                    if(blArr[2] == 500)
                    {
                        blArr[2] = 0;
                        arr3 = giveLocation();
                        bulletBoolean[2] = true;
                    }
                }
                if(!bulletBoolean[3])
                {
                    blArr[3]+=4;
                    if(blArr[3] == 500)
                    {
                        blArr[3] = 0;
                        arr4 = giveLocation();
                        bulletBoolean[3] = true;
                    }
                }
                if(!bulletBoolean[0])
                {
                    blArr[4]+=4;
                    if(blArr[4] == 500)
                    {
                        blArr[4] = 0;
                        arr5 = giveLocation();
                        bulletBoolean[4] = true;
                    }
                }
                if(!bulletBoolean[5])
                {
                    blArr[5]+=4;
                    if(blArr[5] == 500)
                    {
                        blArr[5] = 0;
                        arr6 = giveLocation();
                        bulletBoolean[5] = true;
                    }
                }
                if(!bulletBoolean[6])
                {
                    blArr[6]+=4;
                    if(blArr[0] == 500)
                    {
                        blArr[6] = 0;
                        arr7 = giveLocation();
                        bulletBoolean[6] = true;
                    }
                }
                if(!bulletBoolean[7])
                {
                    blArr[7]+=4;
                    if(blArr[7] == 500)
                    {
                        blArr[7] = 0;
                        arr8 = giveLocation();
                        bulletBoolean[7] = true;
                    }
                }
                if(!bulletBoolean[8])
                {
                    blArr[8]+=4;
                    if(blArr[8] == 500)
                    {
                        blArr[8] = 0;
                        arr9 = giveLocation();
                        bulletBoolean[8] = true;
                    }
                }
                if(!bulletBoolean[9])
                {
                    blArr[9]+=4;
                    if(blArr[9] == 500)
                    {
                        blArr[9] = 0;
                        arr10 = giveLocation();
                        bulletBoolean[9] = true;
                    }
                }
            }
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
            bl+=4;
            if(bl%300 == 0 && !bulletStop)
            {
                bulletBoolean[bl/300-1] = true;
            }
            if(bulletShow)
            if(bLocX != xClick && yClick != bLocX)
            {
                bulletShow = true;
                if(xClick >= xPos)
                {
                    bLocX += 5*(int)(mag/(xClick - xPos));
                    if((bLocX == xClick && bLocY == yClick)|| bLocX >= 800 || bLocY >= 800 || bLocX <= 0 || bLocY <= 0)
                    {
                        bulletShow = false;
                        bLocX = xPos;
                        bLocY = yPos;
                        xClick = xPos;
                        yClick = yPos;
                    }
                }
                else
                {
                    bLocX -= 5*(int)(mag/(xClick - xPos));
                }
                if(yClick >= yPos)
                {
                    bLocY += 5*(int)(mag/(xClick - xPos));
                    if((bLocX == xClick && bLocY == yClick)|| bLocX >= 800 || bLocY >= 800 || bLocX <= 0 || bLocY <= 0)
                    {
                        bulletShow = false;
                        bLocX = xPos;
                        bLocY = yPos;
                        xClick = xPos;
                        yClick = yPos;
                    }
                }
                else
                {
                    bLocY -= 5*(int)(mag/(xClick - xPos));
                }
            }
            if(greenSpawn)
            {
                if(!countRestarted)
                {
                    countdownInt = 0; 
                    countRestarted = true;
                }
                countdownInt += 4;
                if(countdownInt >= 50)
                {
                    gArrX[4] = gArrX[3];
                    gArrX[3] = gArrX[2];
                    gArrX[2] = gArrX[1];
                    gArrX[1] = gArrX[0];
                    gArrX[0] = (xPos+xPos+50)/2;

                    gArrY[4] = gArrY[3];
                    gArrY[3] = gArrY[2];
                    gArrY[2] = gArrY[1];
                    gArrY[1] = gArrY[0];
                    gArrY[0] = (yPos+yPos+50)/2;

                    if(gArrX[4] != 0)
                        gGoTo[0] = (gArrX[4]+gArrX[0])/2;
                    if(gArrY[4] != 0)
                        gGoTo[1] = (gArrY[4]+gArrY[0])/2;
                    countdownInt = 0;
                }
            }
            else
            {
                countdownInt -= 4;
                if(countdownInt/100-1 <= -1)
                    greenSpawn = true;
                else if(countdownInt%100 == 0 && countdownInt%100-1 < 10 && countdownInt%100-1 >= 0)
                    countdownImage = countDown[countdownInt%100-1];
            }

            greenChecker();
            
            repaint();
            grabFocus();
        }
    }
    //checks the location of the green friend of barney to help it move
    public void greenChecker()
    {
        int subGX = greenX;
        int subGY = greenY;
        if(subGX > gGoTo[0])
        {
            subGX -= BARNEY_X_SPEED;
        }
        else if(subGX < gGoTo[0])
        {
            subGX += BARNEY_X_SPEED;
        }
        if(subGY > gGoTo[1])
        {
            subGY -= BARNEY_Y_SPEED;
        }
        else if(subGY < gGoTo[1])
        {
            subGY += BARNEY_Y_SPEED;
        }
        if(greenX > gGoTo[0])
        {
            greenX -= BARNEY_X_SPEED;
        }
        else if(greenX < gGoTo[0])
        {
            greenX += BARNEY_X_SPEED;
        }
        if(greenY > gGoTo[1])
        {
            greenY -= BARNEY_X_SPEED;
        }
        else if(greenY < gGoTo[1])
        {
            greenY += BARNEY_X_SPEED;
        }
    }
    /*public void bulletChecker()
    {
        if(xClick > xBulletPos)
        {

        }
    }*/
    //checks when you collected a bullet
    public boolean collectBullet(int xB, int yB, int bulletNum)
    {
        if(bulletBoolean[bulletNum] == true)
            numBullets++;
        return !(xB >= xPos && xPos+50 >= xB && yB >= yPos && yPos+50 >= yB);
    }
    //gives the location of an object randomly
    public int[] giveLocation()
    {
        int[]arr = new int[]
        {
            (int)(Math.random()*770),
            (int)(Math.random()*780)
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
    //movement input
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyChar() == 'd' || e.getKeyChar() == 'a' || e.getKeyChar() == 's' || e.getKeyChar() == 'w'
                || e.getKeyCode() == KeyEvent.VK_SHIFT)
        {
            moving = true;
            //started = true;
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
    //not going to be used
    public void keyTyped(KeyEvent e)
    {}
    //resets the movement input
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
    // for shooting bullets
    public void mouseClicked(MouseEvent e)
    {
        if(numBullets >= 1)
        {
            xClick = e.getX();
            yClick = e.getY();
            shot = true;
        }
        //mag = Math.sqrt(Math.pow((xClick - xPos),2) + Math.pow((yClick - yPos),2));
        repaint();
        grabFocus();
    }

    //not going to be used
    public void mousePressed(MouseEvent e)
    {}
    //not going to be used
    public void mouseReleased(MouseEvent e)
    {}
    //not going to be used
    public void mouseEntered(MouseEvent e)
    {}
    //not going to be used
    public void mouseExited(MouseEvent e)
    {}
    //not going to be used
    public void mouseMoved(MouseEvent e)
    {}
    //not going to be used
    public void mouseDragged(MouseEvent e)
    {}
}
