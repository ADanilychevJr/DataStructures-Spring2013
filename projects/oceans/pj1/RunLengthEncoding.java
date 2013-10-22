/* RunLengthEncoding.java */

/**
 *  The RunLengthEncoding class defines an object that run-length encodes an
 *  Ocean object.  Descriptions of the methods you must implement appear below.
 *  They include constructors of the form
 *
 *      public RunLengthEncoding(int i, int j, int starveTime);
 *      public RunLengthEncoding(int i, int j, int starveTime,
 *                               int[] runTypes, int[] runLengths) {
 *      public RunLengthEncoding(Ocean ocean) {
 *
 *  that create a run-length encoding of an Ocean having width i and height j,
 *  in which sharks starve after starveTime timesteps.
 *
 *  The first constructor creates a run-length encoding of an Ocean in which
 *  every cell is empty.  The second constructor creates a run-length encoding
 *  for which the runs are provided as parameters.  The third constructor
 *  converts an Ocean object into a run-length encoding of that object.
 *
 *  See the README file accompanying this project for additional details.
 */

public class RunLengthEncoding {

  /**
   *  Define any variables associated with a RunLengthEncoding object here.
   *  These variables MUST be private.
   */
  private int starveTime;
  private int width; /** number of columns **/
  private int height; /** number of rows **/
  private DList2 list;
  private DListNode2 count;
  private DListNode2 count2;
  private int nextRunCount = 0;
  private long size;

  /**
   *  The following methods are required for Part II.
   */

  /**
   *  RunLengthEncoding() (with three parameters) is a constructor that creates
   *  a run-length encoding of an empty ocean having width i and height j,
   *  in which sharks starve after starveTime timesteps.
   *  @param i is the width of the ocean.
   *  @param j is the height of the ocean.
   *  @param starveTime is the number of timesteps sharks survive without food.
   */

  public RunLengthEncoding(int i, int j, int starveTime) {
    this.starveTime = starveTime;
    this.width = i; this.height = j;
    list = new DList2(new Run(0,i*j,0));
    count = list.head;
    count2 = list.head;
    this.size = list.size;
  }

  /**
   *  RunLengthEncoding() (with five parameters) is a constructor that creates
   *  a run-length encoding of an ocean having width i and height j, in which
   *  sharks starve after starveTime timesteps.  The runs of the run-length
   *  encoding are taken from two input arrays.  Run i has length runLengths[i]
   *  and species runTypes[i].
   *  @param i is the width of the ocean.
   *  @param j is the height of the ocean.
   *  @param starveTime is the number of timesteps sharks survive without food.
   *  @param runTypes is an array that represents the species represented by
   *         each run.  Each element of runTypes is Ocean.EMPTY, Ocean.FISH,
   *         or Ocean.SHARK.  Any run of sharks is treated as a run of newborn
   *         sharks (which are equivalent to sharks that have just eaten).
   *  @param runLengths is an array that represents the length of each run.
   *         The sum of all elements of the runLengths array should be i * j.
   */

  public RunLengthEncoding(int i, int j, int starveTime,
                           int[] runTypes, int[] runLengths) {
    this.starveTime = starveTime;
    this.width = i; this.height = j;
    list = new DList2();
    for (int a = 0; a < runTypes.length; a++){
        list.insertEnd(new Run(runTypes[a], runLengths[a], a, starveTime));
    }
    count = list.head; count2 = list.head; this.size = list.size;
  }

  /**
   *  restartRuns() and nextRun() are two methods that work together to return
   *  all the runs in the run-length encoding, one by one.  Each time
   *  nextRun() is invoked, it returns a different run (represented as an
   *  array of two ints), until every run has been returned.  The first time
   *  nextRun() is invoked, it returns the first run in the encoding, which
   *  contains cell (0, 0).  After every run has been returned, nextRun()
   *  returns null, which lets the calling program know that there are no more
   *  runs in the encoding.
   *
   *  The restartRuns() method resets the enumeration, so that nextRun() will
   *  once again enumerate all the runs as if nextRun() were being invoked for
   *  the first time.
   *
   *  (Note:  Don't worry about what might happen if nextRun() is interleaved
   *  with addFish() or addShark(); it won't happen.)
   */

  /**
   *  restartRuns() resets the enumeration as described above, so that
   *  nextRun() will enumerate all the runs from the beginning.
   */

