import java.awt.BorderLayout;
import java.awt.CardLayout;
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

public class Level3p1 extends JPanel implements KeyListener, MouseListener
{
    // size of the window that is mapped to the screen
    private final static Dimension SCREEN_DIMS = new Dimension(200, 300);
    private final static Point START = new Point(36, 547);

    // background information
    private final static Dimension BACKGROUND_DIMS = new Dimension(1392, 959);

    private final static int UPDATE_DELAY = 40;

    private final static Dimension PLAYER_DIMS = new Dimension(50, 50);
    private final static Dimension BARNEY_DIMS = new Dimension(100, 150);
    private final static Dimension CHILD_DIMS = new Dimension(50, 50);
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

    private final static int TOTAL_CHILD = 3;
    private final static int TOTAL_MEDKIT = 5;
    private final static int TOTAL_APPLES = 5;
    private final static int TOTAL_COOKIES = 2;

    private final static int GUN_SHOT_DELAY = 10000;

    private Manager manager;

    private Image run1, back, barneyBlood, number, child, med, gun, apple, cookie, stunIcon, bloodHead;
    private Timer playerTimer;
    private int im, health, barneyInt,
            sprintInt, stamina, shotCooldownLeft, stunTime, cookieTime, index,
            noMoveTime, noMoveTime2, barCooldown;
    private boolean moving, movingLeft, moveLeft, moveRight, moveUp, moveDown, barneySpawn, shiftSprint, started,
            selected1, selected2, selected3, selected4, selected5, bulletCooldown, stun, cookiesActivated,
            songStarted, playerDamageOnMove, barAttackCool, damage,barLeft,barMove,barMove1,barMove2;
    private PlayerMover playerTime;
    private JButton inv1, inv2, inv3, inv4, inv5;
    private String beginSentence, showingSentence, showingSentence2, showingSentence3,
            showingSentence4;
    private Font minecraft;
    private Rectangle[] currentBorder;
    private Clip clip2;

    private CollectibleSet childItems, medkits, apples, cookies;

    private Queue<Point> previousPlayerPosition;

    private Point playerPosition, barneyPosition;

    private Point topLeft;
    private CardLayout cl;

