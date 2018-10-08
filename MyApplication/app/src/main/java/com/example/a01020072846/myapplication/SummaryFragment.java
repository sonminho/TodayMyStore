package com.example.a01020072846.myapplication;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@SuppressLint("ValidFragment")
public class SummaryFragment extends Fragment {

    LineChart mChart, mChart2, mChart3;

    Button btnStartDay, btnEndDay;
    DatePickerDialog startPicker, endPicker;
    ProgressDialog progressDialog;

    ArrayList<Summary> list, list2, list3;
    SimpleDateFormat sdf;
    String userId;
    String date, startDate, endDate;
    Date startDateTime, endDateTime;
    int year, month, day;

    public SummaryFragment() {

    }

    public SummaryFragment(String userId) {
        this.userId = userId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary, container, false);

        mChart = (LineChart) view.findViewById(R.id.totalChart);
        mChart.animateX(1000);

        mChart2 = (LineChart) view.findViewById(R.id.totalChart2);
        mChart2.animateX(1000);

        mChart3 = (LineChart) view.findViewById(R.id.totalChart3);
        mChart3.animateX(1000);

        btnStartDay = (Button) view.findViewById(R.id.btn_start_day);
        btnEndDay = (Button) view.findViewById(R.id.btn_end_day);

        progressDialog = new ProgressDialog(getActivity(), R.style.StyledDialog);
        progressDialog.setMessage("잠시만 기다려주세요");
        progressDialog.setCancelable(false);

        setDate();

        return view;
    }

    private void setDate() {
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

        StringTokenizer stk = new StringTokenizer(date, "-");
        year = Integer.valueOf(stk.nextToken());
        month = Integer.valueOf(stk.nextToken());
        day = Integer.valueOf(stk.nextToken());

        startPicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                startDate = year + "-" + (month + 1) + "-" + dayOfMonth;

                try {
                    startDateTime = sdf.parse(startDate);

                    long calDate = startDateTime.getTime() - endDateTime.getTime();
                    long calDateDays = calDate / (24 * 60 * 60 * 1000);

                    if (calDateDays > 0) {
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
                    new SummaryFragment.SummaryAsyncTask().execute("http://"+ getString(R.string.server_ip) +":8080/TodayMyStore/AndroidController?command=android_summary", userId, "export", startDate, endDate);

                }
            }
        }, year, month - 1, day);

        endPicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                endDate = year + "-" + (month + 1) + "-" + dayOfMonth;

                try {
                    endDateTime = sdf.parse(endDate);

                    long calDate = startDateTime.getTime() - endDateTime.getTime();
                    long calDateDays = calDate / (24 * 60 * 60 * 1000);

                    if (calDateDays > 0) {
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
                    new SummaryFragment.SummaryAsyncTask().execute("http://"+ getString(R.string.server_ip) +":8080/TodayMyStore/AndroidController?command=android_summary", userId, "sales", startDate, endDate);
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
    }

    private void setmChart(ArrayList<Summary> list) {
        int listSize = list.size();
        final HashMap<Integer, String> map = new HashMap<>();

        for(int i = 1; i <= listSize; i++) {
            map.put(i, list.get(i-1).getDate());
        }

        List<Entry> entries = new ArrayList<>();

        for(int i = 1; i <= listSize; i++) {
            entries.add(new Entry(i, list.get(i-1).getTotal()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "매출");
        dataSet.setColor(Color.RED);
        LineData data = new LineData(dataSet);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float v, AxisBase axisBase) {
                return map.get((int) v);
            }
        });

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);

        YAxis yLAxis = mChart.getAxisLeft();
        yLAxis.setTextColor(Color.BLACK);

        YAxis yRAxis = mChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);

        Description description = new Description();
        description.setText("");

        mChart.setDescription(description);
        mChart.getXAxis().setGranularityEnabled(true);

        mChart.animateY(2000, Easing.EasingOption.EaseInCubic);
        mChart.setData(data);
        mChart.invalidate();
    }

    private void setmChart2(ArrayList<Summary> list) {
        int listSize = list.size();
        final HashMap<Integer, String> map = new HashMap<>();

        for(int i = 1; i <= listSize; i++) {
            map.put(i, list.get(i-1).getDate());
        }

        List<Entry> entries = new ArrayList<>();

        for(int i = 1; i <= listSize; i++) {
            entries.add(new Entry(i, list.get(i-1).getTotal()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "매입");
        dataSet.setColor(Color.BLUE);
        LineData data = new LineData(dataSet);

        XAxis xAxis = mChart2.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float v, AxisBase axisBase) {
                return map.get((int) v);
            }
        });

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);

        YAxis yLAxis = mChart2.getAxisLeft();
        yLAxis.setTextColor(Color.BLACK);

        YAxis yRAxis = mChart2.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);

        Description description = new Description();
        description.setText("");

        mChart2.setDescription(description);
        mChart2.getXAxis().setGranularityEnabled(true);

        mChart2.animateY(2000, Easing.EasingOption.EaseInCubic);
        mChart2.setData(data);
        mChart2.invalidate();
    }

    private void setmChart3(ArrayList<Summary> list) {
        int listSize = list.size();
        final HashMap<Integer, String> map = new HashMap<>();

        for(int i = 1; i <= listSize; i++) {
            map.put(i, list.get(i-1).getDate());
        }

        List<Entry> entries = new ArrayList<>();

        for(int i = 1; i <= listSize; i++) {
            entries.add(new Entry(i, list.get(i-1).getTotal()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "순이익");
        dataSet.setColor(Color.GREEN);
        LineData data = new LineData(dataSet);

        XAxis xAxis = mChart3.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float v, AxisBase axisBase) {
                return map.get((int) v);
            }
        });

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);

        YAxis yLAxis = mChart3.getAxisLeft();
        yLAxis.setTextColor(Color.BLACK);

        YAxis yRAxis = mChart3.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);

        Description description = new Description();
        description.setText("");

        mChart3.setDescription(description);
        mChart3.getXAxis().setGranularityEnabled(true);

        mChart3.animateY(2000, Easing.EasingOption.EaseInCubic);
        mChart3.setData(data);
        mChart3.invalidate();
    }

    public class SummaryAsyncTask extends AsyncTask<String, Void, String> {
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
                        .add("summary", jsonObject)
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
            //list = gson.fromJson(result, new TypeToken<ArrayList<Summary>>(){}.getType());

            JsonParser parser = new JsonParser();
            JsonObject jsonObject = (JsonObject) parser.parse(result);
            JsonArray exportList = (JsonArray) jsonObject.get("exportList");
            list = gson.fromJson(exportList, new TypeToken<ArrayList<Summary>>(){}.getType());

            JsonArray importList = (JsonArray) jsonObject.get("importList");
            list2 = gson.fromJson(importList, new TypeToken<ArrayList<Summary>>(){}.getType());

            if(list.size() != 0) {
                setmChart(list);
            }
            if(list2.size() != 0) {
                setmChart2(list2);
            }

            progressDialog.dismiss();
        }

    }
}
