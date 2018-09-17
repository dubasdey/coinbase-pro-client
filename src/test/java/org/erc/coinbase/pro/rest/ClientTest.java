package org.erc.coinbase.pro.rest;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
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
		properties.load(Client.class.getResourceAsStream("/test.properties"));
		
		String publickey = properties.getProperty("publickey");
		String secret = properties.getProperty("secret");
		String pass = properties.getProperty("pass");
		String url = properties.getProperty("url");
		
		ProxyConfig config = new ProxyConfig("172.31.219.30", 8080,"SCISB\\\\xIS15817","Password15");
		client = new Client(publickey,secret,pass,url);
		client.setProxy(config);

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
