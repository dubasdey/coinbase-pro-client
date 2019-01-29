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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.erc.coinbase.pro.Client;
import org.erc.coinbase.pro.exceptions.CoinbaseException;
import org.erc.coinbase.pro.exceptions.RequiredParameterException;
import org.erc.coinbase.pro.exceptions.SignatureException;
import org.erc.coinbase.pro.model.*;

import com.fasterxml.jackson.core.type.TypeReference;


/**
 * The Class Client.
 */
public class RESTClient implements Client {

	/** The http. */
	private HTTPRest http;
	
    /**
	 * Instantiates a new client.
	 *
	 * @param config  the client configuration
     * @throws SignatureException Signature Exception
	 */
    public RESTClient(ClientConfig config) throws SignatureException {
		http = new HTTPRest(config.getBaseUrl(),config.getPublicKey(),config.getSecretKey(),config.getPassphrase());
		if(config.getProxy()!=null) {
			http.setProxyConfig(config.getProxy());
		}
    }

    /**
	 * Inits the parameters.
	 *
	 * @param filter the pagination filter
	 * @return the map
	 */
    private Map<String,Object> initParameters(PaginationFilter filter){
    	Map<String,Object> params = new HashMap<>();
    	if(filter!=null) {
    		putIfAbsent(params, "before", filter.getBefore());
    		putIfAbsent(params, "after", filter.getAfter());
	 		if(filter.getLimit()>0) {
				params.put("limit", filter.getLimit());
			}
    	}
    	return params;
    }
    
    /* (non-Javadoc)
	 * @see org.erc.coinbase.pro.rest.Client#getAccounts(org.erc.coinbase.pro.model.AccountFilter)
	 */
    @Override
	public List<Account> getAccounts(AccountFilter filter) throws CoinbaseException {
    	Map<String,Object> params = initParameters(filter);
    	return http.get("/accounts", new TypeReference<List<Account>>() {},params, true);
    }
    
    /* (non-Javadoc)
	 * @see org.erc.coinbase.pro.rest.Client#getAccount(java.lang.String)
	 */
    @Override
	public Account getAccount(String id) throws CoinbaseException {  
    	assertRequired("accountId",id);
    	return http.get(String.format("/account/%s",id), new TypeReference<Account>() {},null, true);	
    }
    
    
    /* (non-Javadoc)
	 * @see org.erc.coinbase.pro.rest.Client#getAccountHistory(org.erc.coinbase.pro.model.AccountHistoryFilter)
	 */
    @Override
	public List<AccountHistory> getAccountHistory(AccountHistoryFilter filter) throws CoinbaseException {
    	assertRequired("filter",filter);
    	assertRequired("accountId",filter.getAccountId());
    	Map<String,Object> params = initParameters(filter);
    	return http.get(String.format("/account/%s/ledger",filter.getAccountId()), new TypeReference<List<AccountHistory>>() {},params, true);
    }
    
    /* (non-Javadoc)
	 * @see org.erc.coinbase.pro.rest.Client#getAccountHolds(org.erc.coinbase.pro.model.AccountHoldFilter)
	 */
    @Override
	public List<Hold> getAccountHolds(AccountHoldFilter filter) throws CoinbaseException {  
    	assertRequired("filter",filter);
    	assertRequired("accountId",filter.getAccountId());
    	Map<String,Object> params = initParameters(filter);
    	return http.get(String.format("/account/%s/holds",filter.getAccountId()), new TypeReference<List<Hold>>() {},params, true);
    }
 
    /* (non-Javadoc)
	 * @see org.erc.coinbase.pro.rest.Client#placeOrder(org.erc.coinbase.pro.model.OrderRequest)
	 */
    @Override
	public Order placeOrder(OrderRequest request) throws CoinbaseException {
    	return http.post("/orders", new TypeReference<Order>() {}, request);
    }
    
    /* (non-Javadoc)
	 * @see org.erc.coinbase.pro.rest.Client#cancelOrder(java.lang.String)
	 */
    @Override
	public void cancelOrder(String id) throws CoinbaseException {	
    	assertRequired("orderId",id);
    	http.delete(String.format("/orders/%s", id),null,null);
    } 
    
    /* (non-Javadoc)
	 * @see org.erc.coinbase.pro.rest.Client#cancelAllOrders(java.lang.String)
	 */
    @Override
	public List<String> cancelAllOrders(String productId) throws CoinbaseException {
    	Map<String,Object> filter = new HashMap<>();
    	putIfAbsent(filter, "product_id", productId);
    	return http.delete("/orders",new TypeReference<List<String>>() {},filter);
    	
    } 
    
    /* (non-Javadoc)
	 * @see org.erc.coinbase.pro.rest.Client#getOrders(org.erc.coinbase.pro.model.OrderFilter)
	 */
    @Override
	public List<Order> getOrders(OrderFilter orderFilter) throws CoinbaseException {
    	Map<String,Object> filter = new HashMap<>();
    	if(orderFilter!=null) {
    		putIfAbsent(filter, "product_id", orderFilter.getProductId());
    		putIfAbsent(filter, "status", orderFilter.getStatus());
    	}
    	return http.get("/orders", new TypeReference<List<Order>>() {},filter, true);
    }
    
