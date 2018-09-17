package org.erc.coinbase.pro.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * The Class CoinbaseAccountWireDeposit.
 */
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
