package com.example.a01020072846.myapplication;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class SalesTrendFragment extends Fragment {
    PieChart pieChart;
    String userId;

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
        setPieChart();



        return view;
    }

    private void setPieChart() {
        // Popultaing a list of PieEntries:
        List<PieEntry> pieEntries = new ArrayList<>();

        for(int i = 0; i < rainfall.length; i++) {
            pieEntries.add(new PieEntry(rainfall[i], monthNames[i]));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        dataSet.setSliceSpace(4f);
        dataSet.setSelectionShift(5f);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(18f);
        data.setValueTextColor(getResources().getColor(R.color.colorAccent));

        // Get the Chart:
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

}