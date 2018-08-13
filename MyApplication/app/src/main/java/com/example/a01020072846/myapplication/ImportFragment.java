package com.example.a01020072846.myapplication;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

@SuppressLint("ValidFragment")
public class ImportFragment extends Fragment {
    private ItemAdapter adapter;
    Context mContext;
    ListView listView;
    int count, checked;
    ArrayList<Item> list;
    String userId;

    public ImportFragment() {

    }

    @SuppressLint("ValidFragment")
    public ImportFragment(Context mContext, String userId) {
        this.mContext = mContext;
        this.userId = userId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_import, container, false);
        ImageButton plusBtn = (ImageButton) rootView.findViewById(R.id.plus_btn);
        ImageButton minusBtn = (ImageButton) rootView.findViewById(R.id.minus_btn);
        listView = (ListView) rootView.findViewById(R.id.importList);


        new ItemListAsyncTask().execute("http://192.168.0.39:8080/TodayMyStore/AndroidController?command=android_item_list", "import", userId);
        //list.add(new Item("매출", "라면", "3500"));

        //adapter = new ItemAdapter(getActivity(), list);

        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        int w = dm.widthPixels;
        int h = dm.heightPixels;

        final AddDialog dialog = new AddDialog(mContext);
        WindowManager.LayoutParams wm = dialog.getWindow().getAttributes();
        wm.width = w;
        wm.height = w;

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();

                /*
                int count = 0 ; count = adapter.getCount() ;

                for (int i=0; i<count; i++) {
                    listView.setItemChecked(i, true) ;
                }
                */
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray checkedItems = listView.getCheckedItemPositions();

                int count = adapter.getCount();

                for(int i = count-1; i >= 0; i--) {
                    if(checkedItems.get(i))
                        list.remove(i);
                }

                listView.clearChoices();
                adapter.notifyDataSetChanged();
            }
        });
        return rootView;
    }

    class AddDialog extends Dialog {
        EditText nameEt, priceEt;
        Button addOkBtn, addCancelBtn;

        protected AddDialog(Context context) {
            super(context);

            setContentView(R.layout.item_add);

            nameEt = (EditText) findViewById(R.id.item_name);
            priceEt = (EditText) findViewById(R.id.item_price);
            addOkBtn = (Button) findViewById(R.id.add_ok);
            addCancelBtn = (Button) findViewById(R.id.add_cancel);

            addOkBtn.setOnClickListener(new View.OnClickListener() {
                String name, price;
                @Override
                public void onClick(View v) {
                    name = nameEt.getText().toString();
                    price = priceEt.getText().toString();

                    if(name.length() == 0 || price.length() ==0) {
                        Toast.makeText(getActivity(), "빈칸입력바람", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        adapter.addItem("매입", name, price);
                        adapter.notifyDataSetChanged();
                        dismiss();
                    }
                }
            });

            addCancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
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

            }catch(Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
            list = gson.fromJson(result, new TypeToken<ArrayList<Item>>(){}.getType());
            adapter = new ItemAdapter(getActivity(), list);
            listView.setDivider(new ColorDrawable(Color.GRAY));
            listView.setDividerHeight(1);
            listView.setAdapter(adapter);
        }
    }
}