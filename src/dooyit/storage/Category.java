package dooyit.storage;

public class Category {
	private String name;
	private int[] rgb;
	
	public Category() {
		this.name = "All";
		this.rgb = new int[3];
	}
	
	public Category(String name) {
		this.name = name;
		this.rgb = new int[3];
	}
	
	public Category(String[] catInfo) {
		this.name = catInfo[0];
		this.rgb = new int[3];
		this.rgb[0] = Integer.parseInt(catInfo[1]);
		this.rgb[1] = Integer.parseInt(catInfo[2]);
		this.rgb[2] = Integer.parseInt(catInfo[3]);
	}
	
	public String getName() {
		return this.name;
	}
	
	public int[] getRGB() {
		return this.rgb;
	}
	
	public String toString() {
		return name + " " + rgb[0] + " " + rgb[1] + " " + rgb[2];
	}
}
