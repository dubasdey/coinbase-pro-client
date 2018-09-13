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
package org.erc.coinbase.pro;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.erc.coinbase.pro.exceptions.CoinbaseException;
import org.erc.coinbase.pro.exceptions.ForbiddenException;
import org.erc.coinbase.pro.exceptions.InvalidAPIKeyException;
import org.erc.coinbase.pro.exceptions.InvalidRequestException;
import org.erc.coinbase.pro.exceptions.NotFoundException;
import org.erc.coinbase.pro.exceptions.ServerException;
import org.erc.coinbase.pro.exceptions.TooManyRequestException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class HTTPRest.
 */
final class HTTPRest {

	/** The log. */
	private Log log = LogFactory.getLog(HTTPRest.class);
	
	/** The mapper. */
	private ObjectMapper mapper;
	
	/** The base url. */
	private String baseUrl;
	
	/** The secret key. */
	private String secretKey;
	
	/** The public key. */
	private String publicKey;
	
	/** The passphrase. */
	private String passphrase;
	
	/**
	 * Instantiates a new HTTP rest.
	 *
	 * @param baseUrl    the base url
	 * @param secretKey  the secret key
	 * @param publicKey  the public key
	 * @param passphrase the passphrase
	 */
	public HTTPRest(String baseUrl,String secretKey,String publicKey,String passphrase) {
		mapper = new ObjectMapper();
		this.baseUrl = baseUrl;
		this.secretKey = secretKey;
		this.publicKey = publicKey;
		this.passphrase = passphrase;
	}
	
	/**
	 * Gets the.
	 *
	 * @param              <T> the generic type
	 * @param resourcePath the resource path
	 * @param type         the type
	 * @param secured      the secured
	 * @return the t
	 * @throws CoinbaseException the coinbase exception
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String resourcePath, TypeReference<T> type,boolean secured) throws CoinbaseException {
		T resultObject = null;
		try {
			URL url = new URL(baseUrl + resourcePath);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(30000);
			connection.setReadTimeout(30000);
			connection.setAllowUserInteraction(false);
			connection.setUseCaches(false);
			connection.setRequestProperty("accept", "application/json");
			connection.setRequestProperty("content-type", "application/json");
			connection.setRequestProperty("Accept-Language", "en");
			//connection.setRequestProperty("CB-VERSION", "2017-09-22");

			if(secured) {
				String timestamp = Instant.now().getEpochSecond() + "";
				Signature signature = new Signature(secretKey,resourcePath,"GET","",timestamp);
				connection.setRequestProperty("CB-ACCESS-KEY", publicKey);
				connection.setRequestProperty("CB-ACCESS-SIGN", signature.toString());
				connection.setRequestProperty("CB-ACCESS-TIMESTAMP", timestamp);
				connection.setRequestProperty("CB-ACCESS-PASSPHRASE", passphrase);
			}
			
			log.debug(String.format("> Request %s",resourcePath));
			
			int responseCode = connection.getResponseCode();   
			String encoding = connection.getContentEncoding() == null ? "UTF-8" : connection.getContentEncoding();
			String jsonResponse = readStream(connection.getInputStream(),encoding);
			log.debug(String.format("< Response %s:%s",responseCode,jsonResponse));

			if (responseCode < 300) { 
				Class<?> clazz = Class.forName(type.getClass().getName());
				resultObject = (T) mapper.readValue(jsonResponse,clazz);
			}else {
				raiseResponseException(responseCode,jsonResponse);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServerException(e.getMessage(),e);
		}
		return resultObject;
	}    
	
	/**
	 * Raise response exception.
	 *
	 * @param responseCode the response code
	 * @param jsonResponse the json response
	 * @throws CoinbaseException the coinbase exception
	 */
	private void raiseResponseException(int responseCode,String jsonResponse) throws CoinbaseException {
		switch(responseCode) {
			case 400:
				throw new InvalidRequestException(jsonResponse);
			case 401:
				throw new InvalidAPIKeyException(jsonResponse);
			case 403:
				throw new ForbiddenException(jsonResponse);
			case 404:
				throw new NotFoundException(jsonResponse);
			case 429:
				throw new TooManyRequestException(jsonResponse);
			default:
				throw new ServerException(jsonResponse);
		}
	}

	/**
	 * Read stream.
	 *
	 * @param inputStr the input str
	 * @param encoding the encoding
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private String readStream(InputStream inputStr,String encoding) throws IOException {
		BufferedInputStream bis = new BufferedInputStream(inputStr);
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		int buffer = 4006;
		byte[] items = new byte[buffer];
		int readed = buffer;
		while(readed>= buffer) {
			readed = bis.read(items);
			buf.write(items, 0, readed);
		}
		return buf.toString(encoding);
	}
}
