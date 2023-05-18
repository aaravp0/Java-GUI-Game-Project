import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
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

class Level1 extends JPanel implements KeyListener, MouseListener
{
    private Manager manager;
    private Image run1, back, barneyBlood, number, gas, med, gun, apple, cookie, stunIcon, bloodHead;
    private Timer playerTimer;
    private int im, xPos, yPos, health, xBar, yBar, screenX, screenY, screenX2, screenY2, barneyInt, speed,
            sprintInt, stamina, numMed, gunTime, barneySpeed, numCookies, numApples, stunTime, cookieTime, index,
            noMoveTime, noMoveTime2, barCooldown;
    private int[] med1, med2, med3, med4, med5, gas1, gas2, gas3;
    private boolean moving, movingLeft, moveLeft, moveRight, moveUp, moveDown, barneySpawn, shiftSprint, started,
            selected1, selected2, selected3, selected4, selected5, bulletCooldown, stun, cookiesActivated,
            songStarted, noMove, barAttackCool, damage;
    private boolean gas1Picked, gas2Picked, gas3Picked;
    private PlayerMover playerTime;
    private JButton inv1, inv2, inv3, inv4, inv5;
    private String gunTimeValue, beginSentence, showingSentence, showingSentence2, showingSentence3,
            showingSentence4;
    private Font minecraft;
    private Rectangle[] currentBorder;
    private Clip clip2;

