/* Ocean.java */

/**
 *  The Ocean class defines an object that models an ocean full of sharks and
 *  fish.  Descriptions of the methods you must implement appear below.  They
 *  include a constructor of the form
 *
 *      public Ocean(int i, int j, int starveTime);
 *
 *  that creates an empty ocean having width i and height j, in which sharks
 *  starve after starveTime timesteps.
 *
 *  See the README file accompanying this project for additional details.
 */

public class Ocean {

  /**
   *  Do not rename these constants.  WARNING:  if you change the numbers, you
   *  will need to recompile Test4.java.  Failure to do so will give you a very
   *  hard-to-find bug.
   */

  public final static int EMPTY = 0;
  public final static int SHARK = 1;
  public final static int FISH = 2;

  /**
   *  Define any variables associated with an Ocean object here.  These
   *  variables MUST be private.
   */
  private int width = 0; /** number of columns **/
  private int height = 0; /** number of rows **/
  private int starveTime = 0;
  private Tile[][] sea;


  /**
   *  The following methods are required for Part I.
   */

  /**
   *  Ocean() is a constructor that creates an empty ocean having width i and
   *  height j, in which sharks starve after starveTime timesteps.
   *  @param i is the width of the ocean.
   *  @param j is the height of the ocean.
   *  @param starveTime is the number of timesteps sharks survive without food.
   */

  public Ocean(int i, int j, int starveTime) {
    if (i < 1 | j < 1 | starveTime < 1) {
        System.out.println("Invalid entries");
        System.exit(0);
    }
    this.width = i; this.height = j; this.starveTime = starveTime; /** assign instance vars **/
    sea = new Tile[height][width];
    for (int a = 0; a < this.width; a++){
        for (int b = 0; b < this.height; b++){
            sea[b][a] = new Tile();
        }
  }
}

  /**
   *  width() returns the width of an Ocean object.
   *  @return the width of the ocean.
   */

  public int width() {
    // Replace the following line with your solution.
    return this.width;
  }

  /**
   *  height() returns the height of an Ocean object.
   *  @return the height of the ocean.
   */

  public int height() {
    // Replace the following line with your solution.
    return this.height;
  }

  /**
   *  starveTime() returns the number of timesteps sharks survive without food.
   *  @return the number of timesteps sharks survive without food.
   */

  public int starveTime() {
    
    return this.starveTime;
  }
  
  public int personalStarveTime(int x, int y){
      y = yConvert(y);
      x = xConvert(x);
      return sea[y][x].personalStarveTime;
    }

  /**
   *  addFish() places a fish in cell (x, y) if the cell is empty.  If the
   *  cell is already occupied, leave the cell as it is.
   *  @param x is the x-coordinate of the cell to place a fish in.
   *  @param y is the y-coordinate of the cell to place a fish in.
   */

  public void addFish(int x, int y) {
    y = yConvert(y);
    x = xConvert(x);
    if (sea[y][x].getType() == 0){
        sea[y][x] = new Tile(new Fish(x,y));
        sea[y][x].eatenThisTurn = 0;
        sea[y][x].ateThisTurn = false;
        sea[y][x].type = 2;
    }
  }
  
  public void removeSomething(int x, int y) {
      y = yConvert(y);
      x = xConvert(x);
      sea[y][x]= new Tile();
    } 

  /**
   *  addShark() (with two parameters) places a newborn shark in cell (x, y) if
   *  the cell is empty.  A "newborn" shark is equivalent to a shark that has
   *  just eaten.  If the cell is already occupied, leave the cell as it is.
   *  @param x is the x-coordinate of the cell to place a shark in.
   *  @param y is the y-coordinate of the cell to place a shark in.
   */

  public void addShark(int x, int y) {
    y = yConvert(y);
    x = xConvert(x);
    if (sea[y][x].getType() == 0) {
        sea[y][x] = new Tile(new Shark(x,y,this.starveTime));
        sea[y][x].eatenThisTurn = 0;
        sea[y][x].ateThisTurn = true;
        sea[y][x].type = 1;
        sea[y][x].newShark = false;
    }
  }

