package main;

public class GenericSearchAlgorithm {

	public static TreeNode search(SearchStrategy searchStrategy,
			GenericSearchProblem problem, boolean visualize) throws CloneNotSupportedException {

		/*
		 * create an root node from initial state and add it to the queuing data
		 * structure of search strategy
		 */
		TreeNode root = new TreeNode(null, problem.getInitialState(), 0, 0,
				null);
		searchStrategy.addInitialState(root);

		while (!searchStrategy.getQueuingDataStructure().isEmpty()) {
			TreeNode node = searchStrategy.remove();

			if (problem.goalTest(node.getState())) {
				return node;
			} else {
				searchStrategy.queuingFunc(problem.expand(node));
			}
		}

		return null;
	}

	public static void main(String[] args) {
	}

}
