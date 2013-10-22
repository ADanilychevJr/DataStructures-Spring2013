
public class Tile
{
    
    public Object holder;
    public int type;
    public final static int EMPTY = 0;
    public final static int SHARK = 1;
    public final static int FISH = 2;
    public int eatenThisTurn = 0;
    public boolean eaten;
    public boolean ateThisTurn = true;
    public boolean newShark = false;
    public boolean newFish = false;
    public int personalStarveTime;
    public int starveTime;
    
    /**
     * Constructor for objects of class Tile
     */
    public Tile()
    {
        holder = new Ghost();
        this.type = 0;
        this.eaten = false;
        this.personalStarveTime = -1;
    }
    
    public Tile(Shark animal) {
        this.holder = animal;
        this.type = animal.type;
        newFish = false;
        newShark = false;
        ateThisTurn = true;
        personalStarveTime = animal.getTime();
        starveTime = animal.getTime();
        this.eaten = false;
    }
    
    public Tile(Fish animal) {
        this.holder = animal;
        this.type = animal.type;
        newFish = false;
        newShark = false;
        this.eaten = false;
        this.personalStarveTime = -1;
    }
    
    public int getType(){
        return type;
    }
}
