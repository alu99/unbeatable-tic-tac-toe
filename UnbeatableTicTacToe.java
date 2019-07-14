import java.util.Scanner;

public class UnbeatableTicTacToe {

	private char[][] board;
	private boolean[] playedMoves;
	private int remainingMoves;
	private int lastCompMove;
	private int strategy;


	public UnbeatableTicTacToe(){

		board = new char[3][3]; //3x3 matrix representing the tic-tac-toe board
		playedMoves = new boolean[9];
		remainingMoves = 9;
		lastCompMove = -1; //tracks whos turn it is
		strategy = -1;
		playerMove();
	}
	
	private void setMoveToPlayed(int x, int y, char symbol) {
		playedMoves[3*x + y] = true;
		board[x][y] = symbol;
		remainingMoves -= 1;
		
		if(symbol == 'o')
			lastCompMove = x*10 + y;
	}
	
	private boolean checkIfMovePlayed(int x, int y) {
		return playedMoves[3*x + y];
	}
	
	//gets an arbitrary unplayed move if computer runs out of strategized moves
	private int[] getArbitraryUnplayedMove() {
		for(int i = 0; i < 9; i++) {
			if(!playedMoves[i]) {
				int[] toReturn = new int[2];
				toReturn[0] = i/3;
				toReturn[1] = i%3;
				return toReturn;
			}
		}
		return null;
	}

	//displays the current state of the board
	public void displayBoard(){

		System.out.print("  ");
		for(int i = 0; i < 3; i++){
			System.out.print(i + "\t");
		}

		System.out.println();

		for(int row = 0; row < 3; row++){
			System.out.print(row + " ");
			for(int col = 0; col < 3; col++){

				if(board[row][col] == 0)
					System.out.print(".\t");

				else
					System.out.print(board[row][col] + "\t");

			}

			System.out.println();
			System.out.println();
		}
	}

	//prompts player for their move, then initiates computer's move
	public void playerMove(){

		displayBoard();
		System.out.print("Enter coordinates (x,y): ");
		Scanner keyboard = new Scanner(System.in);

		String coords = keyboard.nextLine();

		int xCoord = Integer.parseInt(coords.substring(0, 1));
		int yCoord = Integer.parseInt(coords.substring(2));

		if(isValid(xCoord, yCoord))
			setMoveToPlayed(xCoord, yCoord, 'x');

		else{

			System.out.println("INVALID MOVE");
			playerMove();
		}

		if(remainingMoves == 0) {
			System.out.print("DRAW\n");
			displayBoard();
			System.exit(0);
		}
		compMove(xCoord, yCoord);
	}

	//checks if player move is valid
	private boolean isValid(int x, int y){

		if(x > 2 || x < 0)
			return false;

		if(y > 2 || y < 0)
			return false;
		
		if(checkIfMovePlayed(x, y))
			return false;

		return true;
	}

	//determines computer's next move
	public void compMove(int playerx, int playery){

		//strategy for first computer move is special, must handle separately
		if(lastCompMove == -1){
			performFirstCompMove(playerx, playery);
		}

		else{
			int compx = lastCompMove/10; //gets x coordinate of last computer move
			int compy = lastCompMove%10; //gets y coordinate of last computer move
			
			//computer wins if it can get 3 in a row on it's turn
			if(needsBlock(compx, compy, 'x', 'o')) {
				System.out.println("COMPUTER WINS\n");
				displayBoard();
				System.exit(0);
			}
							
			//strategy for when user's first move is in center
			if(strategy == 2){
				if(!needsBlock(playerx, playery, 'o', 'x')) {
					if(isValid(0,2))
						setMoveToPlayed(0, 2, 'o');
					else if(isValid(2,0))
						setMoveToPlayed(2, 0, 'o');
					else if(isValid(2,2))
						setMoveToPlayed(2, 2, 'o');
					else {
						int[] move = getArbitraryUnplayedMove();
						board[move[0]][move[1]] =  'o';
						setMoveToPlayed(move[0], move[1], 'o');
					}
				}
			}

			//strategy for when user's first move is in corner
			else if(strategy == 0){
				//if player doesn't need to be blocked, play neither corner nor center
				if(!needsBlock(playerx, playery, 'o', 'x')){

					if(isValid(1,0))
						setMoveToPlayed(1, 0, 'o');
					else if(isValid(1,2))
						setMoveToPlayed(1, 2, 'o');
					else if(isValid(0,1))
						setMoveToPlayed(0, 1, 'o');
					else if(isValid(2,1))
						setMoveToPlayed(2, 1, 'o');
					else {
						int[] move = getArbitraryUnplayedMove();
						board[move[0]][move[1]] =  'o';
						setMoveToPlayed(move[0], move[1], 'o');
					}
				}

			}
			
			//strategy for when user's first move is neither center nor corner
			else{
				//if player doesn't need to be blocked, play in corner
				if(!needsBlock(playerx, playery, 'o', 'x')){
					if(isValid(0,0))
						setMoveToPlayed(0, 0, 'o');
					else if(isValid(2,0))
						setMoveToPlayed(2, 0, 'o');
					else if(isValid(0,2))
						setMoveToPlayed(0, 2, 'o');
					else if(isValid(2,2))
						setMoveToPlayed(2, 2, 'o');
					else {
						int[] move = getArbitraryUnplayedMove();
						board[move[0]][move[1]] =  'o';
						setMoveToPlayed(move[0], move[1], 'o');
					}
				}
			}
		}
		if(remainingMoves == 0) {
			System.out.print("DRAW\n");
			displayBoard();
			System.exit(0);
		}
		playerMove();
	}

