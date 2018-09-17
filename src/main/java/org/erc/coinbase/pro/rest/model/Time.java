package org.erc.coinbase.pro.rest.model;

import java.util.Date;

import lombok.Data;

@Data
public class Time {

	private Date iso;
	
	private Long epoch;
}
