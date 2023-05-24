import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayDeque;
import java.util.Queue;
import java.awt.Font;
import java.awt.Point;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

//class for level 1
public class Level1 extends JPanel implements KeyListener, MouseListener
{
    private final static int SCREEN_WIDTH = 200;
    private final static int SCREEN_HEIGHT = 300;
    private final static int START_X = 140;
    private final static int START_Y = 1370;

    private final static int BACKGROUND_WIDTH = 1628;
    private final static int BACKGROUND_HEIGHT = 1620;

    private final static int UPDATE_DELAY = 40;

    private final static Dimension PLAYER_DIMS = new Dimension(75, 75);
    private final static Dimension BARNEY_DIMS = new Dimension(100, 150);
    private final static Dimension GAS_DIMS = new Dimension(50, 50);
    private final static Dimension MEDKIT_DIMS = new Dimension(60, 60);
    private final static Dimension APPLE_DIMS = new Dimension(50, 50);
    private final static Dimension COOKIE_DIMS = new Dimension(50, 50);

    private final static int PLAYER_X_SPEED = 3;
    private final static int PLAYER_Y_SPEED = 3;
    private final static int PLAYER_X_SPEED_SPRINT = 4;
    private final static int PLAYER_Y_SPEED_SPRINT = 4;
    private final static int BARNEY_X_SPEED = 1;
    private final static int BARNEY_Y_SPEED = 1;

    private final static int NUM_POSITIONS_KEPT = 5000 / UPDATE_DELAY;

    private final static int TOTAL_GAS = 3;
    private final static int TOTAL_MEDKIT = 5;
    private final static int TOTAL_APPLES = 5;
    private final static int TOTAL_COOKIES = 2;

    private final static int GUN_SHOT_DELAY = 10000;

    private Manager manager;

    private Image run1, back, barneyBlood, number, gas, med, gun, apple, cookie, stunIcon, bloodHead;
    private Timer playerTimer;
    private int im, health, barneyInt,
            sprintInt, stamina, shotCooldownLeft, stunTime, cookieTime, index,
            noMoveTime, noMoveTime2, barCooldown;
    private boolean moving, movingLeft, moveLeft, moveRight, moveUp, moveDown, barneySpawn, shiftSprint, started,
            selected1, selected2, selected3, selected4, selected5, bulletCooldown, stun, cookiesActivated,
            songStarted, playerDamageOnMove, barAttackCool, damage;
    private PlayerMover playerTime;
    private JButton inv1, inv2, inv3, inv4, inv5;
    private String beginSentence, showingSentence, showingSentence2, showingSentence3,
            showingSentence4;
    private Font minecraft;
    private Rectangle[] currentBorder;
    private Clip clip2;

    private CollectibleSet gasItems, medkits, apples, cookies;

    private Queue<Point> previousPlayerPosition;

    private Point playerPosition, barneyPosition;

    private Point topLeft;

