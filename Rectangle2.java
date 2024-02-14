//checks border for level 2
public class Rectangle2
{
    private int topLeftX, topLeftY;
    private int bottomRightX, bottomRightY;

    // passes in the coordinates for the border
    public Rectangle2(int topLeftX, int topLeftY, int bottomRightX, int bottomRightY)
    {
        this.topLeftX = topLeftX * 800 / 1215;
        this.topLeftY = topLeftY * 800 / 1215;
        this.bottomRightX = bottomRightX * 800 / 1215;
        this.bottomRightY = bottomRightY * 800 / 1215;
    }

    // returns a boolean to check if the next place the character will move to is in
    // a border
    public boolean contains(int x, int y)
    {
        return topLeftX <= x && x <= bottomRightX && topLeftY <= y && y <= bottomRightY
                || topLeftX <= x + 50 && x + 50 <= bottomRightX && topLeftY <= y + 50 && y + 50 <= bottomRightY;
    }
}