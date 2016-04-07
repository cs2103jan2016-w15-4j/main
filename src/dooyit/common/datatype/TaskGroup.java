//@@author A0126356E
package dooyit.common.datatype;

import java.util.ArrayList;
import java.util.Iterator;

import dooyit.common.datatype.DateTime.DAY;
import dooyit.common.datatype.DateTime.MONTH;

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
		if (hasDateTime()) {
			String dateString = dateTime.getDate();
			dateString = dateString.substring(0, dateString.length() - 5);
			return title + ", " + dateString;
		} else {
			return title;
		}
	}

	public ArrayList<Task> getTasks() {

		return tasks;
	}

	public int size() {
		return tasks.size();
	}

	public boolean isEmpty() {
		return tasks.isEmpty();
	}

	public boolean hasDateTime() {
		return dateTime != null;
	}

	public void filterByCategory(Category category) {
		Iterator<Task> taskItr = tasks.iterator();

		while(taskItr.hasNext()){
			Task task = taskItr.next();
			
			if(!task.hasCategory() || !task.getCategory().equals(category)){
				taskItr.remove();
			}
		}
	}
	
	public void filterByName(String searchKey) {
		searchKey = searchKey.toLowerCase();
		Iterator<Task> taskItr = tasks.iterator();

		while(taskItr.hasNext()){
			Task task = taskItr.next();
			
			String taskName = task.getName();
			taskName = taskName.toLowerCase();
			
			if(!taskName.contains(searchKey)){
				taskItr.remove();
			}
		}
	}
	
	public void filterByDate(DateTime dateTime) {
		Iterator<Task> taskItr = tasks.iterator();

		while(taskItr.hasNext()){
			Task task = taskItr.next();
			
			if(!task.isSameDate(dateTime)){
				taskItr.remove();
			}
		}
	}

	public void filterByMonth(String searchKey, MONTH month) {
		searchKey = searchKey.toLowerCase();
		Iterator<Task> taskItr = tasks.iterator();

		while(taskItr.hasNext()){
			Task task = taskItr.next();
			
			String taskName = task.getName();
			taskName = taskName.toLowerCase();
			
			if(!taskName.contains(searchKey) && !task.getDateTime().isMonth(month)){
				taskItr.remove();
			}
		}
	}
	
	public void filterByDay(String searchKey, DAY day) {
		searchKey = searchKey.toLowerCase();
		Iterator<Task> taskItr = tasks.iterator();

		while(taskItr.hasNext()){
			Task task = taskItr.next();
			
			String taskName = task.getName();
			taskName = taskName.toLowerCase();
			
			if(!taskName.contains(searchKey) && !task.getDateTime().isDay(day)){
				taskItr.remove();
			}
		}
	}
}
