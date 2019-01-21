# coinbase-pro-client

Java 7 Coinbase pro client. Using API spec: https://docs.pro.coinbase.com/



## Features

* Object oriented requests and responses
* Proxy Support (Using Java configuration or HTTPClient configuration)
* Custom exceptions
* Logging APIU

## Dependencies

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

## REST API Client

Instance the **Client** class at **org.erc.coinbase.pro.rest.Client** to create a new API Client.

### Instance the client
The client required connection parameters to build the object

    Client(String publicKey, String secretKey, String passphrase, String baseUrl)

* _publicKey_ : The API Public Key
* _secretKey_: The API Secret key
* _passphrase_: Your API passphrase
* _baseUrl_ : The URL to point the API (real URL or Sandbox)
    * Sandbox API points to: https://api-public.sandbox.pro.coinbase.com
    * Real API points to: https://api-public.pro.coinbase.com


### Example

Get and list accounts example

```java
		String api ="https://api.pro.coinbase.com";
		String publicKey= <your api key >;
		String secretKey = < your secret >;
		String passphrase= < your passphrase >;
		Client client = new Client(publicKey,secretKey,passphrase,api);
		List<Account> accounts = client.getAccounts(null);
		for (Account account: accounts){
			System.out.println(account.toString());
		}
```
