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
package org.erc.coinbase.pro.exceptions;

/**
 * The Class SignatureException.
 */
public class SignatureException extends CoinbaseException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6356532144171918886L;

	/**
	 * Instantiates a new signature exception.
	 */
	public SignatureException() {
	}

	/**
	 * Instantiates a new signature exception.
	 *
	 * @param message the message
	 */
	public SignatureException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new signature exception.
	 *
	 * @param cause the cause
	 */
	public SignatureException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new signature exception.
	 *
	 * @param message the message
	 * @param cause   the cause
	 */
	public SignatureException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new signature exception.
	 *
	 * @param message            the message
	 * @param cause              the cause
	 * @param enableSuppression  the enable suppression
	 * @param writableStackTrace the writable stack trace
	 */
	public SignatureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
