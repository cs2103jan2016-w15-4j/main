package dooyit.logic.commands;

import java.util.ArrayList;

import dooyit.common.datatype.CustomColor;
import dooyit.common.datatype.DateTime;
import dooyit.ui.UIMainViewType;

public class CommandUtils {

	public static Command createAddCommandFloat(String data) {
		AddCommand addCommand = new AddCommand(data);
		return addCommand;
	}

	public static Command createAddCommandDeadline(String data, DateTime deadline) {
		AddCommand addCommand = new AddCommand(data, deadline);
		return addCommand;
	}

	public static Command createAddCommandEvent(String data, DateTime start, DateTime end) {
		AddCommand addCommand = new AddCommand(data, start, end);
		return addCommand;
	}

	public static Command createDeleteCommand(int deleteId) {
		DeleteCommand deleteCommand = new DeleteCommand(deleteId);
		return deleteCommand;
	}

	public static Command createDeleteCommand(ArrayList<Integer> deleteIds) {
		DeleteCommand deleteCommand = new DeleteCommand(deleteIds);
		return deleteCommand;
	}

	public static Command createMarkCommand(int markId) {
		MarkCommand deleteCommand = new MarkCommand(markId);
		return deleteCommand;
	}

	public static Command createMarkCommand(ArrayList<Integer> markIds) {
		MarkCommand deleteCommand = new MarkCommand(markIds);
		return deleteCommand;
	}

	public static Command createUnMarkCommand(int unMarkId) {
		UnMarkCommand deleteCommand = new UnMarkCommand(unMarkId);
		return deleteCommand;
	}

	public static Command createUnMarkCommand(ArrayList<Integer> unMarkIds) {
		UnMarkCommand deleteCommand = new UnMarkCommand(unMarkIds);
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
	
	public static Command createAddCategoryCommand(String categoryName, CustomColor color) {
		AddCategoryCommand addCategoryCommand = new AddCategoryCommand(categoryName, color);
		return addCategoryCommand;
	}

	public static Command createSetCategoryCommand(int taskID, String categoryName) {
		SetCategoryCommand addCategoryCommand = new SetCategoryCommand(taskID, categoryName);
		return addCategoryCommand;
	}
	
	public static Command createShowTodayCommand() {
		ShowCommand showCommand = new ShowCommand(UIMainViewType.TODAY);

		return showCommand;
	}

	public static Command createShowNext7DaysCommand() {
		ShowCommand showCommand = new ShowCommand(UIMainViewType.EXTENDED);

		return showCommand;
	}

	public static Command createShowFloatCommand() {
		ShowCommand showCommand = new ShowCommand(UIMainViewType.FLOAT);

		return showCommand;
	}
	
	public static Command createShowAllCommand() {
		ShowCommand showCommand = new ShowCommand(UIMainViewType.ALL);

		return showCommand;
	}

	public static Command createShowCompletedCommand() {
		ShowCommand showCommand = new ShowCommand(UIMainViewType.COMPLETED);

		return showCommand;
	}

	public static Command createShowCategoryCommand(String categoryName) {
		// temp
		ShowCommand showCommand = new ShowCommand(UIMainViewType.COMPLETED, categoryName);

		return showCommand;
	}

	public static Command createEditCommandName(int taskId, String taskName) {
		EditCommand editCommand = new EditCommand(taskId, taskName);
		return editCommand;
	}

	public static Command createEditCommandDeadline(int taskId, DateTime deadline) {
		EditCommand editCommand = new EditCommand(taskId, deadline);
		return editCommand;
	}

	public static Command createEditCommandEvent(int taskId, DateTime start, DateTime end) {
		EditCommand editCommand = new EditCommand(taskId, start, end);
		return editCommand;
	}

	public static Command createEditCommandNameAndDeadline(int taskId, String taskName, DateTime deadline) {
		EditCommand editCommand = new EditCommand(taskId, taskName, deadline);
		return editCommand;
	}

	public static Command createEditCommandNameAndEvent(int taskId, String taskName, DateTime start, DateTime end) {
		EditCommand editCommand = new EditCommand(taskId, taskName, start, end);
		return editCommand;
	}

	public static Command createStorageCommand(String path) {
		StorageCommand storageCommand = new StorageCommand(path);
		return storageCommand;
	}
	
	public static Command createChangeThemeCommand(String themeString){
		ChangeThemeCommand changeThemeCommand = new ChangeThemeCommand(themeString);
		return changeThemeCommand;
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
