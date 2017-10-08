package main;

import Project_Problem.HelpR2D2;
import Project_Problem.R2D2State;
import search_strategies.DFS;

public class GenericSearchAlgorithm {

	public static TreeNode search(SearchStrategy searchStrategy,
			GenericSearchProblem problem, boolean visualize) throws CloneNotSupportedException {

		/*
		 * create an root node from initial state and add it to the queuing data
		 * structure of search strategy
		 */
		TreeNode root = new TreeNode(null, problem.getInitialState(), 0, 0,
				null);
		searchStrategy.addInitialState(root);
		R2D2State stat =(R2D2State) root.getState();
		System.out.println(stat.getX()+" "+ stat.getY());
		int i = 0;
		while (!searchStrategy.getQueuingDataStructure().isEmpty()) {
			TreeNode node = searchStrategy.remove();
			if (problem.goalTest(node.getState())){
				return node;
			}else {
				TreeNode[] ex = problem.expand(node);
				if(i<3){
				for (int j = 0; j < ex.length; j++) {
					System.out.println(((R2D2State)ex[j].getState()).getX()+" "+((R2D2State)ex[j].getState()).getY());
				}
				System.out.println("--------");
				i++;
				}
				searchStrategy.queuingFunc(problem.expand(node));
			}
		}

		return null;
	}

	public static void main(String[] args) throws CloneNotSupportedException {
		SearchStrategy dfs = new DFS();
		GenericSearchProblem help = new HelpR2D2();
		((R2D2State)help.getInitialState()).getGridObj().printGrid();
	    TreeNode goal =  search(dfs, help, false );
	    System.out.println(goal.getDepth()+" "+goal.getDepth()+" "+goal.getParent()+" "+ goal.getState());
		
	}
}

