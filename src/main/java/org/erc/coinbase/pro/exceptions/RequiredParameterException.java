package org.erc.coinbase.pro.exceptions;

/**
 * The Class RequiredParameterException.
 */
public class RequiredParameterException extends CoinbaseException {


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6031287065394410308L;

	/**
	 * Instantiates a new required parameter exception.
	 */
	public RequiredParameterException() {
	}

	/**
	 * Instantiates a new required parameter exception.
	 *
	 * @param message the message
	 */
	public RequiredParameterException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new required parameter exception.
	 *
	 * @param cause the cause
	 */
	public RequiredParameterException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new required parameter exception.
	 *
	 * @param message the message
	 * @param cause   the cause
	 */
	public RequiredParameterException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new required parameter exception.
	 *
	 * @param message            the message
	 * @param cause              the cause
	 * @param enableSuppression  the enable suppression
	 * @param writableStackTrace the writable stack trace
	 */
	public RequiredParameterException(String message, Throwable cause, boolean enableSuppression,boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