	//determines computer's core strategy based on player's first move
	private void performFirstCompMove(int playerx, int playery){

		//if player puts x in center, place o in top left
		if(playerx == 1 && playery == 1){

			setMoveToPlayed(0, 0, 'o');
			lastCompMove = 00; //stores last move in an int (in this case, last move is (0,0))
			strategy = 2;
		}

		//otherwise, place o in center
		else{
			setMoveToPlayed(1, 1, 'o');
			lastCompMove = 11;

			//if the player puts x in a corner, initiate strategy 0
			if((playerx == 0 && playery == 0) || (playerx == 0 && playery == 2) || (playerx == 2 && playery == 0) || (playerx == 2 && playery == 2)){
				strategy = 0;
			}

			//otherwise, initiate strategy 3
			else
				strategy = 3;
		}
	}

	//checks if either comp or player is one move away from 3 in a row
	private boolean needsBlock(int movex, int movey, char symbolAvoid, char symbolCheck){

		int numConsec = 0;
		int emptySpaceCoord = 0;

		int col = 0;
		//counts number of consecutive moves in the x direction
		while(col < 3 && board[movex][col] != symbolAvoid){

			if(board[movex][col] == symbolCheck)
				numConsec ++;

			if(!checkIfMovePlayed(movex, col))
				emptySpaceCoord = col;

			col++;
		}

		//needs to block player from getting 3 in a row
		if(numConsec == 2 && col ==3){
			setMoveToPlayed(movex, emptySpaceCoord, 'o');
			return true;
		}

		numConsec = 0;
		emptySpaceCoord = 0;

		int row = 0;
		//counts number of consecutive moves in the y direction
		while(row < 3 && board[row][movey] != symbolAvoid){

			if(board[row][movey] == symbolCheck)
				numConsec ++;

			if(!checkIfMovePlayed(row, movey))
				emptySpaceCoord = row;

			row++;
		}

		//needs block
		if(numConsec == 2 && row ==3){
			setMoveToPlayed(emptySpaceCoord, movey, 'o');
			return true;
		}

		//counts number of consecutive moves in the diagonal direction
		if(movex == movey){

			//if move is in top left or bottom right, only need to check diagonal direction
			if(movex == 0 || movey == 2){
				if(needsBlockDiag(symbolAvoid, symbolCheck))
					return true;
			}

			//if move is in center, need to check both diagonal and anti diagonal
			else{
				if(needsBlockDiag(symbolAvoid, symbolCheck) || needsBlockAntiDiag(symbolAvoid, symbolCheck))
					return true;
			}
		}

		//counts number of consecutive moves in the anti-diagonal direction
		else{
			if(needsBlockAntiDiag(symbolAvoid, symbolCheck))
				return true;
		}

		return false;
	}

	//checks diagonal to see if player/comp is one away from 3 in a row
	private boolean needsBlockDiag(char symbolAvoid, char symbolCheck){

		int coord = 0;
		int numConsec = 0;
		int emptySpaceCoord = 0;

		while(coord < 3 && board[coord][coord] != symbolAvoid){

			if(board[coord][coord] == symbolCheck)
				numConsec ++;

			if(!checkIfMovePlayed(coord,coord))
				emptySpaceCoord = coord;

			coord++;
		}

		return blockHelper(numConsec, coord, emptySpaceCoord, emptySpaceCoord, symbolAvoid, symbolCheck);
	}
	
	//checks anti-diagonal to see if player/comp is one away from 3 in a row
	private boolean needsBlockAntiDiag(char symbolAvoid, char symbolCheck){

		int coord = 2;
		int numConsec = 0;
		int emptySpaceX = 0;
		int emptySpaceY = 0;

		while(coord >= 0 && board[2-coord][coord] != symbolAvoid){

			if(board[2-coord][coord] == symbolCheck)
				numConsec ++;

			if(!checkIfMovePlayed(2-coord,coord)){
				emptySpaceX = 2-coord;
				emptySpaceY = coord;
			}

			coord--;
		}

		return blockHelper(numConsec, 2-coord, emptySpaceX, emptySpaceY, symbolAvoid, symbolCheck);
	}

	//helps with diagonal/anti-diagonal needs block
	private boolean blockHelper(int numConsec, int coord, int emptySpaceX, int emptySpaceY, char symbolAvoid, char symbolCheck){

		if(numConsec == 2 && coord == 3){
			
			setMoveToPlayed(emptySpaceX, emptySpaceY, 'o');
			return true;
		}

		return false;
	}
	public static void main(String[] args){

		UnbeatableTicTacToe ttt = new UnbeatableTicTacToe();
		ttt.displayBoard();
		ttt.playerMove();
		ttt.displayBoard();
	}
}