    // declares all of the variables and timers
    public Level1(Manager manager)
    {
        this.manager = manager;
        setLayout(new BorderLayout());
        songStarted = false;
        noMoveTime = 3000;
        noMoveTime2 = 300;
        playerDamageOnMove = false;
        minecraft = new Font("Minecraft", Font.BOLD, 16);
        currentBorder = new Rectangle[]
        {
                new Rectangle(132, 127, 466, 165),
                new Rectangle(132, 208, 466, 320),
                new Rectangle(132, 363, 466, 486),
                new Rectangle(132, 530, 466, 647),
                new Rectangle(132, 691, 466, 808),
                new Rectangle(132, 850, 466, 903),
                new Rectangle(132, 1000, 466, 1058),
                new Rectangle(889, 115, 894, 494),
                new Rectangle(895, 453, 1003, 494),
                new Rectangle(1055, 453, 1555, 494),
                new Rectangle(755, 100, 818, 193),
                new Rectangle(755, 260, 818, 372),
                new Rectangle(820, 584, 1354, 654),
                new Rectangle(1354, 554, 1493, 654),
                new Rectangle(819, 762, 1472, 853),
                new Rectangle(827, 984, 1504, 1068),
                new Rectangle(820, 1132, 934, 1580),
                new Rectangle(976, 1132, 1084, 1580),
                new Rectangle(1128, 1132, 1237, 1580),
                new Rectangle(1278, 1132, 1384, 1580),
                new Rectangle(1425, 1132, 1504, 1580),
                new Rectangle(0, 0, 1628, 123),
                new Rectangle(1505, 0, 1628, 1614),
                new Rectangle(0, 1501, 1628, 1614),
                new Rectangle(0, 0, 126, 1616)
        };

        cookiesActivated = false;
        cookieTime = 1000;
        index = -1;
        beginSentence = "Oh no! My car ran out of gas. This city seems to be abandoned. Is that Barney? I need to get 3 gas cans to fuel up my car and escape.";
        showingSentence = "";
        showingSentence2 = "";
        showingSentence3 = "";
        showingSentence4 = "";
        shotCooldownLeft = GUN_SHOT_DELAY;
        barCooldown = 500;
        barAttackCool = false;
        bulletCooldown = false;
        sprintInt = 125;
        shiftSprint = false;
        addKeyListener(this);
        addMouseListener(this);
        movingLeft = false;
        im = 0;
        health = 250;

        damage = true;
        stamina = 250;
        playerTime = new PlayerMover();
        playerTimer = new Timer(UPDATE_DELAY, playerTime);
        this.requestFocus();
        run1 = new ImageIcon("images/MainStand.png").getImage();
        back = new ImageIcon("images/Level1Back.png").getImage();
        barneyBlood = new ImageIcon("images/BarneyBlood.png").getImage();
        stunIcon = new ImageIcon("images/stun.gif").getImage();
        number = new ImageIcon("images/Ten.png").getImage();
        setBackground(Color.BLACK);
        gas = new ImageIcon("images/Gas.png").getImage();
        gun = new ImageIcon("images/Gun.png").getImage();
        med = new ImageIcon("images/med.png").getImage();
        apple = new ImageIcon("images/Apple.png").getImage();
        cookie = new ImageIcon("images/Cookie.png").getImage();
        started = false;
        selected1 = false;
        selected2 = false;
        selected3 = false;
        selected4 = false;
        selected5 = false;
        inv1 = new JButton("");
        inv1.setBounds(30, 690, 60, 60);
        inv1.setBorderPainted(false);
        // action listener for the first inventory slot
        inv1.addActionListener(new ActionListener()
        {
            // every time the button is clicked
            public void actionPerformed(ActionEvent e)
            {
                if (!selected1)
                {
                    selected1 = true;
                    selected2 = false;
                    selected3 = false;
                    selected4 = false;
                    selected5 = false;
                }
                else
                    selected1 = false;
            }
        });
        inv2 = new JButton("");
        inv2.setBounds(190, 690, 60, 60);
        inv2.setBorderPainted(false);
        // action listener for the second inventory slot
        inv2.addActionListener(new ActionListener()
        {
            // every time the button is clicked
            public void actionPerformed(ActionEvent e)
            {
                if (!selected2)
                {
                    selected1 = false;
                    selected2 = true;
                    selected3 = false;
                    selected4 = false;
                    selected5 = false;
                }
                else
                    selected2 = false;
            }
        });
        inv3 = new JButton("");
        inv3.setBounds(350, 690, 60, 60);
        inv3.setBorderPainted(false);
        // action listener for the third inventory slot
        inv3.addActionListener(new ActionListener()
        {
            // every time the button is clicked
            public void actionPerformed(ActionEvent e)
            {
                if (!selected3)
                {
                    selected1 = false;
                    selected2 = false;
                    selected3 = true;
                    selected4 = false;
                    selected5 = false;
                }
                else
                    selected3 = false;
            }
        });
        inv4 = new JButton("");
        inv4.setBounds(510, 690, 60, 60);
        inv4.setBorderPainted(false);
        // action listener for the fourth inventory slot
        inv4.addActionListener(new ActionListener()
        {
            // every time the button is clicked
            public void actionPerformed(ActionEvent e)
            {
                if (!selected4)
                {
                    selected1 = false;
                    selected2 = false;
                    selected3 = false;
                    selected4 = true;
                    selected5 = false;
                }
                else
                    selected4 = false;
            }
        });
        inv5 = new JButton("");
        inv5.setBounds(670, 690, 60, 60);
        inv5.setBorderPainted(false);
        // action listener for the fifth inventory slot
        inv5.addActionListener(new ActionListener()
        {
            // every time the button is clicked
            public void actionPerformed(ActionEvent e)
            {
                if (!selected5)
                {
                    selected1 = false;
                    selected2 = false;
                    selected3 = false;
                    selected4 = false;
                    selected5 = true;
                }
                else
                    selected5 = false;
            }
        });
        JPanel blank = new JPanel();
        blank.setOpaque(false);
        blank.setSize(800, 700);
        JPanel blank2 = new JPanel();
        blank2.setOpaque(false);
        bloodHead = new ImageIcon("images/BloodHead.png").getImage();
        add(blank, BorderLayout.CENTER);
        add(inv1, BorderLayout.SOUTH);
        add(inv2, BorderLayout.SOUTH);
        add(inv3, BorderLayout.SOUTH);
        add(inv4, BorderLayout.SOUTH);
        add(inv5, BorderLayout.SOUTH);
        add(blank2, BorderLayout.SOUTH);

        playerPosition = new Point(START_X, START_Y);
        barneyPosition = new Point(START_X, START_Y);
        topLeft = getScreenTopLeft();

        gasItems = new CollectibleSet(gas, generateRandomLocations(TOTAL_GAS), GAS_DIMS);
        medkits = new CollectibleSet(med, generateRandomLocations(TOTAL_MEDKIT), MEDKIT_DIMS);
        apples = new CollectibleSet(apple, generateRandomLocations(TOTAL_APPLES), APPLE_DIMS);
        cookies = new CollectibleSet(cookie, generateRandomLocations(TOTAL_COOKIES), COOKIE_DIMS);

        previousPlayerPosition = new ArrayDeque<Point>();
    }

