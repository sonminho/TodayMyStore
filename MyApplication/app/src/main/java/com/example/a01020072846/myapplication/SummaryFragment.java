package com.example.a01020072846.myapplication;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class SummaryFragment extends Fragment {

    LineChart mChart;

    public SummaryFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary, container, false);
        mChart = (LineChart) view.findViewById(R.id.chart1);
        mChart.animateX(1000);

        setData(30, 60);


        return view;
    }

    private void setData(int count, int range) {
        ArrayList<Entry> yVals1 = new ArrayList<>();

        for(int i = 0; i < count; i++) {
            float val = (float) (Math.random()*range) + 250;
            yVals1.add(new Entry(i, val));
        }

        ArrayList<Entry> yVals2 = new ArrayList<>();

        for(int i = 0; i < count; i++) {
            float val = (float) (Math.random()*range) + 150;
            yVals2.add(new Entry(i, val));
        }

        ArrayList<Entry> yVals3 = new ArrayList<>();

        for(int i = 0; i < count; i++) {
            float val = (float) (Math.random()*range) + 50;
            yVals3.add(new Entry(i, val));
        }

        LineDataSet set1, set2, set3;

        set1 = new LineDataSet(yVals1, "Data set1");
        set1.setColor(Color.RED);
        set1.setDrawCircles(false);
        set1.setLineWidth(1f);


        set2 = new LineDataSet(yVals2, "Data set2");
        set2.setColor(Color.BLUE);
        set2.setDrawCircles(false);
        set2.setLineWidth(1f);

        LineData data = new LineData(set1, set2);
        mChart.setData(data);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.enableGridDashedLine(8, 8, 0);

        YAxis yLAxis = mChart.getAxisLeft();
        yLAxis.setTextColor(Color.BLACK);

        YAxis yRAxis = mChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);

        Description description = new Description();
        description.setText("");

        mChart.setDescription(description);
        mChart.animateY(2000, Easing.EasingOption.EaseInCubic);
        mChart.invalidate();
    }
}
