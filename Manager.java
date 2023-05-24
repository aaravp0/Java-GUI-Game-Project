import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.awt.Font;
import java.awt.Dimension;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

//class that manages all of the panels in cardlayout
class Manager extends JPanel
{
    private CardLayout layout;
    private Blood bloodPanel;
    private Timer bloodTimer;
    private String nextLevelName;

    private Clip screamClip;

    private static final String SCREAM_FILE = "sounds/mixkit-angry-monster-scream-1963.wav";

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

    //plays the scream audio file
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
        add(new LevelSelect(this), LEVEL_SELECT_NAME);
        add(new GameOver1(), GAME_OVER_NAME);

        setVisible(true);
    }

    //shows level 1
    public void showLevelOne()
    {
        nextLevelName = LEVEL_1_NAME;
        playBloodTransition();
    }
    //shows level 2
    public void showLevelTwo()
    {
        nextLevelName = LEVEL_2_NAME;
        playBloodTransition();
    }
    //shows level 3
    public void showLevelThree()
    {
        nextLevelName = LEVEL_2_NAME;
        playBloodTransition();
    }
    //shows the level select screen
    public void showLevelSelect()
    {
        layout.show(Manager.this, LEVEL_SELECT_NAME);
        playScream();
    }
    //shows the game over screen
    public void showGameOver()
    {
        layout.show(Manager.this, GAME_OVER_NAME);
    }
    //shows the blood transition between panels
    public void playBloodTransition()
    {
        layout.show(Manager.this, BLOOD_PANEL_NAME);
        playScream();
        bloodTimer.start();
    }
    //shows the chosen level
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

        private static final int TIMER_DELAY = 10;

        private static final int BLOOD_SPEED = 3 * TIMER_DELAY;

        // declares the timer
        public Blood()
        {
            setBackground(Color.WHITE);
            bloodTimer = new Timer(TIMER_DELAY, new BloodMover());
            night = new ImageIcon(NIGHT_IMAGE).getImage();
            bloodDrip = new ImageIcon(BLOOD_IMAGE).getImage();
        }

        // paints the blood
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

    //the game over screen
    class GameOver1 extends JPanel
    {
        Image barneyBloodEnd;
        //declares layout, byttons, and images
        public GameOver1()
        {
            setLayout(new BorderLayout());
            JButton menu = new JButton("Menu");
            //button for menu's action listnener
            menu.addActionListener(new ActionListener()
            {
                //what happens every time the menu button is clicked
                public void actionPerformed(ActionEvent e)
                {
                    layout.show(Manager.this, "start");
                }
            });
            JPanel blank = new JPanel();
            blank.setOpaque(false);
            menu.setPreferredSize(new Dimension(200,80));
            blank.add(menu);
            add(blank, BorderLayout.CENTER);
            blank.setSize(800,360);
            add(blank, BorderLayout.NORTH);
            add(blank, BorderLayout.SOUTH);
            setBackground(Color.BLACK);
            barneyBloodEnd = new ImageIcon("images/BarneyEnd1.png").getImage();
        }
        //paints the game over screen
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.drawImage(barneyBloodEnd, 0, 0, 800, 800, null);
            g.setFont(new Font("Minecraft", Font.BOLD, 50));
            g.setColor(Color.WHITE);
            g.drawString("Game Over!", 100, 100);
        }
    }
    //end cutscene, commented out because not finished
    /*class EndCutscene1 extends JPanel
    {
        Image endback, car;
        Timer carBarMover;
        int xCarPos;
        //set values and starts timers
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
        //paints the cutscene
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.drawImage(endback,0,0,800,800,null);
            g.drawImage(car,xCarPos,400,200,100,null);
        }
        //class for timer
        class CarMover implements ActionListener
        {
        //updates position of items in the cutscene
        public void actionPerformed(ActionEvent e)
        {
            xCarPos++;
            repaint();
            grabFocus();
        }
    }
}*/
}