    /* (non-Javadoc)
	 * @see org.erc.coinbase.pro.rest.Client#getOrder(java.lang.String)
	 */    
    @Override
	public Order getOrder(String id) throws CoinbaseException {	
    	assertRequired("order-id",id);
    	return http.get(String.format("/orders/%s",id), new TypeReference<Order>() {},null, true);
    }
    
    /* (non-Javadoc)
	 * @see org.erc.coinbase.pro.rest.Client#getFills(org.erc.coinbase.pro.model.FillFilter)
	 */
    @Override
	public List<Fill> getFills(FillFilter fillFilter) throws CoinbaseException {
    	assertRequired("filter",fillFilter);
    	if(fillFilter.getOrderId() == null && fillFilter.getProductId() == null) {
    		throw new RequiredParameterException("order_id or product_id");
    	}    	
    	Map<String,Object> filter = initParameters(fillFilter);
    	putIfAbsent(filter, "order_id", fillFilter.getOrderId());
    	putIfAbsent(filter, "product_id", fillFilter.getProductId());
    	return http.get("/fills", new TypeReference<List<Fill>>() {},filter, true);
    }
    
    /* (non-Javadoc)
	 * @see org.erc.coinbase.pro.rest.Client#deposit(org.erc.coinbase.pro.model.DepositRequest)
	 */
    @Override
	public Deposit deposit(DepositRequest deposit) throws CoinbaseException {
    	assertRequired("deposit",deposit);
    	if( deposit instanceof  DepositCoinbaseRequest) {
    		return http.post("/deposits/coinbase-account", new TypeReference<Deposit>() {}, deposit);
    	} else if (deposit instanceof DepositPaymentMethodRequest) {
    		return http.post("/deposits/payment-method", new TypeReference<Deposit>() {}, deposit);
    	} else {
    		throw new RequiredParameterException("DepositCoinbaseRequest or DepositPaymentMethodRequest");
    	}
    }
    
    /* (non-Javadoc)
	 * @see org.erc.coinbase.pro.rest.Client#withdrawal(org.erc.coinbase.pro.model.WithdrawalRequest)
	 */
    @Override
	public Withdrawal withdrawal(WithdrawalRequest withdrawal) throws CoinbaseException {
    	assertRequired("request",withdrawal);
    	if (withdrawal instanceof WithdrawalRequestCoinbase) {
    		return http.post("/withdrawals/coinbase-account", new TypeReference<Withdrawal>() {}, withdrawal);
    	} else if (withdrawal instanceof WithdrawalRequestPaymentMethod) {
    		return http.post("/withdrawals/payment-method", new TypeReference<Withdrawal>() {}, withdrawal);
    	} else if (withdrawal instanceof WithdrawalRequestCrypto) {
    		return http.post("/withdrawals/crypto", new TypeReference<Withdrawal>() {}, withdrawal);
    	} else {
    		throw new RequiredParameterException("WithdrawalRequestCoinbase or WithdrawalRequestPaymentMethod or WithdrawalRequestCrypto");
    	}
    }
    
    /* (non-Javadoc)
	 * @see org.erc.coinbase.pro.rest.Client#getPaymentMethods()
	 */
    @Override
	public List<Payment> getPaymentMethods() throws CoinbaseException {
    	return http.get("/payment-methods", new TypeReference<List<Payment>>() {},null, true);
    }
    
    /* (non-Javadoc)
	 * @see org.erc.coinbase.pro.rest.Client#getCoinbaseAccounts()
	 */
    @Override
	public List<CoinbaseAccount> getCoinbaseAccounts() throws CoinbaseException {
    	return http.get("/coinbase-accounts", new TypeReference<List<CoinbaseAccount>>() {},null, true);
    }
    
    /* (non-Javadoc)
	 * @see org.erc.coinbase.pro.rest.Client#createReport(org.erc.coinbase.pro.model.ReportRequest)
	 */
    @Override
	public Report createReport(ReportRequest request) throws CoinbaseException {
    	return http.post("/reports", new TypeReference<Report>() {}, request);
    }
    
    /* (non-Javadoc)
	 * @see org.erc.coinbase.pro.rest.Client#getReportStatus(java.lang.String)
	 */
    @Override
	public Report getReportStatus(String id) throws CoinbaseException {  	
    	assertRequired("report_id",id);
    	return http.get(String.format("/reports/%s",id), new TypeReference<Report>() {},null, true);
    }
    
    /* (non-Javadoc)
	 * @see org.erc.coinbase.pro.rest.Client#getTrailingVolume()
	 */
    @Override
	public List<TrailingVolume> getTrailingVolume() throws CoinbaseException {
    	return http.get("/users/self/trailing-volume", new TypeReference<List<TrailingVolume>>() {},null, true);
    }
    
