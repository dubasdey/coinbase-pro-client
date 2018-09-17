package org.erc.coinbase.pro.rest;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.erc.coinbase.pro.rest.exceptions.CoinbaseException;
import org.erc.coinbase.pro.rest.model.Currency;
import org.junit.Before;
import org.junit.Test;

public class ClientTest {

	private Client client;
	
	@Before
	public void init() throws IOException {
		
		Properties properties = new Properties();
		InputStream stream = Client.class.getResourceAsStream("/test.properties");
		
		if(stream!=null) {
			properties.load(stream);
			
			String publickey = properties.getProperty("publickey");
			String secret = properties.getProperty("secret");
			String pass = properties.getProperty("pass");
			String url = properties.getProperty("url");
			client = new Client(publickey,secret,pass,url);
			
			String host = properties.getProperty("proxy.host");
			if(host!=null) {
				int port = Integer.parseInt(properties.getProperty("proxy.port","8080"));
				String user = properties.getProperty("proxy.user");
				String ppass = properties.getProperty("proxy.pass");
				ProxyConfig config = new ProxyConfig(host, port,user,ppass);
				client.setProxy(config);
			}
		}
	}

	@Test
	public void currenciesTest() throws CoinbaseException {
		List<Currency> currency = client.getCurrencies();
		assertNotNull(currency);
	}
	
	@Test
	public void timeTest() throws CoinbaseException {
		Date value = client.getTime();
		assertNotNull(value);
	}
}
