package rathore.pooja.viacom18.View.Interface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitInterface {
    @GET("/")
    Call<ResponseBody> getWords();
}
