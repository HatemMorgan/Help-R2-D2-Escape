package search_strategies;

import java.util.Collection;
import java.util.EmptyStackException;

import main.SearchStrategy;
import main.TreeNode;

import java.util.Stack;

import Project_Problem.RepeatedStatesController;

/**
 * Depth​ ​First​ ​Search,​ ​the​ ​queuing​ ​data​ ​structure​ ​is​ ​a​ ​ stack,​ ​ which​ ​is​ ​a  LIFO(Last​ ​In​ ​First​ ​Out)​ data​ ​structure.​
 */
public class DFS implements SearchStrategy {

   private static Stack<TreeNode> DfsQueue ;
   private RepeatedStatesController repeatedStatesController;
	
   public DFS(){
	   DfsQueue = new Stack<TreeNode>();
		repeatedStatesController = new RepeatedStatesController();
   }
	@Override
	public void queuingFunc(TreeNode[] expandedNodes) {
		for(TreeNode N: expandedNodes){
			if(N != null)
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

//	@Override
//	public TreeNode remove() {
//		return DfsQueue.pop();
//	}

	@Override
	public TreeNode remove() {
		TreeNode node;
		do{
			try{
			node = DfsQueue.pop();
			// if queue is empty then  return null indicating that queue is empty  
			}catch(EmptyStackException e ){
				return null;
			}
		 }while(repeatedStatesController.isRepeated(node));
		
		return node;
	}
	
}
