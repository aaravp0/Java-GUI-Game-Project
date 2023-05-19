import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

// has the level screen
class LevelSelect extends JPanel
{
    private Manager manager;

    private JButton levelOneButton, levelTwoButton, levelThreeButton;
    private JPanel[] rows;
    private Image night, med, gas;

    private static final int NUM_ROWS = 7;
    private static final int BUTTON_WIDTH = 400;
    private static final int BUTTON_HEIGHT = 80;

    private static final String NIGHT_IMAGE = "images/Night.png";

    // declares the components of the level screen
    public LevelSelect(Manager manager)
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
                manager.showLevelOne();
            }
        });

        // the second level
        levelTwoButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                manager.showLevelTwo();
            }
        });

        // the third level
        levelThreeButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                manager.showLevelThree();
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
