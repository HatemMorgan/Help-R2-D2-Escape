package search_strategies;

import java.util.Collection;

import main.SearchStrategy;
import main.TreeNode;

import java.util.Stack;

public class DFS implements SearchStrategy {

   private static Stack<TreeNode> DfsQueue ;
   public DFS(){
	   DfsQueue = new Stack<TreeNode>();
   }
	@Override
	public void queuingFunc(TreeNode[] expandedNodes) {
		for(TreeNode N: expandedNodes){
			DfsQueue.push(N);
		}
	}

	@Override
	public Collection<TreeNode> getQueuingDataStructure() {
		return DfsQueue;
	}

	@Override
	public void addInitialState(TreeNode root) {
		DfsQueue.push(root);
	}

	@Override
	public TreeNode remove() {
		return DfsQueue.pop();
	}

}
