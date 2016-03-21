package dooyit.common.datatype;

public class Task {

	public enum TaskType {
		DEADLINE, EVENT, FLOATING
	};

	private String taskName;
	private int taskId;
	private TaskType taskType;
	private DateTime dateTimeDeadline;
	private DateTime dateTimeStart;
	private DateTime dateTimeEnd;
	private boolean isCompleted;
	private Category category;

	public static int curTaskId = 1;

	public Task() {
		taskId = curTaskId++;
	}

	public void initTaskFloat(String taskName) {
		taskType = TaskType.FLOATING;
		this.taskName = taskName;
	}

	public void initTaskDeadline(String taskName, DateTime deadline) {
		assert (deadline != null);

		taskType = TaskType.DEADLINE;
		this.taskName = taskName;
		this.dateTimeDeadline = deadline;
	}

	public void initTaskEvent(String taskName, DateTime start, DateTime end) {
		assert (start != null && end != null);

		taskType = TaskType.EVENT;
		this.taskName = taskName;
		this.dateTimeStart = start;
		this.dateTimeEnd = end;
	}

	public void changeName(String taskName) {
		this.taskName = taskName;
	}

	public void changeDeadline(DateTime deadline) {
		assert (deadline != null);

		taskType = TaskType.DEADLINE;
		this.dateTimeDeadline = deadline;
		this.dateTimeStart = null;
		this.dateTimeEnd = null;
	}

	public void changeEvent(DateTime start, DateTime end) {
		assert (start != null && end != null);

		taskType = TaskType.EVENT;
		this.dateTimeDeadline = null;
		this.dateTimeStart = start;
		this.dateTimeEnd = end;
	}

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

	public DateTime getDeadlineTime() {
		return dateTimeDeadline;
	}

	public DateTime getDateTimeStart() {
		return dateTimeStart;
	}

	public DateTime getDateTimeEnd() {
		return dateTimeEnd;
	}

	

	public boolean hasDeadlineTime() {
		if (dateTimeDeadline == null) {
			return false;
		} else {
			return dateTimeDeadline.hasTime();
		}
	}

	public boolean hasStartTime() {
		if (dateTimeStart == null) {
			return false;
		} else {
			return dateTimeStart.hasTime();
		}
	}

	public boolean hasEndTime() {
		if (dateTimeEnd == null) {
			return false;
		} else {
			return dateTimeEnd.hasTime();
		}
	}

	@Override
	public String toString() {
		String str = "";

		switch (taskType) {
		case FLOATING:
			str = taskName;
			break;

		case DEADLINE:
			str = taskName + ": deadline: " + dateTimeDeadline.toString();
			break;

		case EVENT:
			str = taskName + ": event: " + dateTimeStart.toString() + "," + dateTimeEnd.toString();
			break;
		default:
			str = "";
		}
		
		if(category != null){
			System.out.println("Has Cat: " + category.getName());
		}

		return str;
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
