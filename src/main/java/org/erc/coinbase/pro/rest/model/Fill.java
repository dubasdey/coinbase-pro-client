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

import lombok.Data;

/**
 * The Class Fill.
 */
@Data
public class Fill {

	/** The trade id. */
	private String trade_id;
	
	/** The product id. */
	private String product_id;
	
	/** The price. */
	private BigDecimal price;
	
	/** The size. */
	private BigDecimal size;
	
	/** The order id. */
	private String order_id;
	
	/** The created at. */
	private Date created_at;
	
	/** The liquidity. */
	private String liquidity;
	
	/** The fee. */
	private BigDecimal fee;
	
	/** The settled. */
	private boolean settled;
	
	/** The side. */
	private String side;
}
