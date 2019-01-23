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

import org.erc.coinbase.pro.rest.exceptions.CoinbaseException;
import org.erc.coinbase.pro.rest.exceptions.RequiredParameterException;
import org.erc.coinbase.pro.rest.exceptions.SignatureException;
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
	 * @param ClientConfig  the client configuration
     * @throws SignatureException Signature Exception
	 */
    public Client(ClientConfig config) throws SignatureException {
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
			if(filter.getBefore()!=null) {
				params.put("before", filter.getBefore());
			}
			if(filter.getAfter()!=null) {
				params.put("after", filter.getAfter());
			}
			
	 		if(filter.getLimit()>0) {
				params.put("limit", filter.getLimit());
			}
    	}
    	return params;
    }
    
    /**
	 * <p>
	 * 	<b>Get a list of trading accounts</b><br>
	 * 	Your trading accounts are separate from your Coinbase accounts. 
	 * 	See the Deposits section for documentation on how to deposit funds to begin trading.
	 * </p>
	 * <p>
	 * <b>HTTP Request</b><br>
	 * 		<code>GET /accounts</code>
	 * </p>
	 * <p>
	 * <b>API Key Permissions</b><br>
	 * 		This endpoint requires either the “view” or “trade” permission.
	 * </p>
	 * <p>
	 * 	<b>Account Fields</b><br>
	 * 	<br>
	 * 	Field 		Description<br>
	 * 	id 			Account ID<br>
	 * 	currency 	the currency of the account<br>
	 * 	balance 	total funds in the account<br>
	 * 	holds 		funds on hold (not available for use)<br>
	 * 	available 	funds available to withdraw or trade<br>
	 * </p>
	 * <p>
	 * <b>Funds on Hold</b><br>
	 * 	When you place an order, the funds for the order are placed on hold. 
	 * 	They cannot be used for other orders or withdrawn. Funds will remain on hold until the order is 
	 * 	filled or canceled.
	 *</p>
	 * @param filter the filter
	 * @return the list
	 * @throws CoinbaseException the coinbase exception
	 */
    public List<Account> getAccounts(AccountFilter filter) throws CoinbaseException {
    	Map<String,Object> params = initParameters(filter);
    	return http.get("/accounts", new TypeReference<List<Account>>() {},params, true);
    }
    
    /**
	 * Get an Account
	 *
	 * @param id the id
	 * @return account
	 * @throws CoinbaseException the coinbase exception
	 */
    public Account getAccount(String id) throws CoinbaseException {
    	if( id == null || id.isEmpty()) {
    		throw new RequiredParameterException("accountId");
    	}        	
    	return http.get(String.format("/account/%s",id), new TypeReference<Account>() {},null, true);	
    }
    
    
    /**
     * <p>
	 *	<b>List account activity.</b><br> 
	 *
	 *	Account activity either increases or decreases your account balance. 
	 *	Items are paginated and sorted latest first. See the Pagination section for retrieving 
	 *	additional entries after the first page.
	 *	</p>
	 *	<p>
	 *		<b>HTTP request</b><br>
	 *		<code>GET /accounts/[account-id]/ledger</code>
	 *	</p>
	 *	<p>
	 *	API Key Permissions
	 *		This endpoint requires either the “view” or “trade” permission.
	 *	</p>
	 *	<p>
	 *	<b>Entry Types</b><br>
	 *
	 *		Entry type indicates the reason for the account change.<br>
	 *	
	 *		Type 		Description<br>
	 *		transfer 	Funds moved to/from Coinbase to Coinbase Pro<br>
	 *		match 		Funds moved as a result of a trade<br>
	 *		fee 		Fee as a result of a trade<br>
	 *		rebate 		Fee rebate as per our fee schedule<br>
	 *	</p>
	 *	<p>
	 *	<b>Details</b><br>
	 *	If an entry is the result of a trade (match, fee), the details field will contain additional 
	 *	information about the trade.
	 *
	 *	This request is paginated
	 * </p>
	 * @param filter the filter
	 * @return the account history
	 * @throws CoinbaseException the coinbase exception
	 */
    public List<AccountHistory> getAccountHistory(AccountHistoryFilter filter) throws CoinbaseException {
    	if( filter == null || filter.getAccountId() == null || filter.getAccountId().isEmpty()) {
    		throw new RequiredParameterException("accountId");
    	}
    	Map<String,Object> params = initParameters(filter);
    	return http.get(String.format("/account/%s/ledger",filter.getAccountId()), new TypeReference<List<AccountHistory>>() {},params, true);
    }
    
    /**
     * <p>
	 * <b>Gets the account holds.</b>
	 * 
	 *	Holds are placed on an account for any active orders or pending withdraw requests. As an order 
	 *	is filled, the hold amount is updated. If an order is canceled, any remaining hold is removed. 
	 *	For a withdraw, once it is completed, the hold is removed.
	 *</p>
	 *<p>
	 *	<b>HTTP Request</b><br>
	 *		<code>GET /accounts/[account_id]/holds</code>
	 *</p>
	 *<p>
	 *	<b>API Key Permissions</b><br>
	 *		This endpoint requires either the “view” or “trade” permission.
	 *</p>
	 *<p>
	 *	This request is paginated
	 *</p>
	 *<p>
	 *	<b>Type</b><br>
	 *		The type of the hold will indicate why the hold exists. The hold type is order for holds 
	 *		related to open orders and transfer for holds related to a withdraw.
	 *	<br>
	 *	<b>Ref</b><br>
	 *		The ref field contains the id of the order or transfer which created the hold.
	 *</p>
	 * @param filter the filter
	 * @return the account holds
	 * @throws CoinbaseException the coinbase exception
	 */
    public List<Hold> getAccountHolds(AccountHoldFilter filter) throws CoinbaseException {
    	if( filter == null || filter.getAccountId() == null || filter.getAccountId().isEmpty()) {
    		throw new RequiredParameterException("accountId");
    	}      	
    	Map<String,Object> params = initParameters(filter);
    	return http.get(String.format("/account/%s/holds",filter.getAccountId()), new TypeReference<List<Hold>>() {},params, true);
    }
 
    /**
	 * You can place two types of orders: limit and market. 
	 * 
	 * Orders can only be placed if your account has sufficient funds. 
	 * Once an order is placed, your account funds will be put on hold for the duration of the order. 
	 * How much and which funds are put on hold depends on the order type and parameters specified. 
	 * See the Holds details below.
	 * 
	 * 	HTTP Request
	 * 		POST /orders
	 * 
	 * 	API Key Permissions
	 * 		This endpoint requires the “trade” permission.
	 * 
	 * 	Parameters
	 * 
	 * 		These parameters are common to all order types. Depending on the order type, additional parameters 
	 * 		will be required (see below).
	 * 
	 * 		Param 			Description
	 * 		client_oid 		[optional] Order ID selected by you to identify your order
	 * 		type 			[optional] limit or market (default is limit)
	 * 		side 			buy or sell
	 * 		product_id 		A valid product id
	 * 		stp 			[optional] Self-trade prevention flag
	 * 		stop 			[optional] Either loss or entry. Requires stop_price to be defined.
	 * 		stop_price 		[optional] Only if stop is defined. Sets trigger price for stop order.
	 * 
	 * 		limit order parameters
	 * 
	 * 		Param 			Description
	 * 		price 			Price per bitcoin
	 * 		size 			Amount of BTC to buy or sell
	 * 		time_in_force 	[optional] GTC, GTT, IOC, or FOK (default is GTC)
	 * 		cancel_after 	[optional] * min, hour, day
	 * 		post_only 		[optional] ** Post only flag
	 * 				* Requires time_in_force to be GTT
	 * 				** Invalid when time_in_force is IOC or FOK
	 * 
	 * 		market order parameters
	 * 
	 * 		Param 			Description
	 * 		size 			[optional] * Desired amount in BTC
	 * 		funds 			[optional] * Desired amount of quote currency to use
	 * 				* One of size or funds is required.
	 * 
	 * 	Product ID
	 * 	The product_id must match a valid product. The products list is available via the /products endpoint.
	 * 
	 * 	Client Order ID
	 * 	The optional client_oid field must be a UUID generated by your trading application. 
	 * 	This field value will be broadcast in the public feed for received messages. 
	 * 	You can use this field to identify your orders in the public feed.
	 * 
	 * 	The client_oid is different than the server-assigned order id. 
	 * 	If you are consuming the public feed and see a received message with your client_oid, you 
	 * 	should record the server-assigned order_id as it will be used for future order status updates. 
	 * 	The client_oid will NOT be used after the received message is sent.
	 * 
	 * 	The server-assigned order id is also returned as the id field to this HTTP POST request.
	 * 
	 * 	Type
	 * 
	 * 	When placing an order, you can specify the order type. The order type you specify will influence 
	 * 	which other order parameters are required as well as how your order will be executed by the matching 
	 * 	engine. If type is not specified, the order will default to a limit order.
	 * 
	 * 	limit orders are both the default and basic order type. A limit order requires specifying a price 
	 * 	and size. The size is the number of bitcoin to buy or sell, and the price is the price per bitcoin. 
	 * 	The limit order will be filled at the price specified or better. A sell order can be filled at the 
	 * 	specified price per bitcoin or a higher price per bitcoin and a buy order can be filled at the specified 
	 * 	price or a lower price depending on market conditions. If market conditions cannot fill the limit order 
	 * 	immediately, then the limit order will become part of the open order book until filled by another incoming 
	 * 	order or canceled by the user.
	 * 
	 * 	market orders differ from limit orders in that they provide no pricing guarantees. 
	 * 	They however do provide a way to buy or sell specific amounts of bitcoin or fiat without having to 
	 * 	specify the price. Market orders execute immediately and no part of the market order will go on the 
	 * 	open order book. Market orders are always considered takers and incur taker fees. When placing a 
	 * 	market order you can specify funds and/or size. Funds will limit how much of your quote currency 
	 * 	account balance is used and size will limit the bitcoin amount transacted.
	 * 
	 * 	Stop orders
	 * 	Stop orders become active and wait to trigger based on the movement of the last trade price. 
	 * 	There are two types of stop orders, stop loss and stop entry:
	 * 
	 * 		stop: 'loss': Triggers when the last trade price changes to a value at or below the stop_price.
	 * 		stop: 'entry': Triggers when the last trade price changes to a value at or above the stop_price.
	 * 
	 * 	The last trade price is the last price at which an order was filled. This price can be found in the 
	 * 	latest match message. Note that not all match messages may be received due to dropped messages.
	 * 
	 * 	Note that when triggered, stop orders execute as either market or limit orders, depending on the type. 
	 * They are therefore subject to holds.
	 * 
	 * Price
	 * 	The price must be specified in quote_increment product units. The quote increment is the smallest unit 
	 * 	of price. For the BTC-USD product, the quote increment is 0.01 or 1 penny. Prices less than 1 penny 
	 * 	will not be accepted, and no fractional penny prices will be accepted. Not required for market orders.
	 * 
	 * 	Size
	 * 	The size must be greater than the base_min_size for the product and no larger than the base_max_size. 
	 * 	The size can be in any increment of the base currency (BTC for the BTC-USD product), which includes 
	 * 	satoshi units. size indicates the amount of BTC (or base currency) to buy or sell.
	 * 
	 * 	Funds
	 * 	The funds field is optionally used for market orders. When specified it indicates how much of the 
	 * 	product quote currency to buy or sell. For example, a market buy for BTC-USD with funds specified 
	 * 	as 150.00 will spend 150 USD to buy BTC (including any fees). If the funds field is not specified 
	 * 	for a market buy order, size must be specified and Coinbase Pro will use available funds in your 
	 * 	account to buy bitcoin.
	 * 
	 * 	A market sell order can also specify the funds. If funds is specified, it will limit the sell to the 
	 * 	amount of funds specified. You can use funds with sell orders to limit the amount of quote currency 
	 * 	funds received.
	 * 
	 * 	Time in force
	 * 	Time in force policies provide guarantees about the lifetime of an order. There are four policies: 
	 * 	good till canceled GTC, good till time GTT, immediate or cancel IOC, and fill or kill FOK.
	 * 
	 * 		GTC Good till canceled orders remain open on the book until canceled. This is the 
	 * 		default behavior if no policy is specified.
	 * 
	 * 		GTT Good till time orders remain open on the book until canceled or the allotted cancel_after 
	 * 		is depleted on the matching engine. GTT orders are guaranteed to cancel before any other order 
	 * 		is processed after the cancel_after timestamp which is returned by the API. A day is 
	 * 		considered 24 hours.
	 * 
	 * 		IOC Immediate or cancel orders instantly cancel the remaining size of the limit order instead 
	 * 		of opening it on the book.
	 * 
	 * 		FOK Fill or kill orders are rejected if the entire size cannot be matched.
	 * 
	 * 		Note, match also refers to self trades.
	 * 
	 * 	Post only
	 * 	The post-only flag indicates that the order should only make liquidity. If any part of the 
	 * 	order results in taking liquidity, the order will be rejected and no part of it will execute.
	 * 
	 * 	Holds
	 * 	For limit buy orders, we will hold price x size x (1 + fee-percent) USD. For sell orders, we will 
	 * 	hold the number of Bitcoin you wish to sell. Actual fees are assessed at time of trade. If you 
	 * 	cancel a partially filled or unfilled order, any remaining funds will be released from hold.
	 * 
	 * 	For market buy orders where funds is specified, the funds amount will be put on hold. If only 
	 * 	size is specified, all of your account balance (in the quote account) will be put on hold for the 
	 * 	duration of the market order (usually a trivially short time). For a sell order, the size in BTC will 
	 * 	be put on hold. If size is not specified (and only funds is specified), your entire BTC balance will 
	 * 	be on hold for the duration of the market order.
	 * 
	 * 	Self-trade prevention
	 * 	Self-trading is not allowed on Coinbase Pro. Two orders from the same user will not be allowed 
	 * 	to match with one another. To change the self-trade behavior, specify the stp flag.
	 * 
	 * 	Flag 	Name
	 * 	dc 		Decrease and Cancel (default)
	 * 	co 		Cancel oldest
	 * 	cn 		Cancel newest
	 * 	cb 		Cancel both
	 * 
	 * 	See the self-trade prevention documentation for details about these fields.
	 * 
	 * 	Order Lifecycle
	 * 	The HTTP Request will respond when an order is either rejected (insufficient funds, invalid parameters, 
	 * 	etc) or received (accepted by the matching engine). A 200 response indicates that the order was 
	 * 	received and is active. Active orders may execute immediately (depending on price and market 
	 * 	conditions) either partially or fully. A partial execution will put the remaining size of the order 
	 * 	in the open state. An order that is filled completely, will go into the done state.
	 * 
	 * 	Users listening to streaming market data are encouraged to use the client_oid field to identify 
	 * 	their received messages in the feed. The REST response with a server order_id may come after the 
	 * 	received message in the public data feed.
	 * 
	 * 	Response
	 * 
	 * 	A successful order will be assigned an order id. A successful order is defined as one that has 
	 * 	been accepted by the matching engine.
	 * 
	 * 	Open orders do not expire and will remain open until they are either filled or canceled.
	 *
	 * @param request the request
	 * @return the order
	 * @throws CoinbaseException the coinbase exception
	 */
    public Order placeOrder(OrderRequest request) throws CoinbaseException {
    	return http.post("/orders", new TypeReference<Order>() {}, request);
    }
    
    /**
	 * Cancel order.
	 * Cancel a previously placed order.
	 * 
	 * 	If the order had no matches during its lifetime its record may be purged. 
	 * 	This means the order details will not be available with GET /orders/[order-id].
	 * 
	 * 	HTTP Request
	 * 		DELETE /orders/[order-id]
	 * 
	 * 	API Key Permissions
	 * 
	 * 		This endpoint requires the “trade” permission.
	 * 
	 * 	The order id is the server-assigned order id and not the optional client_oid.
	 * 
	 * 	Cancel Reject
	 * 		If the order could not be canceled (already filled or previously canceled, etc), 
	 * 		then an error response will indicate the reason in the message field.
	 * 
	 * @param id the id
	 * @throws CoinbaseException the coinbase exception
	 */
    public void cancelOrder(String id) throws CoinbaseException {
    	if( id == null || id.isEmpty()) {
    		throw new RequiredParameterException("orderId");
    	}     	
    	http.delete(String.format("/orders/%s", id),null,null);
    } 
    
    /**
	 * Cancel all order. With best effort, cancel all open orders. The response is a
	 * list of ids of the canceled orders.
	 * 
	 * HTTP Request DELETE /orders
	 * 
	 * API Key Permissions This endpoint requires the “trade” permission.
	 * 
	 * Query Parameters Param Default Description product_id [optional] Only cancel
	 * orders open for a specific product
	 *
	 * @param productId
	 *            the product id
	 * @return the list
	 * @throws CoinbaseException
	 *             the coinbase exception
	 */
    public List<String> cancelAllOrders(String productId) throws CoinbaseException {
    	Map<String,Object> filter = new HashMap<>();
		putIfAbsent(filter, "product_id", productId);
    	return http.delete("/orders",new TypeReference<List<String>>() {},filter);
    	
    } 
    
    /**
	 * Gets the orders.
	 * 
	 * List your current open orders. Only open or un-settled orders are returned.
	 * As soon as an order is no longer open and settled, it will no longer appear
	 * in the default request.
	 * 
	 * HTTP REQUEST GET /orders
	 * 
	 * API Key Permissions This endpoint requires either the “view” or “trade”
	 * permission.
	 * 
	 * Query Parameters Param Default Description status [open, pending, active]
	 * Limit list of orders to these statuses. Passing all returns orders of all
	 * statuses. product_id [optional] Only list orders for a specific product
	 * 
	 * To specify multiple statuses, use the status query argument multiple times
	 * 
	 * This request is paginated.
	 * 
	 * Order status and settlement Orders which are no longer resting on the order
	 * book, will be marked with the done status. There is a small window between an
	 * order being done and settled. An order is settled when all of the fills have
	 * settled and the remaining holds (if any) have been removed.
	 * 
	 * Polling For high-volume trading it is strongly recommended that you maintain
	 * your own list of open orders and use one of the streaming market data feeds
	 * to keep it updated. You should poll the open orders endpoint once when you
	 * start trading to obtain the current state of any open orders.
	 * 
	 * executed_value is the cumulative match size * price and is only present for
	 * orders placed after 2016-05-20.
	 * 
	 * Open orders may change state between the request and the response depending
	 * on market conditions.
	 *
	 * @param orderFilter
	 *            the order filter
	 * @return the orders
	 * @throws CoinbaseException
	 *             the coinbase exception
	 */
    public List<Order> getOrders(OrderFilter orderFilter) throws CoinbaseException {
    	Map<String,Object> filter = new HashMap<>();
    	if(orderFilter!=null) {
    		putIfAbsent(filter, "product_id", orderFilter.getProductId());
    		putIfAbsent(filter, "status", orderFilter.getStatus());
    	}
    	return http.get("/orders", new TypeReference<List<Order>>() {},filter, true);
    }
    
    /**
	 * Gets the orders.
	 *	Get a single order by order id.
	 *	HTTP Request
	 *		GET /orders/[order-id]
	 *
	 *	API Key Permissions
	 *		This endpoint requires either the “view” or “trade” permission.
	 *
	 *	If the order is canceled the response may have status code 404 if the order had no matches.
	 *	Open orders may change state between the request and the response depending on market conditions
	 *
	 * @param id the filter
	 * @return the order
	 * @throws CoinbaseException the coinbase exception
	 */    
    public Order getOrder(String id) throws CoinbaseException {
    	if(id == null || id.isEmpty()) {
    		throw new RequiredParameterException("order-id");
    	}    	
    	return http.get(String.format("/orders/%s",id), new TypeReference<Order>() {},null, true);
    }
    
    /**
	 * Gets the fills. Get a list of recent fills.
	 * 
	 * HTTP request GET /fills
	 * 
	 * API Key Permissions This endpoint requires either the “view” or “trade”
	 * permission.
	 * 
	 * Query Parameters You can request fills for specific orders or products using
	 * query parameters. Param Default Description order_id all Limit list of fills
	 * to this order_id product_id all Limit list of fills to this product_id
	 * 
	 * DEPRECATION NOTICE - Requests without either order_id or product_id will be
	 * rejected
	 * 
	 * Settlement and Fees
	 * 
	 * Fees are recorded in two stages. Immediately after the matching engine
	 * completes a match, the fill is inserted into our datastore. Once the fill is
	 * recorded, a settlement process will settle the fill and credit both trading
	 * counterparties.
	 * 
	 * The fee field indicates the fees charged for this individual fill.
	 * 
	 * Liquidity
	 * 
	 * The liquidity field indicates if the fill was the result of a liquidity
	 * provider or liquidity taker. M indicates Maker and T indicates Taker.
	 * 
	 * Pagination Fills are returned sorted by descending trade_id from the largest
	 * trade_id to the smallest trade_id. The CB-BEFORE header will have this first
	 * trade id so that future requests using the cb-before parameter will fetch
	 * fills with a greater trade id (newer fills).
	 * 
	 * This request is paginated.
	 *
	 * @param fillFilter
	 *            the fill filter
	 * @return the fills
	 * @throws CoinbaseException
	 *             the coinbase exception
	 */
    public List<Fill> getFills(FillFilter fillFilter) throws CoinbaseException {
    	if(fillFilter == null || (fillFilter.getOrderId() == null && fillFilter.getProductId() == null)) {
    		throw new RequiredParameterException("order_id or product_id");
    	}    	
    	Map<String,Object> filter = initParameters(fillFilter);
    	putIfAbsent(filter, "order_id", fillFilter.getOrderId());
    	putIfAbsent(filter, "product_id", fillFilter.getProductId());
    	return http.get("/fills", new TypeReference<List<Fill>>() {},filter, true);
    }
    
    /**
	 * Deposit.
	 * 
	 * Payment method (DepositPaymentMethodRequest)
	 * 
	 * Deposit funds from a payment method. See the Payment Methods section for retrieving your payment methods.
	 * 
	 * 	HTTP request
	 * 		POST /deposits/payment-method
	 * 
	 * 	API Key Permissions
	 * 		This endpoint requires the “transfer” permission.
	 * 
	 * 	Parameters
	 * 		Param 				Description
	 * 		amount 				The amount to deposit
	 * 		currency 			The type of currency
	 * 		payment_method_id 	ID of the payment method
	 *
	 * Coinbase (DepositCoinbaseRequest)
	 * 
	 * Deposit funds from a coinbase account. You can move funds between your Coinbase accounts and your 
	 * Coinbase Pro trading accounts within your daily limits. Moving funds between Coinbase and Coinbase 
	 * Pro is instant and free. See the Coinbase Accounts section for retrieving your Coinbase accounts.
	 * 
	 * 	HTTP request
	 * 		POST /deposits/coinbase-account
	 * 
	 * 	API Key Permissions
	 * 		This endpoint requires the “transfer” permission.
	 * 
	 * 	Parameters
	 * 	Param 					Description
	 * 	amount 					The amount to deposit
	 * 	currency 				The type of currency
	 * 	coinbase_account_id 	ID of the coinbase account
	 *
	 * @param deposit the deposit
	 * @return the deposit
	 * @throws CoinbaseException the coinbase exception
	 */
    public Deposit deposit(DepositRequest deposit) throws CoinbaseException {
    	if( deposit instanceof  DepositCoinbaseRequest) {
    		return http.post("/deposits/coinbase-account", new TypeReference<Deposit>() {}, deposit);
    	} else if (deposit instanceof DepositPaymentMethodRequest) {
    		return http.post("/deposits/payment-method", new TypeReference<Deposit>() {}, deposit);
    	} else {
    		throw new RequiredParameterException("DepositCoinbaseRequest or DepositPaymentMethodRequest");
    	}
    }
    
    /**
	 * Withdrawal.
	 * 
	 * Payment method
	 * 
	 * Withdraw funds to a payment method. See the Payment Methods section for
	 * retrieving your payment methods.
	 * 
	 * HTTP request POST /withdrawals/payment-method
	 * 
	 * API Key Permissions This endpoint requires the “transfer” permission.
	 * 
	 * Parameters Param Description amount The amount to withdraw currency The type
	 * of currency payment_method_id ID of the payment method
	 * 
	 * Coinbase Withdraw funds to a coinbase account. You can move funds between
	 * your Coinbase accounts and your Coinbase Pro trading accounts within your
	 * daily limits. Moving funds between Coinbase and Coinbase Pro is instant and
	 * free. See the Coinbase Accounts section for retrieving your Coinbase
	 * accounts.
	 * 
	 * HTTP request POST /withdrawals/coinbase-account
	 * 
	 * API Key Permissions This endpoint requires the “transfer” permission.
	 * 
	 * Parameters Param Description amount The amount to withdraw currency The type
	 * of currency coinbase_account_id ID of the coinbase account
	 * 
	 * Crypto Withdraws funds to a crypto address.
	 * 
	 * HTTP request POST /withdrawals/crypto
	 * 
	 * API Key Permissions This endpoint requires the “transfer” permission.
	 * 
	 * Parameters Param Description amount The amount to withdraw currency The type
	 * of currency crypto_address A crypto address of the recipient
	 *
	 * @param withdrawal
	 *            the withdrawal
	 * @return the withdrawal
	 * @throws CoinbaseException
	 *             the coinbase exception
	 */
    public Withdrawal withdrawal(WithdrawalRequest withdrawal) throws CoinbaseException {
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
    
    /**
	 * Gets the payment methods.
	 * Get a list of your payment methods
	 * 
	 * 	HTTP Request
	 * 		GET /payment-methods
	 * 
	 * 	API Key Permissions
	 * 		This endpoint requires the “transfer” permission.
	 *
	 * @return the payment methods
	 * @throws CoinbaseException the coinbase exception
	 */
    public List<Payment> getPaymentMethods() throws CoinbaseException {
    	return http.get("/payment-methods", new TypeReference<List<Payment>>() {},null, true);
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
	 * 	Reports provide batches of historic information about your account in various human and machine 
	 * 	readable forms.
	 * 
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
    	return http.post("/reports", new TypeReference<Report>() {}, request);
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
	 * Get a list of available currency pairs for trading. GET /products Details The
	 * base_min_size and base_max_size fields define the min and max order size. The
	 * quote_increment field specifies the min order price as well as the price
	 * increment.
	 * 
	 * The order price must be a multiple of this increment (i.e. if the increment
	 * is 0.01, order prices of 0.001 or 0.021 would be rejected).
	 * 
	 * Product ID will not change once assigned to a product but the min/max/quote
	 * sizes can be updated in the future.
	 *
	 * @param productFilter
	 *            the product filter
	 * @return the products
	 * @throws CoinbaseException
	 *             the coinbase exception
	 */
    public List<Product> getProducts(ProductsFilter productFilter) throws CoinbaseException {
    	Map<String,Object> filter = new HashMap<>();
    	if(productFilter!=null) {
			putIfAbsent(filter, "base_min_size", productFilter.getMinSize());
			putIfAbsent(filter, "base_max_size", productFilter.getMaxSize());
			putIfAbsent(filter, "quote_increment", productFilter.getIncrement());
    	}
    	return http.get("/products", new TypeReference<List<Product>>() {},filter, false);
    }
    
    /**
	 * Get a list of open orders for a product. The amount of detail shown can be customized with the level parameter.
	 * 	HTTP Request
	 * 		GET /products/[product-id]/book
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
	 * 		GET /products/[product-id]/ticker
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
	 * HTTP request GET /products/[product-id]/trades
	 * 
	 * This request is paginated.
	 * 
	 * Side The trade side indicates the maker order side. The maker order is the
	 * order that was open on the order book. buy side indicates a down-tick because
	 * the maker was a buy order and their order was removed. Conversely, sell side
	 * indicates an up-tick.
	 *
	 * @param productFilter
	 *            the product filter
	 * @return the product trades
	 * @throws CoinbaseException
	 *             the coinbase exception
	 */
    public List<Trade> getProductTrades(ProductTradesFilter productFilter) throws CoinbaseException{
    	if(productFilter == null || productFilter.getProductId() == null || productFilter.getProductId().isEmpty()) {
    		throw new RequiredParameterException("productId");
    	}    	
    	Map<String,Object> params = initParameters(productFilter);
    	return http.get(String.format("/products/%s/trades",productFilter.getProductId()), new TypeReference<List<Trade>>() {},params, false);
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
	 * 		GET /products/[product-id]/candles
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
	 * 		GET /products/[product-id]/stats
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
	 * Put in map if not exists.
	 *
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @param map
	 *            Map
	 * @param k
	 *            Key
	 * @param v
	 *            Value
	 */
    private <K,V> void putIfAbsent(Map<K,V> map,K k,V v) {
    	if(map !=null && k!=null) {
    		map.putIfAbsent(k, v);
    	}
    }
}
