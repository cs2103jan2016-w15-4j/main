//@@author A0124586Y
package dooyit.storage;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import dooyit.common.datatype.DateTime;

/**
 * The DateTimeSerializer converts the DateTime object to a JSON object
 * 
 * @author Dex
 *
 */
public class DateTimeSerializer implements JsonSerializer<DateTime> {

	private static final String SPACE = " ";
	private static final String DATE = "date";
	private static final String TIME = "time";

	@Override
	public JsonElement serialize(DateTime dt, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject jsonObject = new JsonObject();
		String date = "";
		
		date = dt.getDD() + SPACE + dt.getMM() + SPACE + dt.getYY();
		jsonObject.addProperty(DATE, date);
		
		jsonObject.addProperty(TIME, dt.getTimeInt());
		return jsonObject;
	}

}
