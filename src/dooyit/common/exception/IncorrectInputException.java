package dooyit.common.exception;

public class IncorrectInputException extends RuntimeException {

	public IncorrectInputException() {
		super();
	}

	public IncorrectInputException(String s) {
		super(s);
	}

	public IncorrectInputException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public IncorrectInputException(Throwable throwable) {
		super(throwable);
	}
}
