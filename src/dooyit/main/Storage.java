package dooyit.main;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import dooyit.logic.Task;

public class Storage {
	
	String currentPath;
	String configFilePath;
	String filePath;
	TaskSaver saver;
	TaskLoader loader;
	
	static final String DEFAULT_FOLDER_STORAGE = "data\\";
	static final String NAME_FILE_CONFIG = "config.txt";

	public Storage() throws IOException{
		currentPath = System.getProperty("user.dir");
		configFilePath = getConfigPath(currentPath);
		filePath = getFileDestination(configFilePath);
		loader = new TaskLoader(filePath);
		saver = new TaskSaver(filePath);
	}
	
	private String getConfigPath(String currentPath) {
		return currentPath + File.separatorChar + NAME_FILE_CONFIG;
	}
	
	public boolean setFileDestination(String newFilePath) throws IOException {
		modifyConfig(newFilePath);
		saver.setFileDestination(newFilePath);
		loader.setFileDestination(newFilePath);
		
		return true;
	}

	public boolean saveTasks(ArrayList<Task> tasks) throws IOException{
		return saver.saveTasks(tasks);

	}

	public ArrayList<Task> loadTasks() throws IOException{
		return loader.loadTasks();
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
