import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class Storage {
	
	static final String STORAGE_FORMAT = "%1$s, %2$s, %3$s, %4$s";
	
	public Storage(){
		
	}
	
	public String setFormat(Task task){
		return String.format(STORAGE_FORMAT, task.taskName, task.day, task.time, task.taskId);
	}
	
	public void saveTasks(ArrayList<Task> tasks) throws IOException{
		File file = new File("mytextfile.txt");
		BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));
		
		for(Task existingTask: tasks) {
			bWriter.append(setFormat(existingTask));
			bWriter.newLine();
		}
		bWriter.close();
	}
	
	public ArrayList<Task> loadTasks(){
		
		return null;
	}
}
