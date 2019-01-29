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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * The Class CoinbaseAccountWireDeposit.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CoinbaseAccountWireDeposit {

	/** The account number. */
	@JsonProperty("account_number")
	private String accountNumber;
	
	/** The routing number. */
	@JsonProperty("routing_number")
	private String routingNumber;
	
	/** The bank name. */
	@JsonProperty("bank_name")
	private String bankName;
	
	/** The bank address. */
	@JsonProperty("bank_address")
	private String bankAddress;
	
	/** The account name. */
	@JsonProperty("account_name")
	private String accountName;
	
	/** The account address. */
	@JsonProperty("account_address")
	private String accountAddress;
	
	/** The reference. */
	private String reference;
	
	/** The bank country. */
	@JsonProperty("bank_country")
	private CoinbaseAccountWireDepositCountry bankCountry;
}
