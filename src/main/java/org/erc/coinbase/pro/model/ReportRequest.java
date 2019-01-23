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
 * The Class ReportRequest.
 */
@Data
public class ReportRequest {

	/** The type. */
	private String type;
	
	/** The start date. */
	@JsonProperty("start_date")
	private Date startDate;
	
	/** The end date. */
	@JsonProperty("end_date")
	private Date endDate;
	
	/** The product id. */
	@JsonProperty("product_id")
	private String productId;
	
	/** The account id. */
	@JsonProperty("account_id")
	private String accountId;
	
	/** The format. */
	private String format;
	
	/** The email. */
	private String email;
}
