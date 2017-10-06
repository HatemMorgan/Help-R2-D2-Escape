package main;

import java.util.Collection;
import java.util.PriorityQueue;
import java.util.Stack;

public interface SearchStrategy {
	public void queuingFunc(TreeNode[] expandedNodes) ;
	public Collection<TreeNode> getQueuingDataStructure ();
	public void addInitialState (TreeNode root);
	public TreeNode remove();
}
