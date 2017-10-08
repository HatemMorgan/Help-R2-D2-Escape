package search_strategies;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

import main.SearchStrategy;
import main.TreeNode;

public class BFS implements SearchStrategy {
	static Queue<TreeNode> bfsQueue = new LinkedList<TreeNode>();
	
	@Override
	public void queuingFunc(TreeNode[] expandedNodes) {
		for (TreeNode node : expandedNodes)
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

	@Override
	public TreeNode remove() {
		return bfsQueue.remove();
	}

}
