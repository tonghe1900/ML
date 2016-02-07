package ml.decisiontree;

import java.util.ArrayList;
import java.util.List;

public class Record {

	private List<Attribute> attributes = new ArrayList<>();

	public Record(String oneline) {
		loadAttributes(oneline);

	}

	private void loadAttributes(String oneline) {
		String[] splits = oneline.split(",");
		for (int i = 0; i < splits.length; i++) {
			attributes.add(new Attribute(new Integer(i).toString(), splits[i]
					.trim()));

		}
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public String getAttribute(String attribute) {
		return attributes.get(new Integer(attribute)).getValue();
	}

	public Attribute getTargetAttribute() {
		return attributes.get(attributes.size() - 1);
	}

}
