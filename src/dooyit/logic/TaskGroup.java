package dooyit.logic;

import java.util.ArrayList;

public class TaskGroup {

	private String title;
	private ArrayList<Task> tasks;
	
	public TaskGroup(String title){
		this.title = title;
		tasks = new ArrayList<Task>();
	}
	
	public TaskGroup(String title, ArrayList<Task> tasks){
		this.title = title;
		this.tasks = tasks;
	}
	
	public void addTask(Task task){
		this.tasks.add(task);
	}
	
	public void addTasks(ArrayList<Task> tasks){
		for(Task task : tasks){
			this.tasks.add(task);
		}
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
}
