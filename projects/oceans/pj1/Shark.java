

public class Shark
{
    public int personalStarveTime;
    private int originalStarveTime;
    public final static int type = 1;
    public boolean ateThisTurn;
    public final static boolean eatenThisTurn = false;
    public Shark(int x, int y, int starveTime)
    {
        this.personalStarveTime = starveTime;
        this.originalStarveTime = starveTime;
    }
    
    public void resetTime() {
        this.personalStarveTime = this.originalStarveTime;
    }
    
    public void reduceTime(){
        this.personalStarveTime--;
        ateThisTurn = false;
    }
    
   
    public int getTime()    /** Accessor method for time **/
    {
        return this.personalStarveTime;
    }
    
    
}
