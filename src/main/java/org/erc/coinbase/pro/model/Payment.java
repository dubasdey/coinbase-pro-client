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

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

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
	@JsonProperty("primary_buy")
	private boolean primaryBuy;
	
	/** The primary sell. */
	@JsonProperty("primary_sell")
	private boolean primarySell;
	
	/** The allow buy. */
	@JsonProperty("allow_buy")
	private boolean allowBuy;
	
	/** The allow sell. */
	@JsonProperty("allow_sell")
	private boolean allowSell;
	
	/** The allow deposit. */
	@JsonProperty("allow_deposit")
	private boolean allowDeposit;
	
	/** The allow withdraw. */
	@JsonProperty("allow_withdraw")
	private boolean allowWithdraw;
	
	/** The limits. */
	private PaymentLimits limits;
	
	/** The currency. */
	private String currency;

	/** The allow withdraw. */
	@JsonProperty("created_at")
	private Date createdAt;
	
	/** The allow withdraw. */
	@JsonProperty("updated_at")
	private Date updatedAt;	
	
	/** The allow withdraw. */
	private String resource;	
	
	/** The allow withdraw. */
	@JsonProperty("resource_path")
	private String resourcePath;

	/** The fiat account. */
	@JsonProperty("fiat_account")
	private PaymentFiatAccount fiatAccount;
	
	/** The verified. */
	private boolean verified;
	
	/** The verification method. */
	@JsonProperty("verification_method")
	private String verificationMethod;
	
	/** The cdv status. */
	@JsonProperty("cdv_status")
	private String cdvStatus;
}
