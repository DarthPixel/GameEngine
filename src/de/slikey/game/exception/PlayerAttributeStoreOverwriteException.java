package de.slikey.game.exception;

public class PlayerAttributeStoreOverwriteException extends RuntimeException {

	private static final long serialVersionUID = 4607076373048574592L;

	public PlayerAttributeStoreOverwriteException() {
		super();
	}

	public PlayerAttributeStoreOverwriteException(String message) {
		super(message);
	}

	public PlayerAttributeStoreOverwriteException(String message, Throwable cause) {
		super(message, cause);
	}

	public PlayerAttributeStoreOverwriteException(Throwable cause) {
		super(cause);
	}

}
