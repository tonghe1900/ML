package ml.decisiontree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public enum DecisionTree {
	INSTANCE;
	public static class Node {

		
		private String bestClassifierAttribute;
		private Attribute attribute;
		private SampleDistribute sample = new SampleDistribute();
		private double entropy;
		private List<Node> children = new ArrayList<>();
		private Set<String> potentialClassifierAttrs;

		public boolean whetherDivided() {
		    return sample.getDistribution().size()>0;
			
		}

		public Set<String> getPotentialClassifierAttrs() {
			return potentialClassifierAttrs;
		}

		public void setPotentialClassifierAttrs(
				Set<String> potentialClassifierAttrs) {
			this.potentialClassifierAttrs = potentialClassifierAttrs;
		}

		public Collection<Node> generateChildren(String attribute) {

			Map<String, Node> map = new HashMap<>();
			List<Record> records = this.sample.getSample();

			for (Record record : records) {
				String attributeVal = record.getAttribute(attribute);
				Node node = null;

				if (!map.containsKey(attributeVal)) {
					node = new Node();
					node.setAttribute(new Attribute(attribute, attributeVal));

					map.put(attributeVal, node);

				} else {
					node = map.get(attributeVal);
				}
				SampleDistribute sampleDistribute = node.getSampleDistribute();
				sampleDistribute.acceptOneRecord(record);

			}
			Collection<Node> values = map.values();
			for (Node node : values) {
				Set<String> potentials = node.getPotentialClassifierAttrs();
				if (potentials == null) {
					Set<String> potentialAttrs = new HashSet<>(
							this.getPotentialClassifierAttrs());
					potentialAttrs.remove(attribute);
					node.setPotentialClassifierAttrs(potentialAttrs);
				}

				node.calculteEntropy();
			}

			return values;
		}

		public double getEntropy() {
			return entropy;
		}

		public String getBestClassifierAttribute() {
			return bestClassifierAttribute;
		}

		public void setBestClassifierAttribute(String bestClassifierAttribute) {
			this.bestClassifierAttribute = bestClassifierAttribute;
		}

		public Attribute getAttribute() {
			return attribute;
		}

		public void setAttribute(Attribute attribute) {
			this.attribute = attribute;
		}

		public int getTotal() {
			SampleDistribute sample = getSampleDistribute();
			return sample.getSample().size();
		}

		public SampleDistribute getSampleDistribute() {
			return sample;
		}

		public void setSample(SampleDistribute sample) {
			this.sample = sample;
		}

		public List<Node> getChildren() {
			return children;
		}

		public void setChildren(List<Node> children) {
			this.children = children;
		}

		public void calculteEntropy() {
			int total = sample.getSample().size();
			this.entropy =  sample.getDistribution().values().stream().map(count -> {
				double percent = (double) count / (double) total;
				return percent* ((-1) * Math.log10(percent) / Math.log10(2));
			}).reduce(0.0, (x,y )-> x+y);
			
			

		}

	}

	private Node root = new Node();

	public Node getRoot() {
		return root;
	}

}
