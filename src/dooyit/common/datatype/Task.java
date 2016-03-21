package dooyit.common.datatype;

public class Task {

	public enum TaskType {
		DEADLINE, EVENT, FLOATING
	};

	protected String taskName;
	protected int taskId;
	protected TaskType taskType;
	
	protected boolean isCompleted;
	protected Category category;

	public static int curTaskId = 1;

	public Task() {
		taskId = curTaskId++;
	}

	public void changeName(String taskName) {
		this.taskName = taskName;
	}

//	public void changeDeadline(DateTime deadline) {
//		assert (deadline != null);
//
//		taskType = TaskType.DEADLINE;
//		this.dateTimeDeadline = deadline;
//		this.dateTimeStart = null;
//		this.dateTimeEnd = null;
//	}
//
//	public void changeEvent(DateTime start, DateTime end) {
//		assert (start != null && end != null);
//
//		taskType = TaskType.EVENT;
//		this.dateTimeDeadline = null;
//		this.dateTimeStart = start;
//		this.dateTimeEnd = end;
//	}

	public void mark() {
		isCompleted = true;
	}

	public void unMark() {
		isCompleted = false;
	}

	public boolean isCompleted() {
		return isCompleted;
	}
	
	public boolean isOverDue(DateTime dateTime){
		
		return true;
	}

	public boolean setCategory(Category category) {
		assert (category != null);

		if(hasCategory()){
			return false;
		}else{
			this.category = category;
			return true;
		}
	}
	
	public Category getCategory(){
		return category;
	}
	
	public boolean hasCategory(){
		return category != null;
	}

	public String getName() {
		return taskName;
	}

	public int getId() {
		return taskId;
	}

	public TaskType getTaskType() {
		return taskType;
	}

//	public DateTime getDeadlineTime() {
//		return dateTimeDeadline;
//	}
//
//	public DateTime getDateTimeStart() {
//		return dateTimeStart;
//	}
//
//	public DateTime getDateTimeEnd() {
//		return dateTimeEnd;
//	}

	

//	public boolean hasDeadlineTime() {
//		if (dateTimeDeadline == null) {
//			return false;
//		} else {
//			return dateTimeDeadline.hasTime();
//		}
//	}
//
//	public boolean hasStartTime() {
//		if (dateTimeStart == null) {
//			return false;
//		} else {
//			return dateTimeStart.hasTime();
//		}
//	}
//
//	public boolean hasEndTime() {
//		if (dateTimeEnd == null) {
//			return false;
//		} else {
//			return dateTimeEnd.hasTime();
//		}
//	}

	public String getDateString(){
		return "";
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Task) {
			Task task = (Task) o;
			return task.getId() == taskId;
		} else {
			return false;
		}
	}
}
