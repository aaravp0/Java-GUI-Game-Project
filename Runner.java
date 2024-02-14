import java.awt.Image;
import javax.swing.ImageIcon;

//class for sending the image for the main character
public class Runner
{
    private Image[] runningLeft, runningRight;
    private Image standingLeft, standingRight;

    // creates the arrays for the images
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

    // return the image for the current character model
    public Image returnImage(int speed, boolean left, boolean moving)
    {
        if (moving)
        {
            if (left)
                return runningLeft[speed];
            else if (!left)
                return runningRight[speed];
        }

        if (left)
            return standingLeft;
        else
            return standingRight;
    }
}
