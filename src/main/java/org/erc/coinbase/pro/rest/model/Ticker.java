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
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * The Class Ticker.
 */
@Data
public class Ticker {

	/** The trade id. */
	@JsonProperty("trade_id")
	private String tradeId;
	
	/** The price. */
	private BigDecimal price;
	
	/** The size. */
	private BigDecimal size;
	
	/** The bid. */
	private BigDecimal bid;
	
	/** The ask. */
	private BigDecimal ask;
	
	/** The volume. */
	private BigDecimal volume;
	
	/** The time. */
	private Date time;
}
