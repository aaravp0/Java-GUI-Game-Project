public class Rectangle 
{
    private int topLeftX, topLeftY;
    private int bottomRightX, bottomRightY;

    public Rectangle(int topLeftX, int topLeftY, int bottomRightX, int bottomRightY) 
    {
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
        this.bottomRightX = bottomRightX;
        this.bottomRightY = bottomRightY;
    }

    public boolean contains(int x, int y) 
    {
        return topLeftX <= x && x <= bottomRightX && topLeftY <= y && y <= bottomRightY || topLeftX <= x+75 && x+75 <= bottomRightX && topLeftY <= y+75 && y+75 <= bottomRightY;
    }
}