  public void restartRuns() {
    count = list.head;
    count2 = list.head;
    nextRunCount = 0;
  }

  /**
   *  nextRun() returns the next run in the enumeration, as described above.
   *  If the runs have been exhausted, it returns null.  The return value is
   *  an array of two ints (constructed here), representing the type and the
   *  size of the run, in that order.
   *  @return the next run in the enumeration, represented by an array of
   *          two ints.  The int at index zero indicates the run type
   *          (Ocean.EMPTY, Ocean.SHARK, or Ocean.FISH).  The int at index one
   *          indicates the run length (which must be at least 1).
   */

  public int[] nextRun() {
    /**int[] temp = new int[2];
    if (count == null) {
        count = count2;
        return null;
    }
    count = count.next;
    
    if (count.item.position == -1){ //checks if iteration is done, if so returns null
        count = count2;
        return null;
    }
    
    temp[0] = count.item.type; temp[1] = count.item.number; //0 is type, 1 is number of whatever
    
    return temp; **/
    
    if (nextRunCount >= this.size) {   //nextRunCount >= this.size
        nextRunCount = 0;
        return null;
    } else {
        nextRunCount++;
        int[] temp = new int[2];
        temp[0] =list.iterate(nextRunCount-1).item.type; temp[1] = list.iterate(nextRunCount-1).item.number;
        //System.out.println("nextRun() returns:" + temp[1]);
        //System.out.println("list contains (should always be 1:" + list.head.next.next.next.item.number);
        return temp;
        
    }
  }
  
  private int typeCheck(Ocean sea){ //checks how many runs there will be
      int numTypes = 0;
      int currType = -1;
      int currStarveTime = -1;
      for (int y = 0; y < sea.height(); y++){
          for (int x = 0; x < sea.width(); x++) {
              if (sea.cellContents(x,y) != currType) {//if different type
                  currStarveTime = sea.personalStarveTime(x,y);
                  currType = sea.cellContents(x,y);
                  numTypes++;
                } else if (sea.personalStarveTime(x,y) != currStarveTime) { //if different shark
                    currStarveTime = sea.personalStarveTime(x,y);
                    numTypes++;
                }
            }
        }
      return numTypes;
    }
    
  private int[] runTypes(Ocean sea){ // makes an array of run types in order
      int[] runTypes = new int[typeCheck(sea)];
      int currType = -1;
      int numTypes = 0;
      int currStarveTime = -1;
      for (int y = 0; y < sea.height(); y++){
          for (int x = 0; x < sea.width(); x++) {
              if (sea.cellContents(x,y) != currType){ //if different type
                  currStarveTime = sea.personalStarveTime(x,y);
                  currType = sea.cellContents(x,y);
                  runTypes[numTypes] = currType;
                  numTypes++;
                } else if (sea.personalStarveTime(x,y) != currStarveTime){
                  currStarveTime = sea.personalStarveTime(x,y);
                  runTypes[numTypes] = currType;
                  numTypes++;
                }
            }
        }
      
      return runTypes;
    }
    
  private int[] runLengths(Ocean sea) { //makes an array of run lengths
      int[] runLengths = new int[typeCheck(sea)];
      int currType=-2;
      int index = 0; int numSameTypes = 1; int currStarveTime=-2;
      for (int y = 0; y < sea.height(); y++){
          for (int x = 0; x < sea.width(); x++) {
              if (x == 0 && y == 0){
                  currType = sea.cellContents(0,0);
                  currStarveTime = sea.personalStarveTime(0,0);
             } else { //MAIN CLAUSE
                    if (sea.cellContents(x,y) == currType && sea.personalStarveTime(x,y) == currStarveTime) { //if exactly same, increment numSameTypes
                        numSameTypes++;
                    } else /**if (sea.cellContents(x,y) == currType && sea.personalStarveTime(x,y) != currStarveTime)**/ { //both sharks but diff starvetime
                        runLengths[index] = numSameTypes;
                        index++;
                        currType = sea.cellContents(x,y);
                        currStarveTime = sea.personalStarveTime(x,y);
                        numSameTypes = 1;
                    
                    }  
                }
            }
        }
      runLengths[index] = numSameTypes;
      return runLengths;
    }
    
