package dooyit.logic;

import java.util.ArrayList;

import dooyit.parser.DateTime;

public class TaskGroup {

	private String title;
	private ArrayList<Task> tasks;
	private DateTime dateTime;
	
	public TaskGroup(String title){
		this.title = title;
		tasks = new ArrayList<Task>();
	}
	
	public TaskGroup(String title, ArrayList<Task> tasks){
		this.title = title;
		this.tasks = tasks;
	}
	
	public TaskGroup(String title, ArrayList<Task> tasks, DateTime dateTime){
		this.title = title;
		this.tasks = tasks;
		this.dateTime = dateTime;
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
}
