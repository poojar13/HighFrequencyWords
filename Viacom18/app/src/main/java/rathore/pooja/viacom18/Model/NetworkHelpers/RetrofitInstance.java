package rathore.pooja.viacom18.Model.NetworkHelpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import rathore.pooja.viacom18.utils.HostSelectionInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    public static HostSelectionInterceptor hostSelectionInterceptor = new HostSelectionInterceptor();

    public static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .setLenient()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .create();

    public static OkHttpClient okHttpClient = new OkHttpClient()
            .newBuilder()
            .build();

    public static Retrofit getClient(String url) {
        Retrofit retrofit = null;
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit;
    }
}
