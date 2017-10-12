package main;

import Project_Problem.GridObjects;
import Project_Problem.HelpR2D2;
import Project_Problem.R2D2State;
import search_strategies.BFS;
import search_strategies.DFS;
import search_strategies.IterativeDeepening;
import search_strategies.UniformCost;

public class GenericSearchAlgorithm {
	private static final StringBuilder expansionSequenceBuilder = new StringBuilder();

	
	public static TreeNode search(SearchStrategy searchStrategy,
			GenericSearchProblem problem, boolean visualize) throws CloneNotSupportedException {

		/*
		 * create an root node from initial state and add it to the queuing data
		 * structure of search strategy
		 */
		TreeNode root = new TreeNode(null, problem.getInitialState(), 0, 0,null);
		((HelpR2D2)problem).printGrid(((R2D2State)root.getState()).getGrid());

		searchStrategy.addInitialState(root);
		while (!searchStrategy.getQueuingDataStructure().isEmpty()) {
			TreeNode node = searchStrategy.remove();
			System.out.println(((R2D2State)node.getState()).getX()+","+((R2D2State)node.getState()).getY());
			// add position of state in removed node from queue
			expansionSequenceBuilder.append("("+((R2D2State)node.getState()).getX()+","+((R2D2State)node.getState()).getY()+") , ");
//				System.out.println(node.getOperator());
//				System.out.println(node.getCost()+" "+node.getDepth());
//				System.out.println(((R2D2State)node.getState()).getRemainingRocks());
//				if(node.getParent() != null)
//					((HelpR2D2)problem).printGrid(((R2D2State)node.getParent().getState()).getGrid());
//				System.out.println();
//				((HelpR2D2)problem).printGrid(((R2D2State)node.getState()).getGrid());
			
			if (problem.goalTest(node.getState())){
//				System.out.println("goal");
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
//				System.out.println(searchStrategy.getQueuingDataStructure().toString());
			}
		}

		return null;
	}

	public static void main(String[] args) throws CloneNotSupportedException {
//		SearchStrategy dfs =new UniformCost();
//		GenericSearchProblem help = new HelpR2D2();
//		TreeNode goal =  search(dfs, help, false );
//	    System.out.println(goal.getDepth()+" "+goal.getDepth()+" "+goal.getParent()+" "+ goal.getState());
		
//		SearchStrategy dfs = new BFS();
//		SearchStrategy dfs =new UniformCost();
//		GridObjects[][] g = new GridObjects[][]{{GridObjects.PressurePad},{GridObjects.Teleportal},{GridObjects.Rock},{GridObjects.Agent}};
//		HelpR2D2 help = new HelpR2D2(g,new int[]{3,0},new int[]{1,0},1);
//		TreeNode goal =  search(dfs, help, false );
//	    System.out.println(goal.getDepth()+" "+goal.getDepth()+" "+goal.getParent()+" "+ goal.getState());
		
		
		
		GridObjects[][] g = new GridObjects[][]{{GridObjects.Agent,GridObjects.Rock,GridObjects.PressurePad},
																		   {GridObjects.FreeSpace,GridObjects.Rock,GridObjects.Obstacle},
																		   {GridObjects.Teleportal,GridObjects.PressurePad,GridObjects.FreeSpace}};
		HelpR2D2 help = new HelpR2D2(g,new int[]{0,0},new int[]{2,0},2);
		
//		SearchStrategy uniformCost = new UniformCost();
//		TreeNode goal =  search(uniformCost, help, false );
		
//		SearchStrategy bfs = new BFS();
//		TreeNode goal =  search(bfs, help, false );

//		SearchStrategy dfs = new DFS();
//		TreeNode goal =  search(dfs, help, false );
		
		IterativeDeepening iterativeDeepening = new IterativeDeepening();
		TreeNode goal =  search(iterativeDeepening, help, false );
		
	    System.out.println(goal.getDepth()+" "+goal.getDepth()+" "+" "+ goal.getState());
	    System.out.println(goal.toString());
		System.out.println(expansionSequenceBuilder.toString());
	}
}

