import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TaskLoader extends StorageOperations{
	
	TaskLoader(String filePath_) {
		this.filePath = filePath_;
	}
	
	public ArrayList<Task> loadTasks() throws IOException{
		File directory = new File(filePath);
		ArrayList<Task> taskList = new ArrayList<Task>();
		FileReader fReader = null;

		if(directory.exists()) {
			File file = new File(filePath + File.separatorChar + NAME_FILE_STORAGE);
			fReader = tryToOpen(file);
			if(fReader == null) {
				return taskList;
			}
			else {
				readFromFile(fReader, taskList);
			}
		}
		else {
			directory.mkdirs();
		}

		return taskList;
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
	
	private void loadToMemory(ArrayList<Task> taskList, String taskFormat) {
		String[] taskInfo = taskFormat.split(", ");
		Task existingTask = setTaskType(taskInfo);
		taskList.add(existingTask);
	}
	
	private void readFromFile(FileReader fReader, ArrayList<Task> taskList) throws IOException {
		BufferedReader bReader = new BufferedReader(fReader);
		String taskInfo = bReader.readLine();
		while(taskInfo != null) {
			loadToMemory(taskList, taskInfo);
			taskInfo = bReader.readLine();
		}
		bReader.close();
		fReader.close();
	}
}