    // declares all of the variables and timers
    public void reset(Manager manager)
    {
        this.manager = manager;
        setLayout(new BorderLayout());
        CardLayout cl = new CardLayout();
        setLayout(cl);
        songStarted = false;
        noMoveTime = 3000;
        noMoveTime2 = 300;
        playerDamageOnMove = false;
        minecraft = new Font("Minecraft", Font.BOLD, 16);
        currentBorder = new Rectangle[]
        {
            new Rectangle(0,0,117,53),
            new Rectangle(0,117,153,157),
            new Rectangle(136,116,214,156),
            new Rectangle(157,0,171,9),
            new Rectangle(171,0,265,11),
            new Rectangle(218,0,265,40),
            new Rectangle(218,0,265,66),
            new Rectangle(231,66,265,88),
            new Rectangle(246,88,265,94),
            new Rectangle(253,94,263,113),
            new Rectangle(265,119,335,151),
            new Rectangle(306,21,338,69),
            new Rectangle(376,3,407,50),
            new Rectangle(417,69,499,72),
            new Rectangle(414,59,496,69),
            new Rectangle(417,47,496,59),
            new Rectangle(420,44,496,47),
            new Rectangle(423,31,496,44),
            new Rectangle(426,28,496,31),
            new Rectangle(429,25,496,28),
            new Rectangle(445,21,496,25),
            new Rectangle(448,18,496,21),
            new Rectangle(451,15,496,18),
            new Rectangle(382,116,414,163),
            new Rectangle(433,116,464,163),
            new Rectangle(505,116,537,163),
            new Rectangle(546,138,560,157),
            new Rectangle(543,138,546,151),
            new Rectangle(546,135,552,138),
            new Rectangle(559,141,565,176),
            new Rectangle(565,148,568,173),
            new Rectangle(568,151,574,163),
            new Rectangle(546,151,574,160),
            new Rectangle(552,160,571,163),
            new Rectangle(556,163,568,173),
            new Rectangle(543,3,574,50),
            new Rectangle(634,0,666,36),
            new Rectangle(584,103,616,138),
            new Rectangle(679,104,710,138),
            new Rectangle(347,214,379,261),
            new Rectangle(429,214,461,261),
            new Rectangle(483,214,515,261),
            new Rectangle(688,214,720,261),
            new Rectangle(732,214,764,261),
            new Rectangle(0,107,22,135),
            new Rectangle(35,107,67,135),
            new Rectangle(0,195,10,223),
            new Rectangle(22,195,67,223),
            new Rectangle(22,227,67,258),
            new Rectangle(123,195,155,223),
            new Rectangle(152,220,186,258),
            new Rectangle(243,204,300,290),
            new Rectangle(111,305,142,334),
            new Rectangle(13,309,13,520),
            new Rectangle(60,413,325,520),
            new Rectangle(335,413,439,520),
            new Rectangle(439,413,477,450),
            new Rectangle(505,384,562,460),
            new Rectangle(628,422,650,432),
            new Rectangle(628,432,672,482),
            new Rectangle(685,422,729,482),
            new Rectangle(742,413,912,520),
            new Rectangle(912,437,998,520),
            new Rectangle(565,330,631,355),
            new Rectangle(666,331,792,359),
            new Rectangle(792,299,836,360),
            new Rectangle(803,210,824,299),
            new Rectangle(761,0,877,176),
            new Rectangle(848,0,1092,103),
            new Rectangle(896,107,1022,171),
            new Rectangle(1019,104,1105,147),
            new Rectangle(1105,0,1392,137),
            new Rectangle(1345,137,1392,338),
            new Rectangle(1275,345,1353,394),
            new Rectangle(881,204,966,226),
            new Rectangle(852,249,884,277),
            new Rectangle(890,249,922,277),
            new Rectangle(947,249,978,277),
            new Rectangle(1032,220,1070,258),
            new Rectangle(893,312,987,340),
            new Rectangle(1029,312,1073,337),
            new Rectangle(1013,353,1073,372),
            new Rectangle(931,379,980,419),
            new Rectangle(0,571,13,950),
            new Rectangle(71,678,231,719),
            new Rectangle(63,574,322,693),
            new Rectangle(339,576,433,601),
            new Rectangle(362,601,426,641),
            new Rectangle(357,621,407,688),
            new Rectangle(417,630,451,697),
            new Rectangle(451,640,505,697),
            new Rectangle(571,640,628,697),
            new Rectangle(625,566,676,615),
            new Rectangle(682,657,733,634),
            new Rectangle(682,657,707,646),
            new Rectangle(749,565,1004,643),
            new Rectangle(749,565,865,694),
            new Rectangle(896,646,928,674),
            new Rectangle(940,646,972,674),
            new Rectangle(978,706,1010,734),
            new Rectangle(1004,413,1079,454),
            new Rectangle(1057,485,1073,505),
            new Rectangle(1073,505,1392,517),
            new Rectangle(1250,427,1392,517),
            new Rectangle(1120,447,1190,473),
            new Rectangle(1057,573,1392,649),
            new Rectangle(1249,649,1392,774),
            new Rectangle(127,826,1319,854),
            new Rectangle(1332,826,1363,854),
            new Rectangle(1054,775,1224,892),
            new Rectangle(1193,889,1224,930),
            new Rectangle(1291,902,1313,927),
            new Rectangle(1332,902,1354,927),
            new Rectangle(1291,943,1313,959),
            new Rectangle(1086,921,1117,949),
            new Rectangle(554,737,1007,865),
            new Rectangle(742,865,1007,924),
            new Rectangle(213,737,508,801),
            new Rectangle(211,742,518,905),
            new Rectangle(118,775,149,804),
            new Rectangle(0,877,74,959),
            new Rectangle(1094,701,1121,730),
            new Rectangle(1094,701,1164,715)
        };

        cookiesActivated = false;
        cookieTime = 1000;
        index = -1;
        beginSentence = "Oh no! My car ran out of child. This city seems to be abandoned. Is that Barney? I need to get 3 child cans to fuel up my car and escape.";
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
        back = new ImageIcon("images/Level3Back.png").getImage();
        barneyBlood = new ImageIcon("images/BarneyBlood.png").getImage();
        stunIcon = new ImageIcon("images/stun.gif").getImage();
        number = new ImageIcon("images/Ten.png").getImage();
        setBackground(Color.BLACK);
        child = new ImageIcon("images/Gas.png").getImage();
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

        playerPosition = new Point(START.x, START.y);
        barneyPosition = new Point(START.x, START.y);
        topLeft = getScreenTopLeft();

        childItems = new CollectibleSet(child, generateRandomLocations(TOTAL_CHILD), CHILD_DIMS);
        medkits = new CollectibleSet(med, generateRandomLocations(TOTAL_MEDKIT), MEDKIT_DIMS);
        apples = new CollectibleSet(apple, generateRandomLocations(TOTAL_APPLES), APPLE_DIMS);
        cookies = new CollectibleSet(cookie, generateRandomLocations(TOTAL_COOKIES), COOKIE_DIMS);

        previousPlayerPosition = new ArrayDeque<Point>();
    }
    public Level3p1(Manager manager)
    {
        reset(manager);
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

        if (currentPosition.x - SCREEN_DIMS.width / 2 < 0)
        {
            result.x = 0;
        }
        else if (currentPosition.x + SCREEN_DIMS.width / 2 > BACKGROUND_DIMS.width)
        {
            result.x = BACKGROUND_DIMS.width - SCREEN_DIMS.width;
        }
        else
        {
            result.x = currentPosition.x - SCREEN_DIMS.width / 2;
        }

        if (currentPosition.y - SCREEN_DIMS.height / 2 < 0)
        {
            result.y = 0;
        }
        else if (currentPosition.y + SCREEN_DIMS.height / 2 > BACKGROUND_DIMS.height)
        {
            result.y = BACKGROUND_DIMS.height - SCREEN_DIMS.height;
        }
        else
        {
            result.y = currentPosition.y - SCREEN_DIMS.height / 2;
        }

        return result;
    }

