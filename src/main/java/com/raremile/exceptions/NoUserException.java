package com.raremile.exceptions;

/**
 * Exception to notify that there was no user.
 * @author ghanashyam
 *
 */
public class NoUserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public NoUserException() {
		super();
	}

	public NoUserException(String message) {
		super(message);
	}

	public NoUserException(String message, Throwable thrw) {
		super(message, thrw);
	}
}
