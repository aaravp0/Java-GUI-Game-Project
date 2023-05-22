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
    private Image night, button, level1, level2, level3, level1S, level2S, level3S;
    private boolean level1Hover, level2Hover, level3Hover;

    private static final int NUM_ROWS = 7;
    private static final int BUTTON_WIDTH = 400;
    private static final int BUTTON_HEIGHT = 80;

    private static final String NIGHT_IMAGE = "images/Night.png";

    // declares the components of the level screen
    public LevelSelect(Manager manager)
    {
        button = new ImageIcon("images/Button.png").getImage();
        level1 = new ImageIcon("images/Level1Unselected.png").getImage();
        level2 = new ImageIcon("images/Level2Unselected.png").getImage();
        level3 = new ImageIcon("images/Level3Unselected.png").getImage();
        level1S = new ImageIcon("images/Level1Selected.png").getImage();
        level2S = new ImageIcon("images/Level2Selected.png").getImage();
        level3S = new ImageIcon("images/Level3Selected.png").getImage();
        level1Hover = level2Hover = level3Hover = true;
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
        levelOneButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            // when mouse is over the button
            public void mouseEntered(MouseEvent e)
            {
                level1Hover = false;
                repaint();
            }

            // when mouse is away from the button
            public void mouseExited(MouseEvent e)
            {
                level1Hover = true;
                repaint();
            }
        });
        levelTwoButton = new JButton("");
        levelTwoButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            // when mouse is over the button
            public void mouseEntered(MouseEvent e)
            {
                level2Hover = false;
                repaint();
            }

            // when mouse is away from the button
            public void mouseExited(MouseEvent e)
            {
                level2Hover = true;
                repaint();
            }
        });
        levelThreeButton = new JButton("");
        levelThreeButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            // when mouse is over the button
            public void mouseEntered(MouseEvent e)
            {
                level3Hover = false;
                repaint();
            }

            // when mouse is away from the button
            public void mouseExited(MouseEvent e)
            {
                level3Hover = true;
                repaint();
            }
        });
        levelOneButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        levelTwoButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        levelThreeButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        levelOneButton.setBorderPainted(false);
        levelTwoButton.setBorderPainted(false);
        levelThreeButton.setBorderPainted(false);

        rows[1].add(levelOneButton);
        rows[3].add(levelTwoButton);
        rows[5].add(levelThreeButton);

        // the first level
        levelOneButton.addActionListener(e -> manager.showLevelOne());

        // the second level
        levelTwoButton.addActionListener(e ->manager.showLevelTwo());

        // the third level
        levelThreeButton.addActionListener(e -> manager.showLevelThree());
        setBackground(Color.WHITE);
    }

    // nothing currently, but paints the levels screen
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(night, 0, 0, NightOfBarney.FRAME_DIMS.width, NightOfBarney.FRAME_DIMS.height, null);
        g.drawImage(button,200,116,400,80,null);
        g.drawImage(button,200,335,400,80,null);
        g.drawImage(button,200,555,400,80,null);
        if(level1Hover)
            g.drawImage(level1,330,133,140,45,null);
        else
            g.drawImage(level1S,330,133,140,45,null);
        if(level2Hover)
            g.drawImage(level2,330,352,140,45,null);
        else
            g.drawImage(level2S,330,352,140,45,null);
        if(level3Hover)
            g.drawImage(level3,330,572,140,45,null);
        else
            g.drawImage(level3S,330,572,140,45,null);
    }
}
