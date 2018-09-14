/**
    This file is part of coinbase-pro-client.

    coinbase-pro-client is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Foobar is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with coinbase-pro-client.  If not, see <https://www.gnu.org/licenses/>.
 */
package org.erc.coinbase.pro.rest.exceptions;

/**
 * The Class NotFoundException.
 */
public class NotFoundException extends CoinbaseException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4904100699028023311L;

	/**
	 * Instantiates a new not found exception.
	 */
	public NotFoundException() {
	}

	/**
	 * Instantiates a new not found exception.
	 *
	 * @param message the message
	 */
	public NotFoundException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new not found exception.
	 *
	 * @param cause the cause
	 */
	public NotFoundException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new not found exception.
	 *
	 * @param message the message
	 * @param cause   the cause
	 */
	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new not found exception.
	 *
	 * @param message            the message
	 * @param cause              the cause
	 * @param enableSuppression  the enable suppression
	 * @param writableStackTrace the writable stack trace
	 */
	public NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
