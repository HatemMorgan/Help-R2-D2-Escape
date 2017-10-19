package main;

import Project_Problem.GridObjects;
import Project_Problem.HelpR2D2;
import Project_Problem.R2D2State;
import search_strategies.*;


public class GenericSearchAlgorithm {
	private static final StringBuilder expansionSequenceBuilder = new StringBuilder();
	private static int expantionNodesNum = 0;
	public static String[] search(SearchStrategy searchStrategy,
			GenericSearchProblem problem, boolean visualize) throws CloneNotSupportedException, InterruptedException {

		/*
		 * create an root node from initial state and add it to the queuing data
		 * structure of search strategy
		 */
		TreeNode root = new TreeNode(null, problem.getInitialState(), 0, 0,null);
		searchStrategy.addInitialState(root);
		while (!searchStrategy.getQueuingDataStructure().isEmpty()) {
			TreeNode node = searchStrategy.remove();
			
			// add position of state in removed node from queue
			expansionSequenceBuilder.append("("+((R2D2State)node.getState()).getX()+","+((R2D2State)node.getState()).getY()+") , ");			
			expantionNodesNum++;
			if (problem.goalTest(node.getState())){
			    if(visualize)
				   problem.printGoalPath(node);
				return new String[]{node.toString(),node.getCost()+"",expantionNodesNum+""};
			}else {			
				searchStrategy.queuingFunc(problem.expand(node));
			}
		}
        System.out.println("No solution");
		return null;
	}
	
	public static void main(String[] args) throws CloneNotSupportedException, InterruptedException {
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

		GridObjects[][] g2 = new GridObjects[][]{{GridObjects.Agent,GridObjects.Rock,GridObjects.FreeSpace,GridObjects.PressurePad},
																			   {GridObjects.FreeSpace,GridObjects.FreeSpace,GridObjects.FreeSpace,GridObjects.Obstacle},
																			   {GridObjects.Rock,GridObjects.Rock,GridObjects.FreeSpace,GridObjects.FreeSpace},
																			   {GridObjects.PressurePad,GridObjects.FreeSpace,GridObjects.FreeSpace,GridObjects.Rock},
																			   {GridObjects.Obstacle,GridObjects.PressurePad,GridObjects.Teleportal,GridObjects.PressurePad}};
		HelpR2D2 help2 = new HelpR2D2(g2,new int[]{0,0},new int[]{4,2},4);

		
		GridObjects[][] g3 = new GridObjects[][]{{GridObjects.Agent,GridObjects.FreeSpace,GridObjects.FreeSpace,GridObjects.Teleportal},
																			 {GridObjects.FreeSpace,GridObjects.Rock,GridObjects.PressurePad,GridObjects.FreeSpace},
																			 {GridObjects.Rock,GridObjects.Obstacle,GridObjects.FreeSpace,GridObjects.FreeSpace},
																			 {GridObjects.PressurePad,GridObjects.FreeSpace,GridObjects.FreeSpace,GridObjects.Obstacle}};
HelpR2D2 help3 = new HelpR2D2(g3,new int[]{0,0},new int[]{0,3},2);

GridObjects[][] g4 = new GridObjects[][]{{GridObjects.FreeSpace,GridObjects.FreeSpace,GridObjects.Agent,GridObjects.FreeSpace},
		 {GridObjects.FreeSpace,GridObjects.Teleportal,GridObjects.Rock,GridObjects.FreeSpace},
		 {GridObjects.FreeSpace,GridObjects.FreeSpace,GridObjects.PressurePad,GridObjects.FreeSpace},
		 {GridObjects.Obstacle,GridObjects.FreeSpace,GridObjects.FreeSpace,GridObjects.FreeSpace}};
HelpR2D2 help4 = new HelpR2D2(g4,new int[]{0,2},new int[]{1,1},1);

GridObjects[][] g5 = new GridObjects[][]{{GridObjects.Obstacle,GridObjects.FreeSpace,GridObjects.Rock,GridObjects.FreeSpace},
		 {GridObjects.FreeSpace,GridObjects.Agent,GridObjects.FreeSpace,GridObjects.FreeSpace},
		 {GridObjects.PressurePad,GridObjects.FreeSpace,GridObjects.FreeSpace,GridObjects.FreeSpace},
		 {GridObjects.Teleportal,GridObjects.FreeSpace,GridObjects.Obstacle,GridObjects.FreeSpace}};
HelpR2D2 help5 = new HelpR2D2(g5,new int[]{1,1},new int[]{3,0},1);

//HelpR2D2 help6 = new HelpR2D2();
//help6.printGrid(help6.getGrid());


GridObjects[][] g7 = new GridObjects[][]{{GridObjects.Teleportal,GridObjects.Obstacle,GridObjects.FreeSpace,GridObjects.Obstacle,GridObjects.Obstacle,GridObjects.Obstacle},
		 {GridObjects.FreeSpace,GridObjects.FreeSpace,GridObjects.Obstacle,GridObjects.Obstacle,GridObjects.Obstacle,GridObjects.Obstacle},
		 {GridObjects.Obstacle,GridObjects.FreeSpace,GridObjects.Obstacle,GridObjects.Obstacle,GridObjects.Obstacle,GridObjects.FreeSpace},
		 {GridObjects.Obstacle,GridObjects.FreeSpace,GridObjects.FreeSpace,GridObjects.Rock,GridObjects.FreeSpace,GridObjects.PressurePad},
		 {GridObjects.Obstacle,GridObjects.FreeSpace,GridObjects.FreeSpace,GridObjects.FreeSpace,GridObjects.Obstacle,GridObjects.Obstacle},
		 {GridObjects.PressurePad,GridObjects.Rock,GridObjects.Agent,GridObjects.Obstacle,GridObjects.Obstacle,GridObjects.Obstacle}};
HelpR2D2 help7 = new HelpR2D2(g7,new int[]{5,2},new int[]{0,0},2);
		
//		SearchStrategy uniformCost = new UniformCost();
//		TreeNode goal =  search(uniformCost, help6, true );
		
//		SearchStrategy bfs = new BFS();
//		String[] results =  search(bfs, help, true );

//		SearchStrategy dfs = new DFS();
//		TreeNode goal =  search(dfs, help4, false );
		
//		IterativeDeepening iterativeDeepening = new IterativeDeepening();
//		TreeNode goal =  search(iterativeDeepening, help5, true);
//		
//		GreedyHeuristic1 greedyHeuristic1 = new GreedyHeuristic1(); 
//		String[] results =  search(greedyHeuristic1, help, true );
		
//		GreedyHeuristic2 greedyHeuristic2 = new GreedyHeuristic2(); 
//		String[] results =  search(greedyHeuristic2, help, true );
		
		AStarHeuristic1 aStarHeuristic1 = new AStarHeuristic1();
		String[] results =  search(aStarHeuristic1, help, true );
		
//		AStarHeuristic2 aStarHeuristic2 = new AStarHeuristic2();
//		TreeNode goal =  search(aStarHeuristic2, help7, true );

		System.out.println("Sequence of moves to goal: \n"+ results[0]);
		System.out.println("Path cost = "+ results[1]);
		System.out.println("Number of expanded nodes = "+ results[2]);
//	    System.out.println(goal.getDepth()+" "+goal.getCost()+" "+" "+ goal.getState());
//	    System.out.println(goal.toString());
//		System.out.println(expansionSequenceBuilder.toString());
	}
}

