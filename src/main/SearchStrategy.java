package main;

import java.util.Collection;
import java.util.PriorityQueue;
import java.util.Stack;

public interface SearchStrategy {
	/**
	 * takes​ ​the​ ​expanded​ ​nodes​ ​as​ ​input​ ​and​ ​enqueues​ ​them​
	 * ​according​ ​to​ ​the queuing​ ​function​ ​corresponding​ ​to​ ​the​
	 * ​strategy
	 * 
	 * @param expandedNodes
	 *            array of the expanded nodes that will be enqueued
	 */
	public void queuingFunc(TreeNode[] expandedNodes);

	/**
	 * returns​ ​the​ ​data​ ​structure​ ​containing​ ​the​ ​expanded​ ​nodes
	 * not​ ​yet​ ​explored
	 * 
	 * @return java.util.Collection<TreeNode>
	 */
	public Collection<TreeNode> getQueuingDataStructure();

	/**
	 * takes​ ​the​ ​root​ ​node​ ​as​ ​an​ ​input​ ​and​ ​adds​ ​it​ ​to​ ​the​
	 * ​queuing​ ​data structure
	 * 
	 * @param root
	 */
	public void addInitialState(TreeNode root);

	/**
	 * removes​ ​the​ ​node​ ​to​ ​be​ ​explored​ ​next based on internal data
	 * structure of the search strategy implementing this interface
	 * 
	 * @return TreeNode
	 */
	public TreeNode remove();
}
