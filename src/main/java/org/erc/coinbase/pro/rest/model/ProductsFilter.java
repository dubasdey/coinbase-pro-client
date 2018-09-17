package org.erc.coinbase.pro.rest.model;

import java.math.BigDecimal;

import lombok.Data;

/**
 * Instantiates a new products filter.
 */
@Data
public class ProductsFilter {

	/** The min size. */
	private BigDecimal minSize;
	
	/** The max size. */
	private BigDecimal maxSize;
	
	/** The increment. */
	private BigDecimal increment;
}
