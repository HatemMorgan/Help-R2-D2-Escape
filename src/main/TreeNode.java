package main;

public class TreeNode {

	private TreeNode parent;
	private State state;
	private int depth;
	private int cost;
	private String operator;

	public TreeNode(TreeNode parent, State state, int depth, int cost,
			String operator) {
		this.parent = parent;
		this.state = state;
		this.depth = depth;
		this.cost = cost;
		this.operator = operator;
	}

	public TreeNode getParent() {
		return parent;
	}

	public State getState() {
		return state;
	}

	public int getDepth() {
		return depth;
	}

	public int getCost() {
		return cost;
	}

	public String getOperator() {
		return operator;
	}

}
