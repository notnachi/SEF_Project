package app_exceptions;

public class JobNotAvailableException extends Exception {
	public JobNotAvailableException (String message) {
		super(message);
	}
}
