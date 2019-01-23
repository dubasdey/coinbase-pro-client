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
package org.erc.coinbase.pro.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Instantiates a new client config.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class ClientConfig {

	/** The public key. */
	private String publicKey;
	
	/** The secret key. */
	private String secretKey;
	
	/** The passphrase. */
	private String passphrase;
	
	/** The base url. */
	private String baseUrl;
	
	/** The proxy. */
	private ProxyConfig proxy;
}