  private int[] starveTimes(Ocean sea) { //makes an array of starve Times
      int[] starveTimes = new int[typeCheck(sea)];
      int currType = -1;
      int numTypes = 0;
      int currStarveTime = -1;
      for (int y = 0; y < sea.height(); y++){
          for (int x = 0; x < sea.width(); x++) {
              if (sea.cellContents(x,y) != currType){ //if different type
                  if (currType == -1){//if first cell (obv different)
                      currType = sea.cellContents(x,y);
                      currStarveTime = sea.personalStarveTime(x,y);
                    } else { //not the frist cell but is different
                  starveTimes[numTypes] = currStarveTime;
                  currStarveTime = sea.personalStarveTime(x,y);
                  currType = sea.cellContents(x,y);
                  
                  numTypes++;
                }
                } else if (sea.personalStarveTime(x,y) != currStarveTime){ //shark but different starveTime
                  starveTimes[numTypes] = currStarveTime;
                  currStarveTime = sea.personalStarveTime(x,y);
                  currType = sea.cellContents(x,y);
                  numTypes++;
                } else {
                    
                }
            }
        }
      return starveTimes;
    }

  /**
   *  toOcean() converts a run-length encoding of an ocean into an Ocean
   *  object.  You will need to implement the three-parameter addShark method
   *  in the Ocean class for this method's use.
   *  @return the Ocean represented by a run-length encoding.
   */

  public Ocean toOcean() {
    
    int dListPosition = 0;
    int y = 0;
    int x = 0;
    int positionCounter = 0;
    Ocean newOcean = new Ocean(this.width, this.height, this.starveTime);
    while (dListPosition < list.size){
        int tileType = list.iterate(dListPosition).item.type; 
        int tileNumber = list.iterate(dListPosition).item.number;
        int tileStarveTime = list.iterate(dListPosition).item.personalStarveTime;
        while (tileNumber > 0){
        if (tileType == Ocean.SHARK){
            newOcean.addShark(positionCounter % this.width, positionCounter / this.width, tileStarveTime);
            tileNumber--;
            positionCounter++;
        } else if (tileType == Ocean.FISH) {
            newOcean.addFish(positionCounter % this.width, positionCounter / this.width);
            tileNumber--;
            positionCounter++;
        } else {
            tileNumber--;
            positionCounter++;
        }
    }
    dListPosition++;
}
    
    return newOcean;
  }

  /**
   *  The following method is required for Part III.
   */

  /**
   *  RunLengthEncoding() (with one parameter) is a constructor that creates
   *  a run-length encoding of an input Ocean.  You will need to implement
   *  the sharkFeeding method in the Ocean class for this constructor's use.
   *  @param sea is the ocean to encode.
   */

  public RunLengthEncoding(Ocean sea) {
    this.starveTime = sea.starveTime();
    this.width = sea.width(); this.height = sea.height();
    list = new DList2();
    this.size = typeCheck(sea);
    int[] runTypes = runTypes(sea);
    int[] runLengths = runLengths(sea);
    int[] starveTimes = starveTimes(sea);
    for (int a = 0; a < size; a++){
        list.insertEnd(new Run(runTypes[a], runLengths[a], a, starveTimes[a]));
        //System.out.print(runTypes[a]); System.out.println(runLengths[a]);
    }
    count = list.head; count2 = list.head; 
    check();
  }

  /**
   *  The following methods are required for Part IV.
   */

  /**
   *  addFish() places a fish in cell (x, y) if the cell is empty.  If the
   *  cell is already occupied, leave the cell as it is.  The final run-length
   *  encoding should be compressed as much as possible; there should not be
   *  two consecutive runs of sharks with the same degree of hunger.
   *  @param x is the x-coordinate of the cell to place a fish in.
   *  @param y is the y-coordinate of the cell to place a fish in.
   */

