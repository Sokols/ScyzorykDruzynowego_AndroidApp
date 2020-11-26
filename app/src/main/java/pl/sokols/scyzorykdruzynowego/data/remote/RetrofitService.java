package pl.sokols.scyzorykdruzynowego.data.remote;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    public static JsonServerApi getServer() {

        OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(chain -> {
            Request originalRequest = chain.request();
            Request.Builder builder = originalRequest.newBuilder().header("x-apikey",  JsonServerApi.API_BASE_KEY);
            Request newRequest = builder.build();
            return chain.proceed(newRequest);
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(JsonServerApi.API_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(JsonServerApi.class);
    }
}
