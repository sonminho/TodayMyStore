package com.example.a01020072846.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TransactionImportActivity extends AppCompatActivity {
    TrasactionAdapter adapter = null;
    ArrayList<Item> list = null;
    ListView listView = null;
    String userId = null;
    ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_activity);

        listView = (ListView) findViewById(R.id.tx_item_list_view);

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("잠시만 기다려주세요");

        Intent intent = getIntent();

        userId = intent.getStringExtra("id");

        list = new ArrayList<Item>();

        new ItemListAsyncTask().execute("http://"+ getString(R.string.server_ip) +":8080/TodayMyStore/AndroidController?command=android_item_list", "import", userId);

    }

    private class ItemListAsyncTask extends AsyncTask<String, Void, String> {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();

        String URL, itemType, userId;
        String result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            URL = strings[0];
            itemType = strings[1];
            userId = strings[2];

            Item item = new Item();

            item.setItemType(itemType);
            item.setUserId(userId);

            String jsonObject = gson.toJson(item);

            try {
                RequestBody requestBody = new FormBody.Builder()
                        .add("item", jsonObject)
                        .build();

                Request request = new Request.Builder()
                        .url(URL)
                        .post(requestBody)
                        .build();

                Response response = client.newCall(request).execute();
                result = response.body().string();

            } catch(Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            list = gson.fromJson(result, new TypeToken<ArrayList<Item>>(){}.getType());
            adapter = new TrasactionAdapter(getApplicationContext(), list);
            listView.setAdapter(adapter);
            progressDialog.dismiss();
        }
    }
}
