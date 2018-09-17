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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.erc.coinbase.pro.rest.exceptions.CoinbaseException;
import org.erc.coinbase.pro.rest.exceptions.RequiredParameterException;
import org.erc.coinbase.pro.rest.model.*;

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
	 * Sets the proxy (if required)
	 *
	 * @param proxy the new proxy
	 */
    public void setProxy(ProxyConfig proxy) {
    	http.setProxyConfig(proxy);
    }
    
    /**
	 * Gets the current proxy configuration 
	 *
	 * @return the proxy (or null if not configured)
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
    		result = http.get("/accounts", new TypeReference<List<Account>>() {},null, true);
    	} else if (filter.getId() !=null ) {
    		Account acc = http.get(String.format("/account/%s",filter.getId()), new TypeReference<Account>() {},null, true);
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
    	return http.get(String.format("/account/%s",id), new TypeReference<Account>() {},null, true);	
    }
    
    
    /**
	 * Gets the account history.
	 *
	 * @param id the id
	 * @return the account history
	 * @throws CoinbaseException the coinbase exception
	 */
    public List<AccountHistory> getAccountHistory(String id) throws CoinbaseException {
    	return http.get(String.format("/account/%s/ledger",id), new TypeReference<List<AccountHistory>>() {},null, true);
    }
    
    /**
	 * Gets the account holds.
	 *
	 * @param id the id
	 * @return the account holds
	 * @throws CoinbaseException the coinbase exception
	 */
    public List<Hold> getAccountHolds(String id) throws CoinbaseException {
    	return http.get(String.format("/account/%s/holds",id), new TypeReference<List<Hold>>() {},null, true);
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
	 * Gets the coinbase accounts
	 * 
	 * Get a list of your coinbase accounts.
	 * Visit the Coinbase accounts API for more information.
	 * 
	 * 	HTTP Request
	 * 		GET /coinbase-accounts
	 * 
	 * 	API Key Permissions
	 * 		This endpoint requires either the “view” or “transfer” permission.
	 *
	 * @return the coinbase accounts
	 * @throws CoinbaseException the coinbase exception
	 */
    public List<CoinbaseAccount> getCoinbaseAccounts() throws CoinbaseException {
    	return http.get("/coinbase-accounts", new TypeReference<List<CoinbaseAccount>>() {},null, true);
    }
    
    /**
	 * Create a new report
	 * Reports provide batches of historic information about your account in various human and machine readable forms.
	 * 	HTTP request
	 * 		POST /reports
	 * 
	 * 	API Key Permissions
	 * 		This endpoint requires either the “view” or “trade” permission.
	 * 
	 * 	Parameters
	 * 		Param 			Description
	 * 		type 			fills or account
	 * 		start_date 		Starting date for the report (inclusive)
	 * 		end_date 		Ending date for the report (inclusive)
	 * 		product_id 		ID of the product to generate a fills report for. E.g. BTC-USD. Required if type is fills
	 * 		account_id 		ID of the account to generate an account report for. Required if type is account
	 * 		format 			pdf or csv (defualt is pdf)
	 * 		email 			Email address to send the report to (optional)
	 * 
	 * 	The report will be generated when resources are available. Report status can be queried via 
	 * 	the /reports/:report_id endpoint. The file_url field will be available once the report has successfully 
	 * 	been created and is available for download.
	 * 
	 * 	Expired reports
	 * 	Reports are only available for download for a few days after being created. Once a report expires, 
	 * 	the report is no longer available for download and is deleted.
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
	 * 	HTTP request
	 * 		GET /reports/:report_id
	 * 
	 * 	Once a report request has been accepted for processing, the status is available by 
	 * 	polling the report resource endpoint.
	 * 
	 * 	The final report will be uploaded and available at file_url once the status indicates ready
	 * 
	 * 	API Key Permissions
	 * 		This endpoint requires either the “view” or “trade” permission.
	 * 
	 * 	Status
	 * 		Status 	Description
	 * 		pending 	The report request has been accepted and is awaiting processing
	 * 		creating 	The report is being created
	 * 		ready 		The report is ready for download from file_url
	 * @param id the id
	 * @return the report status
	 * @throws CoinbaseException the coinbase exception
	 */
    public Report getReportStatus(String id) throws CoinbaseException {
    	if(id == null || id.isEmpty()) {
    		throw new RequiredParameterException("report_id");
    	}    	
    	return http.get(String.format("/reports/%s",id), new TypeReference<Report>() {},null, true);
    }
    
    /**
	 * Gets the trailing volume.
	 * HTTP request
	 * 		GET /users/self/trailing-volume
	 * 
	 * API Key Permissions
	 * 	This endpoint requires either the “view” or “trade” permission.
	 * 	This request will return your 30-day trailing volume for all products. 
	 * 	This is a cached value that’s calculated every day at midnight UTC.
	 *
	 * @return the trailing volume
	 * @throws CoinbaseException the coinbase exception
	 */
    public List<TrailingVolume> getTrailingVolume() throws CoinbaseException {
    	return http.get("/users/self/trailing-volume", new TypeReference<List<TrailingVolume>>() {},null, true);
    }
    
    /**
	 * Get a list of available currency pairs for trading.
	 * 		GET /products
	 * Details
	 * 		The base_min_size and base_max_size fields define the min and max order size. 
	 * 		The quote_increment field specifies the min order price as well as the price increment.
	 * 
	 * 		The order price must be a multiple of this increment (i.e. if the increment is 0.01, order 
	 * 		prices of 0.001 or 0.021 would be rejected).
	 * 
	 * 		Product ID will not change once assigned to a product but the min/max/quote sizes can 
	 * 		be updated in the future.
	 *
	 * @return the products
	 * @throws CoinbaseException the coinbase exception
	 */
    public List<Product> getProducts(ProductsFilter productFilter) throws CoinbaseException {
    	Map<String,Object> filter = new HashMap<>();
		putIfAbsent(filter, "base_min_size", productFilter.getMinSize());
		putIfAbsent(filter, "base_max_size", productFilter.getMaxSize());
		putIfAbsent(filter, "quote_increment", productFilter.getIncrement());
    	return http.get("/products", new TypeReference<List<Product>>() {},filter, false);
    }
    
    /**
	 * Get a list of open orders for a product. The amount of detail shown can be customized with the level parameter.
	 * 	HTTP Request
	 * 		GET /products/<product-id>/book
	 * 	Details
	 * 		By default, only the inside (i.e. best) bid and ask are returned. This is equivalent to a book depth of 1 level. 
	 * 		If you would like to see a larger order book, specify the level query parameter.
	 * 
	 * 	If a level is not aggregated, then all of the orders at each price will be returned. Aggregated levels return 
	 * 	only one size for each active price (as if there was only a single order for that size at the level).
	 * 
	 * 	Parameters
	 * 		Name 	Default 	Description
	 * 		level 	1 	Select response detail. Valid levels are documented below
	 * 
	 * 	Levels
	 * 		Level 	Description
	 * 			1 	Only the best bid and ask
	 * 			2 	Top 50 bids and asks (aggregated)
	 * 			3 	Full order book (non aggregated)
	 * 
	 * 	Levels 1 and 2 are aggregated. The size field is the sum of the size of the orders at that price, and num-orders 
	 * 	is the count of orders at that price; size should not be multiplied by num-orders.
	 * 
	 * 	Level 3 is non-aggregated and returns the entire order book.
	 * 
	 * 	This request is NOT paginated. The entire book is returned in one response.
	 * 
	 * 	Level 1 and Level 2 are recommended for polling. For the most up-to-date data, consider using the websocket stream.
	 * 
	 * 	Level 3 is only recommended for users wishing to maintain a full real-time order book using the websocket stream. 
	 * 	Abuse of Level 3 via polling will cause your access to be limited or blocked.
	 *
	 * @param productId the product id
	 * @param level     the level
	 * @return the product order book
	 * @throws CoinbaseException the coinbase exception
	 */
    public Book getProductOrderBook(String productId,int level) throws CoinbaseException {
    	if(level<1 || level > 3) {
    		level = 1;
    	}
    	if(productId == null || productId.isEmpty()) {
    		throw new RequiredParameterException("productId");
    	}
    	return http.get(String.format("/products/%s/book?level=%s",productId,level), new TypeReference<Book>() {},null, false);
    }
    
    /**
	 * Snapshot information about the last trade (tick), best bid/ask and 24h volume.
	 * 
	 * 	HTTP request
	 * 		GET /products/<product-id>/ticker
	 * 
	 * 	Real-time updates
	 * 		Polling is discouraged in favor of connecting via the websocket stream and listening for match messages.
	 *
	 * @param productId the product id
	 * @return the product ticker
	 * @throws CoinbaseException the coinbase exception
	 */
    public Ticker getProductTicker(String productId) throws CoinbaseException {
    	if(productId == null || productId.isEmpty()) {
    		throw new RequiredParameterException("productId");
    	}
    	return http.get(String.format("/products/%s/ticker",productId), new TypeReference<Ticker>() {},null, false);
    }
    
    /**
	 * List the latest trades for a product.
	 * 
	 * HTTP request
	 * 		GET /products/<product-id>/trades
	 * 
	 * This request is paginated.
	 * 
	 * Side
	 * 	The trade side indicates the maker order side. The maker order is the order that was open on the order book. 
	 * 	buy side indicates a down-tick because the maker was a buy order and their order was removed. 
	 * 	Conversely, sell side indicates an up-tick.
	 *
	 * @param productId the product id
	 * @return the product trades
	 * @throws CoinbaseException the coinbase exception
	 */
    public List<Trade> getProductTrades(ProductTradesFilter productFilter) throws CoinbaseException{
    	if(productFilter == null || productFilter.getProductId() == null || productFilter.getProductId().isEmpty()) {
    		throw new RequiredParameterException("productId");
    	}    	
    	//TODO Pagination
    	return http.get(String.format("/products/%s/trades",productFilter.getProductId()), new TypeReference<List<Trade>>() {},null, false);
    } 
    
    /**
	 * Historic rates for a product. Rates are returned in grouped buckets based on requested granularity.
	 * 
	 * Historical rate data may be incomplete. No data is published for intervals where there are no ticks.
	 * 
	 * Historical rates should not be polled frequently. If you need real-time information, use the trade and book 
	 * endpoints along with the websocket feed.
	 * 
	 * 	HTTP request
	 * 		GET /products/<product-id>/candles
	 * 
	 * 	Parameters
	 * 		Param 	Description
	 * 		start 	Start time in ISO 8601
	 * 		end 	End time in ISO 8601
	 * 		granularity 	Desired timeslice in seconds
	 * 
	 * 	Details
	 * 
	 * 	If either one of the start or end fields are not provided then both fields will be ignored. 
	 * 	If a custom time range is not declared then one ending now is selected.
	 * 
	 * 	The granularity field must be one of the following values: {60, 300, 900, 3600, 21600, 86400}. 
	 * 	Otherwise, your request will be rejected. These values correspond to timeslices representing one minute, 
	 * 	five minutes, fifteen minutes, one hour, six hours, and one day, respectively.
	 * 
	 * 	If data points are readily available, your response may contain as many as 300 candles and some of 
	 * 	those candles may precede your declared start value.
	 * 
	 * 	The maximum number of data points for a single request is 300 candles. 
	 * 	If your selection of start/end time and granularity will result in more than 300 data points, your request 
	 * 	will be rejected. If you wish to retrieve fine granularity data over a larger time range, you will need to 
	 * 	make multiple requests with new start/end ranges.
	 * 
	 * 	Response Items
	 * 
	 * 		Each bucket is an array of the following information:
	 * 
	 * 		time 	bucket start time
	 * 		low 	lowest price during the bucket interval
	 * 		high 	highest price during the bucket interval
	 * 		open 	opening price (first trade) in the bucket interval
	 * 		close 	closing price (last trade) in the bucket interval
	 * 		volume 	volume of trading activity during the bucket interval

	 *
	 * @param request the request
	 * @return the product candles
	 * @throws CoinbaseException the coinbase exception
	 */
    public List<String[]> getProductHistoricRate(ProductCandleFilter request) throws CoinbaseException{
    	if(request == null || request.getProductId() == null || request.getProductId().isEmpty()) {
    		throw new RequiredParameterException("productId");
    	} 
    	Map<String,Object> filter = new HashMap<>();
		putIfAbsent(filter, "start", request.getStart());
		putIfAbsent(filter, "end", request.getEnd());
		
		if(request.getGranularity()!=null) {
			putIfAbsent(filter, "granularity", request.getGranularity().getSeconds());
		}
    	return http.get(String.format("/products/%s/trades",request.getProductId()), new TypeReference<List<String[]>>() {},filter, false);
    }    
    
    /**
	 * Get 24 hr stats for the product. volume is in base currency units. open, high, low are in quote currency units.
	 * 	HTTP request
	 * 		GET /products/<product-id>/stats
	 *
	 * @param productId the product id
	 * @return the product stats
	 * @throws CoinbaseException the coinbase exception
	 */
    public Stats getProductStats(String productId) throws CoinbaseException{
    	if( productId == null || productId.isEmpty()) {
    		throw new RequiredParameterException("productId");
    	} 
    	return http.get(String.format("/products/%s/stats",productId), new TypeReference<Stats>() {},null, false);
    }
    
    /**
	 * List known currencies.
	 * 	HTTP request
	 * 		GET /currencies
	 * 
	 * 	Not all currencies may be currently in use for trading.
	 * 	Currency Codes
	 * 		Currency codes will conform to the ISO 4217 standard where possible. 
	 * 		Currencies which have or had no representation in ISO 4217 may use a custom code.
	 * 
	 * 		Code 	Description
	 * 		BTC 	Bitcoin
	 * 		ETH 	Ether
	 * 		ETC		Ether Classic
	 * 		LTC 	Litecoin
	 *
	 * @return the currencies
	 * @throws CoinbaseException the coinbase exception
	 */
    public List<Currency> getCurrencies() throws CoinbaseException{
    	return http.get("/currencies", new TypeReference<List<Currency>>() {},null, false);
    } 
    
    /**
	 * Get the API server time.
	 * HTTP request
	 * 		GET /time
	 * 		Epoch
	 * 			The epoch field represents decimal seconds since Unix Epoch
	 *
	 * @return the time
	 * @throws CoinbaseException the coinbase exception
	 */
    public Date getTime() throws CoinbaseException{
    	Time time = http.get("/time", new TypeReference<Time>() {},null, false);
    	return time!=null?time.getIso():null; 
    } 
    
    /**
     * Put in map if not exists
     * @param map	Map
     * @param k		Key
     * @param v		Value
     */
    private <K,V> void putIfAbsent(Map<K,V> map,K k,V v) {
    	if(map !=null && k!=null) {
    		map.putIfAbsent(k, v);
    	}
    }
}
