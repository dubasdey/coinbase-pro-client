package org.erc.coinbase.pro.rest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.erc.coinbase.pro.Client;
import org.erc.coinbase.pro.exceptions.*;
import org.erc.coinbase.pro.model.*;
import org.junit.Before;
import org.junit.Test;

public class ClientTest {

	private Client client;
	
	@Before
	public void init() throws IOException, SignatureException {
		
		Properties properties = new Properties();
		InputStream stream = RESTClient.class.getResourceAsStream("/test.properties");
		
		if(stream!=null) {
			properties.load(stream);
			
			ClientConfig config = new ClientConfig();
			config.setPublicKey(properties.getProperty("publickey"));
			config.setSecretKey(properties.getProperty("secret"));
			config.setPassphrase(properties.getProperty("pass"));
			config.setBaseUrl(properties.getProperty("url"));

			String host = properties.getProperty("proxy.host");
			if(host!=null) {
				int port = Integer.parseInt(properties.getProperty("proxy.port","8080"));
				String user = properties.getProperty("proxy.user");
				String ppass = properties.getProperty("proxy.pass");
				ProxyConfig proxyConfig = new ProxyConfig(host, port,user,ppass);
				config.setProxy(proxyConfig);
			}
			client = new RESTClient(config);
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
	
	
	@Test
	public void getAccounts1() throws CoinbaseException {
		List<Account> account = client.getAccounts(null);
		assertNotNull(account);
		assertFalse(account.isEmpty());
		String accId = account.get(0).getId();
		Account account2 = client.getAccount(accId);
		assertNotNull(account2);
	}
	
	@Test
	public void getAccounts2() throws CoinbaseException {
		AccountFilter filter = new AccountFilter();
		filter.setLimit(2);
		List<Account> account = client.getAccounts(filter);
		assertNotNull(account);
		assertFalse(account.isEmpty());
		
		String accId = account.get(0).getId();
		
		Account account2 = client.getAccount(accId);
		assertNotNull(account2);
	}
	
	@Test
	public void getCoinbaseAccounts() throws CoinbaseException {
		List<CoinbaseAccount> items = client.getCoinbaseAccounts();
		assertNotNull(items);
		
	}
	
	@Test
	public void getFills() throws CoinbaseException {
		FillFilter filter = new FillFilter();
		filter.setProductId("BTC-USD");
		List<Fill> items = client.getFills(filter);
		assertNotNull(items);
	}	
	
	@Test
	public void getProducts() throws CoinbaseException {
		List<Product> items = client.getProducts(null);
		assertNotNull(items);
	}
	
	@Test
	public void getTrailingVolume() throws CoinbaseException {
		List<TrailingVolume> items = client.getTrailingVolume();
		assertNotNull(items);
	}	
	
	@Test
	public void getPaymentMethods() throws CoinbaseException {
		List<Payment> items = client.getPaymentMethods();
		assertNotNull(items);
	}	
	
}
