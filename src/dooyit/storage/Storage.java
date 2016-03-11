package dooyit.storage;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import dooyit.storage.TaskController;
import dooyit.common.datatype.Category;
import dooyit.common.datatype.Task;
import dooyit.logic.core.TaskManager;
import dooyit.storage.CategoryController;

public class Storage{
	
	String currentPath;
	String configFilePath;
	String filePath;
	CategoryController catControl;
	TaskController taskControl;
	
	static final String DEFAULT_FOLDER_STORAGE = "data\\";
	static final String NAME_FILE_CONFIG = "config.txt";

	public Storage() throws IOException{
		currentPath = System.getProperty("user.dir");
		configFilePath = getConfigPath(currentPath);
		filePath = getFileDestination(configFilePath);
		catControl = new CategoryController(currentPath);
		taskControl = new TaskController(filePath);
	}
	
	private String getConfigPath(String currentPath) {
		return currentPath + File.separatorChar + NAME_FILE_CONFIG;
	}
	
	public boolean setFileDestination(String newFilePath) throws IOException {
		modifyConfig(newFilePath);
		taskControl.setFileDestination(newFilePath);
		
		return true;
	}

	public boolean saveTasks(ArrayList<Task> tasks) throws IOException{
		return taskControl.save(tasks);

	}

	public boolean loadTasks(TaskManager taskManager) throws IOException{
		return taskControl.load(taskManager);
	}
	
	public boolean saveCategory(ArrayList<Category> categories) throws IOException {
		return catControl.saveCategory(categories);
	}
	
	public ArrayList<Category> loadCategory() throws IOException {
		return catControl.loadCategory();
	}
	
	private String getFileDestination(String configFilePath) throws IOException {
		File configFile = new File(configFilePath);
		String saveDestination = "";
		if(configFile.exists()) {
			BufferedReader bReader = new BufferedReader(new FileReader(configFile));
			saveDestination = bReader.readLine();
			bReader.close();
		}
		else {
			String defaultSavePath = currentPath + File.separatorChar + DEFAULT_FOLDER_STORAGE;
			modifyConfig(defaultSavePath);
			return defaultSavePath;
		}
		
		return saveDestination;
	}

	private void modifyConfig(String newFilePath) throws IOException {
		File configFile = new File(configFilePath);
		BufferedWriter bWriter = new BufferedWriter(new FileWriter(configFile));
		bWriter.write(newFilePath);
		bWriter.close();
	}
}
