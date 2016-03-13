package dooyit.logic.core;
import java.util.ArrayList;

import dooyit.common.datatype.Task;
import dooyit.common.datatype.TaskGroup;
import dooyit.common.datatype.Task.TaskType;
import dooyit.parser.DateTime;

public class TaskManager {
	private ArrayList<Task> tasks;
	private DateTime dateTime;
	
	
	public TaskManager(){
		tasks = new ArrayList<Task>();
	}
	
	public Task AddTaskFloat(String data){
		Task task = new Task();
		task.initTaskFloat(data);
		tasks.add(task);
		return task;
	}
	
	public Task AddTaskDeadline(String data, DateTime dateTime){
		Task task = new Task();
		task.initTaskDeadline(data, dateTime);
		tasks.add(task);
		return task;
	}

	public Task AddTaskEvent(String data, DateTime start, DateTime end){
		Task task = new Task();
		task.initTaskEvent(data, start, end);
		tasks.add(task);
		return task;
	}
	
	public Task AddCompletedTaskFloat(String data){
		Task task = new Task();
		task.initTaskFloat(data);
		task.mark();
		tasks.add(task);
		return task;
	}
	
	public Task AddCompletedTaskDeadline(String data, DateTime dateTime){
		Task task = new Task();
		task.initTaskDeadline(data, dateTime);
		task.mark();
		tasks.add(task);
		return task;
	}

	public Task AddCompletedTaskEventDone(String data, DateTime start, DateTime end){
		Task task = new Task();
		task.initTaskEvent(data, start, end);
		task.mark();
		tasks.add(task);
		return task;
	}
	
	public Task deleteTask(int id){
		for(int i=0; i<tasks.size(); i++){
			if(tasks.get(i).getId() == id){
				return  tasks.remove(i);
			}
		}
		
//		for(int i=0; i<doneTasks.size(); i++){
//			if(tasks.get(i).getId() == id){
//				return  tasks.remove(i);
//			}
//		}
		
		return null;
	}
	
	public boolean markTask(int id){
		for(int i=0; i<tasks.size(); i++){
			if(tasks.get(i).getId() == id ){
				if(tasks.get(i).isCompleted()){
					return false;
				}
				else{
					tasks.get(i).mark();
					return true;
				}
			}
		}
		// tell user if task is already marked.
		return false;
	}
	
	public boolean unMarkTask(int id){
		for(int i=0; i<tasks.size(); i++){
			if(tasks.get(i).getId() == id){
				tasks.get(i).unMark();
				return true;
			}
		}
		// tell user if task is already marked.
		return false;
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
	
	public ArrayList<Task> getIncompletedTasks(){
		ArrayList<Task> allIncompleteTask = new ArrayList<Task>();
		
		for(Task task : tasks){
			if(!task.isCompleted()){
				allIncompleteTask.add(task);
			}
		}
		return allIncompleteTask;
	}
	
	public ArrayList<Task> getCompletedTasks(){
		ArrayList<Task> allCompletedTask = new ArrayList<Task>();
		
		for(Task task : tasks){
			if(task.isCompleted()){
				allCompletedTask.add(task);
			}
		}
		return allCompletedTask;
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
		
		ArrayList<Task> deadlineTasks = new ArrayList<Task>();
		
		for(Task task : tasks){
			if(task.getTaskType() == TaskType.DEADLINE){
				deadlineTasks.add(task);
			}
		}
		return deadlineTasks;
	}
	
	public ArrayList<Task> getDeadlineTasks(DateTime dateTime){
		
		ArrayList<Task> deadlineTasks = new ArrayList<Task>();
		
		for(Task task : tasks){
			if(task.getTaskType() == TaskType.DEADLINE && task.getDeadlineTime().isTheSameDateAs(dateTime)){
				deadlineTasks.add(task);
			}
		}
		return deadlineTasks;
	}
	
	public ArrayList<Task> getEventTasks(){
		
		ArrayList<Task> eventTasks = new ArrayList<Task>();
		
		for(Task task : tasks){
			if(task.getTaskType() == TaskType.EVENT){
				eventTasks.add(task);
			}
		}
		return eventTasks;
	}

	public ArrayList<Task> getEventTasks(DateTime dateTime){
		
		ArrayList<Task> eventTasks = new ArrayList<Task>();
		
		for(Task task : tasks){
			if(task.getTaskType() == TaskType.EVENT && task.getDateTimeStart().isTheSameDateAs(dateTime)){
				eventTasks.add(task);
			}
		}
		return eventTasks;
	}
	
	public ArrayList<TaskGroup> getTaskGroupsAll(){
		ArrayList<TaskGroup> taskGroups = new ArrayList<TaskGroup>();
		taskGroups.add(new TaskGroup("All", getIncompletedTasks()));
		return taskGroups;
	}
	
	public ArrayList<TaskGroup> getTaskGroupsToday(){
		ArrayList<TaskGroup> taskGroups = new ArrayList<TaskGroup>();
		DateTime currDate = new DateTime();
		taskGroups.add(new TaskGroup("Today", getDeadlineTasks(currDate), getEventTasks(currDate), currDate));
		return taskGroups;
	}

	public ArrayList<TaskGroup> getTaskGroupsCompleted(){
		ArrayList<TaskGroup> taskGroups = new ArrayList<TaskGroup>();
		taskGroups.add(new TaskGroup("Completed", getCompletedTasks()));
		return taskGroups;
	}
	
	public ArrayList<TaskGroup> getTaskGroupsNext7Days(){
		
		DateTime currDate = new DateTime();
		ArrayList<TaskGroup> taskGroups = new ArrayList<TaskGroup>();
		
		
		taskGroups.add(new TaskGroup("Today", getDeadlineTasks(currDate), getEventTasks(currDate), new DateTime(currDate)));
		currDate.increaseByOne();
		
		taskGroups.add(new TaskGroup("Tomorrow", getDeadlineTasks(currDate), getEventTasks(currDate), new DateTime(currDate)));
		currDate.increaseByOne();
		
		
		for(int i=0; i<5; i++){
			taskGroups.add(new TaskGroup(currDate.getDayStr(), getDeadlineTasks(currDate), getEventTasks(currDate), new DateTime(currDate)));
			currDate.increaseByOne();
			
		}
		
		return taskGroups;
	}
	
	public void display(){
		System.out.println();
		System.out.println("Task List");

		for (Task task : tasks){
			System.out.println(task.getId() + ": " + task);
			//System.out.println(task.convertToSavableString());
		}
		
		System.out.println();
	}
}
