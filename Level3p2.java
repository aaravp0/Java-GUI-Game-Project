import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.*;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.BorderLayout;

//level2 class
public class Level3p2 extends JPanel implements MouseListener, KeyListener, MouseMotionListener
{
    private Manager manager;
    private Image barGreen, back2, player, bulletMove, countdownImage;
    private Image bulletIcon;
    private Timer playerTimer;
    private int greenX, greenY, im, xPos, yPos, bl, numBullets, barneyInt, greenHealth, health, greenInt, numGreenImage, numMed, cooldownAttack, mag, ml;
    private boolean moveDown, moveUp, moveLeft, moveRight, movingLeft, moving, bulletStop, bulletShow, countdown, greenSpawn, countRestarted, shootSelected, greenMoving, greenMovingLeft, medStop,barAttackCool,shootHover,healHover;
    private Image med;
    private JButton shoot, heal;
    private Rectangle3[] currentBorder;
    private boolean[] bulletPresent, medPresent;
    private Point[] bulletLocations, medLocations;
    private int[] blArr, gArrX, gArrY, gGoTo, mArr;
    private Font minecraft;

    // bullet location
    private double bulletX, bulletY;
    private double bulletAngle;

    private final static int BARNEY_X_SPEED = 3;
    private final static int BARNEY_Y_SPEED = 3;
    private final static Dimension GREEN_DIMS = new Dimension(75, 125);

    private final static int NUM_GROUND_BULLETS = 10;
    
    private final static Dimension BULLET_DIMS = new Dimension(20, 10);
    
    private final static double BULLET_SPEED = 20;
    private final static int NUM_INTERMEDIATE = 10;
    
    private final static int NUM_GROUND_MED = 5;
    private final static Dimension MED_DIMS = new Dimension(30,30);

