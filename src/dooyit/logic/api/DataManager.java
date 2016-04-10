//@@author A0126356E
package dooyit.logic.api;

import java.util.ArrayList;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.CategoryData;
import dooyit.common.datatype.Task;
import dooyit.common.datatype.TaskData;
import dooyit.common.exception.IncorrectInputException;

/**
 * DataManager is responsible for converting all tasks into taskDatas and
 * loading task and category into logic from taskData
 * 
 * @author limtaeu
 *
 */
public class DataManager {

	public DataManager() {

	}

	public ArrayList<TaskData> convertTaskstoTaskDatas(ArrayList<Task> tasks) {
		ArrayList<TaskData> taskDatas = new ArrayList<TaskData>();

		for (Task task : tasks) {
			TaskData taskData = task.convertToData();
			taskDatas.add(taskData);
		}

		return taskDatas;
	}

	public ArrayList<CategoryData> convertCategorytoCategoryDatas(ArrayList<Category> categories) {
		ArrayList<CategoryData> categoryDatas = new ArrayList<CategoryData>();

		for (Category category : categories) {
			CategoryData categoryData = category.convertToData();
			categoryDatas.add(categoryData);
		}

		return categoryDatas;
	}

	public void loadCategoryData(LogicController logic, ArrayList<CategoryData> categoryDatas) {
		for (CategoryData categoryData : categoryDatas) {
			try {
				logic.addCategory(categoryData.getName(), categoryData.getColor());
			} catch (IncorrectInputException e) {

			}
		}
	}

	public void loadTaskData(LogicController logic, ArrayList<TaskData> taskDatas) {
		for (TaskData taskData : taskDatas) {
			Task task = taskData.convertToTask();

			if (taskData.hasCategory()) {
				Category category = logic.findCategory(taskData.getCategory());
				task.setCategory(category);
			}

			logic.addTask(task);
		}
	}

}
