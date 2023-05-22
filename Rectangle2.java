public class Rectangle2 
{
    private int topLeftX, topLeftY;
    private int bottomRightX, bottomRightY;

    public Rectangle2(int topLeftX, int topLeftY, int bottomRightX, int bottomRightY) 
    {
        this.topLeftX = topLeftX*800/1215;
        this.topLeftY = topLeftY*800/1215;
        this.bottomRightX = bottomRightX*800/1215;
        this.bottomRightY = bottomRightY*800/1215;
    }

    public boolean contains(int x, int y) 
    {
        return topLeftX <= x && x <= bottomRightX && topLeftY <= y && y <= bottomRightY || topLeftX <= x+50 && x+50 <= bottomRightX && topLeftY <= y+50 && y+50 <= bottomRightY;
    }
}