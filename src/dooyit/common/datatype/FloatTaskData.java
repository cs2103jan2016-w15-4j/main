package dooyit.common.datatype;

public class FloatTaskData extends TaskData{
	
	public FloatTaskData(String name, boolean isCompleted) {
		this.taskName = name;
		this.isCompleted = isCompleted;
	}
	
	public FloatTaskData(String name, String category, boolean isCompleted) {
		this.taskName = name;
		this.isCompleted = isCompleted;
		this.category = category;
	}
	
	public boolean equals(Object o) {
		if(o instanceof FloatTaskData) {
			FloatTaskData data = (FloatTaskData) o;
			if(this.hasCategory()) {
				return this.taskName.equals(data.getName())
						&& this.isCompleted == data.isCompleted()
						&& this.category.equals(data.getCategory());
			} else {
				return this.taskName.equals(data.getName())
						&& this.isCompleted == data.isCompleted();
			}
		}
		
		return false;
	}
}
