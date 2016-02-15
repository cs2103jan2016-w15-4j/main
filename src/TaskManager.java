import java.util.ArrayList;

public class TaskManager {
	ArrayList<Task> tasks;

	public TaskManager(){
		tasks = new ArrayList<Task>();
	}
	
	public void AddTask(String data){
		Task task = new Task();
		task.initTask(data);
		tasks.add(task);
	}
	
	public void AddTaskWithTime(String data, String time){
		Task task = new Task();
		task.initTaskWithTime(data, time);
		tasks.add(task);
	}
	
	public void AddTaskWithDay(String data, String day){
		Task task = new Task();
		task.initTaskWithDay(data, day);
		tasks.add(task);
	}
	
	public void AddTaskDayAndTime(String data, String day, String time){
		Task task = new Task();
		task.initTaskDayAndTime(data, day, time);
		tasks.add(task);
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
			System.out.println(task.getId() + ": " + task.getName());
		}
		
		System.out.println();
	}
}