    // generates random locations for the items
    private Point[] generateRandomLocations(int locationCount)
    {
        Point[] locations = new Point[locationCount];
        for (int i = 0; i < locationCount; i++)
        {
            locations[i] = itemCoordinateMaker();
        }
        return locations;
    }

    // gets the top left of the screen in terms of the map
    private Point getScreenTopLeft()
    {
        Point result = new Point();
        Point currentPosition = playerPosition;

        if (currentPosition.x - SCREEN_WIDTH / 2 < 0)
        {
            result.x = SCREEN_WIDTH / 2;
        }
        else if (currentPosition.x + SCREEN_WIDTH / 2 > BACKGROUND_WIDTH)
        {
            result.x = BACKGROUND_WIDTH - SCREEN_WIDTH;
        }
        else
        {
            result.x = currentPosition.x - SCREEN_WIDTH / 2;
        }

        if (currentPosition.y - SCREEN_HEIGHT / 2 < 0)
        {
            result.y = SCREEN_HEIGHT / 2;
        }
        else if (currentPosition.y + SCREEN_HEIGHT / 2 > BACKGROUND_HEIGHT)
        {
            result.y = BACKGROUND_HEIGHT - SCREEN_HEIGHT;
        }
        else
        {
            result.y = currentPosition.y - SCREEN_HEIGHT / 2;
        }

        return result;
    }

    // finds the position of a point on the screen
    private Point convertPosition(Point position)
    {
        int offsetX = (position.x - topLeft.x) * NightOfBarney.FRAME_WIDTH / SCREEN_WIDTH;
        int offsetY = (position.y - topLeft.y) * NightOfBarney.FRAME_HEIGHT / SCREEN_HEIGHT;
        return new Point(offsetX, offsetY);
    }

    // returns the position where the object was drawn
    private Point drawOnScreen(Graphics g, Image image, Point position, Dimension dimension)
    {
        Point screenPosition = convertPosition(position);
        g.drawImage(image, screenPosition.x, screenPosition.y, dimension.width, dimension.height, null);
        // g.drawRect(screenPosition.x, screenPosition.y, dimension.width,
        // dimension.height);
        return screenPosition;
    }

