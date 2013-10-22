/* MachinePlayer.java */

package player;
import list.*;

/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */
public class MachinePlayer extends Player {

	public Gameboard game; //Stores current GameBoard
	public String myName; 
	private int color; // BLACK or WHITE (0 or 1 respectively), does not change
	private int oppColor;
	private int searchDepth; //this MachinePlayerÕs searchDepth (number of moves ahead  this looks)
	protected static final int WHITE = 1;
	protected static final int BLACK = 0;
	private static boolean PLAYER = true;
	private static boolean OPPONENT = false;
	protected DList placedChips;



	// Creates a machine player with the given color.  Color is either 0 (black)
	// or 1 (white).  (White has the first move.)
	public MachinePlayer(int color) {
		this(color,1);
	}

	// Creates a machine player with the given color and search depth.  Color is
	// either 0 (black) or 1 (white).  (White has the first move.)
	public MachinePlayer(int color, int searchDepth) {
		this.color = color;
		this.oppColor = oppositeColor(color);
		this.searchDepth = searchDepth;
		this.game = new Gameboard();
	}

	public Move chooseMove() {
		return bestMove(PLAYER, -1, 1, searchDepth).move;
	}

	// Returns a new move by "this" player.  Internally records the move (updates
	// the internal game board) as a move by "this" player.
	public Best bestMove(boolean side, double alpha, double beta, int depth) {
		Best myBest = new Best(); // My best move
		Best reply; // OpponentÕs best reply

		if (side == PLAYER) {
			if(game.hasNetwork(color)) {
				myBest.score = 1;
			}
		} else {
			if (game.hasNetwork(oppColor)) {
				myBest.score = -1;
			}
		}
		if (depth == 0) {
			myBest.score = game.evaluateBoard();
		}
		if (side == PLAYER) { 
            myBest.score = Integer.MIN_VALUE;    //alpha;
        } else {
            myBest.score = Integer.MAX_VALUE;    //beta;
        }
		DList moves = validMoves(color);
		DListNode currentNode = moves.front();

		while(currentNode != null){
			Move m = (Move) currentNode.item;
			game.doMove(m,color); // Modifies "this" Grid
			reply = bestMove(! side, alpha, beta, depth-1);
			game.undoMove(m,color); // Restores "this" Grid
			if ((side == PLAYER) && (reply.score >= myBest.score)) {
				myBest.move = m;
				myBest.score = reply.score;
				alpha = reply.score;
			} else if ((side == OPPONENT) && (reply.score <= myBest.score)) {
				myBest.move = m;
				myBest.score = reply.score;
				beta = reply.score;
			}
			if (alpha >= beta) { return myBest; }
			
			currentNode = moves.next(currentNode);
		}
		return myBest;
	}

	// If the Move m is legal, records the move as a move by the opponent
	// (updates the internal game board) and returns true.  If the move is
	// illegal, returns false without modifying the internal state of "this"
	// player.  This method allows your opponents to inform you of their moves.
	public boolean opponentMove(Move m) {
		if (isValidMove(m, oppColor)) {
			switch(m.moveKind) 
			{
			case Move.QUIT:
				return false;
			case Move.ADD:
				game.addChip(m.x1, m.y1, oppColor);
				return true;
			case Move.STEP:
				game.removeChip(m.x2, m.y2);
				game.addChip(m.x1,m.y1, oppColor);
				return true;
			}
		}
		return false;
	}

	// If the Move m is legal, records the move as a move by "this" player
	// (updates the internal game board) and returns true.  If the move is
	// illegal, returns false without modifying the internal state of "this"
	// player.  This method is used to help set up "Network problems" for your
	// player to solve.
	public boolean forceMove(Move m) {
		if (isValidMove(m, color)) {
			switch(m.moveKind) 
			{
			case Move.QUIT:
				return false;
			case Move.ADD:
				game.addChip(m.x1, m.y1, color);
				return true;
			case Move.STEP:
				game.removeChip(m.x2, m.y2);
				game.addChip(m.x1,m.y1, color);
				return true;
			}
		}
		return false;
	}


