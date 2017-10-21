package main;

public abstract class GenericSearchProblem {

	private String[] operators;
	private State initialState;
	private TreeNode treeNode;
	
	/**
	 * An​ ​abstract​ ​transition​ ​function​ ​which​ ​take​ ​as​ ​a​ ​parameter​ ​the​ ​state​ ​and​ ​the operator​ ​and​ ​according​ ​to​ ​the​ ​search​ ​problem​
	 *  ​and​ ​operator​ ​the​ ​expanded​ ​node​ ​is  generated. 
	 * @param currState
	 * @param operator
	 * @return next state
	 * @throws CloneNotSupportedException
	 */
	public abstract State transitionFunction(State currState, String operator) throws CloneNotSupportedException;

	public abstract boolean goalTest(State s);
	
	/**
	 * This​ ​is​ ​an​ ​abstract​ ​general​ ​expand​ ​method​ ​which​ ​take​ ​as​ ​a​ ​parameter​ ​a​ ​node​ ​and  return​ ​its​ ​expanded​ ​nodes​ ​children​ 
	 *  ​in​ ​a​ ​form​ ​of​ ​array​ ​of​ ​nodes.​ ​The​ ​expanded  nodes​ ​are​ ​generated​ ​using​ ​the​ ​operators​ ​which​ ​are​ ​specified​ ​to​ ​each​ ​search 
	 *  problem,​ ​For​ ​each​ ​operator​ ​there​ ​is​ ​a​ ​generated​ ​node. 
	 *  
	 * @param node
	 * @return
	 * @throws CloneNotSupportedException
	 */
	public abstract TreeNode[] expand(TreeNode node) throws CloneNotSupportedException ;
	
	public abstract int pathCost();
	
	/**
	 * This​ ​method​ ​print​ ​the​ ​path​ ​from​ ​the​ ​goal​ ​to​ ​the​ ​node. 
	 * 
	 * @param goal
	 * @throws InterruptedException
	 * @throws CloneNotSupportedException
	 */
	public abstract void  printGoalPath(TreeNode goal) throws InterruptedException, CloneNotSupportedException;
	
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
