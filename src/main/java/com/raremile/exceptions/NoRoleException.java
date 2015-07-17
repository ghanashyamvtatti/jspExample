package com.raremile.exceptions;

/**
 * Exception to notify that no role was found
 * @author ghanashyam
 *
 */
public class NoRoleException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public NoRoleException() {
		super();
	}

	public NoRoleException(String message) {
		super(message);
	}

	public NoRoleException(String message, Throwable thrw) {
		super(message, thrw);
	}
}
