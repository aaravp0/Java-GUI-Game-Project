// manages all of the panels in cardLayout

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
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

class Manager extends JPanel
{
    private CardLayout layout;
    private Timer bloodTimer;
    private boolean first, second, third;

    // calls runIt()
    public Manager()
    {
        runIt();
    }

    // creates panels and adds them to cardlayout
    public void runIt()
    {
        layout = new CardLayout();
        setLayout(layout);

        add(new StartScreen(), "start");
        add(new JPanel(), "end");
        add(new Blood(), "blood");
        add(new Level1(), "level 1");
        //add(new Level2(), "level 2");
        //add(new Level3(), "level 3");
        add(new LevelScreen(), "level select");
        add(new GameOver1(),"Game over1");

        setVisible(true);
    }

    // the JPanel for the start screen
    class StartScreen extends JPanel
    {
        private Clip themeSong;
        private final String THEME_SONG = "sounds/barneyTheme.wav";
        private JButton toStartButton, toSelectButton;
        Image night, the, nig, ht, of, bar, ney, button, start, hoverStart, hoveringStart, levelSelected,
                levelUnselected, bloody, sad;
        boolean startHover, levelHover;

        // adds mouse listeners and components to the start screen
        public StartScreen()
        {
            themeSong = null;
            try
            {
                AudioInputStream audioInputStream = AudioSystem
                        .getAudioInputStream(new File(THEME_SONG).getAbsoluteFile());
                themeSong = AudioSystem.getClip();
                themeSong.open(audioInputStream);
                themeSong.start();
                themeSong.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (IOException e)
            {
                System.err.printf("Unable to find sound clip %s\n", THEME_SONG);
                e.printStackTrace();
                System.exit(1);
            } catch (UnsupportedAudioFileException e)
            {
                System.err.printf("Unable to open sound clip %s\n", THEME_SONG);
                e.printStackTrace();
                System.exit(1);
            } catch (LineUnavailableException e)
            {
                System.err.printf("Unable to open sound clip %s\n", THEME_SONG);
                e.printStackTrace();
                System.exit(1);
            }

            startHover = false;
            levelHover = false;
            // when the start button is pressed
            toStartButton = new JButton("");
            toStartButton.addActionListener(new ActionListener()
            {
                // everytime button is clicked
                public void actionPerformed(ActionEvent e)
                {
                    first = true;
                    layout.show(Manager.this, "blood");
                    bloodTimer.start();
                    themeSong.stop();
                    try
                    {
                        String soundName1 = "sounds/mixkit-angry-monster-scream-1963.wav";
                        AudioInputStream audioInputStream1 = AudioSystem
                                .getAudioInputStream(new File(soundName1).getAbsoluteFile());
                        Clip clip1 = AudioSystem.getClip();
                        clip1.open(audioInputStream1);
                        clip1.start();
                    } catch (Exception i)
                    {
                    }
                }
            });
            toSelectButton = new JButton("");
            // when the level button is pressed
            toSelectButton.addActionListener(new java.awt.event.ActionListener()
            {
                // everytime button is clicked
                public void actionPerformed(ActionEvent e)
                {
                    layout.show(Manager.this, "level select");
                    try
                    {
                        String soundName1 = "sounds/mixkit-angry-monster-scream-1963.wav";
                        AudioInputStream audioInputStream1 = AudioSystem
                                .getAudioInputStream(new File(soundName1).getAbsoluteFile());
                        Clip clip1 = AudioSystem.getClip();
                        clip1.open(audioInputStream1);
                        clip1.start();
                    } catch (Exception i)
                    {
                    }
                }
            });
            // for the effect of hovering over the button
            toStartButton.addMouseListener(new java.awt.event.MouseAdapter()
            {
                // when mouse is over the button
                public void mouseEntered(java.awt.event.MouseEvent e)
                {
                    startHover = true;
                    repaint();
                }

                // when mouse is away from the button
                public void mouseExited(java.awt.event.MouseEvent e)
                {
                    startHover = false;
                    repaint();
                }
            });
            // for the effect of hovering over the button
            toSelectButton.addMouseListener(new java.awt.event.MouseAdapter()
            {
                // when mouse is over the button
                public void mouseEntered(java.awt.event.MouseEvent e)
                {
                    levelHover = true;
                    repaint();
                }

                // when mouse is away from the button
                public void mouseExited(java.awt.event.MouseEvent e)
                {
                    levelHover = false;
                    repaint();
                }
            });
            JPanel blank = new JPanel(new GridLayout(2, 1));
            JPanel blank3 = new JPanel(new GridLayout(4, 1));
            JPanel bl1 = new JPanel();
            JPanel bl2 = new JPanel();
            JPanel bl3 = new JPanel();
            JPanel bl4 = new JPanel();
            blank.setOpaque(false);
            setLayout(new BorderLayout());
            toStartButton.setPreferredSize(new Dimension(400, 80));
            toSelectButton.setPreferredSize(new Dimension(400, 84));
            toStartButton.setBorderPainted(false);
            toSelectButton.setBorderPainted(false);
            JPanel blank2 = new JPanel();
            blank.add(blank2);
            blank.add(blank3);
            bl1.add(toStartButton);
            bl2.add(toSelectButton);
            blank3.add(bl1);
            blank3.add(bl2);
            blank3.add(bl3);
            blank3.add(bl4);
            bl1.setOpaque(false);
            bl2.setOpaque(false);
            bl3.setOpaque(false);
            bl4.setOpaque(false);
            blank2.setOpaque(false);
            blank3.setOpaque(false);
            add(blank, BorderLayout.CENTER);
            setBackground(Color.PINK);
        }

        // paints the screen
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            night = new ImageIcon("images/Night.png").getImage();
            g.drawImage(night, 0, 0, 800, 800, null);
            the = new ImageIcon("images/THE.png").getImage();
            nig = new ImageIcon("images/NIG.png").getImage();
            ht = new ImageIcon("images/HT.png").getImage();
            of = new ImageIcon("images/OF.png").getImage();
            sad = new ImageIcon("images/BarneySad.png").getImage();
            bloody = new ImageIcon("images/BarneyBlood.png").getImage();
            bar = new ImageIcon("images/BAR.png").getImage();
            ney = new ImageIcon("images/NEY.png").getImage();
            g.drawImage(the, 20, 20, 250, 200, null);
            g.drawImage(nig, 280, 0, 250, 200, null);
            g.drawImage(ht, 510, 20, 150, 190, null);
            g.drawImage(of, 640, 0, 170, 220, null);
            g.drawImage(bar, 150, 190, 250, 150, null);
            g.drawImage(ney, 400, 240, 250, 150, null);
            g.drawImage(sad, 5, 420, 200, 300, null);
            g.drawImage(bloody, 595, 420, 200, 300, null);
            button = new ImageIcon("images/Button.png").getImage();
            g.drawImage(button, 200, 390, 400, 80, null);
            g.drawImage(button, 200, 490, 400, 80, null);
            start = new ImageIcon("images/Start.png").getImage();
            hoveringStart = new ImageIcon("images/HoveringStart.png").getImage();
            if (startHover)
                hoverStart = start;
            else
                hoverStart = hoveringStart;
            g.drawImage(hoverStart, 325, 405, 150, 55, null);
            levelSelected = new ImageIcon("images/LEVEL.png").getImage();
            levelUnselected = new ImageIcon("images/level2.png").getImage();
            if (levelHover)
                g.drawImage(levelSelected, 335, 505, 140, 45, null);
            else
                g.drawImage(levelUnselected, 335, 505, 140, 45, null);
        }
    }

