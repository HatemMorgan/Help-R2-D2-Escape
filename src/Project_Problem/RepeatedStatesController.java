package Project_Problem;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import main.TreeNode;

public class RepeatedStatesController {
		private  Hashtable<Long,List<GridObjects[][]>> previousStatesMap 
																							= new Hashtable<Long, List<GridObjects[][]>>();
		
		/**
		 * It checks if passed parameter node’s state was a repeated state or not by checking the the previousStatesMap 
		 * which is  Hashtable<Long,List<GridObjects[][]>> where the key is calculated using 
		 * calculateKey(GridObjects[][] grid) method and the value is a list of 2D array representing grid. 
		 * The list is used to store grids that have the same calculated key which rarely happens but it can occur in some 
		 * specific cases like when using DFS.
		 * 
		 * @param node
		 * 
		 * @return boolean, true if it is a repeated node's state and false if it is a new one
		 */
		public boolean isRepeated(TreeNode node){
			if(node == null || (R2D2State)node.getState() == null)
						System.out.println(node);
			GridObjects[][] newGrid = ((R2D2State)node.getState()).getGrid();
			long key = calculateKey(newGrid);
			
			// it previousStatesMap contains calculated key if so check that grid of parameter node is equivalent to an old grid
			// in the list associated with calculated key
			if(previousStatesMap.containsKey(key)){
				// iterate over list value of calculated key and check if there exist a grid in the list that is equivalent to new grid
				for(GridObjects[][] grid : previousStatesMap.get(key)){
					// if there exist a grid equivalent to newGrid then return true indicating that that it is a repeated state
					if(areDuplicates(newGrid, grid))
							return true;
				}
				// new grid with the same calculated key but different from previous stored list
				previousStatesMap.get(key).add(newGrid);
			}else{
				// it is a new state so add it to previousStatesMap
				// create a new List<GridObjects[][]> to hold grids with the same calculated key. It will rarely happen to have the same calculated key
				List<GridObjects[][]> list = new ArrayList<GridObjects[][]>();
				previousStatesMap.put(key, list);
			}
			
			
			
			return false;
		}
		
		private boolean areDuplicates(GridObjects[][] newGrid, GridObjects[][]  oldGrid){
					for(int i=0; i<newGrid.length; i++){
						for(int j=0; j<newGrid[0].length; j++){
							if(newGrid[i][j] != oldGrid[i][j])
									return false;
						}				
				}
			return true;
		}
		
		/**
		 * CalculateHash method takes a 2D array representing a grid it iterates over each element in the grid calculate
		 * sum of positions of agent and each rock. it gives the x index weight 3 and y index weight 2 to avoid having the
		 * same summation when the agent or a rock move from (1,2) to (2,1) for example
		 * 
		 * @param grid
		 * @return summation 
		 */
		private long calculateKey(GridObjects[][] grid){
			long key = 0;
			
			for(int i=0; i<grid.length; i++){
				for(int j=0; j<grid[0].length; j++){
						if(grid[i][j] == GridObjects.Rock || grid[i][j] == GridObjects.RockTeleportal || grid[i][j] == GridObjects.Agent || grid[i][j] == GridObjects.AgentPressurePad)
							key += 3*i + 2*j;  
				}
			}
			
			return key;
		}
		
		public void flush(){
			previousStatesMap = new Hashtable<Long, List<GridObjects[][]>>();
		}
		
}
