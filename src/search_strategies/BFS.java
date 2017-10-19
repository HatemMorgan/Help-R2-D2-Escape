package search_strategies;

import java.util.Collection;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

import Project_Problem.RepeatedStatesController;
import main.SearchStrategy;
import main.TreeNode;

public class BFS implements SearchStrategy {
	private Queue<TreeNode> bfsQueue = new LinkedList<TreeNode>();
	private RepeatedStatesController repeatedStatesController;
	
	public BFS() {
		repeatedStatesController = new RepeatedStatesController();
	}
	
	@Override
	public void queuingFunc(TreeNode[] expandedNodes) {
		for (TreeNode node : expandedNodes)
			if(node != null)
				bfsQueue.add(node);
	}

	@Override
	public Collection<TreeNode> getQueuingDataStructure() {
		return bfsQueue;
	}

	@Override
	public void addInitialState(TreeNode root) {
		bfsQueue.add(root);

	}

//	@Override
//	public TreeNode remove() {
//		return  bfsQueue.remove();
//	}
	
	@Override
	public TreeNode remove() {
		TreeNode node = null;
		do{
			try{
			node = bfsQueue.remove();
			// if queue is empty then  return null indicating that queue is empty  
			}catch(NoSuchElementException e){
				return null;
			}
		 }while(repeatedStatesController.isRepeated(node));
		
		return node;
	}

}
