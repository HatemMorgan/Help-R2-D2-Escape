package search_strategies;

import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;

import Project_Problem.GridObjects;
import Project_Problem.R2D2State;
import Project_Problem.RepeatedStatesController;
import Utility.Position;
import main.SearchStrategy;
import main.TreeNode;

/**
 * Greedy​ ​Search​ ,​ ​the​ ​queuing​ ​data​ ​structure​ ​is​ ​a​ ​ priority​ ​queue,​ ​ where nodes​ ​are​ ​ordered​ ​according​ ​to​ ​an​ ​evaluation​ ​
 * function.​ ​The​ ​evaluation​ ​function​ ​is  the​ ​value​ ​of​ ​the​ ​heuristic​ ​function​ ​which​ ​is​ ​an​ ​estimate​ ​of​ ​the​ ​cheapest​ ​cost​ ​from 
 * the​ ​current​ ​node​ ​to​ ​a​ ​goal​ ​node​ ​.​ ​The​ ​ queuingFunc​ method​ ​adds​ ​the​ ​nodes​ ​to​ ​the  priority​ ​queue​ ​ ​such​ ​that​ ​whenever​ ​a​ 
 * ​new​ ​node​ ​is​ ​to​ ​be​ ​added,​ ​all​ ​the​ ​nodes​ ​are ordered​ ​according​ ​to​ ​the​ ​value​ ​of​ ​the​ ​evaluation​ ​function​ ​and​ ​the​ ​node​ ​with​ ​the​ ​lowest 
 * value​ ​of​ ​the​ ​evaluation​ ​function,​ ​i.e​ ​the​ ​node​ ​with​ ​the​ ​highest​ ​priority​ ​is​ ​added​ ​to​ ​the​ ​tail  of​ ​the​ ​queue​ ​and​ ​the​ ​ remove​ 
 * method​ ​removes​ ​the​ ​node​ ​at​ ​the​ ​tail​ ​of​ ​the​ ​queue,​ ​i.e​ ​the node​ ​with​ ​the​ ​highest​ ​priority.​ 
 */
public class GreedyHeuristic1 implements SearchStrategy{

		
	private Queue<TreeNode> priorityQueue;
	private RepeatedStatesController repeatedStatesController;

	public GreedyHeuristic1() {
		 priorityQueue = new PriorityQueue<TreeNode>(heuristicCompartor);
			repeatedStatesController  = new RepeatedStatesController();
	}
	
	@Override
	public void queuingFunc(TreeNode[] expandedNodes) {
			
			for(TreeNode node : expandedNodes){
//				 check if the node was explored before or is it in the priorityQueue
//				 if not add it
//				 I checked that this node was not explored before to avoid Repeated States
						if(node != null)
							priorityQueue.add(node);
			}

				
	}

	@Override
	public Collection<TreeNode> getQueuingDataStructure() {
		return priorityQueue;
	}

	@Override
	public void addInitialState(TreeNode root) {
			priorityQueue.add(root);
	}

//	@Override
//	public TreeNode remove() {
//		return priorityQueue.poll();
//	}
	
	@Override
	public TreeNode remove() {
		TreeNode node;
		do{
			try{
				node = priorityQueue.remove();
				// if queue is empty then  return null indicating that queue is empty  
				}catch(NoSuchElementException e){
					return null;
				}
			}while(repeatedStatesController.isRepeated(node));
		
		return node;
	}
	
