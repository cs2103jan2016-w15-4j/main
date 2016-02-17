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
	
	protected String setFormat(Task task) {
		String[] taskInfo = task.convertToSavableString();
		String format = "";
		int infoLength = taskInfo.length;
		
		switch(infoLength) {
		case TYPE_FLOAT :
			format = String.format(STORAGE_FLOAT_FORMAT, taskInfo[0]);
			break;
		case TYPE_DEADLINE :
			//String dateTimeDeadline = convertToDateTimeString(taskInfo, 1);
			//format = String.format(STORAGE_DEADLINE_FORMAT, taskInfo[0], dateTimeDeadline);
			format = String.format(STORAGE_DEADLINE_FORMAT, taskInfo[0], taskInfo[1], taskInfo[2],
					taskInfo[3], taskInfo[4], taskInfo[5]);
			break;
		case TYPE_EVENT :
			//String dateTimeStart = convertToDateTimeString(taskInfo, 1);
			//String dateTimeEnd = convertToDateTimeString(taskInfo, 6);
			//format = String.format(STORAGE_EVENT_FORMAT, taskInfo[0], dateTimeStart, dateTimeEnd);
			format = String.format(STORAGE_EVENT_FORMAT, taskInfo[0], taskInfo[1], taskInfo[2],
					taskInfo[3], taskInfo[4], taskInfo[5], taskInfo[6], taskInfo[7], taskInfo[8],
					taskInfo[9], taskInfo[10]);
			break;
		}
		
		return format;
	}
	
	/*public String convertToDateTimeString(String[] taskInfo, int n) {
		String dateTimeString = "";
		
		for(int i=n; i<n+5; i++) {
			dateTimeString += taskInfo[i] + WHITESPACE;
		}
		
		return dateTimeString.trim();
	}*/
}
