package ml.decisiontree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SampleDistribute {
	

	private List<Record> sample = new ArrayList<>();
	private Map<String, Integer> distribution = new HashMap<>();

	public List<Record> getSample() {
		return sample;
	}
	
	public Map<String, Integer> getDistribution() {
		return distribution;
	}

	public void acceptOneRecord(Record record) {
		sample.add(record);
		Attribute attribute = record.getTargetAttribute();
		String value = attribute.getValue();

		if (!distribution.containsKey(value)) {
			distribution.put(value, 1);

		} else {
			distribution.put(value, distribution.get(value) + 1);
		}
	}

}
