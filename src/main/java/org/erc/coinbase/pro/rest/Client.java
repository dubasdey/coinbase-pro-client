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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.erc.coinbase.pro.rest.exceptions.CoinbaseException;
import org.erc.coinbase.pro.rest.model.Account;
import org.erc.coinbase.pro.rest.model.AccountFilter;
import org.erc.coinbase.pro.rest.model.AccountHistory;
import org.erc.coinbase.pro.rest.model.Book;
import org.erc.coinbase.pro.rest.model.Candle;
import org.erc.coinbase.pro.rest.model.CandleRequest;
import org.erc.coinbase.pro.rest.model.CoinbaseAccount;
import org.erc.coinbase.pro.rest.model.Currency;
import org.erc.coinbase.pro.rest.model.Deposit;
import org.erc.coinbase.pro.rest.model.DepositRequest;
import org.erc.coinbase.pro.rest.model.Fill;
import org.erc.coinbase.pro.rest.model.FillFilter;
import org.erc.coinbase.pro.rest.model.Hold;
import org.erc.coinbase.pro.rest.model.Order;
import org.erc.coinbase.pro.rest.model.OrderFilter;
import org.erc.coinbase.pro.rest.model.OrderRequest;
import org.erc.coinbase.pro.rest.model.Payment;
import org.erc.coinbase.pro.rest.model.Product;
import org.erc.coinbase.pro.rest.model.Report;
import org.erc.coinbase.pro.rest.model.ReportRequest;
import org.erc.coinbase.pro.rest.model.Stats;
import org.erc.coinbase.pro.rest.model.Ticker;
import org.erc.coinbase.pro.rest.model.Trade;
import org.erc.coinbase.pro.rest.model.TrailingVolume;
import org.erc.coinbase.pro.rest.model.Withdrawal;
import org.erc.coinbase.pro.rest.model.WithdrawalRequest;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * The Class Client.
 */
public class Client {

	/** The http. */
	private HTTPRest http;
	
    /**
	 * Instantiates a new client.
	 *
	 * @param publicKey  the public key
	 * @param secretKey  the secret key
	 * @param passphrase the passphrase
	 * @param baseUrl    the base url
	 */
    public Client(String publicKey,String secretKey, String passphrase, String baseUrl) {
		http = new HTTPRest(baseUrl,publicKey,secretKey,passphrase);
    }
	
    /**
	 * Sets the proxy.
	 *
	 * @param proxy the new proxy
	 */
    public void setProxy(ProxyConfig proxy) {
    	http.setProxyConfig(proxy);
    }
    
    /**
	 * Gets the proxy.
	 *
	 * @return the proxy
	 */
    public ProxyConfig getProxy() {
    	return http.getProxyConfig();
    }
    
    /**
	 * Accounts.
	 *
	 * @param filter the filter
	 * @return the list
	 * @throws CoinbaseException the coinbase exception
	 */
    public List<Account> getAccounts(AccountFilter filter) throws CoinbaseException {
    	List<Account> result = new ArrayList<>();
    	if (filter == null) {
    		result = http.get("/accounts", new TypeReference<List<Account>>() {}, true);
    	} else if (filter.getId() !=null ) {
    		Account acc = http.get(String.format("/account/%s",filter.getId()), new TypeReference<Account>() {}, true);
    		result.add(acc);
    	} else {
    		//TODO Pagination
    		//result = http.get("/accounts", new TypeReference<List<Account>>() {}, true);
    	}
    	return result;
    }
    
    /**
	 * Accounts.
	 *
	 * @param id the id
	 * @return account
	 * @throws CoinbaseException the coinbase exception
	 */
    public Account getAccount(String id) throws CoinbaseException {
    	return http.get(String.format("/account/%s",id), new TypeReference<Account>() {}, true);	
    }
    
    
    /**
	 * Gets the account history.
	 *
	 * @param id the id
	 * @return the account history
	 * @throws CoinbaseException the coinbase exception
	 */
    public List<AccountHistory> getAccountHistory(String id) throws CoinbaseException {
    	return http.get(String.format("/account/%s/ledger",id), new TypeReference<List<AccountHistory>>() {}, true);
    }
    
    /**
	 * Gets the account holds.
	 *
	 * @param id the id
	 * @return the account holds
	 * @throws CoinbaseException the coinbase exception
	 */
    public List<Hold> getAccountHolds(String id) throws CoinbaseException {
    	return http.get(String.format("/account/%s/holds",id), new TypeReference<List<Hold>>() {}, true);
    }
 
    /**
	 * Place order.
	 *
	 * @param request the request
	 * @return the order
	 * @throws CoinbaseException the coinbase exception
	 */
    public Order placeOrder(OrderRequest request) throws CoinbaseException {
    	//TODO 
    	return null;
    }
    
    /**
	 * Cancel order.
	 *
	 * @param id the id
	 * @throws CoinbaseException the coinbase exception
	 */
    public void cancelOrder(String id) throws CoinbaseException {
    	//TODO 
    } 
    
