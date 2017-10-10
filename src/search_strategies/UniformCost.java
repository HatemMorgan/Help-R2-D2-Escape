package search_strategies;

import java.util.Collection;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

import main.SearchStrategy;
import main.TreeNode;

public class UniformCost implements SearchStrategy {
	
	private Queue<TreeNode> priorityQueue = new PriorityQueue<TreeNode>(costCompartor);
	
	@Override
	public void queuingFunc(TreeNode[] expandedNodes) {
			
			for(TreeNode node : expandedNodes){
//				 check if the node was explored before or is it in the priorityQueue
//				 if not add it
//				 I checked that this node was not explored before to avoid Repeated States
//				if(! node.isExplored() || !node.isInQueuingDS()){
						if(node != null)
							priorityQueue.add(node);
			}
//				}else{
//					// check if the node is in the priorityQueue which means that I am in an cyclic path so replace the front
//				}
				
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
	
	private static Comparator<TreeNode> costCompartor = new Comparator<TreeNode>() {

		@Override
		public int compare(TreeNode node1, TreeNode node2) {
				return node2.getCost() - node1.getCost();
		}
	};

}
