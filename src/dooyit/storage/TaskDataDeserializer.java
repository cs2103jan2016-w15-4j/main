//@@author A0124586Y
package dooyit.storage;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import dooyit.common.datatype.DateTime;
import dooyit.common.datatype.DeadlineTaskData;
import dooyit.common.datatype.EventTaskData;
import dooyit.common.datatype.FloatingTaskData;
import dooyit.common.datatype.TaskData;

/**
 * The TaskDataDeserializer creates the TaskData object from its JSON
 * representation for loading
 * 
 * @author Dex
 *
 */
public class TaskDataDeserializer implements JsonDeserializer<TaskData> {

	private static final String DEADLINE = "dateTimeDeadline";
	private static final String EVENT_START = "dateTimeStart";
	private static final String EVENT_END = "dateTimeEnd";
	private static final String NAME = "taskName";
	private static final String CATEGORY = "category";
	private static final String IS_COMPLETED = "isCompleted";
	
	@Override
	public TaskData deserialize(JsonElement object, Type arg1, JsonDeserializationContext arg2)
			throws JsonParseException {
		TaskData task = null;
		JsonObject jsonTask = (JsonObject) object;

		String name = getName(jsonTask);
		boolean isCompleted = isCompleted(jsonTask);
		String categoryName = getCategoryName(jsonTask);

		if (jsonTask.has(DEADLINE)) {
			DateTime deadline = resolveDateTime(jsonTask, DEADLINE);
			task = (TaskData) new DeadlineTaskData(name, deadline, categoryName, isCompleted);
		} else if (jsonTask.has(EVENT_START) && jsonTask.has(EVENT_END)) {
			DateTime eventStart = resolveDateTime(jsonTask, EVENT_START);
			DateTime eventEnd = resolveDateTime(jsonTask, EVENT_END);
			task = (TaskData) new EventTaskData(name, eventStart, eventEnd, categoryName, isCompleted);
		} else {
			task = (TaskData) new FloatingTaskData(name, categoryName, isCompleted);
		}

		return task;
	}

	/**
	 * Checks if the task has been completed.
	 * 
	 * @param jsonTask The JSON representation of TaskData
	 * @return The boolean value of isCompleted
	 */
	private boolean isCompleted(JsonObject jsonTask) {
		boolean isCompleted = false;

		if (jsonTask.has(IS_COMPLETED)) {
			isCompleted = jsonTask.get(IS_COMPLETED).getAsBoolean();
		}
		return isCompleted;
	}

	/**
	 * Gets the name of the task from the JSON representation of TaskData
	 * 
	 * @param jsonTask The JSON representation of TaskData
	 * @return The name of the task
	 */
	private String getName(JsonObject jsonTask) {
		String name = "";

		if (jsonTask.has(NAME)) {
			name = jsonTask.get(NAME).getAsString();
		}

		return name;
	}
	
	/**
	 * Gets the name of the category which the task is assigned to
	 * 
	 * @param jsonTask The JSON representation of TaskData
	 * @return The category name which the task is assigned to
	 */
	private String getCategoryName(JsonObject jsonTask) {
		String categoryName = null;
		if (jsonTask.has(CATEGORY)) {
			categoryName = jsonTask.get(CATEGORY).getAsString();
		}

		return categoryName;
	}
	
	/**
	 * Creates a DateTime object from its JSON representation.
	 * 
	 * @param jsonTask The JSON representation of the TaskData
	 * @param type The type of DateTime - deadline, start time or end time
	 * @return The DateTime object of the JSON representation
	 */
	private DateTime resolveDateTime(JsonObject jsonTask, String type) {
		Gson gson = gsonWithDateTimeDeserializer();
		JsonObject dateTimeJson = jsonTask.get(type).getAsJsonObject();
		DateTime dateTime = gson.fromJson(dateTimeJson, DateTime.class);

		return dateTime;
	}
	
	/**
	 * 
	 * @return Gson object with DateTimeDeserializer.
	 */
	private Gson gsonWithDateTimeDeserializer() {
		return new GsonBuilder().registerTypeAdapter(DateTime.class, new DateTimeDeserializer()).create();
	}
}
