package com.example.a01020072846.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TransactionExportActivity extends AppCompatActivity {
    TrasactionAdapter adapter = null;
    ArrayList<Item> itemList = null;
    ArrayList<Txn> txnList = null;
    ListView listView = null;
    String userId = null;
    ProgressDialog progressDialog = null;
    TextView tvDate = null, tvTotal = null;
    Button totalBtn = null, txnBtn, cancelBtn;
    String date = null;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_activity);

        final Gson gSon = new Gson();

        listView = (ListView) findViewById(R.id.tx_item_list_view);
        tvDate = (TextView) findViewById(R.id.tv_date);
        tvTotal = (TextView) findViewById(R.id.tv_total);
        totalBtn = (Button) findViewById(R.id.btn_total);
        txnBtn = (Button) findViewById(R.id.btn_txn);
        cancelBtn = (Button) findViewById(R.id.btn_cancel);

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("잠시만 기다려주세요");

        Intent intent = getIntent();

        userId = intent.getStringExtra("id");

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Date dateNow = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        date = sdf.format(dateNow);

        tvDate.setText(date);

        new TxnCheckAsyncTask().execute("http://"+ getString(R.string.server_ip) +":8080/TodayMyStore/AndroidController?command=android_txn_check",  userId, date);

        totalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sum = 0;
                if(type == 0) {
                    sum = adapter.itemTotalCalc();
                } else {
                    sum = adapter.txnTotalCalc();
                }

                tvTotal.setText(sum + " 원");
            }
        });

        txnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type == 0) { // 거래 내역 추가
                    itemList = adapter.getItemList();
                    new TxnInsertAsyncTask().execute("http://"+ getString(R.string.server_ip) +":8080/TodayMyStore/AndroidController?command=android_txn_insert", gSon.toJson(itemList), userId );
                } else { // 해당 날짜 수정
                    txnList = adapter.getTxnList();
                    new TxnUpdateAsyncTask().execute("http://"+ getString(R.string.server_ip) +":8080/TodayMyStore/AndroidController?command=android_txn_update", gSon.toJson(txnList), userId );
                }
            }
        });
    }

    private class TxnCheckAsyncTask extends AsyncTask<String, Void, String> {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();

        String URL, userId, txnDate;
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
            userId = strings[1];
            txnDate = strings[2];

            Txn txn = new Txn();
            txn.setMname_id(userId);
            txn.setTxn_date(txnDate);

            String jsonObject = gson.toJson(txn);

            try {
                RequestBody requestBody = new FormBody.Builder()
                        .add("txn", jsonObject)
                        .add("type", "export")
                        .build();

                Request request = new Request.Builder()
                        .url(URL)
                        .post(requestBody)
                        .build();

                Response response = client.newCall(request).execute();
                result = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if(result.equals("0")) {
                type = 0;
                new ItemListAsyncTask().execute("http://"+ getString(R.string.server_ip) +":8080/TodayMyStore/AndroidController?command=android_item_list", "export", userId);
            } else {
                type = 1;
                txnList = gson.fromJson(result, new TypeToken<ArrayList<Txn>>(){}.getType());
                adapter = new TrasactionAdapter(getApplicationContext(), null, txnList, 1);
                listView.setAdapter(adapter);
                tvTotal.setText(adapter.txnTotalCalc() + " 원");
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }
            progressDialog.dismiss();
        }

    }

    private class TxnInsertAsyncTask extends AsyncTask<String, Void, String> {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();

        String URL, list, userId;
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
            list = strings[1];
            userId = strings[2];

            try {
                RequestBody requestBody = new FormBody.Builder()
                        .add("itemList", list)
                        .add("userId", userId)
                        .add("type", "0")
                        .add("date", date)
                        .build();

                Request request = new Request.Builder()
                        .url(URL)
                        .post(requestBody)
                        .build();

                Response response = client.newCall(request).execute();
                result = response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if(result.equals("1")) {
                Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
                new TxnCheckAsyncTask().execute("http://"+ getString(R.string.server_ip) +":8080/TodayMyStore/AndroidController?command=android_txn_check",  userId, date);
            }
            progressDialog.dismiss();
        }
    }

    private class TxnUpdateAsyncTask extends AsyncTask<String, Void, String> {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();

        String URL, list, userId;
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
            list = strings[1];
            userId = strings[2];

            try {
                RequestBody requestBody = new FormBody.Builder()
                        .add("txnList", list)
                        .add("userId", userId)
                        .add("type", "1")
                        .add("date", date)
                        .build();

                Request request = new Request.Builder()
                        .url(URL)
                        .post(requestBody)
                        .build();

                Response response = client.newCall(request).execute();
                result = response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if(result.equals("1")) {
                Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
                new TxnCheckAsyncTask().execute("http://"+ getString(R.string.server_ip) +":8080/TodayMyStore/AndroidController?command=android_txn_check",  userId, date);
            }
            progressDialog.dismiss();
        }
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
            itemList = gson.fromJson(result, new TypeToken<ArrayList<Item>>(){}.getType());
            adapter = new TrasactionAdapter(getApplicationContext(), itemList, null, 0);

            listView.setAdapter(adapter);
            progressDialog.dismiss();
        }
    }
}
