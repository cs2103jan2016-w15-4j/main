//@@author A0126356E
package dooyit.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import dooyit.common.comparator.TaskDateComparator;
import dooyit.common.comparator.TaskUniqueIdComparator;
import dooyit.common.datatype.Category;
import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.DeadlineTask;
import dooyit.common.datatype.EventTask;
import dooyit.common.datatype.FloatingTask;
import dooyit.common.datatype.Task;
import dooyit.common.datatype.TaskGroup;
import dooyit.common.datatype.Task.TaskType;

/**
 * The task manager is class that manages all the task and provides various
 * functionalities: create floating task, create deadline task, create event
 * task, remove task, find task, check if task manager contains the task, mark
 * task, delete task, edit task, set task display id, grouping of task with
 * filtering and sorting, search for tasks by keyword, day and month
 * 
 * @author limtaeu
 *
 */
public class TaskManager {

	private static final int DAYS_PER_WEEK = 7;

	enum SearchType {
		NAME, DATE, DAY, MONTH
	}

	private static final int TASKGROUP_COUNT_LIMIT = 30;
	private ArrayList<Task> tasks;

	private String searchKey;
	private DateTime dateTime;

	public TaskManager() {
		tasks = new ArrayList<Task>();
	}

	/**
	 * creates a new floating task, task is not completed by default
	 * 
	 * @param data
	 *            name of the task
	 * @return the added floating task
	 */
	public Task addFloatingTask(String data) {
		return addFloatingTask(data, false);
	}

	/**
	 * creates a new deadline task, task is not completed by default
	 * 
	 * @param data
	 *            name of the task
	 * @param dateTime
	 *            deadline of the task
	 * @return the added deadlineTask
	 */
	public Task addDeadlineTask(String data, DateTime dateTime) {
		return addDeadlineTask(data, dateTime, false);
	}

	/**
	 * Creates a new event task, taks is not completed by default
	 * 
	 * @param data
	 *            name of the task
	 * @param start
	 *            start time of the task
	 * @param end
	 *            end time of the task
	 * @return the added event task
	 */
	public Task addEventTask(String data, DateTime start, DateTime end) {
		return addEventTask(data, start, end, false);
	}

	/**
	 * creates a new floating task
	 * 
	 * @param data
	 *            name of the task
	 * @param isCompleted
	 *            wheather the task is completed
	 * @return the added floating task
	 */
	public Task addFloatingTask(String data, boolean isCompleted) {
		FloatingTask floatingTask = new FloatingTask(data);
		if (isCompleted) {
			floatingTask.mark();
		}

		tasks.add(floatingTask);

		return floatingTask;
	}

	/**
	 * creates a new deadline task
	 * 
	 * @param data
	 *            name of the task
	 * @param isCompleted
	 *            wheather the task is completed
	 * @param dateTime
	 *            deadline of the task
	 * @return the added deadlineTask
	 */
	public Task addDeadlineTask(String data, DateTime dateTime, boolean isCompleted) {
		DeadlineTask deadlineTask = new DeadlineTask(data, dateTime);
		if (isCompleted) {
			deadlineTask.mark();
		}

		tasks.add(deadlineTask);
		return deadlineTask;
	}

	/**
	 * Creates a new event task, taks is not completed by default
	 * 
	 * @param data
	 *            name of the task
	 * @param isCompleted
	 *            wheather the task is completed
	 * @param start
	 *            start time of the task
	 * @param end
	 *            end time of the task
	 * @return the added event task
	 */
	public Task addEventTask(String data, DateTime start, DateTime end, boolean isCompleted) {
		EventTask eventTask = new EventTask(data, start, end);

		if (isCompleted) {
			eventTask.mark();
		}

		tasks.add(eventTask);
		return eventTask;
	}

	/**
	 * add a task into the task manager
	 * 
	 * @param task
	 */
	public void add(Task task) {
		tasks.add(task);
	}

	/**
	 * add all the task from tasks into the task manager
	 * 
	 * @param tasks
	 *            ArrayList containing the tasks to be added into the task
	 *            manager
	 */
	public void load(ArrayList<Task> tasks) {
		this.tasks.addAll(tasks);
	}

