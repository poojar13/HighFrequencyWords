package rathore.pooja.viacom18.View.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jsoup.nodes.Document;

import java.util.ArrayList;

import rathore.pooja.viacom18.Model.Adapter.RecyclerViewAdapter;
import rathore.pooja.viacom18.Model.Pojo.ListData;
import rathore.pooja.viacom18.Presenter.MainActivityPresenter;
import rathore.pooja.viacom18.R;
import rathore.pooja.viacom18.View.Interface.ResponseInterface;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ResponseInterface {
    private static EditText inputUrl, inputListCount;
    private static RecyclerView recyclerView;
    private static int listCount = 5;
    MainActivityPresenter mainActivityPresenter;
    RecyclerViewAdapter recyclerViewAdapter;
    int filter;
    ArrayList<ListData> listDataArrayList;
    private String url, listSize = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.filter:
                filterDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void init() {
        filter = 0;
        inputUrl = (EditText) findViewById(R.id.inputUrl);
        inputListCount = (EditText) findViewById(R.id.inputListItemCount);

        ((Button) findViewById(R.id.submitButton)).setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        mainActivityPresenter = new MainActivityPresenter();
        recyclerViewAdapter = new RecyclerViewAdapter();
        listDataArrayList = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submitButton:
                Log.d("insideClick", String.valueOf(v.getId()));
                try {
                    getWords();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    private void getWords() throws NoSuchFieldException, IllegalAccessException {

        listSize = inputListCount.getText().toString();

        if (listSize.isEmpty()) {
            listCount = 5;
        } else {
            listCount = Integer.parseInt(listSize);
        }
        url = inputUrl.getText().toString();
        if (url.isEmpty()) {
            Toast.makeText(this, "Enter Url", Toast.LENGTH_LONG).show();
        } else {
            Log.d("insideUrl", url);
            mainActivityPresenter.getWords(url, MainActivity.this);
        }

    }

    @Override
    public void onSuccess(Document document) {
        if (filter == 0) {
            listDataArrayList = mainActivityPresenter.list(document, false);
        } else if (filter == 1) {
            listDataArrayList = mainActivityPresenter.list(document, true);
        }
        showList(listDataArrayList);
    }

    @Override
    public void onFailure(String message) {
        mainActivityPresenter.showToast(MainActivity.this, message);
    }

    private void showList(ArrayList<ListData> listData) {
        Log.d("ListSize", String.valueOf(listData.size()));
        recyclerViewAdapter.setData(listData, listCount);
        recyclerView.setAdapter(recyclerViewAdapter);
    }


    private void filterDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

        alertDialog.setTitle("Filter");
        alertDialog.setMessage("Apply filter to remove common words");
        alertDialog.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Filter Applied", Toast.LENGTH_SHORT).show();
                mainActivityPresenter.getWords(url, MainActivity.this);
                filter = 1;
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Filter Removed", Toast.LENGTH_SHORT).show();
                mainActivityPresenter.getWords(url, MainActivity.this);

                filter = 0;
//                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}