  public void addFish(int x, int y) {
    int positionCounter = 0;
    int position = coordinatesToInt(x,y);
    int runCount = 0;
    int numBefore = -1;
    int numAfter = -1;
    DListNode2 temp = list.head.next;
    while (positionCounter <= position){//find exact run where we insert
        if (this.list.iterate(runCount).item.number + positionCounter < position){
        positionCounter += this.list.iterate(runCount).item.number;
        runCount++;
    } else {
        numBefore = position - positionCounter;        
        temp = this.list.iterate(runCount);
        numAfter = temp.item.number - 1 - numBefore;
        positionCounter++;
    }
    }
    positionCounter--;
    if (temp.item.type == 0){ //check to see if this run is actually empty, if not do nothing
        if (numBefore > 0 && numAfter >0){//check if fish must be put in the middle
            DListNode2 tempBefore = new DListNode2(new Run(0,numBefore,temp.prev.item.position));
            temp.prev.next = tempBefore;
            tempBefore.prev = temp.prev.next;
            DListNode2 tempMiddle = new DListNode2(new Run(2,1,0));
            tempBefore.next = tempMiddle;
            tempMiddle.prev = tempBefore;
            DListNode2 tempAfter = new DListNode2(new Run(0, numAfter, 0));
            tempMiddle.next = tempAfter;
            tempAfter.prev = tempMiddle;
            tempAfter.next = temp;
            temp.prev = tempAfter;
            this.size+=2;
        } else if (temp.item.number == 1){ // if run is empty but is length 1
            if( temp.prev.item.type == 1 && temp.next.item.type == 1){// if run is empty but length 1 and sharks on both sides
                temp.item.type = 2;
            } else if (temp.prev.item.type == 2 && temp.next.item.type == 2){ //if fish on both sides, combine all three
                int totalNumber = temp.prev.item.number + temp.next.item.number + 1;
                temp.prev.item.number = totalNumber; 
                DListNode2 tempAfterConnecter = temp.next.next;
                temp.prev.next = tempAfterConnecter;
                tempAfterConnecter.prev = temp.prev;
                this.size-=2;
            } else if (temp.prev.item.type == 2 && temp.next.item.type == 1){//if fish behind shark in front link them
                temp.prev.item.number +=1;
                temp.prev.next = temp.next;
                temp.next.prev = temp.prev;
                size--;
            } else if (temp.prev.item.type == 1 && temp.next.item.type ==2){
                temp.next.item.number+=1;
                temp.prev.next= temp.next;
                temp.next.prev = temp.prev;
                size--;
            }
        } else if (numBefore == 0 && numAfter > 0){ // if new fish goes at beginning of empty run
            if (temp.prev.item.type == 1){
                DListNode2 tempBefore = new DListNode2(new Run(2,1,0));
                temp.item.number--;
                temp.prev.next = tempBefore;
                tempBefore.prev = temp.prev;
                tempBefore.next = temp;
                temp.prev = tempBefore;
                this.size++;
            } else { //else the before thing was a fish
                temp.prev.item.number++;
                temp.item.number--;
            }
        } else if (numBefore >0 && numAfter == 0){ //if fish goes at the end
            if (temp.next.item.type == 1){//if next run is a shark, make a new node in between
                DListNode2 tempAfter = new DListNode2(new Run(2,1,0));
                temp.item.number--;
                temp.next.prev = tempAfter;
                tempAfter.next = temp.next;
                tempAfter.prev = temp;
                temp.next = tempAfter;
                size++;
            } else { //else the tile after is a fish, just add to it and subtract from the empty run
                temp.next.item.number++;
                temp.item.number--;
            }
         
        }
    } 
    
    check();
  }
  