    // draws everything on the screen with the offset
    private Point drawWithOffset(Graphics g, Point original, Image image, Point offset, Dimension dimension)
    {
        Point screenPosition = new Point(original.x + offset.x, original.y + offset.y);
        g.drawImage(image, screenPosition.x, screenPosition.y, dimension.width, dimension.height, null);
        return screenPosition;
    }

    // paints the sprites in the level
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(back, 0, 0, NightOfBarney.FRAME_WIDTH, NightOfBarney.FRAME_HEIGHT,
                topLeft.x, topLeft.y, topLeft.x + SCREEN_WIDTH, topLeft.y + SCREEN_HEIGHT, null, null);

        if (!songStarted)
        {
            try
            {
                String soundName2 = "sounds/BarneyLevelTheme.wav";
                AudioInputStream audioInputStream2 = AudioSystem
                        .getAudioInputStream(new File(soundName2).getAbsoluteFile());
                clip2 = AudioSystem.getClip();
                clip2.open(audioInputStream2);
                clip2.start();
            } catch (Exception i)
            {
                System.err.println("Unable to load song for level1");
                System.exit(1);
            }
            songStarted = true;
        }

        if (!started)
            playerTimer.start();

        if (playerDamageOnMove)
        {
            // g.setColor(Color.RED);
            // g.fillRect(0, 0, NightOfBarney.FRAME_WIDTH, NightOfBarney.FRAME_HEIGHT);
            g.drawImage(bloodHead, 200, 200, 400, 400, null);
        }

        gasItems.drawAll(g);
        medkits.drawAll(g);
        apples.drawAll(g);
        cookies.drawAll(g);

        drawOnScreen(g, run1, playerPosition, PLAYER_DIMS);

        // barney
        if (barneySpawn)
        {
            Point barneyOnscreen = drawOnScreen(g, barneyBlood, barneyPosition, BARNEY_DIMS);

            if (stun)
                drawWithOffset(g, barneyOnscreen, stunIcon, new Point(-25, -50), new Dimension(125, 100));
        }

        g.drawImage(number, 350, 50, 100, 100, null);

        // stamina and health back part
        Color healthBack = new Color(197, 167, 119);
        g.setColor(healthBack);
        g.fillRect(530, 20, 260, 75);
        g.fillRect(20, 20, 260, 75);

        // health green
        Color healthCol = new Color(26, 232, 39);
        g.setColor(healthCol);
        g.fillRect(535, 25, health, 65);

        // stamina orange
        g.setColor(new Color(255, 174, 39));
        g.fillRect(25, 25, stamina, 65);

        // inventory 1
        if (selected1)
        {
            g.setColor(Color.ORANGE);
            g.fillRoundRect(25, 685, 70, 70, 10, 10);
        }
        g.setColor(new Color(94, 43, 38));
        g.fillRoundRect(30, 690, 60, 60, 10, 10);
        g.setColor(healthBack);
        g.fillRoundRect(35, 695, 50, 50, 10, 10);

        // inventory 2
        if (selected2)
        {
            g.setColor(Color.ORANGE);
            g.fillRoundRect(185, 685, 70, 70, 10, 10);
        }
        g.setColor(new Color(94, 43, 38));
        g.fillRoundRect(190, 690, 60, 60, 10, 10);
        g.setColor(healthBack);
        g.fillRoundRect(195, 695, 50, 50, 10, 10);

        // inventory 3
        if (selected3)
        {
            g.setColor(Color.ORANGE);
            g.fillRoundRect(345, 685, 70, 70, 10, 10);
        }
        g.setColor(new Color(94, 43, 38));
        g.fillRoundRect(350, 690, 60, 60, 10, 10);
        g.setColor(healthBack);
        g.fillRoundRect(355, 695, 50, 50, 10, 10);

        // inventory 4
        if (selected4)
        {
            g.setColor(Color.ORANGE);
            g.fillRoundRect(505, 685, 70, 70, 10, 10);
        }
        g.setColor(new Color(94, 43, 38));
        g.fillRoundRect(510, 690, 60, 60, 10, 10);
        g.setColor(healthBack);
        g.fillRoundRect(515, 695, 50, 50, 10, 10);

