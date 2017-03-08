package org.mig.frontend;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ClientApiClient {
	
	@Headers("Accept: application/json")
	@GET("springPractica/clientApi/all")
    Call<List<Client>> clients();
	
	@Headers("Accept: application/json")
	@GET("springPractica/clientApi/{id}")
    Call<Client> client(
			@Path("id") Long id);

	@Headers("Accept: application/json")
	@GET("springPractica/clientApi/last/{id}")
	Call<List<Client>>  lastClients(
			@Path("id") Integer id);
	
	@Headers("Accept: application/json")
	@POST("springPractica/clientApi/new")
    Call<Integer> create(@Body Client client);
	
	@PUT("springPractica/clientApi/{id}")
    Call<Void> update( Integer id,Client item);
	
	@DELETE("springPractica/clientApi/delete/{id}")
    Call<Void> delete(
			@Path("id") Long id);
}
