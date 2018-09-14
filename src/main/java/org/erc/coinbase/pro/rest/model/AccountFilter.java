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

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Instantiates a new account filter.
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class AccountFilter extends PaginationFilter{

	/** The id. */
	private String id;
	
	/** The currency. */
	private String currency;
	
	/** The balance. */
	private BigDecimal balance;
	
	/** The holds. */
	private BigDecimal holds;
	
	/** The available. */
	private BigDecimal available;
		
	/**
	 * Checks for only id.
	 */
	public boolean hasOnlyId() {
		return id!=null && currency == null && balance == null && holds == null && available == null;
	}

}
