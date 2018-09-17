package org.erc.coinbase.pro.rest.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * The Class Product.
 */
@Data
public class Product {
	
	/** The id. */
	private String id;
	
	/** The base currency. */
	@JsonProperty("base_currency")
	private String baseCurrency;
	
	/** The quote currency. */
	@JsonProperty("quote_currency")
	private String quoteCurrency;
	
	/** The base min size. */
	@JsonProperty("base_min_size")
	private BigDecimal baseMinSize;
	
	/** The base max size. */
	@JsonProperty("base_max_size")
	private BigDecimal baseMaxSize;
	
	/** The quote increment. */
	@JsonProperty("quote_increment")
	private BigDecimal quoteIncrement;

}
