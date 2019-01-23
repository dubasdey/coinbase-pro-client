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

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The Class OrderLimitRequest.
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class OrderLimitRequest extends OrderRequest {

	/** The type. */
	private final String type = "limit";
	
	/** The price. */
	private BigDecimal price;
	
	/** The size. */
	private BigDecimal size;
	
	/** The time in force. */
	@JsonProperty("time_in_force")
	private String timeInForce;
	
	/** The cancel after. */
	@JsonProperty("cancel_after")
	private String cancelAfter;
	
	/** The post only. */
	@JsonProperty("post_only")
	private boolean postOnly;
}
