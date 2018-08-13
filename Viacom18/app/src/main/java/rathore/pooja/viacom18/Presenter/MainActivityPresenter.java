package rathore.pooja.viacom18.Presenter;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import okhttp3.ResponseBody;
import rathore.pooja.viacom18.Model.NetworkHelpers.RetrofitInstance;
import rathore.pooja.viacom18.Model.Pojo.ListData;
import rathore.pooja.viacom18.View.Interface.ResponseInterface;
import rathore.pooja.viacom18.View.Interface.RetrofitInterface;
import rathore.pooja.viacom18.utils.AppUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivityPresenter {

    public void getWords(String url, final ResponseInterface responseInterface) {
        String baseUrl ;
        if(url.contains("https://")||url.contains("http://")){
            baseUrl = url;
        }
        else {
            baseUrl = AppUtils.BASE_URL + url;
        }
        RetrofitInterface apiService = RetrofitInstance.getClient(baseUrl).create(RetrofitInterface.class);
        Call<ResponseBody> call = apiService.getWords();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                call.request().url();
                if (response.isSuccessful()) {

                    String html = null;
                    Document document = null;
                    try {
                        html = response.body().string();
                        document = Jsoup.parse(html);
                        responseInterface.onSuccess(document);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("failure", t.getMessage());
                responseInterface.onFailure("Something went wrong!");
            }
        });
    }

    public void showToast(Activity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    public ArrayList<ListData> list(Document document, boolean filter) {
        ArrayList<ListData> listDataArrayList = new ArrayList<>();
        String documentText = document.text();
        String resltText = documentText.replaceAll("[-+.^:,]", "");
        String[] keys = resltText.split("\\s+");
        String[] uniqueKeys;
        int count = 0;
        String[] filterList = AppUtils.FILTER_ARRAY;

        uniqueKeys = AppUtils.getUniqueKeys(keys);
        for (String key : uniqueKeys) {
            ListData listData = new ListData();
            if (null == key) {
                break;
            }

            for (String s : keys) {
                if (key.equals(s)) {
                    count++;
                }
            }
            listDataArrayList.add(new ListData(key, count));


            count = 0;
        }

        Collections.sort(listDataArrayList, Collections.<ListData>reverseOrder(new ListData.FrequencyComparator()));
        if (filter == false) {
            return listDataArrayList;
        }
        if (filter == true) {
            for (int j = 0; j < listDataArrayList.size(); j++) {
                for (int i = 0; i < filterList.length; i++) {
                    if (listDataArrayList.get(j).getWordName().equals(filterList[i])) {
                        listDataArrayList.remove(j);
                    }
//                    return listDataArrayList;
                }

            }
        }
        return listDataArrayList;
    }

}
