package player;

import list.*;

public class Chip {

	protected int x;
	protected int y;
	protected int color;//  BLACK or WHITE or EMPTY
	protected Gameboard gb;
	// Potentially a field storing all current connections?


	// Constructor for Chip Object. Chip objects know their location in the GameBoard, their 
	// color, and the GameBoard object that they are located in
	public Chip(int location,int color, Gameboard gb){
		this.x = location/10;
		this.y = location%10;
		this.color = color;
		this.gb = gb;

	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public Chip(int x, int y, int color, Gameboard gb){
		this.x = x;
		this.y = y;
		this.color = color;
		this.gb = gb;

	}

	// Returns a DList of Chips that are “connected” to this Chip.  A connection implies these
	// two chips could be a part of a network. 
	//Loops through all eight directions for a chip with its color.
	//If an individual loop hits an opposing chip, break from the loop
	//DList will have at most 8 chips, but probably won't happen
	public DList findConnections(){
		DList connections = new DList();
		//check to the right
		for (int xTemp = this.x+1; xTemp <= 7; xTemp++){
			System.out.println(xTemp);
			if (connectionHelper(xTemp, this.y,connections,this.color)){
				System.out.println("breaking at "+"("+xTemp+","+this.y+")");
				break;
			}
		}
		//check to the left
		for (int xTemp = this.x-1; xTemp >= 0; xTemp--){
			if (connectionHelper(xTemp, this.y,connections,this.color)){
				break;
			}          
		}
		//check down
		for (int yTemp = this.y+1; yTemp <= 7; yTemp++){
			if (connectionHelper(this.x, yTemp,connections,this.color)){
				break;
			}        
		}
		//check up
		for (int yTemp = this.y-1; yTemp >= 0; yTemp--){
			if (connectionHelper(this.x, yTemp,connections,this.color)){
				break;
			}        
		}
		//check the bottom right diagonal
		int xTemp = this.x; int yTemp = this.y;
		while(true){
			xTemp++; yTemp++;
			if (connectionHelper(xTemp,yTemp,connections,this.color)){
				break;
			}
		}
		//check the top right diagonal
		xTemp = this.x; yTemp = this.y;
		while(true){
			xTemp++; yTemp--;
			if (connectionHelper(xTemp,yTemp,connections,this.color)){
				break;
			}
		}
		//check the top left diagonal
		xTemp = this.x; yTemp = this.y;
		while(true){
			xTemp--; yTemp--;
			if (connectionHelper(xTemp,yTemp,connections,this.color)){
				break;
			}
		}
		//check the bottom left diagonal
		xTemp = this.x; yTemp = this.y;
		while(true){
			xTemp--; yTemp++;
			if (connectionHelper(xTemp,yTemp,connections,this.color)){
				break;
			}
		}
		return connections;
	}

	//checks connections for a given x, y. Mutates currentList
	//returns true if loop should stop, false if it should keep going
	private boolean connectionHelper(int x, int y, DList currentList, int thisColor){
		if(Gameboard.onBoard(x,y)){
			Chip otherChip = gb.board[y][x];
			int otherColor = otherChip.color;
			if(otherColor == thisColor){
				currentList.insertFront(otherChip); //add chip to DList if same color
				return true; // stop searching because we found connection
			} else if (otherColor != thisColor && otherColor != Gameboard.EMPTY){
				return true; //stop searching, no connection this direction
			} else {
				return false; //keep searching, don't kill the loop
			}
		} else {
			return true; //tile not on board, stop the loop
		} 
	}


	protected boolean inEndZone() {
		if (color == Gameboard.WHITE) {
			return (x==7 && y>0 && y<7);
		} else if (color == Gameboard.BLACK) {
			return (y==7 && x>0 && x<7);
		} 
		return false;
	}
	
	protected boolean inFirstGoal() {
		if (color == Gameboard.WHITE) {
			return (x==0 && y>0 && y<7);
		} else if (color == Gameboard.BLACK) {
			return (y==0 && x>0 && x<7);
		}
		return false;
	}
	
	public String toString() { 
		String c;
		if (color == Gameboard.BLACK) {
			c = "B";
		} else if(color == Gameboard.WHITE){
			c = "W";
		} else {
			c = "E";
		}
		return c+"("+x+","+y+")";
	}


}