	/**
	 * The​ ​first​ ​heuristic​ ​function​ ​implemented​ ​was​ ​the​ ​sum​ ​of​ ​the city​ ​block​ ​​distance​ ​from​ ​the​ ​agent’s​ ​position​ ​to​ ​the​ ​nearest​
	 *  ​rock​ ​and​ ​the​ city​ ​block  distance​ ​from​ ​the​ ​nearest​ ​rock​ ​to​ ​the​ ​nearest​ ​pressure​ ​pad​ ​to​ ​this​ ​rock.​ ​This heuristic​ ​function​ ​
	 *  is​ ​admissible,​ ​since​ ​the​ ​best​ ​case​ ​scenario​ ​is​ ​when​ ​all​ ​rocks​ ​have been​ ​placed​ ​on​ ​pressure​ ​pads,​ ​and​ ​all​ ​the​ ​agent​ ​has​ 
	 *  ​to​ ​do​ ​is​ ​to​ ​move​ ​to​ ​the  teleportal.​ ​In​ ​this​ ​case​ ​the​ ​value​ ​of​ ​the​ ​heuristic​ ​function​ ​will​ ​be​ ​0​ ​since​ ​in​ ​our  implementation​ ​
	 *  we​ ​differentiate​ ​between​ ​cells​ ​containing​ ​rocks​ ​and​ ​cells​ ​containing  rocks​ ​on​ ​pressure​ ​pads​ ​or​ ​on​ ​the​ ​teleportal.​ ​Therefore,​ ​the​ ​distance​ ​to​ ​the​ ​nearest 
	 *  rock​ ​will​ ​be​ ​0​ ​since​ ​all​ ​rocks​ ​are​ ​on​ ​pressure​ ​pads. 
	 * @param node
	 * @return
	 */
	private static int HeuristicFun(TreeNode node) {
		// get grid of the passed treeNode
		GridObjects[][] grid = ((R2D2State) node.getState()).getGrid();
		
		// create two arrays for remaining rocks and free pressurePads
		Position[] remainingRocks = new Position [((R2D2State) node.getState()).getRemainingRocks()];
		Position[] freePressurePads = new Position [((R2D2State) node.getState()).getRemainingRocks()];
		
		// Indices for remainingRocks and freePressurePads
		int k1= 0;
		int k2 = 0;
		// Iterate over the grid to get positions of rocks and pressurePads
		for(int i=0; i<grid.length; i++){
			for(int j=0;  j<grid[0].length; j++){
					
					if(grid[i][j].equals(GridObjects.Rock) || grid[i][j].equals(GridObjects.RockTeleportal)){
							remainingRocks[k1] = new Position(i, j);
							k1++;
					}
					if(grid[i][j].equals(GridObjects.PressurePad) || grid[i][j].equals(GridObjects.AgentPressurePad)){
						freePressurePads[k2] = new Position(i, j);
						k2++;
					}
			
			}
		}
		
		//
		int distance = Integer.MAX_VALUE;
		Position minPosition = null;
		int x = ((R2D2State)(node.getState())).getX();
		int y = ((R2D2State)(node.getState())).getY();
		// getting nearest rock to the agent 
		for(Position rock : remainingRocks ){
			int d = Math.abs(x-rock.getX()) + Math.abs(y-rock.getY());
			if(d < distance){
				minPosition = rock;
				distance = d;
			}
		}
		
		int distance2 = Integer.MAX_VALUE;
		// getting nearest pressure pad to nearest rock
		for(Position presurePad : freePressurePads){
			int d = Math.abs(minPosition.getX()-presurePad.getX()) + Math.abs(minPosition.getY()-presurePad.getY());
			if(d < distance2){
				distance2 = d;
			}
		}
		return (distance == Integer.MAX_VALUE)? 0 : distance+distance2;
				
	}
	
	
	private static Comparator<TreeNode> heuristicCompartor = new Comparator<TreeNode>() {
		
		@Override
		public int compare(TreeNode node1, TreeNode node2) {
				// calculate heuristic if it was not calculated before and set it in the state of the node
				int hn1,hn2;
				if(((R2D2State)node1.getState()).getHeuristicFunResult() == null){
					 hn1 = HeuristicFun(node1);
					 ((R2D2State)node1.getState()).setHeuristicFunResult(hn1);
				}else{
					 hn1 = ((R2D2State)node1.getState()).getHeuristicFunResult();
				}

				if(((R2D2State)node2.getState()).getHeuristicFunResult() == null){
					 hn2 = HeuristicFun(node2);
					 ((R2D2State)node2.getState()).setHeuristicFunResult(hn2);
				}else{
					 hn2 = ((R2D2State)node2.getState()).getHeuristicFunResult();
				}
				
				return hn1 - hn2;
		}		
	};
	
}


