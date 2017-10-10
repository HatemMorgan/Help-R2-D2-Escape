package main;

import Project_Problem.GridObjects;
import Project_Problem.HelpR2D2;
import Project_Problem.R2D2State;
import search_strategies.BFS;
import search_strategies.DFS;
import search_strategies.UniformCost;

public class GenericSearchAlgorithm {

	public static TreeNode search(SearchStrategy searchStrategy,
			GenericSearchProblem problem, boolean visualize) throws CloneNotSupportedException {

		/*
		 * create an root node from initial state and add it to the queuing data
		 * structure of search strategy
		 */
		TreeNode root = new TreeNode(null, problem.getInitialState(), 0, 0,null);
		searchStrategy.addInitialState(root);
		R2D2State stat =(R2D2State) root.getState();
		int i = 0;
		int u = 0;
		while (!searchStrategy.getQueuingDataStructure().isEmpty()) {
			TreeNode node = searchStrategy.remove();
//			if(u < 4){
				((HelpR2D2)problem).printGrid(((R2D2State)node.getState()).getGrid());
//				System.out.println(" "+node.getOperator());
//				System.out.println(((R2D2State)node.getState()).getRemainingRocks());
//				System.out.println("X & Y" + " " + ((R2D2State)node.getState()).getX() + " " + ((R2D2State)node.getState()).getY());
//				u++;
//				
//			}
			
			if (problem.goalTest(node.getState())){
				System.out.println("goal");
				return node;
			}else {
//				TreeNode[] ex = problem.expand(node);
//				if(i<3){
//				for (int j = 0; j < ex.length; j++) {
//					System.out.println(((R2D2State)ex[j].getState()).getX()+" "+((R2D2State)ex[j].getState()).getY());
//				}
//				System.out.println("--------");
//				i++;
//				}
				searchStrategy.queuingFunc(problem.expand(node));
			}
		}

		return null;
	}

	public static void main(String[] args) throws CloneNotSupportedException {
//		SearchStrategy dfs =new UniformCost();
//		GenericSearchProblem help = new HelpR2D2();
//		TreeNode goal =  search(dfs, help, false );
//	    System.out.println(goal.getDepth()+" "+goal.getDepth()+" "+goal.getParent()+" "+ goal.getState());
		
		SearchStrategy dfs = new BFS();
		GridObjects[][] g = new GridObjects[][]{{GridObjects.PressurePad},{GridObjects.Teleportal},{GridObjects.Rock},{GridObjects.Agent}};
		HelpR2D2 help = new HelpR2D2(g,new int[]{3,0},new int[]{1,0},1);
		TreeNode goal =  search(dfs, help, false );
	    System.out.println(goal.getDepth()+" "+goal.getDepth()+" "+goal.getParent()+" "+ goal.getState());
		
		
	}
}

