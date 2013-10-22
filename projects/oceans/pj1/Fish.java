
public class Fish
{    
    private int xCoordinate;
    private int yCoordinate;
    public final static int type = 2;
    public int eatenThisTurn;
    public Fish(int x, int y)
    {
        this.xCoordinate = x;
        this.yCoordinate = y;
        eatenThisTurn = 0;
    }

    public int getX() {
        return xCoordinate;
    }
    
    public int getY() {
        return yCoordinate;
    }
}
