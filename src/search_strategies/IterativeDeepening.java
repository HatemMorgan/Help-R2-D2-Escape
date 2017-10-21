package search_strategies;

import java.util.Collection;
import java.util.EmptyStackException;
import java.util.Stack;

import Project_Problem.R2D2State;
import Project_Problem.RepeatedStatesController;
import main.SearchStrategy;
import main.TreeNode;

/**
 * The​ ​iterative​ ​deepening​ ​class​ ​has​ ​an​ ​instance​ ​variable  currentDepth​ which​ ​stores​ ​the​ ​current​ ​maximum​ ​depth.​ 
 * ​The​ ​queuing​ ​data​ ​structure  for​ ​the​ ​ Iterative​ ​Deepening​ ​Search​ i ​ s​ ​a​ ​stack 
 */
public class IterativeDeepening implements SearchStrategy {
	private Stack<TreeNode> iterDeepeningQueue;
	private int currentDepth; 
	private TreeNode root;
	private RepeatedStatesController repeatedStatesController;

	
	public  IterativeDeepening() {
		this.iterDeepeningQueue = new Stack<TreeNode>();
		this.currentDepth = 0;
		this.repeatedStatesController = new RepeatedStatesController();
	}
	
	@Override
	public void queuingFunc(TreeNode[] expandedNodes) {
					
					// check if the queue is empty so we have to increment current depth and start again from root TreeNode
					// if node (last TreeNode in queue) is a goal node the GeneralSearchAlgrothim will terminate and will not remove 
					// root element again from queue
					if(iterDeepeningQueue.isEmpty() && expandedNodes[0].getDepth() > currentDepth ){
						currentDepth++;
						iterDeepeningQueue.push(root);
						repeatedStatesController.flush();
						System.out.println("Current Depth = "+currentDepth);
						return;
					}
					
					// check the depth of the first node. all the TreeNodes in expandedNodes array have the same depth
					if(expandedNodes[0].getDepth() > currentDepth)
							return;
					
					// push elements to iterDeepeningQueue 
					for(TreeNode node : expandedNodes){
							if(node != null)
								iterDeepeningQueue.push(node);
					}
	}
	
	@Override
	public Collection<TreeNode> getQueuingDataStructure() {
			return iterDeepeningQueue;
	}

	
	
	@Override
	public void addInitialState(TreeNode root) {
		this.root = root;
		iterDeepeningQueue.push(root);
	}

//	@Override
//	public TreeNode remove() {
//		return iterDeepeningQueue.pop();
//	}
	
	@Override
	public TreeNode remove() {
		TreeNode node = null;
		do{
			try{
				node = iterDeepeningQueue.pop();
			}catch(EmptyStackException e){
				currentDepth++;
				iterDeepeningQueue.push(root);
				repeatedStatesController.flush();
				System.out.println("Current Depth = "+currentDepth);
				break;
			}
		 }while(repeatedStatesController.isRepeated(node));
		
		return node;
	}

}
