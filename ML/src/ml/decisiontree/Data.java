package ml.decisiontree;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public enum Data {
	INSTANCE;
	private List<Record> records = new ArrayList<>();
	private List<String> determiningAttributes = new ArrayList<>();
	public List<String> getDeterminingAttributes() {
		return determiningAttributes;
	}
	public List<Record> loadData(String fullFilePath) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					fullFilePath)));
			String line = "";
			while ((line = br.readLine()) != null) {
				if (line.length() > 0) {
					records.add(new Record(line));
				}

			}
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}

			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		Record record = records.get(0);
		for(int i=0;i<record.getAttributes().size()-1;i++){
			determiningAttributes.add(new Integer(i).toString());
		}
		return records;
	}

}