  public int coordinatesToInt(int x, int y){
      x = xConvert(x);
      y = yConvert(y);
      return (y * this.height) + x;
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

  /**
   *  addShark() (with two parameters) places a newborn shark in cell (x, y) if
   *  the cell is empty.  A "newborn" shark is equivalent to a shark that has
   *  just eaten.  If the cell is already occupied, leave the cell as it is.
   *  The final run-length encoding should be compressed as much as possible;
   *  there should not be two consecutive runs of sharks with the same degree
   *  of hunger.
   *  @param x is the x-coordinate of the cell to place a shark in.
   *  @param y is the y-coordinate of the cell to place a shark in.
   */

  public void addShark(int x, int y) {
    int positionCounter = 0;
    int position = coordinatesToInt(x,y); //guarunteed to work
    int runCount = 0;
    int numBefore = -1;
    int numAfter = -1;
    int beforeHelper=-1;
    DListNode2 temp = list.head.next;
    while (positionCounter <= position){//find exact run where we insert
        if (this.list.iterate(runCount).item.number + positionCounter < position){
        
            
            positionCounter += this.list.iterate(runCount).item.number;
        runCount++;
        beforeHelper = positionCounter;
    } else {
        numBefore = position - beforeHelper;        
        temp = this.list.iterate(runCount);
        numAfter = temp.item.number - 1 - numBefore;
        positionCounter++;
    }
    }
    positionCounter--;
    
    int time = this.starveTime;
    if (temp.item.type == 0){ //check if run is actually empty
        if (numBefore > 0 && numAfter >0) { //check if shark must be inserted inside empty run
            DListNode2 tempBefore = new DListNode2(new Run(0,numBefore,temp.prev.item.position));
            temp.prev.next = tempBefore;
            tempBefore.prev = temp.prev.next;
            DListNode2 tempMiddle = new DListNode2(new Run(1,1,0,this.starveTime));
            tempBefore.next = tempMiddle;
            tempMiddle.prev = tempBefore;
            DListNode2 tempAfter = new DListNode2(new Run(0, numAfter, 0));
            tempMiddle.next = tempAfter;
            tempAfter.prev = tempMiddle;
            tempAfter.next = temp;
            temp.prev = tempAfter;
            this.size+=2;
        } else if (temp.item.number == 1){//check if the empty run has a single tile
            if (temp.prev.item.type == 1 && temp.next.item.type == 1){
                if (temp.prev.item.personalStarveTime == time && temp.next.item.personalStarveTime == time){
                    int totalNumber = temp.prev.item.number + temp.next.item.number + 1;
                    temp.prev.item.number = totalNumber; 
                    DListNode2 tempAfterConnecter = temp.next.next;
                    temp.prev.next = tempAfterConnecter;
                    tempAfterConnecter.prev = temp.prev;
                    this.size-=2;
                } else if (temp.prev.item.personalStarveTime != time && temp.prev.item.personalStarveTime != time){
                    temp.item.type = 1; temp.item.personalStarveTime = time;
                } else if (temp.prev.item.personalStarveTime == time){
                    temp.prev.item.number++;
                    temp.prev.next = temp.next;
                    temp.next.prev = temp.prev;
                    size--;
                } else if (temp.next.item.personalStarveTime == time){
                    temp.next.item.number++;
                    temp.next.prev = temp.prev;
                    temp.prev.next = temp.next;
                    size--;
                }
            } else if (temp.next.item.personalStarveTime == time){
                    temp.next.item.number++;
                    temp.next.prev = temp.prev;
                    temp.prev.next = temp.next;
                    size--;
            } else if (temp.prev.item.personalStarveTime == time){
                    temp.prev.item.number++;
                    temp.prev.next = temp.next;
                    temp.next.prev = temp.prev;
                    size--;
            }
        
        } else if (numBefore == 0 && numAfter > 0) {
            if (temp.prev.item.personalStarveTime == time){
                temp.prev.item.number++;
                temp.item.number--;                
            } else if (temp.prev.item.personalStarveTime != time){
                DListNode2 tempBefore = new DListNode2(new Run(1,1,0));
                temp.item.number--;
                temp.prev.next = tempBefore;
                tempBefore.prev = temp.prev;
                tempBefore.next = temp;
                temp.prev = tempBefore;
                this.size++;
            }
        } else if (numBefore > 0 && numAfter == 0){
            if (temp.next.item.personalStarveTime == time){
                temp.next.item.number++;
                temp.item.number--;
            } else if (temp.next.item.personalStarveTime != time){
                DListNode2 tempAfter = new DListNode2(new Run(1,1,0));
                temp.item.number--;
                temp.next.prev = tempAfter;
                tempAfter.next = temp.next;
                tempAfter.prev = temp;
                temp.next = tempAfter;
                this.size++;
            
            }
        }
        }
  //System.out.println("actual tempBefore:" + list.head.next.next.next.item.type);
           // System.out.println("actual tempBefore's number:" + list.head.next.next.next.item.number);
  
  //check();
}

  /**
   *  check() walks through the run-length encoding and prints an error message
   *  if two consecutive runs have the same contents, or if the sum of all run
   *  lengths does not equal the number of cells in the ocean.
   */

  private void check() {
      long numElements = this.size;
      int x = 0;
      while (x < numElements-1){
          DListNode2 before = this.list.iterate(x);
          DListNode2 after = this.list.iterate(x+1);
          if (before.item.type == after.item.type && before.item.personalStarveTime == after.item.personalStarveTime) {
              System.out.println("Not a valid Run Length Encoding! Loser!");
              System.exit(0);
            }
          x++;
        }
      x = 0;
      int sum = 0;
      while (x < numElements) {
          sum+=this.list.iterate(x).item.number;
          x++;
        }
      if (sum != this.width * this.height || this.size < 1){
          System.out.println("Not a valid Run Length Encoding! Loser!");
              System.exit(0);
        }
      
  }

}
