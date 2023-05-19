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
import java.awt.Font;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

class Manager extends JPanel
{
    private CardLayout layout;
    private Timer bloodTimer;
    private boolean first, second, third;

    private Clip screamClip;

    private static final String SCREAM_FILE = "sounds/mixkit-angry-monster-scream-1963.wav";

    // identifiers for each of the cards
    private static final String START_SCREEN_NAME = "start";
    private static final String END_SCREEN_NAME = "end";
    private static final String BLOOD_PANEL_NAME = "blood";
    private static final String LEVEL_1_NAME = "level 1";
    private static final String LEVEL_2_NAME = "level 2";
    private static final String LEVEL_3_NAME = "level 3";
    private static final String LEVEL_SELECT_NAME = "level select";
    private static final String GAME_OVER_NAME = "Game over1";

    // calls runIt()
    public Manager()
    {
        screamClip = FileUtils.openClip(SCREAM_FILE);
        runIt();
    }

    private void playScream()
    {
        screamClip.setFramePosition(0);
        screamClip.start();
    }

    // creates panels and adds them to cardlayout
    public void runIt()
    {
        layout = new CardLayout();
        setLayout(layout);

        add(new StartScreen(this), START_SCREEN_NAME);
        add(new JPanel(), END_SCREEN_NAME);
        add(new Blood(), BLOOD_PANEL_NAME);
        add(new Level1(this), LEVEL_1_NAME);
        add(new Level2(), LEVEL_2_NAME);
        // add(new Level3(), LEVEL_3_NAME);
        add(new LevelScreen(), LEVEL_SELECT_NAME);
        add(new GameOver1(), GAME_OVER_NAME);

        setVisible(true);
    }

    public void showLevelOne()
    {
        first = true;
        layout.show(Manager.this, BLOOD_PANEL_NAME);
        playScream();
        bloodTimer.start();
    }

    public void showLevelSelect()
    {
        layout.show(Manager.this, LEVEL_SELECT_NAME);
        playScream();
    }

    public void showGameOver()
    {
        layout.show(Manager.this, GAME_OVER_NAME);
    }

    public void moveToSetLevel()
    {
        if (first)
        {
            layout.show(Manager.this, LEVEL_1_NAME);
        }
        else if (second)
        {
            layout.show(Manager.this, LEVEL_2_NAME);
        }
        else if (third)
        {
            layout.show(Manager.this, LEVEL_3_NAME);
        }
    }

    // JPanel for the Blood dripping down
    class Blood extends JPanel
    {
        Image night, bloodDrip;
        int bloodHeight;

        private static final String NIGHT_IMAGE = "images/Night.png";
        private static final String BLOOD_IMAGE = "images/Blood.png";

        // delay in milliseconds
        private static final int TIMER_DELAY = 10;

        // how many pixels the blood moves down per timer tick
        private static final int BLOOD_SPEED = 3 * TIMER_DELAY;

        // declares the timer
        public Blood()
        {
            setBackground(Color.WHITE);
            bloodTimer = new Timer(TIMER_DELAY, new BloodMover());
            night = new ImageIcon(NIGHT_IMAGE).getImage();
            bloodDrip = new ImageIcon(BLOOD_IMAGE).getImage();
        }

        // paint the blood
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.drawImage(night, 0, 0, NightOfBarney.FRAME_WIDTH, NightOfBarney.FRAME_HEIGHT, null);
            g.drawImage(bloodDrip, 0, 0, NightOfBarney.FRAME_WIDTH, bloodHeight, null);
        }

        // class for the timer
        class BloodMover implements ActionListener
        {
            // everytime the timer does an action, it stretches the blood
            public void actionPerformed(ActionEvent e)
            {
                bloodHeight += BLOOD_SPEED;
                if (bloodHeight >= NightOfBarney.FRAME_WIDTH)
                {
                    bloodTimer.stop();
                    moveToSetLevel();
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
        Image night, med, gas;

        // declares the components of the level screen
        public LevelScreen()
        {
            night = new ImageIcon("images/Night.png").getImage();
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
                    layout.show(Manager.this, "start");
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
            g.drawImage(barneyBloodEnd, 0, 0, 800, 800, null);
            g.setFont(new Font("Minecraft", Font.BOLD, 50));
            g.setColor(Color.WHITE);
            g.drawString("Game Over!", 100, 100);
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