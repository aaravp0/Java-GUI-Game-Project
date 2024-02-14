//checks border for level 3
public class Rectangle3
{
    private int topLeftX, topLeftY;
    private int bottomRightX, bottomRightY;

    // passes in the coordinates for the border
    public Rectangle3(int topLeftX, int topLeftY, int bottomRightX, int bottomRightY)
    {
        this.topLeftX = topLeftX * 800 / 1550;
        this.topLeftY = topLeftY * 800 / 1550;
        this.bottomRightX = bottomRightX * 800 / 1426;
        this.bottomRightY = bottomRightY * 800 / 1426;
    }

    // returns a boolean to check if the next place the character will move to is in
    // a border
    public boolean contains(int x, int y)
    {
        return topLeftX <= x && x <= bottomRightX && topLeftY <= y && y <= bottomRightY
                || topLeftX <= x + 50 && x + 50 <= bottomRightX && topLeftY <= y + 50 && y + 50 <= bottomRightY;
    }
}