    public Level3p2(Manager manager)
    {
        reset(manager);
    }
    //add listeners, components, and set values to variables
    private void reset(Manager manager)
    {
        minecraft = new Font("Minecraft",Font.BOLD,18);
        greenInt = 1;
        greenMoving = false;
        greenMovingLeft = false;
        cooldownAttack = 500;
        setLayout(new BorderLayout());
        JPanel blank = new JPanel();
        blank.setSize(800,720);
        blank.setOpaque(false);
        add(blank, BorderLayout.CENTER);
        JPanel blank2 = new JPanel();
        shootSelected = false;
        blank2.setSize(800, 80);
        shoot = new JButton("");
        shoot.setPreferredSize(new Dimension(80,80));
        heal = new JButton("");
        shoot.setBorderPainted(false);
        heal.setBorderPainted(false);
        medLocations = new Point[5];
        medPresent = new boolean[5];
        heal.setPreferredSize(new Dimension(80,80));
        numBullets = 0;
        numMed = 0;
        health = 250;
        mArr = new int[5];
        shootHover = true;
        shoot.addMouseListener(new MouseAdapter()
        {
            // when mouse is over the button
            public void mouseEntered(MouseEvent e)
            {
                shootHover = false;
                repaint();
            }

            // when mouse is away from the button
            public void mouseExited(MouseEvent e)
            {
                shootHover = true;
                repaint();
            }
        });
        healHover = true;
        heal.addMouseListener(new java.awt.event.MouseAdapter()
        {
            // when mouse is over the button
            public void mouseEntered(MouseEvent e)
            {
                healHover = false;
                repaint();
                grabFocus();
            }

            // when mouse is away from the button
            public void mouseExited(MouseEvent e)
            {
                healHover = true;
                repaint();
                grabFocus();
            }
        });
        shoot.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(shootSelected)
                {
                    shootSelected = false;
                }
                else
                {
                    shootSelected = true;
                }
            }
        });
        heal.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(numMed >= 1)
                {
                    numMed--;
                    health+=60;
                    if(health >= 250)
                        health = 250;
                    shootSelected = false;
                }
            }
        });
        med = new ImageIcon("images/med.png").getImage();
        //blank2.setLayout(new BorderLayout());
        blank2.add(shoot);
        blank2.add(heal);
        blank2.setOpaque(false);
        add(blank2, BorderLayout.SOUTH);
        this.manager = manager;
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        mag = 0;
        greenSpawn = false;
        bulletStop = false;
        blArr = new int[10];
        bl = 0;
        im = 0;
        
        xPos = yPos = 400;
        barGreen = new ImageIcon("images/BarneyStandRight.png").getImage();
        back2 = new ImageIcon("images/Level3p2Back.png").getImage();
        player = new ImageIcon("images/MainStandLeft.png").getImage();
        bulletMove = new ImageIcon("images/Bullet.png").getImage();
        countdownImage = new ImageIcon("images/Ten.png").getImage();
        bulletShow = false;
        movingLeft = true;
        countdown = true;
        gArrX = new int[5];
        gArrY = new int[5];
        gGoTo = new int[2];
        greenHealth = 500;
        currentBorder = new Rectangle3[]
        {
            new Rectangle3(0,276,91,603),
            new Rectangle3(146,275,586,375),
            new Rectangle3(146,275,473,606),
            new Rectangle3(563,491,727,561),
            new Rectangle3(563,491,624,666),
            new Rectangle3(657,596,707,673),
            new Rectangle3(657,596,719,664),
            new Rectangle3(657,596,728,653),
            new Rectangle3(,,739,642),
        };

        bulletIcon = new ImageIcon("images/Bullet.png").getImage();
        bulletPresent = new boolean[NUM_GROUND_BULLETS];
        bulletLocations = new Point[NUM_GROUND_BULLETS];
        for(int i = 0; i < 10; i++)
        {
            blArr[i] = 0;
        }
        for(int i = 0; i < 5; i++)
        {
            mArr[i] = 0;
        }

        PlayerMover playerMover = new PlayerMover();
        playerTimer = new Timer(40,playerMover);
        bulletLocator();
        setBackground(Color.WHITE);
    }

    //paints the images in level 2
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(back2,0,0,800,800,null);

        for (int i = 0; i < NUM_GROUND_BULLETS; i++)
        {
            if (bulletPresent[i])
            {
                g.drawImage(bulletIcon, bulletLocations[i].x, bulletLocations[i].y, BULLET_DIMS.width, BULLET_DIMS.height, null);
                if (i == NUM_GROUND_BULLETS - 1)
                {
                    bulletStop = true;
                }
            }
        }
        for (int i = 0; i < NUM_GROUND_MED; i++)
        {
            if (medPresent[i])
            {
                g.drawImage(med, medLocations[i].x, medLocations[i].y, MED_DIMS.width, MED_DIMS.height, null);
                if (i == NUM_GROUND_MED - 1)
                {
                    medStop = true;
                }
            }
        }

        if(bulletShow)
        {
            Graphics2D g2d = (Graphics2D) g;
            AffineTransform old = g2d.getTransform();

            int roundedX = (int) Math.round(bulletX);
            int roundedY = (int) Math.round(bulletY);
            g2d.rotate(bulletAngle, roundedX + BULLET_DIMS.width / 2, roundedY + BULLET_DIMS.height / 2);
            g2d.drawImage(bulletMove, roundedX, roundedY, BULLET_DIMS.width, BULLET_DIMS.height,null);
            g2d.setTransform(old);
            updateBullet();
        }

        g.drawImage(player, xPos, yPos, 50, 50, null);
        g.setColor(new Color(197, 167, 119));
        g.fillRect(xPos-10,yPos-19,58,20);
        g.setColor(Color.GREEN);
        g.fillRect(xPos-6,yPos-15,(int)(health/5),12);
        if(countdown)
            g.drawImage(countdownImage,350, 50, 100, 100, null);
        if(greenSpawn)
        {
            /*g.setColor(Color.BLACK);
            g.drawRect(greenX, greenY, GREEN_DIMS.width, GREEN_DIMS.height);*/
            g.drawImage(barGreen,greenX, greenY,GREEN_DIMS.width,GREEN_DIMS.height,null);
            g.setColor(new Color(197, 167, 119));
            g.fillRect(greenX-15,greenY-15,108,20);
            g.setColor(Color.RED);
            g.fillRect(greenX-11,greenY-11,(int)(greenHealth/5),12);
        }
        
        if(healHover)
            g.setColor(new Color(133,0,0));
        else
            g.setColor(new Color(100,0,0));
        g.fillRoundRect(403,685,80,80,20,20);

        if(shootHover)
            g.setColor(new Color(0,0,133));
        else
            g.setColor(new Color(0,0,100));
        g.fillRoundRect(317,685,80,80,20,20);

        if(shootHover)
            g.setColor(Color.BLUE);
        else
            g.setColor(new Color(0,0,200));
        g.fillRoundRect(322,690,70,70,20,20);

        if(healHover)
            g.setColor(Color.RED);
        else
            g.setColor(new Color(200,0,0));
        g.fillRoundRect(408,690,70,70,20,20);

        g.setFont(minecraft);
        if(shootHover)
            g.setColor(Color.WHITE);
        else
            g.setColor(Color.LIGHT_GRAY);
        g.drawString("SHOOT",326,733);
        if(healHover)
            g.setColor(Color.WHITE);
        else
            g.setColor(Color.LIGHT_GRAY);
        g.drawString("HEAL",422,733);
        g.drawString("" + numBullets, 390,768);
        g.drawString("" + numMed, 476,768);
    }
    //gives the random locations to the bullets for the first time
    public void bulletLocator()
    {
        for (int i = 0; i < NUM_GROUND_BULLETS; i++)
        {
            bulletLocations[i] = giveLocation();
        }
        for(int i = 0; i < NUM_GROUND_MED; i++)
        {
            medLocations[i] = giveLocation();
        }
    }
    //has the countdown till the game starts
    private void showSpawnCountdown()
    {
        if (!greenSpawn)
        {
            health = 250;
            barneyInt += 4;
            if (barneyInt == 100)
                countdownImage = new ImageIcon("images/Ten.png").getImage();
            if (barneyInt == 200)
                countdownImage = new ImageIcon("images/Nine.png").getImage();
            if (barneyInt == 300)
                countdownImage = new ImageIcon("images/Eight.png").getImage();
            if (barneyInt == 400)
                countdownImage = new ImageIcon("images/Seven.png").getImage();
            if (barneyInt == 500)
                countdownImage = new ImageIcon("images/Six.png").getImage();
            if (barneyInt == 600)
                countdownImage = new ImageIcon("images/Five.png").getImage();
            if (barneyInt == 700)
                countdownImage = new ImageIcon("images/Four.png").getImage();
            if (barneyInt == 800)
                countdownImage = new ImageIcon("images/Three.png").getImage();
            if (barneyInt == 900)
                countdownImage = new ImageIcon("images/Two.png").getImage();
            if (barneyInt == 1000)
                countdownImage = new ImageIcon("images/One.png").getImage();
            if (barneyInt == 1100)
            {
                countdownImage = null;
                greenSpawn = true;
            }
        }
    }

    //the timer class for level 2
    class PlayerMover implements ActionListener
    {
        //what happens every time the timer calls the class
        public void actionPerformed(ActionEvent e)
        {
            if(!greenSpawn)
            {
                showSpawnCountdown();
            }
            /*if (bulletStop)
            {*/
                for (int i = 0; i < NUM_GROUND_BULLETS; i++)
                {
                    if (bulletPresent[i])
                        bulletPresent[i] = collectBullet(bulletLocations[i].x, bulletLocations[i].y, i);

                    // if its now not present anymore
                    if (!bulletPresent[i])
                    {
                        if(blArr[i] == 0)
                        {
                            mag++;
                        }
                        blArr[i] += 4;
                        if (blArr[i] == 500)
                        {
                            blArr[i] = 0;
                            bulletLocations[i] = giveLocation();
                            bulletPresent[i] = true;
                        }
                    }
                }
            //}
            /*if (medStop)
            {*/
                for (int i = 0; i < NUM_GROUND_MED; i++)
                {
                    if (medPresent[i])
                    {
                        medPresent[i] = collectMed(medLocations[i].x, medLocations[i].y, i);
                    }

                    // if its now not present anymore
                    if (!medPresent[i])
                    {
                        mArr[i] += 4;
                        if (mArr[i] == 500)
                        {
                            mArr[i] = 0;
                            medLocations[i] = giveLocation();
                            medPresent[i] = true;
                        }
                    }
                }
            //}

            int charPosY = yPos;
            int charPosX = xPos;
            if(moveDown)
                charPosY+=5;
            else if(moveUp)
                charPosY-=5;
            if(moveRight)
                charPosX+=5;
            else if(moveLeft)
                charPosX-=5;
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
                    yPos+=5;
                else if(moveUp)
                    yPos-=5;
                if(moveRight)
                    xPos+=5;
                else if(moveLeft)
                    xPos-=5;
            }

            Runner rn = new Runner();
            player = rn.returnImage(im,movingLeft,moving);
            im++;
            if(im >= 8)
                im = 0;
            bl+=4;
            ml+=4;
            if(bl%300 == 0 && !bulletStop)
            {
                bulletPresent[bl/300-1] = true;
            }
            if(ml%300 == 0 && !medStop)
            {
                medPresent[ml/300-1] = true;
            }
            
            if(greenSpawn)
            {
                if(!countRestarted)
                {
                    barneyInt = 0; 
                    countRestarted = true;
                }
                barneyInt += 4;
                if(barneyInt >= 50)
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
                    barneyInt = 0;
                }
            }
            greenMover();
            barneySpriteMover();
            greenAttacker();
            
            healthChecker();
            repaint();
            grabFocus();
        }
    }
    public void greenAttacker()
    {
        if(!barAttackCool)
        {
            Point playerTopRight = new Point(xPos,yPos);
            Point greenTopRight = new Point(greenX,greenY);
            
            Point playerBottomRight = new Point(xPos,yPos);
            Point greenBottomRight = new Point(greenX,greenY);
            playerBottomRight.translate(50, 50);
            greenBottomRight.translate(GREEN_DIMS.width, GREEN_DIMS.height);

            boolean separateX = (playerBottomRight.x < greenTopRight.x)
                    || (greenBottomRight.x < playerTopRight.x);
            boolean separateY = (playerBottomRight.y < greenTopRight.y)
                    || (greenBottomRight.y < playerTopRight.y);

            if (!separateX && !separateY)
            {
                health -= 75;
                barAttackCool = true;
            }
        }
        else
        {
            cooldownAttack -= 4;
            if (cooldownAttack == 0)
            {
                barAttackCool = false;
                cooldownAttack = 500;
            }
        }
    }
    public void healthChecker()
    {
        if(greenHealth <= 0)
        {
            playerTimer.stop();
            manager.showGameOver();
        }
        else if(health <= 0)
        {
            playerTimer.stop();
            manager.showGameOver();
        }
    }
    //moves barney's green friend
    public void greenMover()
    {
        if(greenX > gGoTo[0])
        {
            greenX -= BARNEY_X_SPEED;
            greenMovingLeft = true;
            greenMoving = true;
        }
        else if(greenX < gGoTo[0])
        {
            greenX += BARNEY_X_SPEED;
            greenMovingLeft = false;
            greenMoving = true;
        }
        if(greenY > gGoTo[1])
        {
            greenY -= BARNEY_Y_SPEED;
            greenMoving = true;
        }
        else if(greenY < gGoTo[1])
        {
            greenY += BARNEY_Y_SPEED;
            greenMoving = true;
        }
    }
    //checks when you collected a bullet
    public boolean collectBullet(int xB, int yB, int bulletNum)
    {
        if(xB >= xPos && xPos+50 >= xB && yB >= yPos && yPos+50 >= yB)
            bulletPresent[bulletNum] = false;
        if(bulletPresent[bulletNum] == false)
        {
            numBullets++;
        }
        return !(xB >= xPos && xPos+50 >= xB && yB >= yPos && yPos+50 >= yB);
    }
    public boolean collectMed(int xM, int yM, int medNum)
    {
        if(xM >= xPos && xPos+50 >= xM && yM >= yPos && yPos+50 >= yM)
            medPresent[medNum] = false;
        if(medPresent[medNum] == false)
        {
            numMed++;
        }
        return !(xM >= xPos && xPos+50 >= xM && yM >= yPos && yPos+50 >= yM);
    }
    //gives the location of an object randomly
    public Point giveLocation()
    {
        Point loc = new Point(
            (int)(Math.random()*770),
            (int)(Math.random()*780)
        );

        boolean bulletInBounds = true;
        for (int i = 0; i < currentBorder.length; i++)
        {
            if (currentBorder[i].contains(loc.x, loc.y))
            {
                bulletInBounds = false;
                break;
            }
        }

        if (bulletInBounds)
            return loc;
        else
            return giveLocation();
    }
    public void barneySpriteMover()
    {
        if(greenMoving)
        {
            if(greenMovingLeft)
            {
                barGreen = new ImageIcon("images/BarneyLeft.gif").getImage();
            }
            else
            {
                barGreen = new ImageIcon("images/BarneyRun.gif").getImage();
            }
        }
        else
        {
            if(movingLeft)
                barGreen = new ImageIcon("images/BarneyStandRight.png").getImage();
            else
                barGreen = new ImageIcon("images/BarneyStandLeft.png").getImage();
        }
    }
    public void updateBullet()
    {
        double newBulletX = bulletX +  BULLET_SPEED * Math.cos(bulletAngle);
        double newBulletY = bulletY +  BULLET_SPEED * Math.sin(bulletAngle);

        boolean hitTarget = false;
        Rectangle hitbox = new Rectangle(greenX, greenY, greenX + GREEN_DIMS.width, greenY + GREEN_DIMS.height);
        for (int i = 1; i <= NUM_INTERMEDIATE; i++)
        {
            double ratio = (double) i / (NUM_INTERMEDIATE + 1);
            double fractionalX = ratio * bulletX + (1 - ratio) * newBulletX;
            double fractionalY = ratio * bulletY + (1 - ratio) * newBulletY;
            if (hitbox.contains((int) fractionalX, (int) fractionalY))
            {
                hitTarget = true;
                break;
            }
        }

        if(hitTarget)
            greenHealth -= 50;
        // if we are out of bounds or we hit, then don't show the bullet
        if (newBulletX < 0 || newBulletY < 0 || newBulletX >= 800 || newBulletY >= 800 || hitTarget)
        {
            bulletShow = false;
        }
        else
        {
            bulletX = newBulletX;
            bulletY = newBulletY;
        }
    }
    //movement input
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyChar() == 'd' || e.getKeyChar() == 'a' || e.getKeyChar() == 's' || e.getKeyChar() == 'w'
                || e.getKeyCode() == KeyEvent.VK_SHIFT)
        {
            moving = true;
            playerTimer.start();
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
    public void mousePressed(MouseEvent e)
    {
        // check if we are already shooting a bullet
        if(numBullets >= 1 && !bulletShow && shootSelected)
        {
            double bulletOffsetX = e.getX() - xPos;
            double bulletOffsetY = e.getY() - yPos;
            bulletAngle = Math.atan2(bulletOffsetY, bulletOffsetX);
            bulletX = xPos;
            bulletY = yPos;
            bulletShow = true;
            numBullets--;
        }

        repaint();
        grabFocus();
    }
    //not going to be used
    public void mouseClicked(MouseEvent e)
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