    // declares all of the variables and timers
    public Level1(Manager manager)
    {
        this.manager = manager;
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
        /*gas1X = 2450;
        gas1Y = -2800;
        gas2X = 500;
        gas2Y = -975;
        gas3X = 5425;
        gas3Y = 600;*/
        gas1 = itemCoordinateMaker();
        gas1[0] -= (screenX+screenX2);
        gas1[0] -= (screenY+screenY);
        System.out.println(gas1[0] + " " + gas1[1]);
        gas2 = itemCoordinateMaker();
        gas3 = itemCoordinateMaker();

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
        selected1 = false;
        selected2 = false;
        selected3 = false;
        selected4 = false;
        selected5 = false;
        inv1 = new JButton("");
        inv1.setBounds(30, 690, 60, 60);
        inv1.setBorderPainted(false);
        inv1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(!selected1)
                {
                    selected1 = true;
                    selected2 = false;
                    selected3 = false;
                    selected4 = false;
                    selected5 = false;
                }
                else
                    selected1 = false;
            }
        });
        inv2 = new JButton("");
        inv2.setBounds(190, 690, 60, 60);
        inv2.setBorderPainted(false);
        inv2.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(!selected2)
                {
                    selected1 = false;
                    selected2 = true;
                    selected3 = false;
                    selected4 = false;
                    selected5 = false;
                }
                else
                    selected2 = false;
            }
        });
        inv3 = new JButton("");
        inv3.setBounds(350, 690, 60, 60);
        inv3.setBorderPainted(false);
        inv3.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(!selected3)
                {
                    selected1 = false;
                    selected2 = false;
                    selected3 = true;
                    selected4 = false;
                    selected5 = false;
                }
                else
                    selected3 = false;
            }
        });
        inv4 = new JButton("");
        inv4.setBounds(510, 690, 60, 60);
        inv4.setBorderPainted(false);
        inv4.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(!selected4)
                {
                    selected1 = false;
                    selected2 = false;
                    selected3 = false;
                    selected4 = true;
                    selected5 = false;
                }
                else
                    selected4 = false;
            }
        });
        inv5 = new JButton("");
        inv5.setBounds(670, 690, 60, 60);
        inv5.setBorderPainted(false);
        inv5.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(!selected5)
                {
                    selected1 = false;
                    selected2 = false;
                    selected3 = false;
                    selected4 = false;
                    selected5 = true;
                }
                else
                    selected5 = false;
            }
        });
        JPanel blank = new JPanel();
        blank.setOpaque(false);
        blank.setSize(800, 700);
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
    // paints the sprites in the level
    public void paintComponent(Graphics g)
    {
        if (!songStarted)
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
        if (!started)
            playerTimer.start();
        super.paintComponent(g);
        g.drawImage(back, 0, 0, 800, 800, 2 * screenX, 2 * screenY, 2 * screenX2, 2 * screenY2, null, null);

        if (noMove)
        {
            g.setColor(Color.RED);
            g.fillRect(0, 0, 800, 800);
            g.drawImage(bloodHead, 200, 200, 400, 400, null);
        }

        if (gas1[0] > -1 && gas1[1] > -1 && !gas1Picked)
        {
            //g.drawImage(gas, gas1X, gas1Y, 50, 50, null);
            g.drawImage(gas, gas1[0], gas1[1], 50, 50, null);
        }

        if (gas2[0] > -1 && gas2[1] > -1 && !gas2Picked)
        {
            //g.drawImage(gas, gas2X, gas2Y, 50, 50, null);
            g.drawImage(gas, gas2[0], gas2[1], 50, 50, null);
        }

        if (gas3[0] > -1 && gas3[0] > -1 && !gas3Picked)
        {
            //g.drawImage(gas, gas3X, gas3Y, 50, 50, null);
            g.drawImage(gas, gas3[0], gas3[1], 60, 60, null);
        }

        g.drawImage(run1, 400, 400, 75, 75, null);
        // letters at beginning
        if (!barneySpawn)
        {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Minecraft", Font.BOLD, 30));
            g.drawString(showingSentence, 100, 210);
            g.drawString(showingSentence2, 100, 240);
            g.drawString(showingSentence3, 100, 270);
            g.drawString(showingSentence4, 100, 300);
        }

        // barney
        if (barneySpawn && xBar > -1 && yBar > -1)
            g.drawImage(barneyBlood, xBar, yBar, 100, 150, null);
        if (stun)
            g.drawImage(stunIcon, xBar - 25, yBar - 50, 125, 100, null);
        g.drawImage(number, 350, 50, 100, 100, null);

        // stamina and health back part
        Color healthBack = new Color(197, 167, 119);
        g.setColor(healthBack);
        g.fillRect(530, 20, 260, 75);
        g.fillRect(20, 20, 260, 75);

        // health green
        Color healthCol = new Color(26, 232, 39);
        g.setColor(healthCol);
        g.fillRect(535, 25, health, 65);

        // stamina orange
        g.setColor(new Color(255, 174, 39));
        g.fillRect(25, 25, stamina, 65);

        // inventory 1
        if (selected1)
        {
            g.setColor(Color.ORANGE);
            g.fillRoundRect(25, 685, 70, 70, 10, 10);
        }
        g.setColor(new Color(94, 43, 38));
        g.fillRoundRect(30, 690, 60, 60, 10, 10);
        g.setColor(healthBack);
        g.fillRoundRect(35, 695, 50, 50, 10, 10);

        // inventory 2
        if (selected2)
        {
            g.setColor(Color.ORANGE);
            g.fillRoundRect(185, 685, 70, 70, 10, 10);
        }
        g.setColor(new Color(94, 43, 38));
        g.fillRoundRect(190, 690, 60, 60, 10, 10);
        g.setColor(healthBack);
        g.fillRoundRect(195, 695, 50, 50, 10, 10);

        // inventory 3
        if (selected3)
        {
            g.setColor(Color.ORANGE);
            g.fillRoundRect(345, 685, 70, 70, 10, 10);
        }
        g.setColor(new Color(94, 43, 38));
        g.fillRoundRect(350, 690, 60, 60, 10, 10);
        g.setColor(healthBack);
        g.fillRoundRect(355, 695, 50, 50, 10, 10);

        // inventory 4
        if (selected4)
        {
            g.setColor(Color.ORANGE);
            g.fillRoundRect(505, 685, 70, 70, 10, 10);
        }
        g.setColor(new Color(94, 43, 38));
        g.fillRoundRect(510, 690, 60, 60, 10, 10);
        g.setColor(healthBack);
        g.fillRoundRect(515, 695, 50, 50, 10, 10);

        // inventory 5
        if (selected5)
        {
            g.setColor(Color.ORANGE);
            g.fillRoundRect(665, 685, 70, 70, 10, 10);
        }
        g.setColor(new Color(94, 43, 38));
        g.fillRoundRect(670, 690, 60, 60, 10, 10);
        g.setColor(healthBack);
        g.fillRoundRect(675, 695, 50, 50, 10, 10);

        // medkit
        g.drawImage(med, 350, 690, 60, 60, null);

        // apple
        g.drawImage(apple, 515, 695, 50, 50, null);

        // cookie
        g.drawImage(cookie, 675, 695, 50, 50, null);

        // gas
        g.drawImage(gas, 195, 695, 50, 50, null);

        // gun
        g.drawImage(gun, 35, 695, 50, 50, null);

        g.setFont(minecraft);
        g.setColor(Color.WHITE);
        g.drawString("" + numMed, 405, 755);
        g.drawString("" + numMed, 405, 755);
        if(bulletCooldown)
            g.drawString("" + gunTimeValue, 75, 755);
        g.drawString("" + numCookies, 725, 755);
        g.drawString("" + numApples, 565, 755);
        g.drawString(getNumGas() + "/3", 245, 755);
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

    public int[] itemCoordinateMaker()
    {
        int[]arr1 =  new int[]
        {
            (int)(Math.random()*1620+1),
            (int)(Math.random()*1620+1)
        };
        boolean works = true;
        for (int i = 0; i < currentBorder.length; i++)
        {
            if (currentBorder[i].contains(arr1[0],arr1[1]))
            {
                works = false;
                break;
            }
        }
        if(works)
            return arr1;
        else
            return itemCoordinateMaker();
    }

    // class for a timer that moves the characters and map
    class PlayerMover implements ActionListener
    {
        // everytime timer occurs
        public void actionPerformed(ActionEvent e)
        {
            if (health <= 0 || getNumGas() == 3)
            {
                playerTimer.stop();
                clip2.stop();
                manager.showGameOver();
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

            if (!shiftSprint)
            {
                if (moveLeft)
                    charPosX -= 2;
                if (moveRight)
                    charPosX += 2;
                if (moveDown)
                    charPosY += 2;
                if (moveUp)
                    charPosX -= 2;
            }
            if (shiftSprint)
            {
                if (moveLeft)
                    charPosX -= 3;
                if (moveRight)
                    charPosX += 3;
                if (moveDown)
                    charPosY += 2;
                if (moveUp)
                    charPosX -= 2;
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
                if (canMove)
                {
                    xBar += speed;
                    gas1[0] += speed * 8;
                    gas2[0] += speed * 8;
                    gas3[0] += speed * 8;
                }
            }
            else if (moveRight)
            {
                if (canMove)
                {
                    xBar -= speed;
                    gas1[0] -= speed * 8;
                    gas2[0] -= speed * 8;
                    gas3[0] -= speed * 8;
                }
            }
            if (moveUp)
            {
                if (canMove)
                {
                    yBar += speed;
                    gas1[1] += speed * 5;
                    gas2[1] += speed * 5;
                    gas3[1] += speed * 5;
                }
            }
            else if (moveDown)
            {
                if (canMove)
                {
                    yBar -= speed;
                    gas1[1] -= speed * 5;
                    gas2[1] -= speed * 5;
                    gas3[1] -= speed * 5;
                }
            }
            if (!noMove)
            {
                noMoveTime -= 4;
                if (noMoveTime == 0)
                {
                    noMove = true;
                    noMoveTime = 3000;
                }
            }
            if (noMove)
            {
                noMoveTime2 -= 4;
                if (noMoveTime2 == 0)
                {
                    noMove = false;
                    noMoveTime2 = 300;
                    noMoveTime = 3000;
                }
                if (moving && noMoveTime2 <= 200)
                {
                    health -= 100;
                    noMove = false;
                    noMoveTime2 = 300;
                    noMoveTime = 3000;
                }
            }
            if (!barneySpawn)
            {
                index += 4;
                showingSentence = showingSentence2 = showingSentence3 = showingSentence4 = "";
                for (int i = 0; i <= (int) ((index / 7)); i++)
                {
                    if (i < 133)
                    {
                        if (i >= 99)
                            showingSentence4 += "" + beginSentence.charAt(i);
                        else if (i >= 63)
                            showingSentence3 += "" + beginSentence.charAt(i);
                        else if (i >= 30)
                            showingSentence2 += "" + beginSentence.charAt(i);
                        else
                            showingSentence += "" + beginSentence.charAt(i);
                    }
                }
            }
            if (cookiesActivated)
            {
                if (numCookies > 0)
                {
                    cookieTime -= 4;
                    if (cookieTime <= 0)
                        cookiesActivated = false;
                }
            }
            if (stun)
            {
                stunTime += 40;
                if (stunTime >= 3000)
                {
                    barneySpeed = 1;
                    stun = false;
                    stunTime = 0;
                }
                else
                    barneySpeed = 0;
            }
            if (bulletCooldown)
            {
                gunTime -= 4;
                gunTimeValue = "" + gunTime;
                if (gunTime <= 1000)
                {
                    gunTimeValue = "";
                    gunTime = 10000;
                    bulletCooldown = false;
                }
                else
                    gunTimeValue = "" + gunTimeValue.charAt(1) + "." + gunTimeValue.charAt(2);
                if (gunTimeValue.equals("0.0"))
                {
                    bulletCooldown = false;
                    gunTimeValue = "";
                }
            }
            if (shiftSprint)
            {
                if (sprintInt > 0)
                    sprintInt--;
                else if (sprintInt == 0)
                    shiftSprint = false;
            }
            if (!shiftSprint)
            {
                if (sprintInt < 125)
                    sprintInt++;
            }
            stamina = sprintInt * 2;
            if (cookiesActivated)
            {
                shiftSprint = true;
                sprintInt = 125;
            }
            if (shiftSprint)
                speed = 3;
            else
                speed = 2;
            if (moving && canMove)
            {
                im++;
                String ims = "images/";
                if (!movingLeft)
                {
                    
                    if (im >= 8)
                        im = 1;
                    ims += "Right" + im + ".png";
                }
                else
                {
                    if (im == 8)
                        im = 1;
                    ims += "Run" + im + ".png";
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
            if (canMove)
            {
                screenX = newScreenX;
                screenY = newScreenY;
                screenX2 = newScreenX2;
                screenY2 = newScreenY2;
            }

            int xBar2 = xBar + 100;
            int yBar2 = yBar + 150;

            if (!barAttackCool)
            {
                if (xBar <= xPos && xPos + 75 <= xBar2 && yBar <= yPos && yPos + 75 <= yBar2)
                {
                    health -= 100;
                    barAttackCool = true;
                }

            }
            else
            {
                barCooldown -= 4;
                if (barCooldown == 0)
                {
                    barAttackCool = false;
                    barCooldown = 500;
                }
            }

            if (!barneySpawn)
            {
                health = 250;
                barneyInt += 4;
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
            if (!damage)
                health = 250;
            repaint();
            grabFocus();
        }
    }

    // movement input
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyChar() == 'g')
        {
            damage = !damage;
        }
        if (e.getKeyChar() == 'e')
        {
            playerTimer.stop();
            manager.showGameOver();
        }
        if (e.getKeyChar() == 'k')
        {
            numCookies = 5;
            numApples = 5;
            numMed = 5;
        }
        if (e.getKeyCode() == KeyEvent.VK_SHIFT)
        {
            shiftSprint = true;
        }
        if (e.getKeyChar() == 'd' || e.getKeyChar() == 'a' || e.getKeyChar() == 's' || e.getKeyChar() == 'w'
                || e.getKeyCode() == KeyEvent.VK_SHIFT)
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
    {
    }

    public void mouseClicked(MouseEvent e)
    {
        if (selected1)
        {
            if (!bulletCooldown)
            {
                stun = true;
                bulletCooldown = true;
            }
        }
        else if (selected2)
        {
        }
        else if (selected3)
        {
            if (numMed > 0)
            {
                health += 50;
                if (health > 250)
                {
                    health = 250;
                }
                numMed--;
            }
        }
        else if (selected4)
        {
            if (numApples > 0)
            {
                numApples--;
                stamina += 50;
                if (stamina >= 125)
                    sprintInt = 125;
            }
        }
        else if (selected5)
        {
            if (numCookies > 0)
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

            if (gas1[0] <= x && x <= gas1[0] + GAS_SIZE && gas1[1] <= y && y <= gas1[1] + GAS_SIZE)
            {
                gas1Picked = true;
            }

            if (gas2[0] <= x && x <= gas2[0] + GAS_SIZE && gas2[1] <= y && y <= gas2[1] + GAS_SIZE)
            {
                gas2Picked = true;
            }

            if (gas3[0] <= x && x <= gas3[0] + GAS_SIZE && gas3[1] <= y && y <= gas3[1] + GAS_SIZE)
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