  /**
   *  cellContents() returns EMPTY if cell (x, y) is empty, FISH if it contains
   *  a fish, and SHARK if it contains a shark.
   *  @param x is the x-coordinate of the cell whose contents are queried.
   *  @param y is the y-coordinate of the cell whose contents are queried.
   */

  public int cellContents(int x, int y) {
    y = yConvert(y);
    x = xConvert(x);
    if (sea[y][x].getType() == 0){
        return EMPTY;
    }  else if (sea[y][x].getType() == 1) {
        return SHARK;
    } else {
        return FISH;
    }
    
  }

  /**
   *  timeStep() performs a simulation timestep as described in README.
   *  @return an ocean representing the elapse of one timestep.
   */

  public Ocean timeStep() {
    for (int a = 0; a < this.width; a++){
        for (int b = 0; b < this.height; b++){
            if (cellContents(a,b) == 0){ //If cell is empty, tag it empty, new fish needed or new shark needed
                nullCheck(a,b);
            } else if (cellContents(a,b) == 1){ //If cell has shark, says if it eats this turn or not
                sharkCheck(a,b);
            } else {
                fishCheck(a,b);
            }
        }
    }
    Ocean newOcean = new Ocean(width,height,starveTime);
    for (int a = 0; a < this.width; a++){
        for (int b = 0; b < this.height; b++){
            if (cellContents(a,b) == 0){ //if cell is empty, check tag and make new cell in sea2 based on original cell's tag
                if (sea[b][a].newFish == true){
                    newOcean.addFish(a,b);
                } else if (sea[b][a].newShark == true){
                    newOcean.addShark(a,b);
                }
            } else if (cellContents(a,b) == 1){ //handle shark whether its starved or not
                if (sea[b][a].ateThisTurn == false && sea[b][a].personalStarveTime == 0){
                    newOcean.sea[b][a] = new Tile();
                } else if (sea[b][a].ateThisTurn == false) {
                    newOcean.sea[b][a] = new Tile(new Shark(a,b, sea[b][a].personalStarveTime-1));
                } else {
                    newOcean.addShark(a,b);
                }
            } else {
                if (sea[b][a].newShark ) {
                    newOcean.addShark(a,b);
                } else if ( sea[b][a].eaten) {
                    newOcean.sea[b][a] = new Tile();       
                } else {
                    newOcean.addFish(a,b);
                }
            }
        }
    }
    return newOcean;
  }
  
  public int xConvert(int x){
      if (x>-1){
          return x % width;
        } else{
          int holder = (x % width) + width;
          if (holder < 0 || holder >= width) {
              return xConvert(holder);
            } else {
          return (x % width) + width;
        }
        }
    }
    
  public int yConvert(int y){
      if (y>-1){
          return y % height;
        } else{
          int holder = (y % height) + height;
          if (holder < 0 || holder >= height) {
              return yConvert(holder);
            } else {
          return (y % height) + height;
        }
        }
    }
  
  public void sharkCheck(int x, int y){
      y = yConvert(y);
      x = xConvert(x);
      boolean ate = false;
      if (cellContents(xConvert(x-1), yConvert(y-1)) ==2 ){ // Top left
          sea[yConvert(y-1)][xConvert(x-1)].eatenThisTurn++;
          ate = true;
        } 
      if (cellContents(xConvert(x), yConvert(y-1)) ==2 ){ // Top middle
          sea[yConvert(y-1)][xConvert(x)].eatenThisTurn++;
          ate = true;
        } 
      if (cellContents(xConvert(x+1), yConvert(y-1)) ==2 ){ // Top right
          sea[yConvert(y-1)][xConvert(x+1)].eatenThisTurn++;
          ate = true;
        } 
      if (cellContents(xConvert(x-1), yConvert(y)) ==2 ){ // Middle left
          sea[yConvert(y)][xConvert(x-1)].eatenThisTurn++;
          ate = true;
        } 
      if (cellContents(xConvert(x+1), yConvert(y)) ==2 ){ // Middle right
          sea[yConvert(y)][xConvert(x+1)].eatenThisTurn++;
          ate = true;
        } 
      if (cellContents(xConvert(x-1), yConvert(y+1)) ==2 ){ // Bottom left
          sea[yConvert(y+1)][xConvert(x-1)].eatenThisTurn++;
          ate = true;
        } 
      if (cellContents(xConvert(x), yConvert(y+1)) ==2 ){ // Bottom middle
          sea[yConvert(y+1)][xConvert(x)].eatenThisTurn++;
          ate = true;
        } 
      if (cellContents(xConvert(x+1), yConvert(y+1)) ==2 ){ // Bottom right
          sea[yConvert(y+1)][xConvert(x+1)].eatenThisTurn++;
          ate = true;
        }
      //tag this shark if it ate
      sea[y][x].ateThisTurn = ate;
      
    }
    
