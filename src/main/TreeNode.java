package main;

import java.util.ArrayList;

public class TreeNode {

	private TreeNode parent;
	private State state;
	private int depth;
	private int cost;
	private String operator;

	// private boolean explored;
	// private boolean inQueuingDS;

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
	
	public String toString(){
		return toString(this,this.getDepth());
	}

	private  String toString(TreeNode node,int depth) {
			// if its the root then stop
			if(node.getParent() == null)
				return node.getState().toString();
				
			// if it is the goal node
			if(node.getDepth() == depth){
				return toString(node.getParent(),depth)+"--- "+node.getOperator()+"-->"+node.getState().toString();
			}else{
				return toString(node.getParent(),depth)+"--- "+node.getOperator()+"-->"+node.getState().toString();
			}
			
    }
	
	

}