	/**
	 * Remove the task with the specified id
	 * 
	 * @param id
	 *            The ID of the task to be removed
	 * 
	 * @return the Task that has been removed
	 */
	public Task remove(int id) {
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getDisplayId() == id) {
				return tasks.remove(i);
			}
		}
		return null;
	}

	/**
	 * Remove the task
	 * 
	 * @param task
	 *            The Task to be removed
	 * @return true if successful
	 */
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
	 * Mark a task with the specified id
	 * 
	 * @param id
	 *            the ID of the task to be marked
	 * @return false if task is already marked
	 */
	public boolean markTask(int id) {
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getDisplayId() == id) {
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

	/**
	 * Mark a task with the specified task
	 * 
	 * @param id
	 *            the ID of the task to be marked
	 * @return false if task is already marked
	 */
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

	/**
	 * unmark a task with the specified id
	 * 
	 * @param id
	 *            the ID of the task to be marked
	 * @return false if task is already unmarked
	 */
	public boolean unmarkTask(int id) {
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getDisplayId() == id) {
				tasks.get(i).unMark();
				return true;
			}
		}
		// tell user if task is already marked.
		return false;
	}

	/**
	 * unmark a task with the specified task
	 * 
	 * @param id
	 *            the ID of the task to be marked
	 * @return false if task is already marked
	 */
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

	/**
	 * check if the task manager contains the task with the specified id
	 * 
	 * @param id
	 *            the ID of the task to be checked
	 * @return true if the task manager contains the task with the specified id
	 */
	public boolean contains(int id) {
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getDisplayId() == id) {
				return true;
			}
		}
		return false;
	}

	/**
	 * check if the task manager contains the task
	 * 
	 * @param task
	 *            the task to be checked
	 * @return true if the task manager contains the task
	 */
	public boolean contains(Task task) {
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).equals(task)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * find the task based on the given ID
	 * 
	 * @param id
	 *            the ID of the task
	 * @return the task with the id, null if task with ID is not found
	 */
	public Task find(int id) {
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getDisplayId() == id) {
				return tasks.get(i);
			}
		}
		return null;
	}

	/**
	 * find the task based on the given task
	 * 
	 * @param task
	 *            the task
	 * @return the task, null if task with ID is not found
	 */
	public Task find(Task task) {
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).equals(task)) {
				return tasks.get(i);
			}
		}
		return null;
	}

	/**
	 * 
	 * @return number of tasks in the task manager
	 */
	public int size() {
		return tasks.size();
	}

	/**
	 * removes the task that has the category
	 * 
	 * @param category
	 * @return the tasks that are removed
	 */
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

	/**
	 * clear all the task from the task manager
	 * 
	 * @return
	 */
	public ArrayList<Task> clear() {
		ArrayList<Task> clearedTasks = new ArrayList<Task>(tasks);
		tasks.clear();
		return clearedTasks;
	}

	/**
	 * 
	 * @return the most recently added task into the task manager
	 */
	public Task getMostRecentTask() {
		int latestId = Integer.MIN_VALUE;
		Task latestTask = null;

		for (Task task : tasks) {
			if (task.getUniqueId() > latestId) {
				latestId = task.getUniqueId();
				latestTask = task;
			}
		}
		return latestTask;
	}

	/**
	 * change the name of a task
	 * 
	 * @param taskId
	 *            ID of a task to be changed
	 * @param newName
	 *            new name of the task
	 * @return the changed task, null if cannot find the task with the id
	 */
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

	/**
	 * change a task into a floating task
	 * 
	 * @param taskId
	 * @return the changed task, null if cannot find the task with the id
	 */
	public Task changeToFloatingTask(int taskId) {
		if (!contains(taskId)) {
			return null;
		}

		Task removedTask = remove(taskId);
		Task newTask = new FloatingTask(removedTask);
		newTask.setCategory(removedTask.getCategory());
		add(newTask);

		return newTask;
	}

	/**
	 * change a task into a deadline task
	 * @param taskId
	 * @param dateTimeDeadline
	 * @return the changed task, null if cannot find the task with the id
	 */
	public Task changeTaskToDeadline(int taskId, DateTime dateTimeDeadline) {
		if (!contains(taskId)) {
			return null;
		}

		Task removedTask = remove(taskId);
		Task newTask = new DeadlineTask(removedTask, dateTimeDeadline);
		newTask.setCategory(removedTask.getCategory());
		add(newTask);

		return newTask;
	}

	/**
	 *  change a task into a event task
	 * @param taskId
	 * @param dateTimeStart
	 * @param dateTimeEnd
	 * @return the changed task, null if cannot find the task with the id
	 */
	public Task changeTaskToEvent(int taskId, DateTime dateTimeStart, DateTime dateTimeEnd) {
		if (!contains(taskId)) {
			return null;
		}

		Task removedTask = remove(taskId);
		Task newTask = new EventTask(removedTask, dateTimeStart, dateTimeEnd);
		newTask.setCategory(removedTask.getCategory());
		add(newTask);

		return newTask;
	}

	/**
	 * checks if the task is a floating task
	 * @param task
	 * @return true if the task is a floating task
	 */
	public boolean isFloatingTask(Task task) {
		return (task instanceof FloatingTask);
	}

	/**
	 * checks if the task has a deadline or event by today
	 * @param task
	 * @return true if the task has a deadline or event by today
	 */
	public boolean isTodayTask(Task task) {
		DateTime currDate = new DateTime();
		return task.isSameDate(currDate);
	}

	/**
	 * checks if the task has a deadline or event within the next 7 days
	 * @param task
	 * @return true if the task has a deadline or event within the next 7 days
	 */
	public boolean isNext7DaysTask(Task task) {
		DateTime currDate = new DateTime();

		for (int i = 0; i < DAYS_PER_WEEK; i++) {
			if (task.isSameDate(currDate)) {
				return true;
			}
			currDate.increaseByOneDay();
		}

		return false;
	}

	public boolean hasOverlapWithOverEventTask(Task inTask) {
		for (Task task : tasks) {
			if (!task.equals(inTask) && task.hasOverlap(inTask)) {
				return true;
			}
		}
		return false;
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

	public ArrayList<Task> getIncompleteTasks() {
		ArrayList<Task> allIncompleteTask = new ArrayList<Task>();

		for (Task task : tasks) {
			if (!task.isCompleted()) {
				allIncompleteTask.add(task);
			}
		}
		return allIncompleteTask;
	}

	public ArrayList<Task> getIncompleteTasks(DateTime overdueTime) {
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
				if (task.getTaskType() == TaskType.EVENT) {
					EventTask eventTask = (EventTask) task;
					size += eventTask.size();
				} else {
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

	public ArrayList<Task> getIncompleteTasksWithCategory(Category category) {
		ArrayList<Task> tasksWithCat = new ArrayList<Task>();

		for (Task task : tasks) {
			if (task.hasCategory() && task.getCategory().equals(category) && !task.isCompleted()) {
				tasksWithCat.add(task);
			}
		}

		return tasksWithCat;
	}

	public ArrayList<TaskGroup> getTaskGroupsAll() {
		onAllEventTasksMultiDayString();
		ArrayList<TaskGroup> taskGroups = new ArrayList<TaskGroup>();
		TaskGroup taskGroup;
		DateTime currDate = new DateTime();

		int numOverdueTask = getOverdueTasksSize(currDate);
		int totalSize = getNoOfIncompleteEventAndDeadlineTask() - numOverdueTask;

		addOverDueTaskGroup(taskGroups, currDate);

		taskGroup = createFloatTaskGroup();
		if (!taskGroup.isEmpty()) {
			taskGroups.add(taskGroup);
		}

		taskGroup = createDayTaskGroup("Today", currDate);
		if (!taskGroup.isEmpty()) {
			taskGroups.add(taskGroup);
			totalSize -= taskGroup.size();
		}

		currDate.increaseByOneDay();
		taskGroup = createDayTaskGroup("Tomorrow", currDate);
		if (!taskGroup.isEmpty()) {
			taskGroups.add(taskGroup);
			totalSize -= taskGroup.size();
		}

		currDate.increaseByOneDay();
		int taskGroupCount = 0;
		while (totalSize > 0 && taskGroupCount < TASKGROUP_COUNT_LIMIT) {
			taskGroup = createDayTaskGroup(currDate.getDayStr(), currDate);
			if (!taskGroup.isEmpty()) {
				taskGroups.add(taskGroup);
				totalSize -= taskGroup.size();
				taskGroupCount++;
			}
			currDate.increaseByOneDay();
		}

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

			taskGroup = createDayTaskGroup(title, currDate);
			taskGroups.add(taskGroup);
			currDate.increaseByOneDay();
		}

		resetTasksId(taskGroups);
		return taskGroups;
	}

	public ArrayList<TaskGroup> getTaskGroupsFloating() {
		ArrayList<TaskGroup> taskGroups = new ArrayList<TaskGroup>();

		TaskGroup taskGroup = createFloatTaskGroup();
		taskGroups.add(taskGroup);

		resetTasksId(taskGroups);
		return taskGroups;
	}

	public ArrayList<TaskGroup> getTaskGroupsToday() {
		onAllEventTasksMultiDayString();
		ArrayList<TaskGroup> taskGroups = new ArrayList<TaskGroup>();
		TaskGroup taskGroup;
		DateTime currDate = new DateTime();

		addOverDueTaskGroup(taskGroups, currDate);

		taskGroup = createDayTaskGroup("Today", currDate);
		taskGroups.add(taskGroup);

		resetTasksId(taskGroups);
		return taskGroups;
	}

	public ArrayList<TaskGroup> getTaskGroupsCompleted() {
		offAllEventTasksMultiDayString();
		ArrayList<TaskGroup> taskGroups = new ArrayList<TaskGroup>();

		TaskGroup taskGroup = createCompletedTaskGroup();
		taskGroups.add(taskGroup);

		resetTasksId(taskGroups);
		return taskGroups;
	}

	public ArrayList<TaskGroup> getTaskGroupCategory(Category category) {
		ArrayList<TaskGroup> taskGroups = getTaskGroupsAll();
		filterTaskGroupsByCategory(taskGroups, category);

		resetTasksId(taskGroups);
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

	private void addOverDueTaskGroup(ArrayList<TaskGroup> taskGroups, DateTime currDate) {
		ArrayList<Task> overdueTasks = getOverdueTasks(currDate);
		if (!overdueTasks.isEmpty()) {
			TaskGroup taskGroup = new TaskGroup("Overdue");
			taskGroup.addTasks(getOverdueTasks(currDate));
			sortTasks(taskGroup.getTasks());
			taskGroups.add(taskGroup);
		}
	}

	/**
	 * @return
	 */
	private TaskGroup createFloatTaskGroup() {
		TaskGroup taskGroup;
		taskGroup = new TaskGroup("Float");
		taskGroup.addTasks(getIncompleteFloatingTasks());
		sortTasks(taskGroup.getTasks());
		return taskGroup;
	}

	private TaskGroup createDayTaskGroup(String title, DateTime currDate) {
		currDate = new DateTime(currDate);

		TaskGroup taskGroup;
		taskGroup = new TaskGroup(title, new DateTime(currDate));
		taskGroup.addTasks(getIncompleteDeadlineTasks(currDate));
		taskGroup.addTasks(getIncompleteEventTasks(currDate));
		sortTasks(taskGroup.getTasks());
		return taskGroup;
	}

	/**
	 * @return
	 */
	public TaskGroup createCompletedTaskGroup() {
		TaskGroup taskGroup;
		taskGroup = new TaskGroup("Completed");
		taskGroup.addTasks(getCompletedTasks());
		sortTasks(taskGroup.getTasks());
		return taskGroup;
	}

	public void filterTaskGroupsByCategory(ArrayList<TaskGroup> taskGroups, Category category) {
		Iterator<TaskGroup> taskGroupsItr = taskGroups.iterator();
		while (taskGroupsItr.hasNext()) {
			TaskGroup taskGroup = taskGroupsItr.next();
			taskGroup.filterByCategory(category);

			if (taskGroup.size() == 0) {
				taskGroupsItr.remove();
			}
		}

	}

	public void filterTaskGroupsByDate(DateTime dateTime) {

	}

	public void filterTaskGroupsByDay() {

	}

	public void filterTaskGroupsByMonth() {

	}

	public void sortTasks(ArrayList<Task> tasks) {
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
			task.resetDisplayId();
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
				boolean isSuccess = task.setDisplayId(taskId);

				if (isSuccess) {
					taskId++;
				}
			}
		}
	}

	public void resetNewTask() {
		for (Task task : tasks) {
			task.setOld();
		}
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public void setSearchKey(DateTime dateTime) {
		this.dateTime = dateTime;
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

	public void display() {
		System.out.println();
		System.out.println("Task List");

		for (Task task : tasks) {
			System.out.println(task.getDisplayId() + ": " + task);
		}

		System.out.println();
	}
}
