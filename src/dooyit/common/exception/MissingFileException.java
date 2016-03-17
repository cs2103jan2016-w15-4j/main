package dooyit.common.exception;

import java.io.FileNotFoundException;

public class MissingFileException extends FileNotFoundException {

	private static final String NOT_EXIST = "does not exist";

	public MissingFileException() {
		super();
	}

	public MissingFileException(String s) {
		super(s + NOT_EXIST);
	}
}
