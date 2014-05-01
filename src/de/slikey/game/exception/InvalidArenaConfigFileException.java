package de.slikey.game.exception;

public class InvalidArenaConfigFileException extends Exception {

	private static final long serialVersionUID = -3541597952025186826L;

	public InvalidArenaConfigFileException() {
		super();
	}

	public InvalidArenaConfigFileException(String message) {
		super(message);
	}

	public InvalidArenaConfigFileException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidArenaConfigFileException(Throwable cause) {
		super(cause);
	}

}
