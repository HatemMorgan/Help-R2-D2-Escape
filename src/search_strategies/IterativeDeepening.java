package search_strategies;

import java.util.Collection;
import java.util.Stack;

import main.SearchStrategy;
import main.TreeNode;

public class IterativeDeepening implements SearchStrategy {
	private Stack<TreeNode> iterDeepeningQueue;
	private int currentDepth; 
	private TreeNode root;
	
	public  IterativeDeepening() {
		this.iterDeepeningQueue = new Stack<TreeNode>();
		this.currentDepth = 0;
	}
	
	@Override
	public void queuingFunc(TreeNode[] expandedNodes) {
			
		// check the depth of the first node. all the TreeNodes in expandedNodes array have the same depth
					if(expandedNodes[0].getDepth() > currentDepth)
							return;
					
					// push elements to iterDeepeningQueue 
					for(TreeNode node : expandedNodes){
							if(node != null)
								iterDeepeningQueue.push(node);
					}
	}
	
	@Override
	public Collection<TreeNode> getQueuingDataStructure() {
			return iterDeepeningQueue;
	}

	
	
	@Override
	public void addInitialState(TreeNode root) {
		this.root = root;
		iterDeepeningQueue.push(root);
	}

	@Override
	public TreeNode remove() {
		
		TreeNode node =  iterDeepeningQueue.pop();
		// check if the queue is empty so we have to increment current depth and start again from root TreeNode
		if(iterDeepeningQueue.isEmpty()){
			currentDepth++;
			iterDeepeningQueue.push(root);
			System.out.println("Current Depth = "+currentDepth);
		}
		// if node (last TreeNode in queue) is a goal node the GeneralSearchAlgrothim will terminate and will not remove 
		// root element again from queue
		return node;
	}

}
