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

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * The Class CoinbaseAccountWireDepositSEPA.
 */
@Data
public class CoinbaseAccountSEPADeposit {

	/** The iban. */
	private String iban;
	
	/** The swift. */
	private String swift;
	
	/** The bank name. */
	@JsonProperty("bank_name")
	private String bankName;
	
	/** The bank address. */
	@JsonProperty("bank_address")
	private String bankAddress;
	
	/** The bank country name. */
	@JsonProperty("bank_country_name")
	private String bankCountryName;
	
	/** The account name. */
	@JsonProperty("account_name")
	private String accountName;
	
	/** The account address. */
	@JsonProperty("account_address")
	private String accountAddress;
	
	/** The reference. */
	private String reference;

}
