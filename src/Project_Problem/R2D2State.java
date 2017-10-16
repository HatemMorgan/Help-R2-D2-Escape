package Project_Problem;

import java.util.Arrays;

import main.State;

public class R2D2State extends State {

	private int x;
	private int y;
	private GridObjects[][] grid;
	private int[] teleportalPos;
	// private boolean rockMoved;
	private int remainingRocks;
	private Integer heuristicFunResult;

	public R2D2State(int x, int y, int remainingRocks, GridObjects[][] grid,
			int[] teleportalPos) {
		this.x = x;
		this.y = y;
		// this.rockMoved = rockMoved;
		this.remainingRocks = remainingRocks;
		this.grid = grid;
		this.teleportalPos = teleportalPos;
		heuristicFunResult = null;
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

	public GridObjects[][] getGrid() {
		return grid;
	}

	public int[] getTeleportalPos() {
		return teleportalPos;
	}

	public Integer getHeuristicFunResult() {
		return heuristicFunResult;
	}

	public void setHeuristicFunResult(Integer heuristicFunResult) {
		this.heuristicFunResult = heuristicFunResult;
	}

	@Override
	public String toString() {
		return "R2D2State [x=" + x + ", y=" + y + ", remainingRocks="
				+ remainingRocks + "]";
	}

}
