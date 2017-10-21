package main;

import Project_Problem.GridObjects;
import Project_Problem.HelpR2D2;
import Project_Problem.R2D2State;
import search_strategies.*;


public class GenericSearchAlgorithm {
	private static final StringBuilder expansionSequenceBuilder = new StringBuilder();
	private static int expantionNodesNum = 0;
	
	/**
	 * This​ ​is​ ​the​ ​generic​ ​search​ ​method​ ​which​ ​is​ ​implemented​ ​to​ ​perform​ ​the​ ​searching  techniques​ ​using​ ​general​ ​problem​ ​
	 * and​ ​general​ ​Search​ ​strategy.​ ​The​ ​method​ ​takes as​ ​a​ ​parameter​ ​the​ ​search​ ​strategy​ ​which​ ​will​ ​handle​ ​the​ ​technique​ 
	 * ​of​ ​the​ ​queuing function,​ ​the​ ​problem​ ​where​ ​the​ ​agent​ ​will​ ​perform​ ​the​ ​searching​ ​algorithm​ ​and​ ​a boolean​ ​which​ ​state​ ​
	 * if​ ​we​ ​need​ ​to​ ​visualize​ ​the​ ​path​ ​to​ ​the​ ​goal​ ​after​ ​finding​ ​the  goal​ ​or​ ​we​ ​do​ ​not​ ​need.​ ​First​ ​the​ ​root​ ​node​ ​where​ ​the​ ​
	 * initial​ ​location​ ​of​ ​the​ ​agent​ ​is  set​ ​will​ ​be​ ​pushed​ ​to​ ​the​ ​queue,​ ​then​ ​a​ ​loop​ ​will​ ​be​ ​performed​ ​in​ ​this​ ​queue​ ​until​ ​it 
	 * is​ ​empty,​ ​for​ ​every​ ​node​ ​in​ ​the​ ​queue​ ​it​ ​will​ ​be​ ​checked​ ​if​ ​it​ ​is​ ​the​ ​goal​ ​node​ ​using the​ ​ ​goal​ ​test​ ​ function​ ​which​ ​is​ 
	 * implemented​ ​in​ ​the​ ​search​ ​problem,​ ​if​ ​the​ ​node​ ​is  not​ ​a​ ​goal​ ​then​ ​it​ ​will​ ​be​ ​expanded​ ​using​ ​the​ ​search​ ​strategy​ 
	 * expansion​ ​function which​ ​expand​ ​the​ ​node​ ​using​ ​the​ ​problem​ ​operators​ ​and​ ​return​ ​the​ ​children​ ​of  this​ ​node.​ ​The​ ​
	 * ​queuing​ ​function​ of​ ​the​ ​given​ ​search​ ​strategy​ ​is​ ​responsible​ ​for inserting​ ​these​ ​expanded​ ​nodes​ ​in​ ​the​ ​queuing​ 
	 * ​data​ ​structure.​ ​The​ ​nodes​ ​will​ ​be removed​ ​from​ ​the​ ​queue​ ​in​ ​each​ ​iteration​ ​using​ ​the​ r ​ emoving​ ​function​ ​ ​which​ ​is 
	 * implemented​ ​in​ ​the​ ​search​ ​strategy.
	 * ​
	 * @param searchStrategy
	 * @param problem
	 * @param visualize
	 * @return ​an​ ​array​ ​of​ ​strings​ ​which contain:  
	 * 		1. The​ ​sequence​ ​of​ ​moves​ ​of​ ​the​ ​path​ ​from​ ​the​ ​starting​ ​position​ ​to​ ​the​ ​goal.  
	 * 		2. The​ path​ ​cost. 
	 * 		3. The​ number​ ​of​ ​expanded​ ​nodes. 
	 * 
	 * @throws CloneNotSupportedException
	 * @throws InterruptedException
	 */
	public static String[] search(SearchStrategy searchStrategy,
			GenericSearchProblem problem, boolean visualize) throws CloneNotSupportedException, InterruptedException {
		
			((HelpR2D2)problem).printGrid(((HelpR2D2)problem).getGrid());
			
		/*
		 * create an root node from initial state and add it to the queuing data
		 * structure of search strategy
		 */
		TreeNode root = new TreeNode(null, problem.getInitialState(), 0, 0,null);
		searchStrategy.addInitialState(root);
		while (!searchStrategy.getQueuingDataStructure().isEmpty()) {
			TreeNode node = searchStrategy.remove();
			
			// if no node returned due to handling repeated states
			if(node == null){
		        System.out.println("No solution");
				return null;
			}
			
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
		GenericSearchProblem help = new HelpR2D2();
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
		HelpR2D2 help2 = new HelpR2D2(g,new int[]{0,0},new int[]{2,0},2);

		GridObjects[][] g2 = new GridObjects[][]{{GridObjects.Agent,GridObjects.Rock,GridObjects.FreeSpace,GridObjects.PressurePad},
																			   {GridObjects.FreeSpace,GridObjects.FreeSpace,GridObjects.FreeSpace,GridObjects.Obstacle},
																			   {GridObjects.Rock,GridObjects.Rock,GridObjects.FreeSpace,GridObjects.FreeSpace},
																			   {GridObjects.PressurePad,GridObjects.FreeSpace,GridObjects.FreeSpace,GridObjects.Rock},
																			   {GridObjects.Obstacle,GridObjects.PressurePad,GridObjects.Teleportal,GridObjects.PressurePad}};
		HelpR2D2 help3 = new HelpR2D2(g2,new int[]{0,0},new int[]{4,2},4);

		
		GridObjects[][] g3 = new GridObjects[][]{{GridObjects.Agent,GridObjects.FreeSpace,GridObjects.FreeSpace,GridObjects.Teleportal},
																			 {GridObjects.FreeSpace,GridObjects.Rock,GridObjects.PressurePad,GridObjects.FreeSpace},
																			 {GridObjects.Rock,GridObjects.Obstacle,GridObjects.FreeSpace,GridObjects.FreeSpace},
																			 {GridObjects.PressurePad,GridObjects.FreeSpace,GridObjects.FreeSpace,GridObjects.Obstacle}};
HelpR2D2 help4 = new HelpR2D2(g3,new int[]{0,0},new int[]{0,3},2);

GridObjects[][] g4 = new GridObjects[][]{{GridObjects.FreeSpace,GridObjects.FreeSpace,GridObjects.Agent,GridObjects.FreeSpace},
		 {GridObjects.FreeSpace,GridObjects.Teleportal,GridObjects.Rock,GridObjects.FreeSpace},
		 {GridObjects.FreeSpace,GridObjects.FreeSpace,GridObjects.PressurePad,GridObjects.FreeSpace},
		 {GridObjects.Obstacle,GridObjects.FreeSpace,GridObjects.FreeSpace,GridObjects.FreeSpace}};
HelpR2D2 help5 = new HelpR2D2(g4,new int[]{0,2},new int[]{1,1},1);

GridObjects[][] g5 = new GridObjects[][]{{GridObjects.Obstacle,GridObjects.FreeSpace,GridObjects.Rock,GridObjects.FreeSpace},
		 {GridObjects.FreeSpace,GridObjects.Agent,GridObjects.FreeSpace,GridObjects.FreeSpace},
		 {GridObjects.PressurePad,GridObjects.FreeSpace,GridObjects.FreeSpace,GridObjects.FreeSpace},
		 {GridObjects.Teleportal,GridObjects.FreeSpace,GridObjects.Obstacle,GridObjects.FreeSpace}};
HelpR2D2 help6 = new HelpR2D2(g5,new int[]{1,1},new int[]{3,0},1);

//HelpR2D2 help6 = new HelpR2D2();
//help6.printGrid(help6.getGrid());


GridObjects[][] g7 = new GridObjects[][]{{GridObjects.Teleportal,GridObjects.Obstacle,GridObjects.FreeSpace,GridObjects.Obstacle,GridObjects.Obstacle,GridObjects.Obstacle},
		 {GridObjects.FreeSpace,GridObjects.FreeSpace,GridObjects.Obstacle,GridObjects.Obstacle,GridObjects.Obstacle,GridObjects.Obstacle},
		 {GridObjects.Obstacle,GridObjects.FreeSpace,GridObjects.Obstacle,GridObjects.Obstacle,GridObjects.Obstacle,GridObjects.FreeSpace},
		 {GridObjects.Obstacle,GridObjects.FreeSpace,GridObjects.FreeSpace,GridObjects.Rock,GridObjects.FreeSpace,GridObjects.PressurePad},
		 {GridObjects.Obstacle,GridObjects.FreeSpace,GridObjects.FreeSpace,GridObjects.FreeSpace,GridObjects.Obstacle,GridObjects.Obstacle},
		 {GridObjects.PressurePad,GridObjects.Rock,GridObjects.Agent,GridObjects.Obstacle,GridObjects.Obstacle,GridObjects.Obstacle}};
HelpR2D2 help7 = new HelpR2D2(g7,new int[]{5,2},new int[]{0,0},2);
		
GridObjects[][] g8 = new GridObjects[][]{{GridObjects.FreeSpace,GridObjects.PressurePad,GridObjects.PressurePad,GridObjects.Rock,GridObjects.Rock},
		 {GridObjects.Rock,GridObjects.Rock,GridObjects.FreeSpace,GridObjects.Rock,GridObjects.PressurePad},
		 {GridObjects.Rock,GridObjects.FreeSpace,GridObjects.Teleportal,GridObjects.Rock,GridObjects.PressurePad},
		 {GridObjects.PressurePad,GridObjects.FreeSpace,GridObjects.Rock,GridObjects.Rock,GridObjects.PressurePad},
      	{GridObjects.FreeSpace,GridObjects.PressurePad,GridObjects.Agent,GridObjects.PressurePad,GridObjects.PressurePad}};

HelpR2D2 help8 = new HelpR2D2(g8,new int[]{4,2},new int[]{2,2},9);

//		SearchStrategy uniformCost = new UniformCost();
//		String[] results  =  search(uniformCost, help3, false );
		
//		SearchStrategy bfs = new BFS();
//		String[] results =  search(bfs, help3, false );

//		SearchStrategy dfs = new DFS();
//		String[] results =  search(dfs, help3, false );
		
//		IterativeDeepening iterativeDeepening = new IterativeDeepening();
//		String[] results =  search(iterativeDeepening, help3, false);
//		
//		GreedyHeuristic1 greedyHeuristic1 = new GreedyHeuristic1(); 
//		String[] results =  search(greedyHeuristic1, help3, false );
		
//		GreedyHeuristic2 greedyHeuristic2 = new GreedyHeuristic2(); 
//		String[] results =  search(greedyHeuristic2, help3, false );
		
//		AStarHeuristic1 aStarHeuristic1 = new AStarHeuristic1();
//		String[] results =  search(aStarHeuristic1, help3, false );
		
		AStarHeuristic2 aStarHeuristic2 = new AStarHeuristic2();
		String[] results =  search(aStarHeuristic2, help3, false );
		
		if(results == null)
			return;
		System.out.println("Sequence of moves to goal: \n"+ results[0]);
		System.out.println("Path cost = "+ results[1]);
		System.out.println("Number of expanded nodes = "+ results[2]);
//	    System.out.println(goal.getDepth()+" "+goal.getCost()+" "+" "+ goal.getState());
//	    System.out.println(goal.toString());
//		System.out.println(expansionSequenceBuilder.toString());
	}
}

