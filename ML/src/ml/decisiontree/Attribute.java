package ml.decisiontree;

public class Attribute {
    private String name;
    private String value;
	public String getName() {
		return name;
	}
	public String getValue() {
		return value;
	}
	public Attribute(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}
    
}
