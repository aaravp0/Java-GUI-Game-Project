import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class StartScreen extends JPanel 
{
    private Manager manager;
    private Clip themeSong;
    private final String THEME_SONG = "sounds/barneyTheme.wav";
    private JButton toStartButton, toSelectButton;
    private Image night, the, nig, ht, of, bar, ney, button, start, hoverStart, hoveringStart, levelSelected,
            levelUnselected, bloody, sad;
    private boolean startHover, levelHover;

    // adds mouse listeners and components to the start screen
    public StartScreen(Manager manager)
    {
        this.manager = manager;
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
                themeSong.stop();
                manager.showLevelOne();
            }
        });

        toSelectButton = new JButton("");
        // when the level button is pressed
        toSelectButton.addActionListener(new ActionListener()
        {
            // everytime button is clicked
            public void actionPerformed(ActionEvent e)
            {
                manager.showLevelSelect();
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
