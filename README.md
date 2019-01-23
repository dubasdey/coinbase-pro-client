# coinbase-pro-client

Java 7 Coinbase pro client. Using API spec: https://docs.pro.coinbase.com/



## Features

* Object oriented requests and responses
* Proxy Support (Using Java configuration or HTTPClient configuration)
* Custom exceptions
* Logging API


## Dependencies

### Maven project

Include the dependency in the maven dependencies. All requirements will be downloaded by maven for you.

```xml
		<dependency>
			<groupId>com.github.dubasdey</groupId>
			<artifactId>coinbase-pro-client</artifactId>
			<version>0.0.4</version>
		</dependency>
```
I recommend to use the last version published in maven central.

### Java project

Dependencies are not included with jar. The client requires the following dependencies (or upper compatible version):

* Commons Logging
    * commons-logging-1.2.jar
* Commons Codec
    * commons-codec-1.10.jar
* Jackson
    * jackson-core-2.9.1.jar
    * jackson-annotations-2.9.0-jar
    * jacksing-databind-2.9.1.jar
* Apache HTTP Client
    * httpclient-4.5.6.jar 
    * httpcore-4.4.10.jar

## Client

The project provides a REST Client but the clients structure allows the posibility of other future clients
(maybe Websocket?)

All clients use the common interface __Client__ and the same model objets

## REST API Client

Instance the **Client** class at **org.erc.coinbase.pro.rest.Client** to create a new API Client.

### Instance the client
The client required connection parameters to build the object using a ClientConfig object

```java

    ClientConfig config = new ClientConfig();
	 config.setBaseUrl( baseUrl );
		config.setPublicKey( publicKey );
		config.setSecretKey( secretKey );
		config.setPassphrase( passphrase );
    
```

* _publicKey_ : The API Public Key
* _secretKey_: The API Secret key
* _passphrase_: Your API passphrase
* _baseUrl_ : The URL to point the API (real URL or Sandbox)
    * Sandbox API points to: https://api-public.sandbox.pro.coinbase.com
    * Real API points to: https://api-public.pro.coinbase.com

### Example

Get and list accounts example

```java

import java.util.List;
import org.erc.coinbase.pro.exceptions.CoinbaseException;
import org.erc.coinbase.pro.model.Account;
import org.erc.coinbase.pro.rest.RESTClient;
import org.erc.coinbase.pro.Client;
import org.erc.coinbase.pro.rest.ClientConfig;
import org.erc.coinbase.pro.rest.ProxyConfig;

public class Start {

	public static void main(String[] args) throws CoinbaseException {
		
		ClientConfig config = new ClientConfig();
		config.setBaseUrl("https://api.pro.coinbase.com");
		config.setPublicKey(< your public key >);
		config.setSecretKey(< your secret >);
		config.setPassphrase(< your passphrase >);
		
		ProxyConfig proxyConfig = new ProxyConfig();
		proxyConfig.setHost("172.31.219.30");
		proxyConfig.setPort(8080);
		proxyConfig.setUser("xIS15817");
		proxyConfig.setPass("Password03");
		config.setProxy(proxyConfig);
		
		Client client = new RESTClient(config);
		List<Account> accounts = client.getAccounts(null);
		for (Account account: accounts){
			System.out.println(account.toString());
		}
	}
}
		
```

## Client Usage

## Working with exceptions

The API converts some HTTP error codes in different exceptions that a developer can use.
All the exceptions contains the original JSON data and have _CoinbaseException_ as parent

* SignatureException: The signature is not correct when building
* InvalidRequestException: The request is invalid. HTTP error 400 (See JSON for more information)
* InvalidAPIKeyException: The request API is not valid when calling. HTTP error 401
* ForbiddenException: The request is not allowed. HTTP error 403
* NotFoundException: The endpoint not exists. HTTP error 404
* TooManyRequestException: Too many request to the server. HTTP error 429
* ServerException: Any other exception

