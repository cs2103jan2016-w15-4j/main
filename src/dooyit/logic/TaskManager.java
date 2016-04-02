package dooyit.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.DeadlineTask;
import dooyit.common.datatype.EventTask;
import dooyit.common.datatype.FloatingTask;
import dooyit.common.datatype.Task;
import dooyit.common.datatype.TaskGroup;
import dooyit.common.datatype.TaskUniqueIdComparator;
import dooyit.common.datatype.Task.TaskType;
import dooyit.common.datatype.TaskDateComparator;

public class TaskManager {
	private ArrayList<Task> tasks;

	public TaskManager() {
		tasks = new ArrayList<Task>();
	}

	public Task addFloatingTask(String data) {
		return addFloatingTask(data, false);
	}

	public Task addDeadlineTask(String data, DateTime dateTime) {
		return addDeadlineTask(data, dateTime, false);
	}

	public Task addEventTask(String data, DateTime start, DateTime end) {
		return addEventTask(data, start, end, false);
	}

	public Task addFloatingTask(String data, boolean isCompleted) {
		FloatingTask floatingTask = new FloatingTask(data);
		if (isCompleted) {
			floatingTask.mark();
		}

		tasks.add(floatingTask);

		return floatingTask;
	}

	public Task addDeadlineTask(String data, DateTime dateTime, boolean isCompleted) {
		DeadlineTask deadlineTask = new DeadlineTask(data, dateTime);
		if (isCompleted) {
			deadlineTask.mark();
		}

		tasks.add(deadlineTask);
		return deadlineTask;
	}

	public Task addEventTask(String data, DateTime start, DateTime end, boolean isCompleted) {
		EventTask eventTask = new EventTask(data, start, end);

		if (isCompleted) {
			eventTask.mark();
		}

		tasks.add(eventTask);
		return eventTask;
	}

	public void add(Task task) {
		tasks.add(task);
	}

	public void load(ArrayList<Task> tasks) {
		this.tasks.addAll(tasks);
	}

