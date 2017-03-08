package org.mig.frontend;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientManager {
	//private static final String URL = "http://192.168.1.107:8080";
	private static final String URL = "http://172.30.1.80:8080";
	private ClientApiClient clientApiClient; 
	
	/**
	 * constructor, inits itemApiClient
	 */
	public ClientManager () {
		Retrofit retrofit = new Retrofit.Builder()
			    .baseUrl(this.URL)
			    .addConverterFactory(GsonConverterFactory.create())
			    .build();	
	
		clientApiClient = retrofit.create(ClientApiClient.class);
	}
	
	/**
	 * uses retrofit API client to get items
	 */
	public List<Client> getClients() {
		Call<List<Client>> clientsApiCall = 
				clientApiClient.clients();
		List<Client> clients = null;
		
		try {
			clients = clientsApiCall.execute().body();
			 
		} catch (IOException e) {
			System.err.println("Error calling items API");
			System.out.println("asdasdasdasd");
			e.printStackTrace();
		}catch (Exception e) {
			System.err.println("Error " + e.getMessage());
			System.out.println("asdasdasdasd");
			e.printStackTrace();
		}
		
		return clients;
	}
	
	

	/**
	 * uses retrofit API client to get one item by id
	 * @param id
	 * @return
	 */
	public Client getClient(Long id) {
		Call<Client> clientApiCall = clientApiClient.client(id);
		Client client = null;
		
		try {
			 client = clientApiCall.execute().body();
		} catch (IOException e) {
			System.err.println("Error calling item API");
			e.printStackTrace();
		} 
		
		return client;
	}


	/**
	 * uses retrofit API client to get last clients by an id
	 *
	 * @param id
	 * @return
	 */
	public List<Client> getLastClients(Integer id) {
		Call<List<Client>> clientsApiCall = clientApiClient.lastClients(id);
		List<Client> clients = null;

		try {
			clients = clientsApiCall.execute().body();
		} catch (IOException e) {
			System.err.println("Error calling last clients API");
			e.printStackTrace();
		}

		return clients;
	}

	/**
	 * uses retrofit API client to create a new item
	 * @param client
	 * @return
	 */
	public int createClient(Client client) {
		Call<Integer> clientApiCall = clientApiClient.create(client);
		Integer id = null;

		try {
			id = clientApiCall.execute().body();
		} catch (IOException e) {
			System.err.println("Error calling client API");
			e.printStackTrace();
		}

		return id.intValue();
	}

	/**
	 * uses retrofit API client to update an item
	 * @param client
	 * @return
	 */
	public boolean updateClient(Integer id, Client client) {
		Call<Void> itemApiCall = clientApiClient.update(id ,client);
		boolean result = false;
		
		try {
			result = itemApiCall.execute().isSuccessful();
		} catch (IOException e) {
			System.err.println("Error calling item API");
			e.printStackTrace();
		} 
		
		return result;
	}
	
	/**
	 * uses retrofit API client to delete item by id
	 * @param id
	 * @return
	 */
	public boolean deleteClient(Long id) {
		Call<Void> clientApiCall = clientApiClient.delete(id);
		boolean result = false;
		
		try {
			result = clientApiCall.execute().isSuccessful();
		} catch (IOException e) {
			System.err.println("Error calling client API");
			e.printStackTrace();
		} 
		
		return result;
	}
}
