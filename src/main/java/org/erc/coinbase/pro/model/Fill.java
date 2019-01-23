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

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * The Class Fill.
 */
@Data
public class Fill {

	/** The trade id. */
	@JsonProperty("trade_id")
	private String tradeId;
	
	/** The product id. */
	@JsonProperty("product_id")
	private String productId;
	
	/** The price. */
	private BigDecimal price;
	
	/** The size. */
	private BigDecimal size;
	
	/** The order id. */
	@JsonProperty("order_id")
	private String orderId;
	
	/** The created at. */
	@JsonProperty("created_at")
	private Date created;
	
	/** The liquidity. */
	private String liquidity;
	
	/** The fee. */
	private BigDecimal fee;
	
	/** The settled. */
	private boolean settled;
	
	/** The side. */
	private String side;
	
	/** The user id. */
	@JsonProperty("user_id")
	private String userId;

	/** The profile id. */
	@JsonProperty("profile_id")
	private String profileId;
	
	/** The usd volume. */
	@JsonProperty("usd_volume")
	private String usdVolume;

}