    /**
	 * Cancel all order.
	 *
	 * @throws CoinbaseException the coinbase exception
	 */
    public void cancelAllOrder() throws CoinbaseException {
    	//TODO 
    } 
    
    /**
	 * Gets the orders.
	 *
	 * @param filter the filter
	 * @return the orders
	 * @throws CoinbaseException the coinbase exception
	 */
    public List<Order> getOrders(OrderFilter filter) throws CoinbaseException {
    	//TODO 
    	return null;    
    }
    
    /**
	 * Gets the fills.
	 *
	 * @param filter the filter
	 * @return the fills
	 * @throws CoinbaseException the coinbase exception
	 */
    public List<Fill> getFills(FillFilter filter ) throws CoinbaseException {
    	//TODO 
    	return null; 
    }
    
    /**
	 * Deposit.
	 *
	 * @param deposit the deposit
	 * @return the deposit
	 * @throws CoinbaseException the coinbase exception
	 */
    public Deposit deposit(DepositRequest deposit) throws CoinbaseException {
    	//TODO 
    	return null; 
    }
    
    /**
	 * Withdrawal.
	 *
	 * @param deposit the deposit
	 * @return the withdrawal
	 * @throws CoinbaseException the coinbase exception
	 */
    public Withdrawal withdrawal(WithdrawalRequest deposit) throws CoinbaseException {
    	//TODO 
    	return null; 
    }
    
    /**
	 * Gets the payment methods.
	 *
	 * @return the payment methods
	 * @throws CoinbaseException the coinbase exception
	 */
    public List<Payment> getPaymentMethods() throws CoinbaseException {
    	//TODO 
    	return null; 
    }
    
    /**
	 * Gets the coinbase accounts.
	 *
	 * @return the coinbase accounts
	 * @throws CoinbaseException the coinbase exception
	 */
    public List<CoinbaseAccount> getCoinbaseAccounts() throws CoinbaseException {
    	//TODO 
    	return null; 
    }
    
    /**
	 * Creates the report.
	 *
	 * @param request the request
	 * @return the report
	 * @throws CoinbaseException the coinbase exception
	 */
    public Report createReport(ReportRequest request) throws CoinbaseException {
    	//TODO 
    	return null; 
    }
    
    /**
	 * Gets the report status.
	 *
	 * @param id the id
	 * @return the report status
	 * @throws CoinbaseException the coinbase exception
	 */
    public Report getReportStatus(String id) throws CoinbaseException {
    	//TODO 
    	return null; 
    }
    
    /**
	 * Gets the trailing volume.
	 *
	 * @return the trailing volume
	 * @throws CoinbaseException the coinbase exception
	 */
    public List<TrailingVolume> getTrailingVolume() throws CoinbaseException {
    	//TODO 
    	return null; 
    }
    
    /**
	 * Gets the products.
	 *
	 * @return the products
	 * @throws CoinbaseException the coinbase exception
	 */
    public List<Product> getProducts() throws CoinbaseException {
    	//TODO 
    	return null; 
    }
    
    /**
	 * Gets the product order book.
	 *
	 * @param productId the product id
	 * @param level     the level
	 * @return the product order book
	 * @throws CoinbaseException the coinbase exception
	 */
    public Book getProductOrderBook(String productId,int level) throws CoinbaseException{
    	//TODO 
    	return null; 
    }
    
    /**
	 * Gets the product ticker.
	 *
	 * @param productId the product id
	 * @return the product ticker
	 * @throws CoinbaseException the coinbase exception
	 */
    public Ticker getProductTicker(String productId) throws CoinbaseException{
    	//TODO 
    	return null; 
    } 
    
    /**
	 * Gets the product trades.
	 *
	 * @param productId the product id
	 * @return the product trades
	 * @throws CoinbaseException the coinbase exception
	 */
    public List<Trade> getProductTrades(String productId) throws CoinbaseException{
    	//TODO 
    	return null; 
    } 
    
    /**
	 * Gets the product candles.
	 *
	 * @param request the request
	 * @return the product candles
	 * @throws CoinbaseException the coinbase exception
	 */
    public List<Candle> getProductCandles(CandleRequest request) throws CoinbaseException{
    	//TODO 
    	return null; 
    }    
    
    /**
	 * Gets the product stats.
	 *
	 * @param productId the product id
	 * @return the product stats
	 * @throws CoinbaseException the coinbase exception
	 */
    public Stats getProductStats(String productId) throws CoinbaseException{
    	//TODO 
    	return null; 
    }
    
    /**
	 * Gets the currencies.
	 *
	 * @return the currencies
	 * @throws CoinbaseException the coinbase exception
	 */
    public List<Currency> getCurrencies() throws CoinbaseException{
    	return http.get("/currencies", new TypeReference<List<Currency>>() {}, false);
    } 
    
    /**
	 * Gets the time.
	 *
	 * @return the time
	 * @throws CoinbaseException the coinbase exception
	 */
    public Date getTime() throws CoinbaseException{
    	//TODO 
    	return null; 
    } 
}