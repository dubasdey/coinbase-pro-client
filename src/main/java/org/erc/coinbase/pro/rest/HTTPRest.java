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

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.erc.coinbase.pro.rest.exceptions.CoinbaseException;
import org.erc.coinbase.pro.rest.exceptions.ForbiddenException;
import org.erc.coinbase.pro.rest.exceptions.InvalidAPIKeyException;
import org.erc.coinbase.pro.rest.exceptions.InvalidRequestException;
import org.erc.coinbase.pro.rest.exceptions.NotFoundException;
import org.erc.coinbase.pro.rest.exceptions.ServerException;
import org.erc.coinbase.pro.rest.exceptions.SignatureException;
import org.erc.coinbase.pro.rest.exceptions.TooManyRequestException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;

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
	

	/** The public key. */
	private String publicKey;
	
	/** The passphrase. */
	private String passphrase;
	
	/** The httpclient. */
	private  CloseableHttpClient httpclient;
	
	private Signature signature;
	
	/** Sets the proxy config */
	@Getter
	@Setter
	private ProxyConfig proxyConfig;
	
	/**
	 * Instantiates a new HTTP rest.
	 *
	 * @param baseUrl    the base url
	 * @param secretKey  the secret key
	 * @param publicKey  the public key
	 * @param passphrase the passphrase
	 * @throws SignatureException 
	 */
	public HTTPRest(String baseUrl,String publicKey,String secretKey,String passphrase) throws SignatureException {
		mapper = new ObjectMapper();
		this.baseUrl = baseUrl;
		this.publicKey = publicKey;
		this.passphrase = passphrase;
		this.signature= new Signature(secretKey);
	}
	
	/**
	 * Inits the HTTP Rest client.
	 */
	private void init() {
		if(httpclient == null) {
			log.debug("Init HTTP Rest client");
		    HttpClientBuilder builder = HttpClients.custom();
		    // Proxy
		    if(proxyConfig !=null) {
		    	log.debug("Using proxy");
				CredentialsProvider credsProvider = new BasicCredentialsProvider();
				if(proxyConfig.getUser() !=null) {
					credsProvider.setCredentials(new AuthScope(proxyConfig.getHost(), 8888),new UsernamePasswordCredentials(proxyConfig.getUser(), proxyConfig.getPass()));
				}
		    	HttpHost proxy = new HttpHost(proxyConfig.getHost(), proxyConfig.getPort(), "http");
		    	builder.setDefaultCredentialsProvider(credsProvider).setProxy(proxy);
		    }
		    httpclient = builder.build();
		}
	}
	
	/**
	 * Parameter format.
	 *
	 * @param param the param
	 * @return the string
	 */
	private String parameterFormat(Object param) {
		if(param!=null) {
			if(param instanceof Date) {
				 DateTimeFormatter printer = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.S");
				 return printer.toFormat().format(param);
			}
			return param.toString();
		}
		return null;
	}
	
	/**
	 * Inject headers.
	 *
	 * @param request the request
	 */
	private void injectHeaders(HttpUriRequest request ) {
		request.addHeader("accept","application/json");
		request.addHeader("User-Agent", "gdax-java unofficial coinbase pro api library");
	    request.addHeader("content-type", "application/json");
	    request.addHeader("Accept-Language", "en");
	}
	
	/**
	 * Inject security headers
	 *
	 * @param request      the request
	 * @param resourcePath the resource path
	 * @param method       the method
	 * @param body         the body
	 * @throws SignatureException the signature exception
	 */
	private void injectSecurity(HttpUriRequest request ,String resourcePath,String method,String body) throws SignatureException {
		Long timestamp = Instant.now().getEpochSecond();
		request.addHeader("CB-ACCESS-KEY", publicKey);
		request.addHeader("CB-ACCESS-SIGN", signature.sign(resourcePath,method,body,timestamp));
		request.addHeader("CB-ACCESS-TIMESTAMP", timestamp.toString());
		request.addHeader("CB-ACCESS-PASSPHRASE", passphrase);		
	}
	
	/**
	 * Builds the path.
	 *
	 * @param resourcePath the resource path
	 * @param params       the params
	 * @return the string
	 * @throws URISyntaxException the URI syntax exception
	 */
	private String buildPath(String resourcePath,Map<String,Object> params) throws URISyntaxException {
		String finalPath =  resourcePath;
		if(params!=null && !params.isEmpty()) {
			URIBuilder builder = new URIBuilder(resourcePath);
			for(Entry<String,Object> param: params.entrySet()) {
				if(param instanceof Collection) {
					Collection<?> it = (Collection<?>) param;
					if(it !=null && !it.isEmpty()){
						for(Object item : it) {
							builder.setParameter(param.getKey(), parameterFormat(item));	
						}
					}
				}else {
					builder.setParameter(param.getKey(), parameterFormat(param.getValue()));
				}
			}
			finalPath = builder.build().toString();
		}
		return finalPath;
	}
	
	/**
	 * Execute.
	 *
	 * @param  <T> the generic type
	 * @param request the request
	 * @param type    the type
	 * @return the t
	 * @throws UnsupportedOperationException the unsupported operation exception
	 * @throws IOException                   Signals that an I/O exception has
	 *                                       occurred.
	 * @throws CoinbaseException             the coinbase exception
	 */
	@SuppressWarnings("unchecked")
	private <T> T execute(HttpUriRequest request,TypeReference<T> type) throws UnsupportedOperationException, IOException, CoinbaseException {
		CloseableHttpResponse response = httpclient.execute(request);
		int responseCode = response.getStatusLine().getStatusCode();
		String jsonResponse = readStream(response.getEntity().getContent(),"UTF-8" );
		response.close();
		log.debug(String.format("< Response %s:%s",responseCode,jsonResponse));
		if (responseCode < 300) { 
			if(type!=null) {
				return (T) mapper.readValue(jsonResponse,type);
			}
		}else {
			raiseResponseException(responseCode,jsonResponse);
		}				
		return null;
	}
	
	/**
	 * Post the Resource.
	 *
	 * @param               <T> the generic type
	 * @param               <R> the generic type
	 * @param resourcePath  the resource path
	 * @param type          the type
	 * @param requestObject the request object
	 * @return the t
	 * @throws CoinbaseException the coinbase exception
	 */
	public <T,R> T post(String resourcePath,TypeReference<T> type,R requestObject) throws CoinbaseException {
		try {
			String requestJson = mapper.writeValueAsString(requestObject);
			log.debug(String.format("> Request %s JSON: %s",resourcePath,requestJson));
			init();
			HttpPost request = new HttpPost(baseUrl + resourcePath);
			injectHeaders(request);
			injectSecurity(request,resourcePath,"POST",requestJson);
			request.setEntity(new StringEntity(requestJson));
			return execute(request,type);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServerException(e.getMessage(),e);
		}
	}

	/**
	 * Gets the Resource.
	 *
	 * @param              <T> the generic type
	 * @param resourcePath the resource path
	 * @param type         the type
	 * @param params       the params
	 * @param secured      the secured
	 * @return the t
	 * @throws CoinbaseException the coinbase exception
	 */
	public <T> T get(String resourcePath, TypeReference<T> type,Map<String,Object> params,boolean secured) throws CoinbaseException {
		try {
			log.debug(String.format("> Request %s",resourcePath));
			init();
			String path = buildPath(resourcePath,params);
			HttpGet request = new HttpGet(baseUrl + path);
			injectHeaders(request);
			if(secured) {				
				injectSecurity(request,path,"GET","");
			}
			return execute(request,type);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServerException(e.getMessage(),e);
		}
	}    
	
	
	public <T> T delete(String resourcePath, TypeReference<T> type,Map<String,Object> params) throws CoinbaseException {
		try {
			log.debug(String.format("> Request %s",resourcePath));
			init();
			HttpGet request = new HttpGet(baseUrl + buildPath(resourcePath,params));
			injectHeaders(request);
			injectSecurity(request,resourcePath,"GET","");
			return execute(request,type);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServerException(e.getMessage(),e);
		}
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
	 * @param inputStream the input str
	 * @param encoding the encoding
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private String readStream(InputStream inputStream,String encoding) throws IOException {
		String text = null;
	    try (Scanner scanner = new Scanner(inputStream, encoding)) {
	        text = scanner.useDelimiter("\\A").next();
	    }
	   return text;
	}
}
