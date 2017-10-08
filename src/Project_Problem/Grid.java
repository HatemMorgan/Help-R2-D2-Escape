package Project_Problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Grid implements Cloneable {
	private GridObjects[][] grid;
	private int[] agentInitialPos;
	private int[] teleportalPos;
    private int RocksNum;
  
	public Grid() {
		GenGrid();
	}

	public Grid(GridObjects[][] grid, int[] agentInitialPos, int[] teleportalPos) {
		this.grid = grid;
		this.agentInitialPos = agentInitialPos;
		this.teleportalPos = teleportalPos;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		GridObjects[][] newGrid = new GridObjects[grid.length][grid[0].length];
		for (int i = 0; i < grid.length; i++) {
			newGrid[i] = (GridObjects[]) grid[i].clone();
		}
		return new Grid(newGrid, agentInitialPos, teleportalPos);
	}

	private void GenGrid() {

		int m = RandomNumber(3, 7);
		int n = RandomNumber(3, 7);
		grid = new GridObjects[n][m];
		int totalCells = (m * n) - 2;
		int Teleportal = 1; // check if correct
		int FreeSpaces = RandomNumber(1, totalCells - 3 + 1);
		totalCells -= FreeSpaces;
		int Pressure = RandomNumber(1, (totalCells - 1) / 2 + 1);
	    int Rocks = Pressure;
	    RocksNum = Rocks;
		totalCells -= Pressure * 2;
		int Obstacles = RandomNumber(1, totalCells + 1);
		FreeSpaces += totalCells - Obstacles;
		int Agent = 1;

		ArrayList<GridObjects> gridObjects = new ArrayList<GridObjects>();
		gridObjects.add(GridObjects.PressurePad);
		gridObjects.add(GridObjects.FreeSpace);
		gridObjects.add(GridObjects.Teleportal);
		gridObjects.add(GridObjects.Agent);
		gridObjects.add(GridObjects.Rock);
		gridObjects.add(GridObjects.Obstacle);

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				int Object = RandomNumber(0, gridObjects.size());
				grid[i][j] = gridObjects.get(Object);
				switch (gridObjects.get(Object)) {
				case PressurePad:
					Pressure--;
					if (Pressure == 0)
						gridObjects.remove(GridObjects.PressurePad);
					break;
				case FreeSpace:
					FreeSpaces--;
					if (FreeSpaces == 0)
						gridObjects.remove(GridObjects.FreeSpace);
					break;
				case Rock:
					Rocks--;
					if (Rocks == 0)
						gridObjects.remove(GridObjects.Rock);
					break;
				case Obstacle:
					Obstacles--;
					if (Obstacles == 0)
						gridObjects.remove(GridObjects.Obstacle);
					break;
				case Agent:
					Agent--;
					if (Agent == 0) {
						gridObjects.remove(GridObjects.Agent);
						agentInitialPos = new int[] { i, j };
					}
					break;
				case Teleportal:
					Teleportal--;
					if (Teleportal == 0) {
						gridObjects.remove(GridObjects.Teleportal);
						teleportalPos = new int[] { i, j };
					}
					break;
				}

			}
		}
	}

	public int getRocks() {
		return RocksNum;
	}

	private int RandomNumber(int low, int high) {
		Random r = new Random();
		return (r.nextInt(high - low) + low);
	}

	public void printGrid() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				System.out.print(grid[i][j].getAlias() + " | ");
			}
			System.out.println();

			for (int j = 0; j < grid[i].length; j++) {
				System.out.print("----");
			}
			System.out.println();

		}
	}

	public GridObjects[][] getGrid() {
		return grid;
	}

	public int[] getAgentInitialPos() {
		return agentInitialPos;
	}

	public int[] getTeleportalPos() {
		return teleportalPos;
	}

	public static void main(String[] args) throws CloneNotSupportedException {
		Grid grid = new Grid();
		grid.printGrid();

		System.out.println();

		Grid grid2 = (Grid) grid.clone();

		grid.grid[0][0] = GridObjects.Teleportal;

		grid.printGrid();
		System.out.println();

		grid2.printGrid();

		int[][] x = { { 1, 2 }, { 3, 4, 5 } };

		int[][] y = (int[][]) x.clone();
		x[0][0] = 2;
		System.out.println(y[0][0]);

		GridObjects[] g = new GridObjects[] { GridObjects.Agent,
				GridObjects.FreeSpace };
		GridObjects[] g2 = (GridObjects[]) g.clone();

		g[0] = GridObjects.Obstacle;

		System.out.println(Arrays.toString(g2));

	}

}
