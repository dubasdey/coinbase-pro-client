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
	
	/** The display name. */
	@JsonProperty("display_name")
	private String displayName;
	
	/** The status. */
	@JsonProperty("status")
	private String status;
	
	/** The margin enabled. */
	@JsonProperty("margin_enabled")
	private boolean marginEnabled;
	
	/** The status message. */
	@JsonProperty("status_message")
	private boolean statusMessage;

	/** The min market funds. */
	@JsonProperty("min_market_funds")
	private BigDecimal minMarketFunds;
	
	/** The max market funds. */
	@JsonProperty("max_market_funds")
	private BigDecimal maxMarketFunds;	
	
	/** The post only. */
	@JsonProperty("post_only")
	private boolean postOnly;
	
	/** The limit only. */
	@JsonProperty("limit_only")
	private boolean limitOnly;	
	
	/** The cancel only. */
	@JsonProperty("cancel_only")
	private boolean cancelOnly;		

}
