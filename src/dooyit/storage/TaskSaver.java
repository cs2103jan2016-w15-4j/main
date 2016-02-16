package dooyit.storage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TaskSaver extends StorageOperations{
	
	TaskSaver(String filePath_) {
		this.filePath = filePath_ + File.separatorChar + NAME_FILE_STORAGE;
	}
	
	public boolean saveTasks(ArrayList<Task> tasks) throws IOException{
		File file = new File(filePath);
		
		BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));

		for(Task existingTask: tasks) {
			bWriter.append(setFormat(existingTask));
			bWriter.newLine();
		}
		bWriter.close();
		
		return true;
	}
}