	// Generates a list of all valid moves
	public DList validMoves(int color){
		DList moves = new DList();
		Move tempMove;
		if(game.chipsPlayed(color)>=10){
			for(int x = 0; x<8; x++){
				for(int y = 0; y<8; y++){
					tempMove = new Move(x,y);
					if(isValidMove(tempMove, color)){
						moves.insertFront(tempMove);
					}
				}
			}
		}else{
			for(int x = 0; x<8; x++){
				for(int y = 0; y<8; y++){
					if(game.board[x][y].color==this.color){
						game.removeChip(x,y);
						for(int i = 0; i<8; i++){
							for(int j = 0; j<8; j++){
								tempMove = new Move(i,j);
								if(isValidMove(tempMove, color)){
									moves.insertFront(tempMove);
								}
							}
						}
						game.addChip(x,y,this.color);
					}
				}
			}
		}
		return moves;

	}



	// Determines the score of a move on a scale of -1 to 1, ;
	public int evaluateMove(Move m){
		return 0;

	}


	public int getColor() {
		return this.color;
	}

	public static int oppositeColor(int color) {
		if (color == WHITE) {
			return BLACK;
		} else {
			return WHITE;
		}
	}

	//returns True if  Move is valid (i.e. not off the board, not next to too many chips, not on the other players endzone)
	public boolean isValidMove(Move m, int color){

		if(!Gameboard.onBoard(m.x1,m.y1)){
			return false;
		}

		if(m.moveKind== Move.STEP && m.x1 == m.x2 && m.y1==m.y2){
			return false;
		}

		if(m.moveKind == Move.ADD && game.chipsPlayed(color) >=10){
			return false;
		}

		if(game.board[m.y1][m.x1].color!=Gameboard.EMPTY){
			return false;
		}

		if(color == Gameboard.BLACK && (m.x1 ==0 || m.x1 == 7) ){
			return false;
		}
		else if(color == Gameboard.WHITE && (m.y1 ==0 || m.y1 == 7) ){
			return false;
		}

		DList chipsAround = game.chipsAround(m.x1,m.y1,color);

		if(chipsAround.length()>1){
			return false;
		}else if(chipsAround.length()==1){
			Chip currentChip = (Chip) (chipsAround.front().item);
			if(game.chipsAround(currentChip.x, currentChip.y, currentChip.color).length() >= 1){
				return false;
			}
		}

		return true;

	}

	// Determines if the given game board contains any networks for a given player
	private boolean winnerWinner() {
		return false;
	}

	public static void main(String args[]) {
		MachinePlayer m = new MachinePlayer(BLACK);
		m.forceMove(new Move(1,0));
		m.opponentMove(new Move(3,1));
		m.forceMove(new Move(1,1));
		m.opponentMove(new Move(6,6));
		m.forceMove(new Move(5,2));
		m.opponentMove(new Move(5,5));
		m.forceMove(new Move(4,4));
		//m.opponentMove(new Move(3,4));
		m.forceMove(new Move(6,0));
		m.opponentMove(new Move(0,1));
		m.forceMove(new Move(1,5));
		m.opponentMove(new Move(7,4));
		m.forceMove(new Move(3,3));
		m.opponentMove(new Move(4,3));
		m.forceMove(new Move(1,3));
		//m.opponentMove(new Move(2,2));
		m.forceMove(new Move(4,7));
		m.opponentMove(new Move(0,5));
		m.forceMove(new Move(3,0));
		m.opponentMove(new Move(7,1));
		m.opponentMove(new Move(2,5));
		System.out.println(m.game);
		System.out.println(m.game.hasNetwork(m.color));
//		System.out.println(m.isValidMove(new Move(2,2), m.color));
//		System.out.println(m.isValidMove(new Move(3,3), m.color));
//		System.out.println(m.isValidMove(new Move(3,3,1,1), m.color));
	}
}
