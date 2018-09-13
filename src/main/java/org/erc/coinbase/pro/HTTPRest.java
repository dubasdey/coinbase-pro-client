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

import com.fasterxml.jackson.databind.ObjectMapper;

final class HTTPRest {

	private Log log = LogFactory.getLog(HTTPRest.class);
	
	private ObjectMapper mapper;
	
	private String baseUrl;
	
	private String secretKey;
	
	private String publicKey;
	
	private String passphrase;
	
	public HTTPRest(String baseUrl,String secretKey,String publicKey,String passphrase) {
		mapper = new ObjectMapper();
		this.baseUrl = baseUrl;
		this.secretKey = secretKey;
		this.publicKey = publicKey;
		this.passphrase = passphrase;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String resourcePath, T type,boolean secured) {
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

			if (responseCode >= 200 & responseCode < 300) { 
				Class<?> clazz = Class.forName(type.getClass().getName());
				resultObject = (T) mapper.readValue(jsonResponse,clazz);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return resultObject;
	}    

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
