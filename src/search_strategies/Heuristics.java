package search_strategies;

import Project_Problem.GridObjects;
import Project_Problem.R2D2State;
import main.TreeNode;

public class Heuristics {


	
	public static int H2(TreeNode node){
		R2D2State state = (R2D2State)node.getState();
		return state.getRemainingRocks()+ Math.abs(state.getX()-state.getTeleportalPos()[0]) + Math.abs(state.getY()-state.getTeleportalPos()[1]);
	}
}




