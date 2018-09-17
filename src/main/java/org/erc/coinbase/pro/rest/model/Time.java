package org.erc.coinbase.pro.rest.model;

import java.util.Date;

import lombok.Data;

/**
 * Instantiates a new time.
 */
@Data
public class Time {

	/** The iso. */
	private Date iso;
	
	/** The epoch. */
	private Long epoch;
}
