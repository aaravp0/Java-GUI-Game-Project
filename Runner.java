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
import java.awt.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.Arrays;
import javax.swing.*;

public class Runner 
{
    private Image[] runningLeft, runningRight;
    private Image standingLeft, standingRight;
    public Runner()
    {
        runningLeft = new Image[]
        {
            new ImageIcon("images/Run1.png").getImage(),
            new ImageIcon("images/Run2.png").getImage(),
            new ImageIcon("images/Run3.png").getImage(),
            new ImageIcon("images/Run4.png").getImage(),
            new ImageIcon("images/Run5.png").getImage(),
            new ImageIcon("images/Run6.png").getImage(),
            new ImageIcon("images/Run7.png").getImage(),
            new ImageIcon("images/Run8.png").getImage()
        };
        runningRight = new Image[]
        {
            new ImageIcon("images/Right1.png").getImage(),
            new ImageIcon("images/Right2.png").getImage(),
            new ImageIcon("images/Right3.png").getImage(),
            new ImageIcon("images/Right4.png").getImage(),
            new ImageIcon("images/Right5.png").getImage(),
            new ImageIcon("images/Right6.png").getImage(),
            new ImageIcon("images/Right7.png").getImage(),
            new ImageIcon("images/Right8.png").getImage()
        };
        standingLeft = new ImageIcon("images/MainStandLeft2.png").getImage();
        standingRight = new ImageIcon("images/MainStand2.png").getImage();
    }
    public Image returnImage(int speed, boolean left, boolean moving)
    {
        if(moving)
        {
            if(left)
                return runningLeft[speed];
            else if(!left)
                return runningRight[speed];
        }
        
        if(left)
            return standingLeft;
        else
            return standingRight;
    }
}