	public Task remove(int id) {
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getId() == id) {
				return tasks.remove(i);
			}
		}
		return null;
	}

	public boolean remove(Task task) {
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).equals(task)) {
				tasks.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * Mark a task based on id
	 * 
	 * @param id
	 *            task id
	 * @return false if task is already marked
	 */
	public boolean markTask(int id) {
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getId() == id) {
				if (tasks.get(i).isCompleted()) {
					return false;
				} else {
					tasks.get(i).mark();
					return true;
				}
			}
		}
		// tell user if task is already marked.
		return false;
	}

	public boolean markTask(Task task) {
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).equals(task)) {
				if (tasks.get(i).isCompleted()) {
					return false;
				} else {
					tasks.get(i).mark();
					return true;
				}
			}
		}
		// tell user if task is already marked.
		return false;
	}

	public boolean unmarkTask(int id) {
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getId() == id) {
				tasks.get(i).unMark();
				return true;
			}
		}
		// tell user if task is already marked.
		return false;
	}

	public boolean unmarkTask(Task task) {
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).equals(task)) {
				tasks.get(i).unMark();
				return true;
			}
		}
		// tell user if task is already marked.
		return false;
	}

	public boolean contains(int id) {
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getId() == id) {
				return true;
			}
		}
		return false;
	}

	public boolean contains(Task task) {
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).equals(task)) {
				return true;
			}
		}
		return false;
	}

	public Task find(int id) {
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getId() == id) {
				return tasks.get(i);
			}
		}
		return null;
	}

	public Task find(Task task) {
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).equals(task)) {
				return tasks.get(i);
			}
		}
		return null;
	}

	public int size() {
		return tasks.size();
	}

	public ArrayList<Task> findTasksWithCategory(Category category) {
		ArrayList<Task> taskWithCat = new ArrayList<Task>();

		for (Task task : tasks) {
			if (task.hasCategory() && task.getCategory().equals(category)) {
				taskWithCat.add(task);
			}
		}
		return taskWithCat;
	}

	public ArrayList<Task> removeTasksWithCategory(Category category) {
		ArrayList<Task> taskWithCat = new ArrayList<Task>();

		Iterator<Task> itr = tasks.iterator();
		while (itr.hasNext()) {
			Task task = itr.next();

			if (task.hasCategory() && task.getCategory().equals(category)) {
				itr.remove();
				taskWithCat.add(task);
			}
		}

		return taskWithCat;
	}

	public ArrayList<Task> clear() {
		ArrayList<Task> clearedTasks = new ArrayList<Task>(tasks);
		tasks.clear();
		return clearedTasks;
	}

	public Task changeTaskName(int taskId, String newName) {
		if (!contains(taskId)) {
			return null;
		}

		Task removedTask = remove(taskId);
		Task newTask = removedTask.copy();
		newTask.changeName(newName);
		add(newTask);
		return newTask;
	}

	public Task changeTaskToDeadline(int taskId, DateTime dateTimeDeadline) {
		if (!contains(taskId)) {
			return null;
		}

		Task task = remove(taskId);
		// Task newTask = addDeadlineTask(task.getName(), dateTimeDeadline,
		// task.isCompleted());
		Task newTask = new DeadlineTask(task, dateTimeDeadline);
		newTask.setCategory(task.getCategory());
		add(newTask);

		return newTask;
	}

	public Task changeTaskToEvent(int taskId, DateTime dateTimeStart, DateTime dateTimeEnd) {
		if (!contains(taskId)) {
			return null;
		}

		Task task = remove(taskId);
		// Task newTask = addEventTask(task.getName(), dateTimeStart,
		// dateTimeEnd, task.isCompleted());
		Task newTask = new EventTask(task, dateTimeStart, dateTimeEnd);
		newTask.setCategory(task.getCategory());
		add(newTask);

		return newTask;
	}

	public void offEventTaskMultiDayString(Task task) {
		if (task.getTaskType() == TaskType.EVENT) {
			EventTask eventTask = (EventTask) task;
			eventTask.offMultiDay();
		}
	}

	public void onEventTaskMultiDayString(Task task) {
		if (task.getTaskType() == TaskType.EVENT) {
			EventTask eventTask = (EventTask) task;
			eventTask.onMultiDay();
		}
	}

	public void offAllEventTasksMultiDayString() {
		for (Task task : tasks) {
			if (task.getTaskType() == TaskType.EVENT) {
				EventTask eventTask = (EventTask) task;
				eventTask.offMultiDay();
			}
		}
	}

	public void onAllEventTasksMultiDayString() {
		for (Task task : tasks) {
			if (task.getTaskType() == TaskType.EVENT) {
				EventTask eventTask = (EventTask) task;
				eventTask.onMultiDay();
			}
		}
	}

	public int getNoOfIncompleteEventAndDeadlineTask() {
		int size = 0;

		size += getIncompleteDeadlineTasks().size();
		size += getIncompleteEventTasksSize();

		return size;
	}

	public ArrayList<Task> getAllTasks() {
		return tasks;
	}

	public ArrayList<Task> searchTask(String searchString) {
		ArrayList<Task> searchedTasks = new ArrayList<Task>();

		for (Task task : tasks) {
			String taskName = task.getName();
			taskName = taskName.toLowerCase();
			searchString = searchString.toLowerCase();

			if (taskName.contains(searchString)) {
				searchedTasks.add(task);
			}
		}

		return searchedTasks;
	}

	public ArrayList<Task> getIncompletedTasks() {
		ArrayList<Task> allIncompleteTask = new ArrayList<Task>();

		for (Task task : tasks) {
			if (!task.isCompleted()) {
				allIncompleteTask.add(task);
			}
		}
		return allIncompleteTask;
	}

	public ArrayList<Task> getIncompletedTasks(DateTime overdueTime) {
		ArrayList<Task> allIncompleteTask = new ArrayList<Task>();

		for (Task task : tasks) {
			if (!task.isCompleted() && !task.isOverDue(overdueTime)) {
				allIncompleteTask.add(task);
			}
		}
		return allIncompleteTask;
	}

	public ArrayList<Task> getCompletedTasks() {
		ArrayList<Task> allCompletedTask = new ArrayList<Task>();

		for (Task task : tasks) {
			if (task.isCompleted()) {
				allCompletedTask.add(task);
			}
		}
		return allCompletedTask;
	}

	public ArrayList<Task> getFloatingTasks() {
		ArrayList<Task> floatingTasks = new ArrayList<Task>();

		for (Task task : tasks) {
			if (task.getTaskType() == TaskType.FLOATING) {
				floatingTasks.add(task);
			}
		}
		return floatingTasks;
	}

	public ArrayList<Task> getIncompleteFloatingTasks() {
		ArrayList<Task> floatingTasks = new ArrayList<Task>();

		for (Task task : tasks) {
			if (task.getTaskType() == TaskType.FLOATING && !task.isCompleted()) {
				floatingTasks.add(task);
			}
		}
		return floatingTasks;
	}

	public ArrayList<Task> getDeadlineTasks() {

		ArrayList<Task> deadlineTasks = new ArrayList<Task>();

		for (Task task : tasks) {
			if (task.getTaskType() == TaskType.DEADLINE) {
				deadlineTasks.add(task);
			}
		}
		return deadlineTasks;
	}

	public ArrayList<Task> getDeadlineTasks(DateTime dateTime) {

		ArrayList<Task> deadlineTasks = new ArrayList<Task>();

		for (Task task : tasks) {
			if (task.getTaskType() == TaskType.DEADLINE) {
				if (task.isSameDate(dateTime)) {
					deadlineTasks.add(task);
				}
			}
		}
		return deadlineTasks;
	}

	public ArrayList<Task> getIncompleteDeadlineTasks() {

		ArrayList<Task> deadlineTasks = new ArrayList<Task>();

		for (Task task : tasks) {
			if (task.getTaskType() == TaskType.DEADLINE) {
				if (!task.isCompleted()) {
					deadlineTasks.add(task);
				}
			}
		}
		return deadlineTasks;
	}

	public ArrayList<Task> getIncompleteDeadlineTasks(DateTime dateTime) {

		ArrayList<Task> deadlineTasks = new ArrayList<Task>();

		for (Task task : tasks) {
			if (task.getTaskType() == TaskType.DEADLINE) {
				if (task.isSameDate(dateTime) && !task.isCompleted()) {
					deadlineTasks.add(task);
				}
			}
		}
		return deadlineTasks;
	}

	public ArrayList<Task> getEventTasks() {

		ArrayList<Task> eventTasks = new ArrayList<Task>();

		for (Task task : tasks) {
			if (task.getTaskType() == TaskType.EVENT) {
				eventTasks.add(task);
			}
		}
		return eventTasks;
	}

	public ArrayList<Task> getEventTasks(DateTime dateTime) {

		ArrayList<Task> eventTasks = new ArrayList<Task>();

		for (Task task : tasks) {
			if (task.getTaskType() == TaskType.EVENT) {
				if (task.isSameDate(dateTime)) {
					eventTasks.add(task);
				}
			}
		}
		return eventTasks;
	}

	public int getIncompleteEventTasksSize() {

		int size = 0;

		for (Task task : tasks) {
			if (task.getTaskType() == TaskType.EVENT) {
				EventTask eventTask = (EventTask) task;
				if (!eventTask.isCompleted()) {
					size += eventTask.size();
				}
			}
		}
		return size;
	}

	public ArrayList<Task> getIncompleteEventTasks() {

		ArrayList<Task> eventTasks = new ArrayList<Task>();

		for (Task task : tasks) {
			if (task.getTaskType() == TaskType.EVENT) {
				if (!task.isCompleted()) {
					eventTasks.add(task);
				}
			}
		}
		return eventTasks;
	}

	public ArrayList<Task> getIncompleteEventTasks(DateTime dateTime) {

		ArrayList<Task> eventTasks = new ArrayList<Task>();

		for (Task task : tasks) {
			if (task.getTaskType() == TaskType.EVENT) {
				if (task.isSameDate(dateTime) && !task.isCompleted()) {
					eventTasks.add(task);
				}
			}
		}
		return eventTasks;
	}

	public ArrayList<Task> getOverdueTasks(DateTime dateTime) {
		ArrayList<Task> overdueTasks = new ArrayList<Task>();

		for (Task task : tasks) {
			if (task.isOverDue(dateTime)) {
				overdueTasks.add(task);
			}
		}

		return overdueTasks;
	}

	public int getOverdueTasksSize(DateTime dateTime) {
		int size = 0;

		for (Task task : tasks) {

			if (task.isOverDue(dateTime)) {
				if(task.getTaskType() == TaskType.EVENT){
					EventTask eventTask = (EventTask)task;
					size += eventTask.size();
				}else{
					size++;
				}
			}
		}

		return size;
	}

	public ArrayList<Task> getTasksWithCategory(Category category) {
		ArrayList<Task> tasksWithCat = new ArrayList<Task>();

		for (Task task : tasks) {
			if (task.hasCategory() && task.getCategory().equals(category)) {
				tasksWithCat.add(task);
			}
		}

		return tasksWithCat;
	}

	public boolean isFloatingTask(Task task) {
		return (task instanceof FloatingTask);
	}

	public boolean isTodayTask(Task task) {
		DateTime currDate = new DateTime();
		return task.isSameDate(currDate);
	}

	public boolean isNext7DaysTask(Task task) {
		DateTime currDate = new DateTime();

		for (int i = 0; i < 7; i++) {
			if (task.isSameDate(currDate)) {
				return true;
			}
			currDate.increaseByOneDay();
		}

		return false;
	}

	public ArrayList<TaskGroup> getTaskGroupsAll() {
		onAllEventTasksMultiDayString();
		ArrayList<TaskGroup> taskGroups = new ArrayList<TaskGroup>();
		TaskGroup taskGroup;
		DateTime currDate = new DateTime();

		int numOverdueTask = getOverdueTasksSize(currDate);
		int totalSize = getNoOfIncompleteEventAndDeadlineTask() - numOverdueTask;

		addOverDueTaskGroup(taskGroups, currDate);

		taskGroup = new TaskGroup("Float");
		taskGroup.addTasks(getIncompleteFloatingTasks());
		if (!taskGroup.isEmpty()) {
			sortTask(taskGroup.getTasks());
			taskGroups.add(taskGroup);
		}

		taskGroup = new TaskGroup("Today", new DateTime(currDate));
		taskGroup.addTasks(getIncompleteDeadlineTasks(currDate));
		taskGroup.addTasks(getIncompleteEventTasks(currDate));
		if (!taskGroup.isEmpty()) {
			sortTask(taskGroup.getTasks());
			taskGroups.add(taskGroup);
			totalSize -= taskGroup.size();
		}
		currDate.increaseByOneDay();

		taskGroup = new TaskGroup("Tomorrow", new DateTime(currDate));
		taskGroup.addTasks(getIncompleteDeadlineTasks(currDate));
		taskGroup.addTasks(getIncompleteEventTasks(currDate));
		if (!taskGroup.isEmpty()) {
			sortTask(taskGroup.getTasks());
			taskGroups.add(taskGroup);
			totalSize -= taskGroup.size();
		}
		currDate.increaseByOneDay();

		while (totalSize != 0) {
			taskGroup = new TaskGroup(currDate.getDayStr(), new DateTime(currDate));
			taskGroup.addTasks(getIncompleteDeadlineTasks(currDate));
			taskGroup.addTasks(getIncompleteEventTasks(currDate));
			if (!taskGroup.isEmpty()) {
				sortTask(taskGroup.getTasks());
				taskGroups.add(taskGroup);
				totalSize -= taskGroup.size();
			}
			currDate.increaseByOneDay();
		}

		resetTasksId(taskGroups);
		return taskGroups;

	}

	public ArrayList<TaskGroup> getTaskGroupsToday() {
		onAllEventTasksMultiDayString();
		ArrayList<TaskGroup> taskGroups = new ArrayList<TaskGroup>();
		TaskGroup taskGroup;
		DateTime currDate = new DateTime();

		addOverDueTaskGroup(taskGroups, currDate);

		taskGroup = new TaskGroup("Today", currDate);
		taskGroup.addTasks(getIncompleteDeadlineTasks(currDate));
		taskGroup.addTasks(getIncompleteEventTasks(currDate));
		sortTask(taskGroup.getTasks());
		taskGroups.add(taskGroup);

		resetTasksId(taskGroups);
		return taskGroups;
	}

	public ArrayList<TaskGroup> getTaskGroupsFloating() {
		ArrayList<TaskGroup> taskGroups = new ArrayList<TaskGroup>();

		TaskGroup taskGroup = new TaskGroup("Float");
		taskGroup.addTasks(getIncompleteFloatingTasks());
		sortTask(taskGroup.getTasks());
		taskGroups.add(taskGroup);

		resetTasksId(taskGroups);
		return taskGroups;
	}

	public ArrayList<TaskGroup> getTaskGroupsCompleted() {
		offAllEventTasksMultiDayString();
		ArrayList<TaskGroup> taskGroups = new ArrayList<TaskGroup>();

		TaskGroup taskGroup = new TaskGroup("Completed");
		taskGroup.addTasks(getCompletedTasks());
		sortTask(taskGroup.getTasks());
		taskGroups.add(taskGroup);

		resetTasksId(taskGroups);
		return taskGroups;
	}

	public ArrayList<TaskGroup> getTaskGroupsNext7Days() {
		onAllEventTasksMultiDayString();
		DateTime currDate = new DateTime();
		ArrayList<TaskGroup> taskGroups = new ArrayList<TaskGroup>();
		TaskGroup taskGroup;

		addOverDueTaskGroup(taskGroups, currDate);

		String title;
		for (int i = 1; i <= 7; i++) {
			if (i == 1) {
				title = "Today";
			} else if (i == 2) {
				title = "Tomorrow";
			} else {
				title = currDate.getDayStr();
			}

			taskGroup = new TaskGroup(title, new DateTime(currDate));
			taskGroup.addTasks(getIncompleteDeadlineTasks(currDate));
			taskGroup.addTasks(getIncompleteEventTasks(currDate));
			sortTask(taskGroup.getTasks());
			taskGroups.add(taskGroup);
			currDate.increaseByOneDay();
		}

		resetTasksId(taskGroups);
		return taskGroups;
	}

	public ArrayList<TaskGroup> getTaskGroupCategory(Category category) {
		ArrayList<TaskGroup> taskGroups = new ArrayList<TaskGroup>();
		TaskGroup taskGroup = new TaskGroup(category.getName());
		taskGroup.addTasks(getTasksWithCategory(category));
		taskGroups.add(taskGroup);

		return taskGroups;
	}

	public ArrayList<TaskGroup> getTaskGroupSearched(String searchString) {
		ArrayList<TaskGroup> taskGroups = new ArrayList<TaskGroup>();
		ArrayList<Task> searchedTasks = searchTask(searchString);

		TaskGroup taskGroup = new TaskGroup("Search List");
		taskGroup.addTasks(searchedTasks);

		taskGroups.add(taskGroup);

		resetTasksId(taskGroups);
		return taskGroups;
	}

	public void addOverDueTaskGroup(ArrayList<TaskGroup> taskGroups, DateTime currDate) {
		ArrayList<Task> overdueTasks = getOverdueTasks(currDate);
		if (!overdueTasks.isEmpty()) {
			TaskGroup taskGroup = new TaskGroup("Overdue");
			taskGroup.addTasks(getOverdueTasks(currDate));
			sortTask(taskGroup.getTasks());
			taskGroups.add(taskGroup);
		}
	}

	public void addTodayTaskGroup(ArrayList<TaskGroup> taskGroups, DateTime currDate) {
		ArrayList<Task> overdueTasks = getOverdueTasks(currDate);
		if (!overdueTasks.isEmpty()) {
			TaskGroup taskGroup = new TaskGroup("Overdue");
			taskGroup.addTasks(getOverdueTasks(currDate));
			sortTask(taskGroup.getTasks());
			taskGroups.add(taskGroup);
		}
	}

	public void sortTask(ArrayList<Task> tasks) {
		TaskUniqueIdComparator uniqueIdComparator = new TaskUniqueIdComparator();
		Collections.sort(tasks, uniqueIdComparator);

		TaskDateComparator dateComparator = new TaskDateComparator();
		Collections.sort(tasks, dateComparator);
	}

	public void resetTasksId(ArrayList<TaskGroup> taskGroups) {
		clearOldId();
		assignNewId(taskGroups);
	}

	/**
	 * 
	 */
	public void clearOldId() {
		for (Task task : tasks) {
			task.resetId();
		}
	}

	/**
	 * @param taskGroups
	 */
	public void assignNewId(ArrayList<TaskGroup> taskGroups) {
		int taskId = 1;

		for (TaskGroup taskGroup : taskGroups) {
			ArrayList<Task> tasks = taskGroup.getTasks();
			for (Task task : tasks) {
				task.setId(taskId++);
			}
		}
	}

	public void display() {
		System.out.println();
		System.out.println("Task List");

		for (Task task : tasks) {
			System.out.println(task.getId() + ": " + task);
		}

		System.out.println();
	}
}
