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

import lombok.Data;

/**
 * The Class Payment.
 */
@Data
public class Payment {

	/** The id. */
	private String id;
	
	/** The type. */
	private String type;
	
	/** The name. */
	private String name;
	
	/** The primary buy. */
	private boolean primary_buy;
	
	/** The primary sell. */
	private boolean primary_sell;
	
	/** The allow buy. */
	private boolean allow_buy;
	
	/** The allow sell. */
	private boolean allow_sell;
	
	/** The allow deposit. */
	private boolean allow_deposit;
	
	/** The allow withdraw. */
	private boolean allow_withdraw;
	
	/** The limits. */
	private PaymentLimits limits;
}
