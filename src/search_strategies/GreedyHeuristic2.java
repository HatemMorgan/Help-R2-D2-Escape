package search_strategies;

import java.util.Collection;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

import Project_Problem.R2D2State;
import main.SearchStrategy;
import main.TreeNode;

public class GreedyHeuristic2 implements SearchStrategy{

		
private Queue<TreeNode> priorityQueue = new PriorityQueue<TreeNode>(heuristicCompartor);
	
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

	@Override
	public TreeNode remove() {
		return priorityQueue.poll();
	}
	
	private static int HeuristicFun(TreeNode node) {
		R2D2State state = (R2D2State)node.getState();
		return state.getRemainingRocks()+ Math.abs(state.getX()-state.getTeleportalPos()[0]) + Math.abs(state.getY()-state.getTeleportalPos()[1]);
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