        // inventory 5
        if (selected5)
        {
            g.setColor(Color.ORANGE);
            g.fillRoundRect(665, 685, 70, 70, 10, 10);
        }
        g.setColor(new Color(94, 43, 38));
        g.fillRoundRect(670, 690, 60, 60, 10, 10);
        g.setColor(healthBack);
        g.fillRoundRect(675, 695, 50, 50, 10, 10);

        // medkit
        g.drawImage(med, 350, 690, 60, 60, null);

        // apple
        g.drawImage(apple, 515, 695, 50, 50, null);

        // cookie
        g.drawImage(cookie, 675, 695, 50, 50, null);

        // gas
        g.drawImage(gas, 195, 695, 50, 50, null);

        // gun
        g.drawImage(gun, 35, 695, 50, 50, null);

        g.setFont(minecraft);
        g.setColor(Color.WHITE);
        g.drawString("" + medkits.getCount(), 405, 755);
        if (bulletCooldown)
            g.drawString(String.format("%.1f", shotCooldownLeft / 1000.0), 75, 755);
        g.drawString("" + cookies.getCount(), 725, 755);
        g.drawString("" + apples.getCount(), 565, 755);
        g.drawString(gasItems.getCount() + "/3", 245, 755);

        // letters at beginning
        if (!barneySpawn)
        {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Minecraft", Font.BOLD, 30));
            g.drawString(showingSentence, 100, 210);
            g.drawString(showingSentence2, 100, 240);
            g.drawString(showingSentence3, 100, 270);
            g.drawString(showingSentence4, 100, 300);
        }
    }

    // returns random coordinates for the top left of the items
    public Point itemCoordinateMaker()
    {
        Point position = new Point(
                (int) (Math.random() * 1620),
                (int) (Math.random() * 1620));

        boolean works = true;
        for (int i = 0; i < currentBorder.length; i++)
        {
            if (currentBorder[i].contains(position.x, position.y))
            {
                works = false;
                break;
            }
        }

        if (works)
            return position;
        else
            return itemCoordinateMaker();
    }

    // checks if the user clicked the screen
    private boolean containsOnScreen(Point itemLocation, Dimension itemDims, Point point)
    {
        itemLocation = convertPosition(itemLocation);
        boolean containsX = itemLocation.x <= point.x && point.x <= itemLocation.x + itemDims.width;
        boolean containsY = itemLocation.y <= point.y && point.y <= itemLocation.y + itemDims.height;
        return containsX && containsY;
    }

    // checks if the character's next position will be in a border
    private Point getNextPlayerPosition()
    {
        Point newPosition = new Point(playerPosition);

        int speedX, speedY;
        if (shiftSprint)
        {
            speedX = PLAYER_X_SPEED_SPRINT;
            speedY = PLAYER_Y_SPEED_SPRINT;
        }
        else
        {
            speedX = PLAYER_X_SPEED;
            speedY = PLAYER_Y_SPEED;
        }

        if (moveLeft)
        {
            newPosition.x -= speedX;
        }
        else if (moveRight)
        {
            newPosition.x += speedX;
        }

        if (moveUp)
        {
            newPosition.y -= speedY;
        }
        else if (moveDown)
        {
            newPosition.y += speedY;
        }

        boolean canMove = true;
        for (int i = 0; i < currentBorder.length; i++)
        {
            if (currentBorder[i].contains(newPosition.x, newPosition.y))
            {
                canMove = false;
                break;
            }
        }

        if (canMove)
        {
            return newPosition;
        }
        else
        {
            return new Point(playerPosition);
        }
    }

    // barney's supernatural ability, that makes it so that you take damage if you
    // move
    private void updateNoMoveTime()
    {
        if (!playerDamageOnMove)
        {
            noMoveTime -= 4;
            if (noMoveTime == 0)
            {
                playerDamageOnMove = true;
                noMoveTime = 3000;
            }
        }

        if (playerDamageOnMove)
        {
            noMoveTime2 -= 4;
            if (noMoveTime2 == 0)
            {
                playerDamageOnMove = false;
                noMoveTime2 = 300;
                noMoveTime = 3000;
            }
            if (moving && noMoveTime2 <= 200)
            {
                health -= 100;
                playerDamageOnMove = false;
                noMoveTime2 = 300;
                noMoveTime = 3000;
            }
        }
    }

    // for the text that shows at the start of the game
    private void showStartingText()
    {
        if (!barneySpawn)
        {
            index += 4;
            showingSentence = showingSentence2 = showingSentence3 = showingSentence4 = "";
            for (int i = 0; i <= (int) ((index / 7)); i++)
            {
                if (i < 133)
                {
                    if (i >= 99)
                        showingSentence4 += "" + beginSentence.charAt(i);
                    else if (i >= 63)
                        showingSentence3 += "" + beginSentence.charAt(i);
                    else if (i >= 30)
                        showingSentence2 += "" + beginSentence.charAt(i);
                    else
                        showingSentence += "" + beginSentence.charAt(i);
                }
            }
        }
    }

    // shows the spawn countdown
    private void showSpawnCountdown()
    {
        if (!barneySpawn)
        {
            health = 250;
            barneyInt += 4;
            if (barneyInt == 100)
                number = new ImageIcon("images/Ten.png").getImage();
            if (barneyInt == 200)
                number = new ImageIcon("images/Nine.png").getImage();
            if (barneyInt == 300)
                number = new ImageIcon("images/Eight.png").getImage();
            if (barneyInt == 400)
                number = new ImageIcon("images/Seven.png").getImage();
            if (barneyInt == 500)
                number = new ImageIcon("images/Six.png").getImage();
            if (barneyInt == 600)
                number = new ImageIcon("images/Five.png").getImage();
            if (barneyInt == 700)
                number = new ImageIcon("images/Four.png").getImage();
            if (barneyInt == 800)
                number = new ImageIcon("images/Three.png").getImage();
            if (barneyInt == 900)
                number = new ImageIcon("images/Two.png").getImage();
            if (barneyInt == 1000)
                number = new ImageIcon("images/One.png").getImage();
            if (barneyInt == 1100)
            {
                number = null;
                barneySpawn = true;
            }
        }
    }

    // performs barney's physical attack
    private void performBarneyAttack()
    {
        if (barneySpawn)
        {
            if (!barAttackCool)
            {
                Point playerTopRight = convertPosition(playerPosition);
                Point barneyTopRight = convertPosition(barneyPosition);

                Point playerBottomRight = new Point(playerTopRight);
                Point barneyBottomRight = new Point(barneyTopRight);
                playerBottomRight.translate(PLAYER_DIMS.width, PLAYER_DIMS.height);
                barneyBottomRight.translate(BARNEY_DIMS.width, BARNEY_DIMS.height);

                boolean separateX = (playerBottomRight.x < barneyTopRight.x)
                        || (barneyBottomRight.x < playerTopRight.x);
                boolean separateY = (playerBottomRight.y < barneyTopRight.y)
                        || (barneyBottomRight.y < playerTopRight.y);

                if (!separateX && !separateY)
                {
                    health -= 100;
                    barAttackCool = true;
                }
            }
            else
            {
                barCooldown -= 4;
                if (barCooldown == 0)
                {
                    barAttackCool = false;
                    barCooldown = 500;
                }
            }
        }
    }

    // updates barney's position
    private void updateBarneyPosition()
    {
        if (barneySpawn)
        {
            Point target;
            if (previousPlayerPosition.isEmpty())
            {
                target = new Point(START_X, START_Y);
            }
            else
            {
                Point oldPosition = previousPlayerPosition.peek();
                int averageX = (playerPosition.x + oldPosition.x) / 2;
                int averageY = (playerPosition.y + oldPosition.y) / 2;
                target = new Point(averageX, averageY);
            }

            if (!stun)
            {
                if (target.x > barneyPosition.x)
                {
                    barneyPosition.x += BARNEY_X_SPEED;
                }
                else if (target.x < barneyPosition.x)
                {
                    barneyPosition.x -= BARNEY_X_SPEED;
                }

                if (target.y > barneyPosition.y)
                {
                    barneyPosition.y += BARNEY_Y_SPEED;
                }
                else if (target.y < barneyPosition.y)
                {
                    barneyPosition.y -= BARNEY_Y_SPEED;
                }
            }
        }
    }

    // updates the player sprite, for running and standing still
    private void updatePlayerSprite()
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
                if (im == 8)
                    im = 1;
                ims += "Run" + im + ".png";
            }
            run1 = new ImageIcon(ims).getImage();
        }
        else
        {
            if (movingLeft)
                run1 = new ImageIcon("images/MainStandLeft.png").getImage();
            else
                run1 = new ImageIcon("images/MainStand.png").getImage();
        }
    }

    // class for a timer that moves the characters and map
    class PlayerMover implements ActionListener
    {
        // everytime timer occurs
        public void actionPerformed(ActionEvent e)
        {
            if (health <= 0 || gasItems.getCount() == 3)
            {
                playerTimer.stop();
                clip2.stop();
                manager.showGameOver();
            }

            Point nextPosition = getNextPlayerPosition();

            previousPlayerPosition.add(playerPosition);
            if (previousPlayerPosition.size() > NUM_POSITIONS_KEPT)
            {
                previousPlayerPosition.poll();
            }

            updateNoMoveTime();
            showStartingText();
            showSpawnCountdown();

            if (cookiesActivated)
            {
                if (cookies.getCount() > 0)
                {
                    cookieTime -= 4;
                    if (cookieTime <= 0)
                        cookiesActivated = false;
                }
            }

            if (stun)
            {
                stunTime += 40;
                if (stunTime >= 3000)
                {
                    stun = false;
                    stunTime = 0;
                }
            }

            if (bulletCooldown)
            {
                shotCooldownLeft -= UPDATE_DELAY;

                if (shotCooldownLeft <= 0)
                {
                    shotCooldownLeft = GUN_SHOT_DELAY;
                    bulletCooldown = false;
                }
            }

            if (shiftSprint)
            {
                if (sprintInt > 0)
                    sprintInt--;
                else if (sprintInt == 0)
                    shiftSprint = false;
            }

            if (!shiftSprint)
            {
                if (sprintInt < 125)
                    sprintInt++;
            }

            stamina = sprintInt * 2;
            if (cookiesActivated)
            {
                shiftSprint = true;
                sprintInt = 125;
            }

            performBarneyAttack();
            playerPosition = nextPosition;
            topLeft = getScreenTopLeft();

            updateBarneyPosition();
            updatePlayerSprite();

            if (!damage)
                health = 250;

            repaint();
            grabFocus();
        }
    }

    // keeps track of the items on the ground
    class CollectibleSet
    {
        private Collectible[] items;
        int collectedCount;

        // looks at the collectible class for the each item's information
        public CollectibleSet(Image image, Point[] locations, Dimension dimension)
        {
            items = new Collectible[locations.length];
            for (int i = 0; i < locations.length; i++)
            {
                items[i] = new Collectible(image, locations[i], dimension);
            }

            collectedCount = 0;
        }

        // draws the items
        public void drawAll(Graphics g)
        {
            for (int i = 0; i < items.length; i++)
            {
                items[i].draw(g);
            }
        }

        // checks if the item is collected
        public boolean tryCollecting(Point clickPos)
        {
            boolean anySelected = false;

            for (int i = 0; i < items.length; i++)
            {
                if (items[i].checkIfCollected(clickPos))
                {
                    anySelected = true;
                    collectedCount++;
                }
            }

            return anySelected;
        }

        // gets the count for each item
        public int getCount()
        {
            return collectedCount;
        }

        // reduces the count when the item is collected
        public void useItem()
        {
            collectedCount--;
        }
    }

    // class for drawing the items
    class Collectible
    {
        private Image image;
        private Point location;
        private Dimension dimension;
        private boolean picked;

        // gives information for the item to draw it
        public Collectible(Image image, Point location, Dimension dimension)
        {
            this.image = image;
            this.location = location;
            this.dimension = dimension;
            picked = false;
        }

        // draws the image
        public void draw(Graphics g)
        {
            if (!picked)
            {
                drawOnScreen(g, image, location, dimension);
            }
        }

        // checks if the item is collected, and sees if it should be drawn or not
        public boolean checkIfCollected(Point clickPos)
        {
            if (!picked && containsOnScreen(location, dimension, clickPos))
            {
                picked = true;
                return true;
            }
            return false;
        }

        // returns picked for the item
        public boolean isPicked()
        {
            return picked;
        }
    }

    // movement input
    public void keyPressed(KeyEvent e)
    {
        /*
         * if (e.getKeyChar() == 'g')
         * {
         * damage = !damage;
         * }
         * if (e.getKeyChar() == 'e')
         * {
         * playerTimer.stop();
         * manager.showGameOver();
         * }
         * if (e.getKeyChar() == 'k')
         * {
         * // numCookies = 5;
         * // numApples = 5;
         * // numMed = 5;
         * }
         */
        if (e.getKeyCode() == KeyEvent.VK_SHIFT)
        {
            shiftSprint = true;
        }
        if (e.getKeyChar() == 'd' || e.getKeyChar() == 'a' || e.getKeyChar() == 's' || e.getKeyChar() == 'w'
                || e.getKeyCode() == KeyEvent.VK_SHIFT || e.getKeyChar() == 'D' || e.getKeyChar() == 'A'
                || e.getKeyChar() == 'S' || e.getKeyChar() == 'W')
        {
            moving = true;
            started = true;
        }
        else
        {
            moving = false;
        }
        if (e.getKeyChar() == 'd' || e.getKeyChar() == 'D')
        {
            moveRight = true;
            moveLeft = false;
            movingLeft = false;
        }
        if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A')
        {
            moveLeft = true;
            moveRight = false;
            movingLeft = true;
        }
        if (e.getKeyChar() == 'w' || e.getKeyChar() == 'W')
        {
            moveUp = true;
            moveDown = false;
        }
        if (e.getKeyChar() == 's' || e.getKeyChar() == 'S')
        {
            moveDown = true;
            moveUp = false;
        }
        repaint();
        grabFocus();
    }

    // more movement input, resets variables that keyPressed activates
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

    // nothing inside, does nothing, will do nothing
    public void keyTyped(KeyEvent e)
    {
    }

    // activates the abilities in the inventory slots and picks up stuff
    public void mousePressed(MouseEvent e)
    {
        if (!tryCollectingAll(e))
        {
            if (selected1)
            {
                if (!bulletCooldown)
                {
                    stun = true;
                    bulletCooldown = true;
                }
            }
            else if (selected2)
            {
            }
            else if (selected3)
            {
                if (medkits.getCount() > 0)
                {
                    medkits.useItem();
                    health += 50;
                    if (health > 250)
                    {
                        health = 250;
                    }
                }
            }
            else if (selected4)
            {
                if (apples.getCount() > 0)
                {
                    apples.useItem();
                    stamina += 50;
                    if (stamina >= 125)
                        sprintInt = 125;
                }
            }
            else if (selected5)
            {
                if (cookies.getCount() > 0)
                {
                    cookies.useItem();
                    cookiesActivated = true;
                }
            }
        }

        repaint();
        grabFocus();
    }

    private boolean tryCollectingAll(MouseEvent e)
    {
        boolean anySelected = false;
        Point clickPos = new Point(e.getX(), e.getY());

        anySelected |= gasItems.tryCollecting(clickPos);
        anySelected |= medkits.tryCollecting(clickPos);
        anySelected |= apples.tryCollecting(clickPos);
        anySelected |= cookies.tryCollecting(clickPos);

        return anySelected;
    }

    // nothing inside, does nothing, will do nothing
    public void mouseClicked(MouseEvent e)
    {
    }

    // nothing inside, does nothing, will do nothing
    public void mouseReleased(MouseEvent e)
    {
    }

    // nothing inside, does nothing, will do nothing
    public void mouseEntered(MouseEvent e)
    {
    }

    // nothing inside, does nothing, will do nothing
    public void mouseExited(MouseEvent e)
    {
    }
}