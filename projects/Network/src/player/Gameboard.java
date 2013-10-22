package player;
import list.*;

public class Gameboard {

	protected static final int WHITE = 1;
	protected static final int BLACK = 0;
	protected static final int EMPTY = -1;
	protected static final int SIZE = 8;
	protected int whiteChips;
	protected int blackChips;


	private static int UP = 1;
	private static int UP_RIGHT = 2;
	private static int RIGHT = 3;
	private static int DOWN_RIGHT = 4;
	private static int DOWN = 5;
	private static int DOWN_LEFT = 6;
	private static int LEFT = 7;
	private static int UP_LEFT = 8;

	// Stores all the Chips that are on the GameBoard in a two-dimensional array.
	// Empty spaces are denoted with a color of -1 that corresponds to Chip.EMPTY;
	public Chip[][] board;

	// Instantiates an empty GameBoard object;
	public Gameboard(){
		board = new Chip[SIZE][SIZE];
		for(int x = 0; x<SIZE; x++){
			for(int y = 0; y<SIZE; y++){
				board[y][x] = new Chip(x,y, EMPTY, this);
			}
		}
		whiteChips = 0;
		blackChips = 0;
	}

	// check if a network exist in the game board and ends the game if any player has won
	protected boolean hasNetwork(int color) {
		DList goalChips = chipsInGoal(color);
		DListNode currNode = goalChips.front();
		while (currNode != null) {
			if (checkForNetwork((Chip) currNode.item)) {
				return true;
			}
			currNode = goalChips.next(currNode);
		}
		return false;
	}

	private boolean checkForNetwork(Chip c) {
		DList connections = c.findConnections();
		DListNode currNode = connections.front();
		DList currNetwork = new DList();
		currNetwork.insertBack(c);
		while (currNode != null) {
			currNetwork.insertBack(currNode.item);
			if(networkHelper((Chip) currNode.item, currNetwork, slope(c, (Chip) currNode.item))) {
				return true;
			}
			currNetwork.remove(currNode);
			currNode = currNetwork.next(currNode);
		}
		return false;
	}

	private boolean networkHelper(Chip c, DList currNetwork, int prevSlope) { 
		int slope;
		if (currNetwork.size() >= 6 && c.inEndZone()) {
			return true;
		} else {
			DList connects = c.findConnections();
			DListNode currNode = connects.front();
			while (currNode != null) {
				slope = slope(c, (Chip) currNode.item);
				if (!isIn(c,currNetwork) && (prevSlope != slope)) {
					currNetwork.insertBack(currNode.item);
					if(networkHelper((Chip) currNode.item, currNetwork, slope)) {
						return true;
					}
					currNetwork.remove(currNode);
				}
			}
			return false;
		}
	}

	private boolean isIn(Chip c, DList network) {
		DListNode currNode = network.front();
		while (currNode != null) {
			if (((Chip)currNode.item).equals(c)) {
				return true;
			}
			currNode = network.next(currNode);
		}
		return false;
	}

	private int slope (Chip c1, Chip c2) {
		if (c1.getX() > c2.getX()) {
			if (c1.getY() > c2.getY()) {
				return UP_LEFT;
			} else if (c1.getY() == c2.getY()) {
				return LEFT;
			} else {
				return DOWN_LEFT;
			}
		} else if (c1.getX() == c2.getX()) {
			if (c1.getY() > c2.getY()) {
				return UP;
			} else {
				return DOWN;
			}
		} else {
			if (c1.getY() > c2.getY()) {
				return UP_RIGHT;
			} else if (c1.getY() == c2.getY()) {
				return RIGHT;
			} else {
				return DOWN_RIGHT;
			}
		}
	}


	//Returns a list of chips in the starting goal (top for black; left for white)
	public DList chipsInGoal(int color) {
		DList chipsInFirstGoal = new DList();
		for (int i=1; i<SIZE-1; i++) {
			if (color == WHITE) {
				if (board[i][0].color == WHITE) {
					chipsInFirstGoal.insertBack(board[i][0]);
				}
			} else {
				if (board[0][i].color == BLACK) {
					chipsInFirstGoal.insertBack(board[0][i]);
				}
			}
		}
		return chipsInFirstGoal;
	}

	//returns the truth value depending if the Player does (not) have any chips left to play
	public int chipsPlayed(int color){
		if(color == BLACK){
			return blackChips;
		}else if(color == WHITE){
			return whiteChips;
		}
		return 0;
	}


	public static boolean onBoard(int x,int y) {
		if(x<0 || x>7 || y<0 || y>7) {
			return false;
		}
		if((x==0 || x==7) && (y==0 || y==7)) {
			return false;
		}
		return true;
	}

	public Gameboard copyGameboard() {
		Gameboard copy = new Gameboard();
		for(int x=0; x<SIZE; x++) {
			for(int y=0; y<SIZE; y++) {
				copy.board[y][x] = this.board[y][x];
			}
		}
		copy.whiteChips = whiteChips;
		copy.blackChips = blackChips;
		return copy;
	}

	public int getChipColor(int x,int y){
		return board[y][x].color;
	}

	public Chip getChip(int x, int y){
		return board[y][x];
	}

	public DList chipsAround(int xPos,int yPos, int color){
		DList chips = new DList();
		for(int x = xPos-1; x<=xPos+1;x++){
			for(int y= yPos-1; y<=yPos+1;y++){
				if(onBoard(x, y) && !(x==xPos && y==yPos) && getChipColor(x, y)==color){
					chips.insertFront(getChip(x, y));
				}
			}
		}
		return chips;

	}

	public void doMove(Move m, int color) {
		this.addChip(m.x1,m.y1, color);
		if (m.moveKind == Move.STEP){
			this.removeChip(m.x2,m.y2);

		}
	}

	public void undoMove(Move m, int color) {
		if (m.moveKind == Move.STEP){
			this.addChip(m.x2,m.y2,color);
		}
		this.removeChip(m.x1,m.y1);

	}

	public void addChip(int x, int y, int color) { //Assumes that your not adding a chip where you shouldnt
		board[y][x] = new Chip(x,y,color,this);
		if(color == WHITE){
			whiteChips++;
		}else if(color == BLACK){
			blackChips++;
		}
	}

	public void removeChip(int x, int y) {
		if(getChipColor(x,y) == WHITE){
			whiteChips--;
		}else if(getChipColor(x,y) == BLACK){
			blackChips--;
		}
		board[y][x] = new Chip(x,y,EMPTY,this);
	}



	public double evaluateBoard() {
		return 0.0;
	}

	//returns the truth value depending if the Player does (not) have any chips left to play
	public boolean chipsLeft(MachinePlayer p) {
		return chipsPlayed(p.getColor())<10;
	}


	public String toString() {
		String theBoard = new String();
		String header = " ----------\n";
		for(int i=0; i<SIZE; i++) {
			String line = i+"|";
			for(int j=0; j<SIZE; j++) {
				if(board[i][j].color == BLACK) {
					line = line + "B";
				} else if(board[i][j].color == WHITE) {
					line = line + "W";
				} else if(board[i][j].color == EMPTY) {
					line = line + " ";
				}
			}
			line = line + "|\n";
			theBoard = theBoard + line;
		}
		theBoard = "  01234567\n" + header + theBoard + header;
		return theBoard;
	}


}
