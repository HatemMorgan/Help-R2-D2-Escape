package main;

public abstract class GenericSearchProblem {

	private String[] operators;
	private State initialState;
	private TreeNode treeNode;

	public abstract State transitionFunction(State currState, String operator) throws CloneNotSupportedException;

	public abstract boolean goalTest(State s);
	
	public abstract TreeNode[] expand(TreeNode node) throws CloneNotSupportedException ;
	
	public abstract int pathCost();
	
	public String[] getOperators() {
		return operators;
	}

	public State getInitialState() {
		return initialState;
	}

	public void setOperators(String[] operators) {
		this.operators = operators;
	}

	public void setInitialState(State initialState) {
		this.initialState = initialState;
	}

	public TreeNode getTreeNode() {
		return treeNode;
	}

	public void setTreeNode(TreeNode treeNode) {
		this.treeNode = treeNode;
	}

}
