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

    private final String SCREAM_FILE = "sounds/mixkit-angry-monster-scream-1963.wav";

    // calls runIt()
    public Manager()
    {
        screamClip = openClip(SCREAM_FILE);
        runIt();
    }

    private Clip openClip(String fileName)
    {
        Clip clip = null;
        try
        {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(fileName).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception e)
        {
            System.out.printf("Unable to open audio file %s\n", fileName);
            e.printStackTrace(); 
            System.exit(1);
        }

        return clip;
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
        JPanel lvl2 = new Level2();

        add(new StartScreen(this), "start");
        add(new JPanel(), "end");
        add(new Blood(), "blood");
        add(new Level1(this), "level 1");
        add(lvl2, "level 2");
        // add(new Level3(), "level 3");
        add(new LevelScreen(), "level select");
        add(new GameOver1(), "Game over1");

        setVisible(true);
    }

    public void showLevelOne()
    {
        first = true;
        layout.show(Manager.this, "blood");
        playScream();
        bloodTimer.start();
    }

    public void showLevelSelect()
    {
        layout.show(Manager.this, "level select");
        playScream();
    }

    public void showGameOver()
    {
        layout.show(Manager.this, "Game over1");
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