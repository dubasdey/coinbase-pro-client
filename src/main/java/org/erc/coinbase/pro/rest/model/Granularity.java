package org.erc.coinbase.pro.rest.model;

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
