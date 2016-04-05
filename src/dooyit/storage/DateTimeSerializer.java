//@@author A0124586Y
package dooyit.storage;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import dooyit.common.datatype.DateTime;

public class DateTimeSerializer implements JsonSerializer<DateTime> {

	private static final String SPACE = " ";

	@Override
	public JsonElement serialize(DateTime dt, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject jsonObject = new JsonObject();
		String date = "";
		date = dt.getDD() + SPACE + dt.getMM() + SPACE + dt.getYY();
		jsonObject.addProperty("date", date);
		jsonObject.addProperty("time", dt.getTimeInt());
		return jsonObject;
	}

}
