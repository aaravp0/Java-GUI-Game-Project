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
import java.awt.GraphicsEnvironment;
import java.io.*;
import java.awt.FontFormatException;

//class that manages all of the panels in cardlayout
class Manager extends JPanel
{
    private CardLayout layout;
    private Blood bloodPanel;
    private Level1 lvl1;
    private Level2 lvl2;
    private Level3p1 lvl3;
    private Level3p2 lvl3p2;
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
    private static final String LEVEL_3_P2_NAME = "level3p2";
    private static final String LEVEL_SELECT_NAME = "level select";
    private static final String GAME_OVER_NAME = "Game over1";
    private static final String RETURN_NAME = "return";

    // calls runIt()
    public Manager()
    {
        screamClip = FileUtils.openClip(SCREAM_FILE);
        runIt();
    }

    // plays the scream audio file
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

        lvl1 = new Level1(this);
        lvl2 = new Level2(this);
        lvl3 = new Level3p1(this);
        lvl3p2 = new Level3p2(this);
        bloodPanel = new Blood();

        add(new StartScreen(this), START_SCREEN_NAME);
        add(new JPanel(), END_SCREEN_NAME);
        add(bloodPanel, BLOOD_PANEL_NAME);
        add(lvl1, LEVEL_1_NAME);
        add(lvl2, LEVEL_2_NAME);
        add(lvl3, LEVEL_3_NAME);
        add(lvl3p2, LEVEL_3_P2_NAME);
        add(new LevelSelect(this), LEVEL_SELECT_NAME);
        add(new GameOver1(), GAME_OVER_NAME);
        add(new Return(), RETURN_NAME);

        setVisible(true);
    }

    // returns to level select jpanel
    public void showReturn()
    {
        layout.show(Manager.this, RETURN_NAME);
    }

    // shows level 1
    public void showLevelOne()
    {
        nextLevelName = LEVEL_1_NAME;
        lvl1.reset(this);
        playBloodTransition();
    }

    // shows level 2
    public void showLevelTwo()
    {
        nextLevelName = LEVEL_2_NAME;
        lvl2.reset(this);
        playBloodTransition();
    }

    // shows level 3
    public void showLevelThree()
    {
        nextLevelName = LEVEL_3_NAME;
        lvl3.reset(this);
        playBloodTransition();
    }

    // shows the second part to level 3
    public void showPartTwo()
    {
        nextLevelName = LEVEL_3_P2_NAME;
        lvl3p2.reset(this);
        playBloodTransition();
    }

    // shows the level select screen
    public void showLevelSelect()
    {
        layout.show(Manager.this, LEVEL_SELECT_NAME);
        playScream();
    }

    // shows the game over screen
    public void showGameOver()
    {
        layout.show(Manager.this, GAME_OVER_NAME);
    }

    // shows the blood transition between panels
    public void playBloodTransition()
    {
        layout.show(Manager.this, BLOOD_PANEL_NAME);
        playScream();
        bloodTimer.start();
    }

    // shows the chosen level
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
            g.drawImage(night, 0, 0, NightOfBarney.FRAME_DIMS.width, NightOfBarney.FRAME_DIMS.height, null);
            g.drawImage(bloodDrip, 0, 0, NightOfBarney.FRAME_DIMS.width, bloodHeight, null);
        }

        // class for the timer
        class BloodMover implements ActionListener
        {
            // everytime the timer does an action, it stretches the blood
            public void actionPerformed(ActionEvent e)
            {
                bloodHeight += BLOOD_SPEED;
                if (bloodHeight >= NightOfBarney.FRAME_DIMS.height)
                {
                    bloodTimer.stop();
                    moveToSetLevel();
                }
                repaint();
                grabFocus();
            }
        }
    }

    // the game over screen
    class GameOver1 extends JPanel
    {
        Image barneyBloodEnd;
        private Font minecraft, minecraft2;

        // declares layout, byttons, and images
        public GameOver1()
        {
            try
            {
                minecraft = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/minecraft.ttf")).deriveFont(50f);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(minecraft);
            } catch (IOException e)
            {
                e.printStackTrace();
            } catch (FontFormatException e)
            {
                e.printStackTrace();
            }
            try
            {
                minecraft2 = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/minecraft.ttf")).deriveFont(18f);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(minecraft);
            } catch (IOException e)
            {
                e.printStackTrace();
            } catch (FontFormatException e)
            {
                e.printStackTrace();
            }
            setLayout(new BorderLayout());
            JButton menu = new JButton("Menu");
            // button for menu's action listnener
            menu.addActionListener(new ActionListener()
            {
                // what happens every time the menu button is clicked
                public void actionPerformed(ActionEvent e)
                {
                    layout.show(Manager.this, "start");
                }
            });
            menu.setBackground(Color.RED);
            menu.setForeground(Color.WHITE);
            menu.setOpaque(true);
            menu.setBorderPainted(false);
            menu.setFont(minecraft2);
            JPanel blank = new JPanel();
            blank.setOpaque(false);
            menu.setPreferredSize(new Dimension(200, 80));
            blank.add(menu);
            add(blank, BorderLayout.CENTER);
            blank.setSize(800, 360);
            add(blank, BorderLayout.NORTH);
            add(blank, BorderLayout.SOUTH);
            setBackground(Color.BLACK);
            barneyBloodEnd = new ImageIcon("images/BarneyEnd1.png").getImage();
        }

        // paints the game over screen
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.drawImage(barneyBloodEnd, 0, 0, 800, 800, null);
            g.setFont(minecraft);
            g.setColor(Color.WHITE);
            g.drawString("Game Over!", 230, 100);
        }
    }

    // goes back to level screen and congratulates player
    class Return extends JPanel
    {
        JButton goBack;
        Font minecraft;
        Image character, night;

        public Return()
        {
            minecraft = null;
            try
            {
                minecraft = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/minecraft.ttf")).deriveFont(25f);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(minecraft);
            } catch (IOException e)
            {
                e.printStackTrace();
            } catch (FontFormatException e)
            {
                e.printStackTrace();
            }
            setLayout(new BorderLayout());
            JPanel blank = new JPanel();
            blank.setSize(800, 730);
            add(blank, BorderLayout.CENTER);
            JPanel blank2 = new JPanel();
            blank.setOpaque(false);
            blank2.setOpaque(false);
            goBack = new JButton("Return");
            goBack.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    showLevelSelect();
                }
            });
            goBack.setPreferredSize(new Dimension(200, 80));
            goBack.setBackground(Color.RED);
            goBack.setForeground(Color.WHITE);
            goBack.setOpaque(true);
            goBack.setBorderPainted(false);
            goBack.setFont(minecraft);
            character = new ImageIcon("images/MainStand.png").getImage();
            night = new ImageIcon("images/Night.png").getImage();
            add(blank2, BorderLayout.SOUTH);
            blank2.add(goBack);
            setBackground(Color.BLACK);
        }

        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.drawImage(night, 0, 0, 800, 800, null);
            g.drawImage(character, 290, 350, 200, 200, null);
            g.setFont(minecraft);
            g.setColor(new Color(255, 255, 255));
            g.drawString("CONGRATULATIONS!", 260, 200);
        }
    }
}