    // finds the position of a point on the screen
    private Point convertPosition(Point position)
    {
        int offsetX = (position.x - topLeft.x) * NightOfBarney.FRAME_DIMS.width / SCREEN_DIMS.width;
        int offsetY = (position.y - topLeft.y) * NightOfBarney.FRAME_DIMS.width / SCREEN_DIMS.height;
        return new Point(offsetX, offsetY);
    }

    // returns the position where the object was drawn
    private Point drawOnScreen(Graphics g, Image image, Point position, Dimension dimension)
    {
        Point screenPosition = convertPosition(position);
        g.drawImage(image, screenPosition.x, screenPosition.y, dimension.width, dimension.height, null);
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

        // draw background
        g.drawImage(back, 0, 0, NightOfBarney.FRAME_DIMS.width, NightOfBarney.FRAME_DIMS.height, 
            topLeft.x, topLeft.y, topLeft.x + SCREEN_DIMS.width, topLeft.y + SCREEN_DIMS.height, null, null);

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
            // g.fillRect(0, 0, NightOfBarney.FRAME_DIMS.width, NightOfBarney.FRAME_DIMS.height);
            g.drawImage(bloodHead, 200, 200, 400, 400, null);
        }

        childItems.drawAll(g);
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

        // child
        g.drawImage(child, 195, 695, 50, 50, null);

        // gun
        g.drawImage(gun, 35, 695, 50, 50, null);

        g.setFont(minecraft);
        g.setColor(Color.WHITE);
        g.drawString("" + medkits.getCount(), 405, 755);
        if (bulletCooldown)
            g.drawString(String.format("%.1f", shotCooldownLeft / 1000.0), 75, 755);
        g.drawString("" + cookies.getCount(), 725, 755);
        g.drawString("" + apples.getCount(), 565, 755);
        g.drawString(childItems.getCount() + "/3", 245, 755);

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
        /*for (int i = 0; i < currentBorder.length; i++)
        {
            if (currentBorder[i].contains(position.x, position.y))
            {
                works = false;
                break;
            }
        }*/

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
        /*for (int i = 0; i < currentBorder.length; i++)
        {
            if (currentBorder[i].contains(newPosition.x, newPosition.y))
            {
                canMove = false;
                break;
            }
        }*/

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

    private void updateBarneyPosition()
    {
        if (barneySpawn)
        {
            Point target;
            if (previousPlayerPosition.isEmpty())
            {
                target = new Point(START.x, START.y);
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
                barMove1 = barMove2 = false;
                if (target.x > barneyPosition.x)
                {
                    barneyPosition.x += BARNEY_X_SPEED;
                    barLeft = false;
                    barMove1 = true;
                }
                else if (target.x < barneyPosition.x)
                {
                    barneyPosition.x -= BARNEY_X_SPEED;
                    barLeft = true;
                    barMove1 = true;
                }

                if (target.y > barneyPosition.y)
                {
                    barneyPosition.y += BARNEY_Y_SPEED;
                    barMove2 = true;
                }
                else if (target.y < barneyPosition.y)
                {
                    barneyPosition.y -= BARNEY_Y_SPEED;
                    barMove2 = true;
                }

                if(!barMove1 && !barMove2)
                    barMove = false;
                else
                    barMove = true;
            }
            else
                barMove = false;

        }
    }
    //updates barney's sprite
    private void updateBarneySprite()
    {
        if(barMove)
        {
            if(barLeft)
                barneyBlood = new ImageIcon("images/BarneyLeft.gif").getImage();
            else
                barneyBlood = new ImageIcon("images/BarneyRun.gif").getImage();
        }
        else
        {
            if(barLeft)
                barneyBlood = new ImageIcon("images/BarneyStandLeft.png").getImage();
            else
                barneyBlood = new ImageIcon("images/BarneyStandRight.png").getImage();
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
            if (health <= 0 || childItems.getCount() == 3)
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
            updateBarneySprite();
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

        anySelected |= childItems.tryCollecting(clickPos);
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