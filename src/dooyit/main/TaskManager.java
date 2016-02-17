package dooyit.main;
import java.util.ArrayList;

public class TaskManager {
	ArrayList<Task> tasks;
	
	public TaskManager(){
		tasks = new ArrayList<Task>();
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
		task.convertToSavableString();
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
	
	public ArrayList<Task> getTasks(){
		return tasks;
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
