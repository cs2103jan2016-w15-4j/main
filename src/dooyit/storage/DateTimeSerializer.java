package dooyit.storage;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import dooyit.common.datatype.DateTime;

public class DateTimeSerializer implements JsonSerializer<DateTime> {

	@Override
	public JsonElement serialize(DateTime dt, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject jsonObject = new JsonObject();
		String date = "" + dt.getDD() + " " + dt.getMM() + " " + dt.getYY() + " " + dt.getDayStr();
		jsonObject.addProperty("date", date);
		jsonObject.addProperty("time", dt.getTimeInt());
		return jsonObject;
	}

}
