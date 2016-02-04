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
	
	public Task deleteTask(int index){
		return tasks.remove(index - 1);
	}
	
	public ArrayList<Task> getTasks(){
		return tasks;
	}
	
	public void display(){
		System.out.println();
		System.out.println("Task List");
		
		int i = 1;
		for (Task task : tasks){
			System.out.println(i++ + ": " + task);
		}
		
		System.out.println();
	}
}
