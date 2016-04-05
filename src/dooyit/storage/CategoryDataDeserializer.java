//@@author A0124586Y
package dooyit.storage;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import dooyit.common.datatype.CategoryData;

public class CategoryDataDeserializer implements JsonDeserializer<CategoryData> {
	private static final String CATEGORY_NAME = "name";
	private static final String CATEGORY_COLOR = "color";

	@Override
	public CategoryData deserialize(JsonElement element, Type arg1, JsonDeserializationContext arg2)
			throws JsonParseException {
		JsonObject object = (JsonObject) element;
		String name = object.get(CATEGORY_NAME).getAsString();
		String color = object.get(CATEGORY_COLOR).getAsString();
		CategoryData categoryData = new CategoryData(name, color);

		return categoryData;
	}

}
