package com.example.a01020072846.myapplication;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class SalesTrendFragment extends Fragment {
    PieChart pieChart;
    Button btnStartDay, btnEndDay;
    DatePickerDialog startPicker, endPicker;
    ProgressDialog progressDialog;
    ArrayList<Trend> list;
    SimpleDateFormat sdf;

    String userId;
    String date, startDate, endDate;
    Date startDateTime, endDateTime;
    int year, month, day;


    private String[] monthNames = {"a", "b", "c", "d", "e"};
    private float[] rainfall = {25.3f, 10.6f, 66.78f, 44.32f, 23.9f};


    public SalesTrendFragment(String userId) {
        this.userId = userId;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sales_trend, container, false);

        pieChart = (PieChart) view.findViewById(R.id.piechart);
        btnStartDay = (Button) view.findViewById(R.id.btn_start_day);
        btnEndDay = (Button) view.findViewById(R.id.btn_end_day);

        progressDialog = new ProgressDialog(getActivity(), R.style.StyledDialog);
        progressDialog.setMessage("잠시만 기다려주세요");
        progressDialog.setCancelable(false);

        Date dateNow = new Date();
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        date = sdf.format(dateNow);
        startDate = date;
        endDate = date;
        btnStartDay.setText(startDate);
        btnEndDay.setText(endDate);

        try {
            startDateTime = sdf.parse(date);
            endDateTime = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        StringTokenizer stk  = new StringTokenizer(date, "-");
        year = Integer.valueOf(stk.nextToken());
        month = Integer.valueOf(stk.nextToken());
        day = Integer.valueOf(stk.nextToken());

        startPicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                startDate = year + "-" + (month+1) + "-" + dayOfMonth;

                try {
                    startDateTime = sdf.parse(startDate);

                    long calDate = startDateTime.getTime() - endDateTime.getTime();
                    long calDateDays = calDate / (24*60*60*1000);

                    if(calDateDays > 0) {
                        btnStartDay.setText(endDate);
                        btnEndDay.setText(startDate);

                        String temp = startDate;
                        startDate = endDate;
                        endDate = temp;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                } finally {
                    btnStartDay.setText(startDate);
                    new TrendAsyncTask().execute("http://"+ getString(R.string.server_ip) +":8080/TodayMyStore/AndroidController?command=android_txn_trend", userId, "export", startDate, endDate);
                }

            }
        }, year, month - 1, day);

        endPicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                endDate = year + "-" + (month+1) + "-" + dayOfMonth;

                try {
                    endDateTime = sdf.parse(endDate);

                    long calDate = startDateTime.getTime() - endDateTime.getTime();
                    long calDateDays = calDate / (24*60*60*1000);
                    Toast.makeText(getActivity(), calDateDays + "", Toast.LENGTH_SHORT).show();

                    if(calDateDays > 0) {
                        btnStartDay.setText(endDate);
                        btnEndDay.setText(startDate);

                        String temp = startDate;
                        startDate = endDate;
                        endDate = temp;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                } finally {
                    btnEndDay.setText(endDate);
                    new TrendAsyncTask().execute("http://"+ getString(R.string.server_ip) +":8080/TodayMyStore/AndroidController?command=android_txn_trend", userId, "export", startDate, endDate);
                }
            }
        }, year, month - 1, day);

        btnStartDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPicker.show();
            }
        });

        btnEndDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endPicker.show();
            }
        });

        //setPieChart();

        return view;
    }

    private void setPieChart(ArrayList<Trend> list) {
        List<PieEntry> pieEntries = new ArrayList<>();

        for(int i = 0; i < list.size(); i++) {
            pieEntries.add(new PieEntry(list.get(i).getSumQuantity(), list.get(i).getItemName()));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        dataSet.setSliceSpace(4f);
        dataSet.setSelectionShift(5f);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(18f);
        data.setValueTextColor(getResources().getColor(R.color.colorAccent));

        pieChart.setData(data);
        pieChart.setDragDecelerationFrictionCoef(0.99f);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(false);
        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);
        pieChart.setDrawEntryLabels(true);
        pieChart.setRotationEnabled(false);
        pieChart.invalidate();
    }

    private class TrendAsyncTask extends AsyncTask<String, Void, String> {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();

        String URL, userId, itemType, startDate, endDate;
        String result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            URL = strings[0];

            userId = strings[1];
            itemType = strings[2];
            startDate = strings[3];
            endDate = strings[4];

            Trend trend = new Trend();
            trend.setUserId(userId);
            trend.setItemType(itemType);
            trend.setStartDate(startDate);
            trend.setEndDate(endDate);

            String jsonObject = gson.toJson(trend);

            try {
                RequestBody requestBody = new FormBody.Builder()
                        .add("trend", jsonObject)
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
            list = gson.fromJson(result, new TypeToken<ArrayList<Trend>>(){}.getType());

            if(list.size() == 0) {
                Toast.makeText(getActivity(), "데이터가 없습니다.", Toast.LENGTH_SHORT).show();
            }
            setPieChart(list);
            progressDialog.dismiss();
        }
    }
}