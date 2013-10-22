

public class Run
{
    public int type; // 0= empty cell
    public int number;
    public int position;
    public int personalStarveTime;

    
    public Run(int position)
    {
        type = 0;
        number = 1;
        this.position = position;
        this.personalStarveTime = -1;
    }
    
    public Run(int type, int number, int position)
    {
        this.type = type;
        this.number = number;
        this.position = position;
        if (type != Ocean.SHARK) {
            this.personalStarveTime = -1;
        }
    }

    public Run(int type, int number, int position, int personalStarveTime)
    {
        this.type = type;
        this.number = number;
        this.position = position;
        this.personalStarveTime = personalStarveTime;
    }
    
}
