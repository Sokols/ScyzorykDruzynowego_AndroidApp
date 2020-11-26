package pl.sokols.scyzorykdruzynowego.data.remote;

import java.util.List;

import pl.sokols.scyzorykdruzynowego.data.entity.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonServerApi {

    String API_BASE_URL = "https://jsonserver-5791.restdb.io/rest/";
    String API_BASE_KEY = "a587bef7361c86e19ba1dbd22d7e5d1a598bb";

    @GET("appusers")
    Call<List<User>> getUsers();

    @POST("appusers")
    Call<User> createUser(@Body User user);
}