    /* (non-Javadoc)
	 * @see org.erc.coinbase.pro.rest.Client#getProducts(org.erc.coinbase.pro.model.ProductsFilter)
	 */
    @Override
	public List<Product> getProducts(ProductsFilter productFilter) throws CoinbaseException {
    	Map<String,Object> filter = new HashMap<>();
    	if(productFilter!=null) {
			putIfAbsent(filter, "base_min_size", productFilter.getMinSize());
			putIfAbsent(filter, "base_max_size", productFilter.getMaxSize());
			putIfAbsent(filter, "quote_increment", productFilter.getIncrement());
    	}
    	return http.get("/products", new TypeReference<List<Product>>() {},filter, false);
    }
    
    /* (non-Javadoc)
	 * @see org.erc.coinbase.pro.rest.Client#getProductOrderBook(java.lang.String, int)
	 */
    @Override
	public Book getProductOrderBook(String productId,int level) throws CoinbaseException {
    	if(level<1 || level > 3) {
    		level = 1;
    	}
    	assertRequired("productId",productId);
    	return http.get(String.format("/products/%s/book?level=%s",productId,level), new TypeReference<Book>() {},null, false);
    }
    
    /* (non-Javadoc)
	 * @see org.erc.coinbase.pro.rest.Client#getProductTicker(java.lang.String)
	 */
    @Override
	public Ticker getProductTicker(String productId) throws CoinbaseException {
    	assertRequired("productId",productId);
    	return http.get(String.format("/products/%s/ticker",productId), new TypeReference<Ticker>() {},null, false);
    }
    
    /* (non-Javadoc)
	 * @see org.erc.coinbase.pro.rest.Client#getProductTrades(org.erc.coinbase.pro.model.ProductTradesFilter)
	 */
    @Override
	public List<Trade> getProductTrades(ProductTradesFilter productFilter) throws CoinbaseException{
    	assertRequired("filter",productFilter);
    	assertRequired("productId",productFilter.getProductId());
    	Map<String,Object> params = initParameters(productFilter);
    	return http.get(String.format("/products/%s/trades",productFilter.getProductId()), new TypeReference<List<Trade>>() {},params, false);
    } 
    
    /* (non-Javadoc)
	 * @see org.erc.coinbase.pro.rest.Client#getProductHistoricRate(org.erc.coinbase.pro.model.ProductCandleFilter)
	 */
    @Override
	public List<String[]> getProductHistoricRate(ProductCandleFilter request) throws CoinbaseException{
    	assertRequired("filter",request);
    	assertRequired("productId",request.getProductId());
    	Map<String,Object> filter = new HashMap<>();
		putIfAbsent(filter, "start", request.getStart());
		putIfAbsent(filter, "end", request.getEnd());
		if(request.getGranularity()!=null) {
			putIfAbsent(filter, "granularity", request.getGranularity().getSeconds());
		}
    	return http.get(String.format("/products/%s/trades",request.getProductId()), new TypeReference<List<String[]>>() {},filter, false);
    }    
    
    /* (non-Javadoc)
	 * @see org.erc.coinbase.pro.rest.Client#getProductStats(java.lang.String)
	 */
    @Override
	public Stats getProductStats(String productId) throws CoinbaseException{
    	assertRequired("productId",productId);
    	return http.get(String.format("/products/%s/stats",productId), new TypeReference<Stats>() {},null, false);
    }
    
    /* (non-Javadoc)
	 * @see org.erc.coinbase.pro.rest.Client#getCurrencies()
	 */
    @Override
	public List<Currency> getCurrencies() throws CoinbaseException{
    	return http.get("/currencies", new TypeReference<List<Currency>>() {},null, false);
    } 
    
    /* (non-Javadoc)
	 * @see org.erc.coinbase.pro.rest.Client#getTime()
	 */
    @Override
	public Date getTime() throws CoinbaseException{
    	Time time = http.get("/time", new TypeReference<Time>() {},null, false);
    	return time!=null?time.getIso():null; 
    } 
    
    /**
	 * Put in map if not exists.
	 *
	 * @param <K>	the key type
	 * @param <V>	the value type
	 * @param map	Map
	 * @param k	Key
	 * @param v	Value
	 */
    private <K,V> void putIfAbsent(Map<K,V> map,K k,V v) {
    	if(map !=null && k!=null && v!=null) {
    		map.putIfAbsent(k, v);
    	}
    }
    
    /**
     * Required assert.
     *
     * @param <T> the generic type
     * @param name key name
     * @param value  key value
     * @throws RequiredParameterException the required parameter exception
     */
    private <T> void assertRequired(String name, T value) throws RequiredParameterException {
    	if(value == null || (value instanceof String && ((String) value).isEmpty())  ) {
    		throw new RequiredParameterException(name);
    	}  
    }

	/* (non-Javadoc)
	 * @see org.erc.coinbase.pro.Client#conversion(org.erc.coinbase.pro.model.ConversionRequest)
	 */
	@Override
	public Conversion conversion(ConversionRequest request) throws CoinbaseException {
		return http.post("/conversions", new TypeReference<Conversion>() {}, request);
	}
}
