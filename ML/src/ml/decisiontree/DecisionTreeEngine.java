package ml.decisiontree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import ml.decisiontree.DecisionTree.Node;

public enum DecisionTreeEngine {
	INSTANCE;
	public DecisionTree createDecisionTree(String fullFilePath) {

		List<Record> data = Data.INSTANCE.loadData(fullFilePath);
		return createDecisionTree(data);
	}

	public DecisionTree createDecisionTree(List<Record> data) {
		Node root = initRoot(data);
		Queue<DecisionTree.Node> queue = new LinkedList<>();

		queue.add(root);
		while (!queue.isEmpty()) {
			DecisionTree.Node node = queue.poll();
			Collection<DecisionTree.Node> children = getChildrenForBestClassifierAttribute(node);
			if (children != null) {

				children.stream().filter(DecisionTree.Node::whetherDivided)
						.forEach((e) -> queue.add(e));
				
			}

		}
		return DecisionTree.INSTANCE;
	}

	public Node initRoot(List<Record> data) {
		Node root = DecisionTree.INSTANCE.getRoot();
		SampleDistribute sampleDis = root.getSampleDistribute();
		for (Record record : data) {
			sampleDis.acceptOneRecord(record);
		}
		root.calculteEntropy();
		root.setPotentialClassifierAttrs(new HashSet<>(Data.INSTANCE
				.getDeterminingAttributes()));
		return root;
	}

	public Collection<Node> generateChildren(Node node, String attribute) {

		Collection<Node> children = node.generateChildren(attribute);
		node.setChildren(new ArrayList<Node>(children));
		return children;
	}

	private Collection<DecisionTree.Node> getChildrenForBestClassifierAttribute(
			Node node) {
		Collection<DecisionTree.Node> result = null;
		Set<String> sets = new HashSet<>(node.getPotentialClassifierAttrs());
		if (sets.isEmpty())
			return null;
	   String selectedAttri = "";
		double min = Double.MAX_VALUE;
		for (String attribute : sets) {
			Collection<DecisionTree.Node> children = generateChildren(node,
					attribute);
			double entropy = calcEntropy(node, children);
			if (entropy < min) {
				min = entropy;
				selectedAttri = attribute;
				result = children;
			}

		}
		node.setBestClassifierAttribute(selectedAttri);

		return result;
	}

	private double calcEntropy(Node parent, Collection<Node> children) {
		int total = parent.getTotal();
		 return children.stream().map(e ->
		 ((double) e.getTotal()/(double)total)*e.getEntropy() ).reduce(0.0, (x, y)-> x+y);
		

	}

}