  public void nullCheck(int x, int y){
      y = yConvert(y);
      x = xConvert(x);
      int fishCount = 0;
      int sharkCount = 0;
      
      /**if (sea[yConvert(y-1)][xConvert(x-1)].getType() ==1){ // Top left CHECK SHARKS
          sharkCount++;
        } 
      if (sea[yConvert(y-1)][xConvert(x)].getType() ==1){ // Top middle
          sharkCount++;
        } 
      if (sea[yConvert(y-1)][xConvert(x+1)].getType() ==1){ // Top right
          sharkCount++;
        } 
      if (sea[yConvert(y)][xConvert(x-1)].getType() ==1){ // Middle left
          sharkCount++;
        } 
      if (sea[yConvert(y)][xConvert(x+1)].getType() ==1){ // Middle right
          sharkCount++;
        } 
      if (sea[yConvert(y+1)][xConvert(x-1)].getType() ==1){ // Bottom left
          sharkCount++;
        } 
      if (sea[yConvert(y+1)][xConvert(x)].getType() ==1){ // Bottom middle
          sharkCount++;
        } 
      if (sea[yConvert(y+1)][xConvert(x+1)].getType() ==1){ // Bottom right
          sharkCount++;
        }**/
      
      if (cellContents(xConvert(x-1), yConvert(y-1)) == 1 ){ // Top left CHECK SHARKS
          sharkCount++;
        } 
      if (cellContents(xConvert(x), yConvert(y-1)) ==1 ){ // Top middle
          sharkCount++;
        } 
      if (cellContents(xConvert(x+1), yConvert(y-1)) ==1 ){ // Top right
          sharkCount++;
        } 
      if (cellContents(xConvert(x-1), yConvert(y)) ==1 ){ // Middle left
          sharkCount++;
        } 
      if (cellContents(xConvert(x+1), yConvert(y)) ==1 ){ // Middle right
          sharkCount++;
        } 
      if (cellContents(xConvert(x-1), yConvert(y+1)) ==1 ){ // Bottom left
          sharkCount++;
        } 
      if (cellContents(xConvert(x), yConvert(y+1)) ==1 ){ // Bottom middle
          sharkCount++;
        } 
      if (cellContents(xConvert(x+1), yConvert(y+1)) ==1 ){ // Bottom right
          sharkCount++;
        } 
      /** CHECK FISH BELOW, SHARKS ABOVE ^ **/
      if (cellContents(xConvert(x-1), yConvert(y-1)) == 2 ){ // Top left CHECK FISH
          fishCount++;
        } 
      if (cellContents(xConvert(x), yConvert(y-1)) ==2 ){ // Top middle
          fishCount++;
        } 
      if (cellContents(xConvert(x+1), yConvert(y-1)) ==2 ){ // Top right
          fishCount++;
        } 
      if (cellContents(xConvert(x-1), yConvert(y)) ==2 ){ // Middle left
          fishCount++;
        } 
      if (cellContents(xConvert(x+1), yConvert(y)) ==2 ){ // Middle right
          fishCount++;
        } 
      if (cellContents(xConvert(x-1), yConvert(y+1)) ==2 ){ // Bottom left
          fishCount++;
        } 
      if (cellContents(xConvert(x), yConvert(y+1)) ==2 ){ // Bottom middle
          fishCount++;
        } 
      if (cellContents(xConvert(x+1), yConvert(y+1)) ==2 ){ // Bottom right
          fishCount++;
        }
      if (fishCount > 1 && sharkCount < 2) {
          sea[y][x].newFish = true;
          sea[y][x].newShark = false;
        } else if ( fishCount >1 && sharkCount>1) {
            sea[y][x].newShark = true;
            sea[y][x].newFish = false;
        } else {
            sea[y][x].newShark = false;
            sea[y][x].newFish = false;
        }
      
    }
    
