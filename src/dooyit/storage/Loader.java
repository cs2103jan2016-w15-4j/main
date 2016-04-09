package dooyit.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public abstract class Loader<T> {
	private static final String ERROR_MESSAGE_FILE_MISSING = "Unable to find %1$s";
	private static final String EMPTY_STRING = "";
	
	private JsonParser parser;
	
	public Loader() {
		this.parser = new JsonParser();
		
	}
	
	protected abstract ArrayList<T> load() throws IOException;

	/**
	 * Creates a new FileReader given the File to read from
	 * 
	 * @param file
	 *            File to read from.
	 * @return FileReader instance of the File to read from.
	 * @throws FileNotFoundException
	 *             If the file is not found.
	 */
	FileReader open(File file) throws FileNotFoundException {
		FileReader fReader = null;
		if (file.exists()) {
			try {
				fReader = new FileReader(file);
			} catch (FileNotFoundException e) {
				throw new FileNotFoundException(String.format(ERROR_MESSAGE_FILE_MISSING, file.getName()));
			}
		}
		return fReader;
	}
	
	/**
	 * Converts the Json String to a JsonObject
	 * 
	 * @param format
	 *            The String representation of the JsonObject
	 * @return JsonObject from String representation if it is not an empty
	 *         string. Otherwise return null
	 */
	JsonObject getAsJson(String format) {
		JsonObject object = null;

		if (!format.equals(EMPTY_STRING)) {
			object = parser.parse(format).getAsJsonObject();
		}

		return object;
	}
}
