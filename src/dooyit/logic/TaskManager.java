package dooyit.logic;
import java.util.ArrayList;

import dooyit.logic.Task.TaskType;
import dooyit.parser.DateTime;

public class TaskManager {
	ArrayList<Task> tasks;
	ArrayList<Task> doneTasks;
	DateTime dateTime;
	
	public TaskManager(){
		tasks = new ArrayList<Task>();
		doneTasks = new ArrayList<Task>();
	}
	
	public void AddTaskFloat(String data){
		Task task = new Task();
		task.initTaskFloat(data);
		tasks.add(task);
	}
	
	public void AddTaskDeadline(String data, DateTime dateTime){
		Task task = new Task();
		task.initTaskDeadline(data, dateTime);
		tasks.add(task);
		//task.convertToSavableString();
	}

	public void AddTaskEvent(String data, DateTime start, DateTime end){
		Task task = new Task();
		task.initTaskEvent(data, start, end);
		tasks.add(task);
		//task.convertToSavableString();
	}
	
	// return false if length is incorrect
	public boolean LoadTask(String[] strings){
		
		if(strings.length == 1){
			
			AddTaskFloat(strings[0]);
			return true;
			
		}else if(strings.length == 6){
			
			AddTaskDeadline(strings[0], 
							new DateTime(Integer.valueOf(strings[1]), Integer.valueOf(strings[2]), Integer.valueOf(strings[3]), strings[4], strings[5]));
			return true;
			
		}else if(strings.length == 11){
			AddTaskEvent(strings[0], 
							new DateTime(Integer.valueOf(strings[1]), Integer.valueOf(strings[2]), Integer.valueOf(strings[3]), strings[4], strings[5]), 
							new DateTime(Integer.valueOf(strings[6]), Integer.valueOf(strings[7]), Integer.valueOf(strings[8]), strings[9], strings[10]));
			return true;
		}
		return false;
	}
	
	public Task deleteTask(int id){
		for(int i=0; i<tasks.size(); i++){
			if(tasks.get(i).getId() == id){
				return tasks.remove(i);
			}
		}
		return null;
	}
	
	public boolean containsTask(int id){
		for(int i=0; i<tasks.size(); i++){
			if(tasks.get(i).getId() == id){
				return true;
			}
		}
		return false;
	}
	
	public boolean containsTask(String taskName){
		for(int i=0; i<tasks.size(); i++){
			if(tasks.get(i).getName().equals(taskName)){
				return true;
			}
		}
		return false;
	}
	
	public Task findTask(int id){
		for(int i=0; i<tasks.size(); i++){
			if(tasks.get(i).getId() == id){
				return tasks.get(i);
			}
		}
		return null;
	}
	
	public ArrayList<Task> getAllTasks(){
		return tasks;
	}
	
	public ArrayList<Task> getFloatingTasks(){
		ArrayList<Task> floatingTasks = new ArrayList<Task>();
		
		for(Task task : tasks){
			if(task.getTaskType() == TaskType.FLOATING){
				floatingTasks.add(task);
			}
		}
		return floatingTasks;
	}
	
	public ArrayList<Task> getDeadlineTasks(){
		
		ArrayList<Task> deadlineFloat = new ArrayList<Task>();
		
		for(Task task : tasks){
			if(task.getTaskType() == TaskType.FLOATING){
				deadlineFloat.add(task);
			}
		}
		return deadlineFloat;
	}
	
	public ArrayList<Task> getDeadlineTasks(int dd, int mm, int yy){
		
		return null;
	}
	
	public ArrayList<Task> getEventTasks(int dd, int mm, int yy){
		
		return null;
	}
	
	public ArrayList<TaskGroup> getAllTaskGroups(){
		ArrayList<TaskGroup> taskGroups = new ArrayList<TaskGroup>();
		taskGroups.add(new TaskGroup("All", getAllTasks()));
		return taskGroups;
	}
	
	public ArrayList<TaskGroup> getTodayTaskGroups(){
		ArrayList<TaskGroup> taskGroups = new ArrayList<TaskGroup>();
		taskGroups.add(new TaskGroup("Today", getAllTasks()));
		return taskGroups;
	}

	public ArrayList<TaskGroup> getNext7DaysTaskGroups(){
		
		return null;
	}
	
	public void display(){
		System.out.println();
		System.out.println("Task List");

		for (Task task : tasks){
			System.out.println(task.getId() + ": " + task);
		}
		
		System.out.println();
	}
}