  public void fishCheck(int x, int y){
      y = yConvert(y);
      x = xConvert(x);
      int numTimesEaten = 0;
      if (cellContents(xConvert(x-1), yConvert(y-1)) == 1 ){ // Top left
          numTimesEaten++;
        } 
      if (cellContents(xConvert(x), yConvert(y-1)) ==1 ){ // Top middle
          numTimesEaten++;
        } 
      if (cellContents(xConvert(x+1), yConvert(y-1)) ==1 ){ // Top right
          numTimesEaten++;
        } 
      if (cellContents(xConvert(x-1), yConvert(y)) ==1 ){ // Middle left
          numTimesEaten++;
        } 
      if (cellContents(xConvert(x+1), yConvert(y)) ==1 ){ // Middle right
          numTimesEaten++;
        } 
      if (cellContents(xConvert(x-1), yConvert(y+1)) ==1 ){ // Bottom left
          numTimesEaten++;
        } 
      if (cellContents(xConvert(x), yConvert(y+1)) ==1 ){ // Bottom middle
          numTimesEaten++;
        } 
      if (cellContents(xConvert(x+1), yConvert(y+1)) ==1 ){ // Bottom right
          numTimesEaten++;
        }
      sea[y][x].eatenThisTurn = numTimesEaten;
      if (numTimesEaten == 2 || numTimesEaten > 2){
          sea[y][x].newShark = true;
          sea[y][x].eaten = true;
        } else if (numTimesEaten == 1) {
            sea[y][x].eaten = true;
        }
    }

  /**
   *  The following method is required for Part II.
   */

  /**
   *  addShark() (with three parameters) places a shark in cell (x, y) if the
   *  cell is empty.  The shark's hunger is represented by the third parameter.
   *  If the cell is already occupied, leave the cell as it is.  You will need
   *  this method to help convert run-length encodings to Oceans.
   *  @param x is the x-coordinate of the cell to place a shark in.
   *  @param y is the y-coordinate of the cell to place a shark in.
   *  @param feeding is an integer that indicates the shark's hunger.  You may
   *         encode it any way you want; for instance, "feeding" may be the
   *         last timestep the shark was fed, or the amount of time that has
   *         passed since the shark was last fed, or the amount of time left
   *         before the shark will starve.  It's up to you, but be consistent.
   */

  public void addShark(int x, int y, int feeding) { // feeding means personalStarveTime
    y = yConvert(y);
    x = xConvert(x);
    if (sea[y][x].getType() == 0) {
        sea[y][x] = new Tile(new Shark(x,y,this.starveTime));
        sea[y][x].eatenThisTurn = 0;
        sea[y][x].type = 1;
        sea[y][x].newShark = false;
        sea[y][x].personalStarveTime = feeding;
        if (this.starveTime > sea[y][x].personalStarveTime){
          sea[y][x].ateThisTurn = false;
        } else {
            sea[y][x].ateThisTurn = true;
    }
}
  }

  /**
   *  The following method is required for Part III.
   */

  /**
   *  sharkFeeding() returns an integer that indicates the hunger of the shark
   *  in cell (x, y), using the same "feeding" representation as the parameter
   *  to addShark() described above.  If cell (x, y) does not contain a shark,
   *  then its return value is undefined--that is, anything you want.
   *  Normally, this method should not be called if cell (x, y) does not
   *  contain a shark.  You will need this method to help convert Oceans to
   *  run-length encodings.
   *  @param x is the x-coordinate of the cell whose contents are queried.
   *  @param y is the y-coordinate of the cell whose contents are queried.
   */

  public int sharkFeeding(int x, int y) {
    return personalStarveTime(x,y);
  }

}
