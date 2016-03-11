package dooyit.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import dooyit.logic.core.TaskManager;

public class TaskLoader extends StorageOperations{
	
	TaskLoader(String filePath_) {
		this.filePath = filePath_;
	}
	
	public boolean loadTasks(TaskManager taskManager) throws IOException{
		File directory = new File(filePath);
		//ArrayList<Task> taskList = new ArrayList<Task>();

		if(directory.exists()) {
			String path = filePath + File.separatorChar + NAME_FILE_STORAGE;
			loadFromFile(path, taskManager);
		}
		else {
			createDirectory(directory);
		}

		return true;
	}
	
	private void createDirectory(File directory) {
		directory.mkdirs();
	}
	
	public void loadFromFile(String filePath, TaskManager taskManager) throws IOException {
		File file = new File(filePath);
		FileReader fReader = tryToOpen(file);
		if(fReader == null) {
			return;
		}
		else {
			readFromFile(fReader, taskManager);
		}
		
		return;
	}
	
	private FileReader tryToOpen(File file) {
		FileReader fReader = null;
		if(file.exists()) {
			try {
				fReader = new FileReader(file);
			} catch (FileNotFoundException fnfe) {
				System.out.println("Unable to access file");
				System.exit(2);
			}
		}
		return fReader;
	}
	
	private void loadToMemory(TaskManager taskManager, String taskFormat) {
		taskManager.LoadTask(taskFormat);
	}
	
	private void readFromFile(FileReader fReader, TaskManager taskManager) throws IOException {
		BufferedReader bReader = new BufferedReader(fReader);
		String taskInfo = bReader.readLine();
		while(taskInfo != null) {
			loadToMemory(taskManager, taskInfo);
			taskInfo = bReader.readLine();
		}
		bReader.close();
		fReader.close();
	}
}
