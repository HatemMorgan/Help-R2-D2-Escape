package Project_Problem;

import main.State;

public class R2D2State extends State {

	private int x;
	private int y;
	private GridObjects[][] grid;
	// private boolean rockMoved;
	private int remainingRocks;

	public R2D2State(int x, int y, int remainingRocks,  GridObjects[][] grid) {
		this.x = x;
		this.y = y;
		// this.rockMoved = rockMoved;
		this.remainingRocks = remainingRocks;
		this.grid = grid;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	// public boolean isRockMoved() {
	// return rockMoved;
	// }

	public int getRemainingRocks() {
		return remainingRocks;
	}

	public  GridObjects[][] getGrid(){
		return grid;
	}

}
