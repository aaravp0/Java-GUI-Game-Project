//checks border for level 1
public class Rectangle
{
    private int topLeftX, topLeftY;
    private int bottomRightX, bottomRightY;

    // passes in the coordinates for the border
    public Rectangle(int topLeftX, int topLeftY, int bottomRightX, int bottomRightY)
    {
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
        this.bottomRightX = bottomRightX;
        this.bottomRightY = bottomRightY;
    }

    // returns a boolean to check if the next place the character will move to is in
    // a border
    public boolean contains(int x, int y)
    {
        return topLeftX <= x && x <= bottomRightX && topLeftY <= y && y <= bottomRightY
                || topLeftX <= x && x <= bottomRightX && topLeftY <= y && y <= bottomRightY;
    }
}