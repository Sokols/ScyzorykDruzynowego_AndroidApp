package pl.sokols.scyzorykdruzynowego.data.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    public static JsonServerApi getServer() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(JsonServerApi.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(JsonServerApi.class);
    }
}
