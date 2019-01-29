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

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Instantiates a new order.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Order {

	/** The id. */
	private String id;
	
	/** The price. */
	private BigDecimal price;
	
	/** The size. */
	private BigDecimal size;
	
	/** The product id. */
	@JsonProperty("product_id")
	private String productId;
	
	/** The side. */
	private String side;
	
	/** The stp. */
	private String stp;
	
	/** The type. */
	private String type;
	
	/** The time in force. */
	@JsonProperty("time_in_force")
	private String timeInForce;
	
	/** The post only. */
	@JsonProperty("post_only")
	private Boolean postOnly;
	
	/** The created at. */
	@JsonProperty("created_at")
	private Date created;
	
	/** The fill fees. */
	@JsonProperty("fill_fees")
	private BigDecimal fillFees;
	
	/** The filled size. */
	@JsonProperty("filled_size")
	private BigDecimal filledSize;
	
	/** The executed value. */
	@JsonProperty("executed_value")
	private BigDecimal executedValue;
	
	/** The status. */
	private String status;
	
	/** The settled. */
	private Boolean settled;
}
