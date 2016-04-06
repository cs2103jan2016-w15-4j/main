//@@author A0126356E
package dooyit.logic.commands;

import java.util.ArrayList;
import dooyit.common.datatype.DateTime;

public class CommandUtils {

	public static Command createAddCommandFloat(String data) {
		AddTaskCommand addCommand = new AddTaskCommand(data);
		return addCommand;
	}

	public static Command createAddCommandDeadline(String data, DateTime deadline) {
		AddTaskCommand addCommand = new AddTaskCommand(data, deadline);
		return addCommand;
	}

	public static Command createAddCommandEvent(String data, DateTime start, DateTime end) {
		AddTaskCommand addCommand = new AddTaskCommand(data, start, end);
		return addCommand;
	}

	public static Command createDeleteCommand(int deleteId) {
		DeleteTaskCommand deleteCommand = new DeleteTaskCommand(deleteId);
		return deleteCommand;
	}

	public static Command createDeleteCommand(ArrayList<Integer> deleteIds) {
		DeleteTaskCommand deleteCommand = new DeleteTaskCommand(deleteIds);
		return deleteCommand;
	}

	public static Command createMarkCommand(int markId) {
		MarkTaskCommand deleteCommand = new MarkTaskCommand(markId);
		return deleteCommand;
	}

	public static Command createMarkCommand(ArrayList<Integer> markIds) {
		MarkTaskCommand deleteCommand = new MarkTaskCommand(markIds);
		return deleteCommand;
	}

	public static Command createUnMarkCommand(int unMarkId) {
		UnmarkTaskCommand deleteCommand = new UnmarkTaskCommand(unMarkId);
		return deleteCommand;
	}

	public static Command createUnMarkCommand(ArrayList<Integer> unMarkIds) {
		UnmarkTaskCommand deleteCommand = new UnmarkTaskCommand(unMarkIds);
		return deleteCommand;
	}

	public static Command createAddCategoryCommand(String categoryName) {
		AddCategoryCommand addCategoryCommand = new AddCategoryCommand(categoryName);
		return addCategoryCommand;
	}

	public static Command createAddCategoryCommand(String categoryName, String colorString) {
		AddCategoryCommand addCategoryCommand = new AddCategoryCommand(categoryName, colorString);
		return addCategoryCommand;
	}

	public static Command createSetCategoryCommand(int taskID, String categoryName) {
		SetCategoryCommand addCategoryCommand = new SetCategoryCommand(taskID, categoryName);
		return addCategoryCommand;
	}

	public static Command createSetCategoryCommand(ArrayList<Integer> taskIDs, String categoryName) {
		SetCategoryCommand addCategoryCommand = new SetCategoryCommand(taskIDs, categoryName);
		return addCategoryCommand;
	}

	public static Command createDeleteCategoryCommand(String categoryName) {
		DeleteCategoryCommand deleteCategoryCommand = new DeleteCategoryCommand(categoryName);
		return deleteCategoryCommand;
	}
	
	public static Command createRemoveCategoryFromTaskCommand(String categoryName, int taskId){
		RemoveCategoryFromTaskCommand removeCategoryFromTaskCommand = new RemoveCategoryFromTaskCommand(categoryName, taskId);
		return removeCategoryFromTaskCommand;
	}
	
	public static Command createEditCategoryCommand(String categoryName, String newCategoryName){
		EditCategoryCommand editCategoryCommand = new EditCategoryCommand(categoryName, newCategoryName);
		return editCategoryCommand;
	}

	public static Command createEditCategoryCommand(String categoryName, String newCategoryName, String newColourString){
		EditCategoryCommand editCategoryCommand = new EditCategoryCommand(categoryName, newCategoryName, newColourString);
		return editCategoryCommand;
	}
	
	public static Command createShowTodayCommand() {
		ShowCommand showCommand = new ShowCommand(ShowCommand.ShowCommandType.TODAY);

		return showCommand;
	}

	public static Command createShowNext7DaysCommand() {
		ShowCommand showCommand = new ShowCommand(ShowCommand.ShowCommandType.NEXT7DAY);

		return showCommand;
	}

	public static Command createShowFloatCommand() {
		ShowCommand showCommand = new ShowCommand(ShowCommand.ShowCommandType.FLOAT);

		return showCommand;
	}

	public static Command createShowAllCommand() {
		ShowCommand showCommand = new ShowCommand(ShowCommand.ShowCommandType.ALL);

		return showCommand;
	}

	public static Command createShowCompletedCommand() {
		ShowCommand showCommand = new ShowCommand(ShowCommand.ShowCommandType.COMPLETED);

		return showCommand;
	}

	public static Command createShowCategoryCommand(String categoryName) {
		// temp
		ShowCommand showCommand = new ShowCommand(ShowCommand.ShowCommandType.CATEGORY, categoryName);

		return showCommand;
	}

	public static Command createEditCommandToFloat(int taskId) {
		EditTaskCommand editCommand = new EditTaskCommand(taskId);
		return editCommand;
	}
	
	public static Command createEditCommandName(int taskId, String taskName) {
		EditTaskCommand editCommand = new EditTaskCommand(taskId, taskName);
		return editCommand;
	}

	public static Command createEditCommandDeadline(int taskId, DateTime deadline) {
		EditTaskCommand editCommand = new EditTaskCommand(taskId, deadline);
		return editCommand;
	}

	public static Command createEditCommandEvent(int taskId, DateTime start, DateTime end) {
		EditTaskCommand editCommand = new EditTaskCommand(taskId, start, end);
		return editCommand;
	}

	public static Command createEditCommandNameAndDeadline(int taskId, String taskName, DateTime deadline) {
		EditTaskCommand editCommand = new EditTaskCommand(taskId, taskName, deadline);
		return editCommand;
	}

	public static Command createEditCommandNameAndEvent(int taskId, String taskName, DateTime start, DateTime end) {
		EditTaskCommand editCommand = new EditTaskCommand(taskId, taskName, start, end);
		return editCommand;
	}

	public static Command createStorageCommand(String path) {
		StorageCommand storageCommand = new StorageCommand(path);
		return storageCommand;
	}

	public static Command createChangeThemeCommand(String themeString) {
		ChangeThemeCommand changeThemeCommand = new ChangeThemeCommand(themeString);
		return changeThemeCommand;
	}

	public static Command createUndoCommand() {
		UndoCommand undoCommand = new UndoCommand();
		return undoCommand;
	}

	public static Command createRedoCommand() {
		RedoCommand undoCommand = new RedoCommand();
		return undoCommand;
	}

	public static Command createSearchCommand(String searchString) {
		SearchCommand searchCommand = new SearchCommand(searchString);
		return searchCommand;
	}

	public static Command createClearCommand() {
		ClearTaskCommand clearCommand = new ClearTaskCommand();
		return clearCommand;
	}

	public static Command createHelpCommand() {
		HelpCommand undoCommand = new HelpCommand();
		return undoCommand;
	}

	public static Command createInvalidCommand(String errorMessage) {
		InvalidCommand invalidCommand = new InvalidCommand(errorMessage);
		return invalidCommand;
	}

	public static Command createExitCommand() {
		ExitCommand exitCommand = new ExitCommand();
		return exitCommand;
	}

}
