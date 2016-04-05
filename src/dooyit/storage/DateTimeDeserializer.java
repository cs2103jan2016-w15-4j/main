//@@author A0124586Y
package dooyit.storage;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import dooyit.common.datatype.DateTime;

public class DateTimeDeserializer implements JsonDeserializer<DateTime>{
	private static final String DATE = "date";
	private static final String TIME = "time";
	private static final int DAY = 0;
	private static final int MONTH = 1;
	private static final int YEAR = 2;

	@Override
	public DateTime deserialize(JsonElement dateTime, Type arg1, JsonDeserializationContext arg2)
			throws JsonParseException {
		JsonObject jsonDateTime = (JsonObject) dateTime;
		String dateString = jsonDateTime.get(DATE).getAsString();
		
		String[] parts = dateString.split(" ");
		int[] date = new int[] { Integer.valueOf(parts[DAY]),
								Integer.valueOf(parts[MONTH]),
								Integer.valueOf(parts[YEAR]) };
		
		int timeInt = jsonDateTime.get(TIME).getAsInt();
		return new DateTime(date, timeInt);
	}

}
