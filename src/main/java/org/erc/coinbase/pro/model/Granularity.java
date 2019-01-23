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
package org.erc.coinbase.pro.model;

/**
 * The Enum Granularity.
 */
public enum Granularity {

	/** The one minute. */
	ONE_MINUTE(60),
	
	/** The five minutes. */
	FIVE_MINUTES(300),
	
	/** The fifteen minute. */
	FIFTEEN_MINUTE(900),
	
	/** The one hour. */
	ONE_HOUR(3600),
	
	/** The six hours. */
	SIX_HOURS(21600),
	
	/** The one day. */
	ONE_DAY(86400);
	
	/** The seconds. */
	private int seconds;
	
	/**
	 * Instantiates a new granularity.
	 *
	 * @param seconds the seconds
	 */
	private Granularity(int seconds) {
		this.seconds = seconds;
	}
	
	/**
	 * Gets the seconds.
	 *
	 * @return the seconds
	 */
	public int getSeconds() {
		return seconds;
	}
}
