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
import javax.xml.transform.TransformerConfigurationException;

class Manager extends JPanel
{
    private CardLayout layout;
    private Blood bloodPanel;
    private Timer bloodTimer;
    private String nextLevelName;

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

        bloodPanel = new Blood();

        add(new StartScreen(this), START_SCREEN_NAME);
        add(new JPanel(), END_SCREEN_NAME);
        add(bloodPanel, BLOOD_PANEL_NAME);
        add(new Level1(this), LEVEL_1_NAME);
        add(new Level2(), LEVEL_2_NAME);
        // add(new Level3(), LEVEL_3_NAME);
        add(new LevelSelect(), LEVEL_SELECT_NAME);
        add(new GameOver1(), GAME_OVER_NAME);

        setVisible(true);
    }

    public void showLevelOne()
    {
        nextLevelName = LEVEL_1_NAME;
        playBloodTransition();
    }

    public void showLevelTwo()
    {
        nextLevelName = LEVEL_2_NAME;
        playBloodTransition();
    }

    public void showLevelThree()
    {
        nextLevelName = LEVEL_2_NAME;
        playBloodTransition();
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

    public void playBloodTransition()
    {
        layout.show(Manager.this, BLOOD_PANEL_NAME);
        playScream();
        bloodTimer.start();
    }

    public void moveToSetLevel()
    {
        layout.show(Manager.this, nextLevelName);
    }

    // JPanel for the Blood dripping down
    class Blood extends JPanel
    {
        private Image night, bloodDrip;
        private int bloodHeight;

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
    class LevelSelect extends JPanel
    {
        private JButton levelOneButton, levelTwoButton, levelThreeButton;
        private JPanel[] rows;
        private Image night, med, gas;

        private static final int NUM_ROWS = 7;
        private static final int BUTTON_WIDTH = 400;
        private static final int BUTTON_HEIGHT = 80;

        private static final String NIGHT_IMAGE = "images/Night.png";

        // declares the components of the level screen
        public LevelSelect()
        {
            night = new ImageIcon(NIGHT_IMAGE).getImage();
            rows = new JPanel[NUM_ROWS];

            setLayout(new GridLayout(NUM_ROWS, 1));
            for (int i = 0; i < NUM_ROWS; i++)
            {
                rows[i] = new JPanel();
                rows[i].setOpaque(false);
                add(rows[i]);
            }

            levelOneButton = new JButton("");
            levelTwoButton = new JButton("");
            levelThreeButton = new JButton("");
            levelOneButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
            levelTwoButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
            levelThreeButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

            rows[1].add(levelOneButton);
            rows[3].add(levelTwoButton);
            rows[5].add(levelThreeButton);

            // the first level
            levelOneButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    showLevelOne();
                }
            });

            // the second level
            levelTwoButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    showLevelTwo();
                }
            });

            // the third level
            levelThreeButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    showLevelThree();
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
            g.drawImage(night, 0, 0, NightOfBarney.FRAME_WIDTH, NightOfBarney.FRAME_HEIGHT, null);
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