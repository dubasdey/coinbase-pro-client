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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * The Class Report.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Report {

	/** The id. */
	private String id;
	
	/** The type. */
	private String type;
	
	/** The status. */
	private String status;
	
	/** The created at. */
	@JsonProperty("created_at")
	private Date created;
	
	/** The completed at. */
	@JsonProperty("completed_at")
	private Date completed;

	/** The expires at. */
	@JsonProperty("expires_at")
	private Date expires;
	
	/** The file url. */
	@JsonProperty("file_url")
	private String fileUrl;
	
	/** The params. */
	private ReportRequest params;
}
