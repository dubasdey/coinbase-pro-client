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
package org.erc.coinbase.pro.rest.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * Instantiates a new order request.
 */
@Data
@JsonInclude(Include.NON_NULL)
public abstract class OrderRequest {

	/** The size. */
	private BigDecimal size;
	
	/** The price. */
	private BigDecimal price;
	
	/** The side. */
	private OrderSide side;
	
	/** The product id. */
	@JsonProperty("product_id")
	private String productId;
	
	/** The client oid. */
	@JsonProperty("client_oid")
	private String clientId;
	
	/** The stp. */
	private String stp;
	
	/** The stop. */
	private String stop;
		
	/** The stop price. */
	@JsonProperty("stop_price")
	private BigDecimal stopPrice;
	
}
