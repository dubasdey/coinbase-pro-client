package org.erc.coinbase.pro.rest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Instantiates a new product trades filter.
 */
@Data

/* (non-Javadoc)
 * @see org.erc.coinbase.pro.rest.model.PaginationFilter#hashCode()
 */
@EqualsAndHashCode(callSuper=true)
public class ProductTradesFilter extends PaginationFilter {

	/** The product id. */
	private String productId; 
}
