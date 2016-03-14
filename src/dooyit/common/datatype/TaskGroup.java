package dooyit.common.datatype;

import java.util.ArrayList;

public class TaskGroup {

	private String title;
	private ArrayList<Task> tasks;
	private DateTime dateTime;
	
	public TaskGroup(String title){
		this.title = title;
		tasks = new ArrayList<Task>();
	}
	
	public TaskGroup(String title, ArrayList<Task> tasks){
		this.tasks = new ArrayList<Task>();
		this.title = title;
		addTasks(tasks);
	}
	
	public TaskGroup(String title, ArrayList<Task> tasks, DateTime dateTime){
		this.tasks = new ArrayList<Task>();
		this.title = title;
		this.dateTime = dateTime;
		addTasks(tasks);
	}
	
	public TaskGroup(String title, ArrayList<Task> tasks1, ArrayList<Task> tasks2, DateTime dateTime){
		this.tasks = new ArrayList<Task>();
		this.title = title;
		this.dateTime = dateTime;
		addTasks(tasks1);
		addTasks(tasks2);
	}
	
	public void addTask(Task task){
		this.tasks.add(task);
	}
	
	public void addTasks(ArrayList<Task> tasks){
		for(Task task : tasks){
			this.tasks.add(task);
		}
	}
	
	public DateTime getDateTime(){
		return dateTime;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getTitle(){
		return title;
	}
	
	public ArrayList<Task> getTasks(){
		
		return tasks;
	}
	
	public boolean hasDateTime(){
		return dateTime != null;
	}
}