    // JPanel for the Blood dripping down
    class Blood extends JPanel
    {
        Image night, bloodDrip;
        BloodMover bloodmover;
        int length;

        // declares the timer
        public Blood()
        {
            setBackground(Color.WHITE);
            bloodmover = new BloodMover();
            bloodTimer = new Timer(1, bloodmover);
        }

        // paint the blood
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            night = new ImageIcon("images/Night.png").getImage();
            bloodDrip = new ImageIcon("images/Blood.png").getImage();
            g.drawImage(night, 0, 0, 800, 800, null);
            g.drawImage(bloodDrip, 0, 0, 800, length / 10, null);
        }

        // class for the timer
        class BloodMover implements ActionListener
        {
            // everytime the timer does an action, it stretches the blood
            public void actionPerformed(ActionEvent e)
            {
                length += 30;
                if (length >= 8000)
                {
                    bloodTimer.stop();
                    if (first)
                    {
                        layout.show(Manager.this, "level 1");
                    }
                    else if (second)
                    {
                        layout.show(Manager.this, "level 2");
                    }
                    else if (third)
                    {
                        layout.show(Manager.this, "level 3");
                    }
                }
                repaint();
                grabFocus();
            }
        }
    }

    // has the level screen
    class LevelScreen extends JPanel
    {
        JButton one, two, three;
        JPanel p1, p2, p3, p4, p5, p6, p7;
        Image night,med,gas;

        // declares the components of the level screen
        public LevelScreen()
        {
            night = new ImageIcon("Night.png").getImage();
            p1 = new JPanel();
            p2 = new JPanel();
            p3 = new JPanel();
            p4 = new JPanel();
            p5 = new JPanel();
            p6 = new JPanel();
            p7 = new JPanel();
            p1.setOpaque(false);
            p2.setOpaque(false);
            p3.setOpaque(false);
            p4.setOpaque(false);
            p5.setOpaque(false);
            p6.setOpaque(false);
            p7.setOpaque(false);
            setLayout(new GridLayout(7, 1));
            add(p1);
            add(p2);
            add(p3);
            add(p4);
            add(p5);
            add(p6);
            add(p7);
            one = new JButton("");
            two = new JButton("");
            three = new JButton("");
            one.setPreferredSize(new Dimension(400, 80));
            two.setPreferredSize(new Dimension(400, 80));
            three.setPreferredSize(new Dimension(400, 80));
            p2.add(one);
            p4.add(two);
            p6.add(three);
            // makes it so that the cardlayout goes to the different levels for cardlayout,
            // the first level
            one.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    first = true;
                    layout.show(Manager.this, "blood");
                    bloodTimer.start();
                    try
                    {
                        String soundName1 = "mixkit-angry-monster-scream-1963.wav";
                        AudioInputStream audioInputStream1 = AudioSystem
                                .getAudioInputStream(new File(soundName1).getAbsoluteFile());
                        Clip clip1 = AudioSystem.getClip();
                        clip1.open(audioInputStream1);
                        clip1.start();
                    } catch (Exception i)
                    {
                    }
                }
            });
            // makes it so that the cardlayout goes to the different levels for cardlayout,
            // the second level
            two.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    second = true;
                    layout.show(Manager.this, "blood");
                    bloodTimer.start();
                    try
                    {
                        String soundName1 = "mixkit-angry-monster-scream-1963.wav";
                        AudioInputStream audioInputStream1 = AudioSystem
                                .getAudioInputStream(new File(soundName1).getAbsoluteFile());
                        Clip clip1 = AudioSystem.getClip();
                        clip1.open(audioInputStream1);
                        clip1.start();
                    } catch (Exception i)
                    {
                    }
                }
            });
            // makes it so that the cardlayout goes to the different levels for cardlayout,
            // the third level
            three.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    third = true;
                    layout.show(Manager.this, "blood");
                    bloodTimer.start();
                    try
                    {
                        String soundName1 = "mixkit-angry-monster-scream-1963.wav";
                        AudioInputStream audioInputStream1 = AudioSystem
                                .getAudioInputStream(new File(soundName1).getAbsoluteFile());
                        Clip clip1 = AudioSystem.getClip();
                        clip1.open(audioInputStream1);
                        clip1.start();
                    } catch (Exception i)
                    {
                    }
                }
            });
            med = new ImageIcon("images/med.png").getImage();
            gas = new ImageIcon("images/Gas.png").getImage();
            setBackground(Color.WHITE);
        }

        // nothing currently, but paints the levels screen
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.drawImage(night, 0, 0, 800, 800, null);
        }
    }

    //class for level 1
    class Level1 extends JPanel implements KeyListener, MouseListener
    {
        private Image run1, back, barneyBlood, number, gas, med, gun, apple, cookie, stunIcon, bloodHead;
        private Timer playerTimer;
        private int im, xPos, yPos, health, xBar, yBar,screenX, screenY, screenX2, screenY2,barneyInt,speed, sprintInt,stamina,numMed, gunTime, barneySpeed, numCookies, numApples, stunTime, cookieTime, index, noMoveTime, noMoveTime2, barCooldown;
        private boolean moving, movingLeft, moveLeft, moveRight, moveUp, moveDown, barneySpawn, shiftSprint, started, selected1, selected2, selected3, selected4, selected5, bulletCooldown, stun, cookiesActivated, songStarted, noMove, barAttackCool, damage;
        private int gas1X, gas2X, gas3X, gas1Y, gas2Y, gas3Y;
        private boolean gas1Picked, gas2Picked, gas3Picked;
        private PlayerMover playerTime;
        private JButton inv1, inv2, inv3, inv4, inv5;
        private String gunTimeValue, beginSentence, showingSentence,showingSentence2,showingSentence3,showingSentence4;
        private Font minecraft;
        private Rectangle[] currentBorder;
        private Clip clip2;

        // declares all of the variables and timers
        public Level1()
        {
            setLayout(new BorderLayout());
            songStarted = false;
            noMoveTime = 3000;
            noMoveTime2 = 300;
            noMove = false;
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
            numCookies = 0;
            numApples = 0;
            index = -1;
            beginSentence = "Oh no! My car ran out of gas. This city seems to be abandoned. Is that Barney? I need to get 3 gas cans to fuel up my car and escape.";
            showingSentence = "";
            showingSentence2 = "";
            showingSentence3 = "";
            showingSentence4 = "";
            barneySpeed = 1;
            gunTimeValue = "";
            gunTime = 1000;
            barCooldown = 500;
            barAttackCool = false;
            bulletCooldown = false;
            speed = 2;
            sprintInt = 125;
            shiftSprint = false;
            addKeyListener(this);
            addMouseListener(this);
            movingLeft = false;
            im = 0;
            xPos = yPos = 400;
            xBar = yBar = 700;
            health = 250;
            screenX = 20;
            screenY = 610;
            screenX2 = 120;
            screenY2 = 760;
            gas1X = 1000;
            gas1Y = -100;
            gas2X = 1100;
            gas2Y = -100;
            gas3X = 1200;
            gas3Y = -100;
            damage = true;
            stamina = 250;
            numMed = 0;
            playerTime = new PlayerMover();
            playerTimer = new Timer(40, playerTime);
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
            inv1 = new JButton("");
            inv1.setBounds(30,690,60,60);
            inv1.setBorderPainted(false);
            inv1.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    selected1 = true;
                    selected2 = false;
                    selected3 = false;
                    selected4 = false;
                    selected5 = false;
                }
            });
            inv2 = new JButton("");
            inv2.setBounds(190,690,60,60);
            inv2.setBorderPainted(false);
            inv2.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    selected1 = false;
                    selected2 = true;
                    selected3 = false;
                    selected4 = false;
                    selected5 = false;
                }
            });
            inv3 = new JButton("");
            inv3.setBounds(350,690,60,60);
            inv3.setBorderPainted(false);
            inv3.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    selected1 = false;
                    selected2 = false;
                    selected3 = true;
                    selected4 = false;
                    selected5 = false;
                }
            });
            inv4 = new JButton("");
            inv4.setBounds(510,690,60,60);
            inv4.setBorderPainted(false);
            inv4.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    selected1 = false;
                    selected2 = false;
                    selected3 = false;
                    selected4 = true;
                    selected5 = false;
                }
            });
            inv5 = new JButton("");
            inv5.setBounds(670,690,60,60);
            inv5.setBorderPainted(false);
            inv5.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    selected1 = false;
                    selected2 = false;
                    selected3 = false;
                    selected4 = false;
                    selected5 = true;
                }
            });
            JPanel blank = new JPanel();
            blank.setOpaque(false);
            blank.setSize(800,700);
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
        }

        //paints the sprites in the level
        public void paintComponent(Graphics g)
        {
            if(!songStarted)
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
            if(!started)
                playerTimer.start();
            super.paintComponent(g);
            g.drawImage(back,0,0,800,800,2*screenX,2*screenY,2*screenX2,2*screenY2,null,null);

            if(noMove)
            {
                g.setColor(Color.RED);
                g.fillRect(0,0,800,800);
                g.drawImage(bloodHead,200,200,400,400,null);
            }

            if(gas1X > -1 && gas1Y > -1)
            {
                g.drawImage(gas,gas1X,gas1Y,60,60,null);
                g.drawRect(gas1X, gas1Y, 60, 60); // TODO: crop gas image properly
            }

            if(gas2X > -1 && gas2Y > -1)
            {
                g.drawImage(gas,gas2X,gas2Y,60,60,null);
                g.drawRect(gas2X, gas2Y, 60, 60); // TODO: crop gas image properly
            }

            if(gas3X > -1 && gas3Y > -1)
            {
                g.drawImage(gas,gas3X,gas3Y,60,60,null);
                g.drawRect(gas3X, gas3Y, 60, 60); // TODO: crop gas image properly
            }

            g.drawImage(run1, 400, 400, 75, 75, null);
            //letters at beginning
            if(!barneySpawn)
            {
                g.setColor(Color.WHITE);
                g.setFont(new Font("Minecraft",Font.BOLD,30));
                g.drawString(showingSentence,100,210);
                g.drawString(showingSentence2,100,240);
                g.drawString(showingSentence3,100,270);
                g.drawString(showingSentence4,100,300);
            }

            //barney
            if (barneySpawn)
                g.drawImage(barneyBlood, xBar, yBar, 100, 150, null);
            if(stun)
                g.drawImage(stunIcon,xBar-25, yBar-50,125,100,null);
            g.drawImage(number, 350, 50, 100, 100, null);
            
            //stamina and health back part
            Color healthBack = new Color(197,167,119);
            g.setColor(healthBack);
            g.fillRect(530,20,260,75);
            g.fillRect(20,20,260,75);
            
            //health green
            Color healthCol = new Color(26,232,39);
            g.setColor(healthCol);
            g.fillRect(535,25,health,65);
            
            //stamina orange
            g.setColor(new Color(255,174,39));
            g.fillRect(25,25,stamina,65);
            
            //inventory 1
            if(selected1)
            {
                g.setColor(Color.ORANGE);
                g.fillRoundRect(25,685,70,70,10,10);
            }
            g.setColor(new Color(94,43,38));
            g.fillRoundRect(30,690,60,60,10,10);
            g.setColor(healthBack);
            g.fillRoundRect(35,695,50,50,10,10);

            //inventory 2
            if(selected2)
            {
                g.setColor(Color.ORANGE);
                g.fillRoundRect(185,685,70,70,10,10);
            }
            g.setColor(new Color(94,43,38));
            g.fillRoundRect(190,690,60,60,10,10);
            g.setColor(healthBack);
            g.fillRoundRect(195,695,50,50,10,10);

            //inventory 3
            if(selected3)
            {
                g.setColor(Color.ORANGE);
                g.fillRoundRect(345,685,70,70,10,10);
            }
            g.setColor(new Color(94,43,38));
            g.fillRoundRect(350,690,60,60,10,10);
            g.setColor(healthBack);
            g.fillRoundRect(355,695,50,50,10,10);

            //inventory 4
            if(selected4)
            {
                g.setColor(Color.ORANGE);
                g.fillRoundRect(505,685,70,70,10,10);
            }
            g.setColor(new Color(94,43,38));
            g.fillRoundRect(510,690,60,60,10,10);
            g.setColor(healthBack);
            g.fillRoundRect(515,695,50,50,10,10);

            //inventory 5
            if(selected5)
            {
                g.setColor(Color.ORANGE);
                g.fillRoundRect(665,685,70,70,10,10);
            }
            g.setColor(new Color(94,43,38));
            g.fillRoundRect(670,690,60,60,10,10);
            g.setColor(healthBack);
            g.fillRoundRect(675,695,50,50,10,10);

            //medkit
            g.drawImage(med,350,690,60,60,null);

            //apple
            g.drawImage(apple,515,695,50,50,null);

            //cookie
            g.drawImage(cookie,675,695,50,50,null);

            //gas
            g.drawImage(gas,185,685,70,70,null);

            //gun
            g.drawImage(gun,35,695,50,50,null);
            
            g.setFont(minecraft);  
            g.setColor(Color.WHITE);
            g.drawString("" + numMed,405,755);
            g.drawString("" + numMed,405,755);
            g.drawString("" + gunTimeValue,75,755);
            g.drawString("" + numCookies,725,755);
            g.drawString("" + numApples,565,755);
            g.drawString(getNumGas() + "/3",245,755);
        }

        public int getNumGas()
        {
            int count = 0;
            if (gas1Picked) 
                count++;
            if (gas2Picked) 
                count++;
            if (gas3Picked) 
                count++;
            return count;
        }

        // class for a timer that moves the characters and map
        class PlayerMover implements ActionListener
        {
            // everytime timer occurs
            public void actionPerformed(ActionEvent e)
            {
                if(health <= 0)
                {
                    playerTimer.stop();
                    clip2.stop();
                    layout.show(Manager.this,"Game over1");
                }
                int newScreenX = screenX;
                int newScreenY = screenY;
                int newScreenX2 = screenX2;
                int newScreenY2 = screenY2;

                if (moveLeft)
                {
                    newScreenX -= speed;
                    newScreenX2 -= speed;
                }
                else if (moveRight)
                {
                    newScreenX += speed;
                    newScreenX2 += speed;
                }
                if (moveUp)
                {
                    newScreenY -= speed;
                    newScreenY2 -= speed;
                }
                else if (moveDown)
                {
                    newScreenY += speed;
                    newScreenY2 += speed;
                }
                
                int charPosX = (newScreenX + newScreenX2);
                int charPosY = (newScreenY + newScreenY2);

                if(!shiftSprint)
                {
                    if(moveLeft)
                        charPosX-=2;
                    if(moveRight)
                        charPosX+=2;
                    if(moveDown)
                        charPosY+=2;
                    if(moveUp)
                        charPosX-=2; 
                }
                if(shiftSprint)
                {
                    if(moveLeft)
                        charPosX-=3;
                    if(moveRight)
                        charPosX+=3;
                    if(moveDown)
                        charPosY+=2;
                    if(moveUp)
                        charPosX-=2; 
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
                if (moveLeft)
                {
                    if(canMove)
                    {
                        xBar += speed;
                        gas1X += speed*8;
                        gas2X += speed*8;
                        gas3X += speed*8;
                    }
                }
                else if (moveRight)
                {
                    if(canMove)
                    {
                        xBar -= speed;
                        gas1X -= speed*8;
                        gas2X -= speed*8;
                        gas3X -= speed*8;
                    }
                }
                if (moveUp)
                {
                    if(canMove)
                    {
                        yBar += speed;
                        gas1Y += speed*5;
                        gas2Y += speed*5;
                        gas3Y += speed*5;
                    }
                }
                else if (moveDown)
                {
                    if(canMove)
                    {
                        yBar -= speed;
                        gas1Y -= speed*5;
                        gas2Y -= speed*5;
                        gas3Y -= speed*5;
                    }
                }
                if(!noMove)
                {
                    noMoveTime -= 4;
                    if(noMoveTime == 0)
                    {
                        noMove = true;
                        noMoveTime = 3000;
                    }
                }
                if(noMove)
                {
                    noMoveTime2-=4;
                    if(noMoveTime2 == 0)
                    {
                        noMove = false;
                        noMoveTime2 = 300;
                        noMoveTime = 3000;
                    }
                    if(moving && noMoveTime2 <= 200)
                    {
                        health-=100;
                        noMove = false;
                        noMoveTime2 = 300;
                        noMoveTime = 3000;
                    }
                }
                if(!barneySpawn)
                {
                    index+=4;
                    showingSentence = showingSentence2 = showingSentence3 = showingSentence4 = "";
                    for(int i = 0; i <= (int)((index/7)); i++)
                    {
                        if(i < 133)
                        {
                            if(i >= 99)
                                showingSentence4 += "" + beginSentence.charAt(i);
                            else if(i >= 63)
                                showingSentence3 += "" + beginSentence.charAt(i);
                            else if(i >= 30)
                                showingSentence2 += "" + beginSentence.charAt(i);
                            else
                                showingSentence += "" + beginSentence.charAt(i);
                        }
                    }
                }
                if(cookiesActivated)
                {
                    if(numCookies > 0)
                    {
                        cookieTime -= 4;
                        if(cookieTime <= 0)
                            cookiesActivated = false;
                    }
                }
                if(stun)
                {
                    stunTime+=40;
                    if(stunTime >= 3000)
                    {
                        barneySpeed = 1;
                        stun = false;
                        stunTime = 0;
                    }
                    else
                        barneySpeed = 0;
                }
                if(bulletCooldown)
                {
                    gunTime-=4;
                    gunTimeValue = "" + gunTime;
                    if(gunTime <= 999)
                    {
                        gunTimeValue = "";
                        gunTime = 10000;
                        bulletCooldown = false;
                    }
                    else
                        gunTimeValue = "" + gunTimeValue.charAt(1) + "." + gunTimeValue.charAt(2);
                    if(gunTimeValue.equals("0.0"))
                    {
                        bulletCooldown = false;
                        gunTimeValue = "";
                    }
                }
                if(shiftSprint)
                {
                    if(sprintInt > 0)
                        sprintInt--;
                    else if(sprintInt == 0)
                        shiftSprint = false;
                }
                if(!shiftSprint)
                {
                    if(sprintInt < 125)
                        sprintInt++;
                }
                stamina = sprintInt*2;
                if(cookiesActivated)
                {
                    shiftSprint = true;
                    sprintInt = 125;
                }
                if(shiftSprint)
                    speed = 3;
                else
                    speed = 2;
                if (moving)
                {
                    im++;
                    String ims = "images/";
                    if (!movingLeft)
                    {
                        if (im == 29)
                            im = 1;
                        ims += "Run" + im + ".png";
                    }
                    else
                    {
                        if (im >= 26)
                            im = 1;
                        ims += "Left" + im + ".png";
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
                if(canMove)
                {
                    screenX = newScreenX;
                    screenY = newScreenY;
                    screenX2 = newScreenX2;
                    screenY2 = newScreenY2;
                }
                
                int xBar2 = xBar + 100;
                int yBar2 = yBar + 150;
                
                if(!barAttackCool)
                {
                    if(xBar <= xPos && xPos+75 <= xBar2 && yBar <= yPos && yPos+75 <= yBar2)
                    {
                        health-=100;
                        barAttackCool = true;
                    }
                    
                }
                else
                {
                    barCooldown-=4;
                    if(barCooldown == 0)
                    {
                        barAttackCool = false;
                        barCooldown = 500;
                    }
                }

                if (!barneySpawn)
                {
                    health = 250;
                    barneyInt+=4;
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
                else
                {
                    if (xPos > xBar)
                    {
                        xBar += barneySpeed;
                    }
                    else if (xBar > 400)
                    {
                        xBar -= barneySpeed;
                    }
                    if (yPos > yBar)
                    {
                        yBar += barneySpeed;
                    }
                    else if (yPos < yBar)
                    {
                        yBar -= barneySpeed;
                    }
                }
                if(!damage)
                    health = 250;
                repaint();
                grabFocus();
            }
        }

        // movement input
        public void keyPressed(KeyEvent e)
        {
            if(e.getKeyChar() == 'g')
            {
                damage = !damage;
            }
            if(e.getKeyChar() == 'e')
            {
                playerTimer.stop();
                layout.show(Manager.this, "endCutscene1");
            }
            if(e.getKeyChar() == 'k')
            {
                numCookies = 5;
                numApples = 5;
                numMed = 5;
            }
            if(e.getKeyCode() == KeyEvent.VK_SHIFT)
            {
                shiftSprint = true;
            }
            if (e.getKeyChar() == 'd' || e.getKeyChar() == 'a' || e.getKeyChar() == 's' || e.getKeyChar() == 'w'||e.getKeyCode() == KeyEvent.VK_SHIFT)
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
        // nothing inside, does nothing
        public void keyTyped(KeyEvent e)
        {}
        
        public void mouseClicked(MouseEvent e)
        {
            if(selected1)
            {
                if(!bulletCooldown)
                {
                    stun = true;
                    bulletCooldown = true;
                    
                }
            }
            else if(selected2)
            {}
            else if(selected3)
            {
                if(numMed > 0)
                {
                    health += 50;
                    if(health > 250)
                    {
                        health = 250;
                    }
                    numMed--;
                }
            }
            else if(selected4)
            {
                if(numApples > 0)
                {
                    numApples--;
                    stamina += 50;
                    if(stamina >= 125)
                        sprintInt = 125;
                }
            }
            else if(selected5)
            {
                if(numCookies > 0)
                {
                    numCookies--;
                    cookiesActivated = true;
                }
            }
            else
            {
                final int GAS_SIZE = 60;
                int x = e.getX();
                int y = e.getY();

                if (gas1X <= x && x <= gas1X + GAS_SIZE && gas1Y <= y && y <= gas1Y + GAS_SIZE)
                {
                    gas1Picked = true;
                }

                if (gas2X <= x && x <= gas2X + GAS_SIZE && gas2Y <= y && y <= gas2Y + GAS_SIZE)
                {
                    gas2Picked = true;
                }

                if (gas3X <= x && x <= gas3X + GAS_SIZE && gas3Y <= y && y <= gas3Y + GAS_SIZE)
                {
                    gas3Picked = true;
                }
            }
            repaint();
            grabFocus();
        }

        public void mousePressed(MouseEvent e)
        {}

        public void mouseReleased(MouseEvent e)
        {}

        public void mouseEntered(MouseEvent e)
        {}

        public void mouseExited(MouseEvent e)
        {}
    }
    class GameOver1 extends JPanel
    {
        Image barneyBloodEnd;
        public GameOver1()
        {
            setLayout(new BorderLayout());
            JButton menu = new JButton("Menu");
            menu.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    layout.show(Manager.this,"start");
                }
            });
            JPanel blank = new JPanel();
            blank.setOpaque(false);
            blank.add(menu);
            add(blank, BorderLayout.CENTER);
            setBackground(Color.BLACK);
            barneyBloodEnd = new ImageIcon("images/BarneyEnd1.png").getImage();
        }
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.drawImage(barneyBloodEnd,0,0,800,800,null);
            g.setFont(new Font("Minecraft",Font.BOLD,50));
            g.setColor(Color.WHITE);
            g.drawString("Game Over!",100,100);
        }
    }
    /*class EndCutscene1 extends JPanel
    {
        Image endback, car;
        Timer carBarMover;
        int xCarPos;
        public EndCutscene1()
        {
            setBackground(Color.BLACK);
            xCarPos = 0;
            endback = new ImageIcon("images/EndBack1.png").getImage();
            car = new ImageIcon("images/Car_Red_Side.png").getImage();
            CarMover carmover = new CarMover();
            carBarMover = new Timer(1, carmover);
            carBarMover.start();
        }
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.drawImage(endback,0,0,800,800,null);
            g.drawImage(car,xCarPos,400,200,100,null);
        }
        class CarMover implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                xCarPos++;
                repaint();
                grabFocus();
            }
        }
    }*/
}