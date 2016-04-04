//@@author A0126356E
package dooyit.common.datatype;

import java.util.ArrayList;

public class TaskGroup {

	private String title;
	private ArrayList<Task> tasks;
	private DateTime dateTime;

	public TaskGroup(String title) {
		this.title = title;
		tasks = new ArrayList<Task>();
	}
	
	public TaskGroup(String title, DateTime dateTime) {
		this.title = title;
		this.dateTime = dateTime;
		tasks = new ArrayList<Task>();
	}

	public void addTask(Task task) {
		this.tasks.add(task);
	}

	public void addTasks(ArrayList<Task> tasks) {
		this.tasks.addAll(tasks);
	}

	public DateTime getDateTime() {
		return dateTime;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		if(hasDateTime()){
			String dateString = dateTime.getDate();
			dateString = dateString.substring(0, dateString.length() - 5);
			return title + ", " + dateString;
		}else{
			return title;
		}
	}

	public ArrayList<Task> getTasks() {

		return tasks;
	}

	public int size(){
		return tasks.size();
	}
	
	public boolean isEmpty(){
		return tasks.isEmpty();
	}
	
	public boolean hasDateTime() {
		return dateTime != null;
	}
}
