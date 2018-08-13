package com.example.a01020072846.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class ExportFragment extends Fragment {
    private ItemAdapter adapter;
    Context mContext;
    ListView listView;
    int count, checked;
    ArrayList<Item> list;

    public ExportFragment() {

    }

    @SuppressLint("ValidFragment")
    public ExportFragment(Context context) {
        this.mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_export, container, false);
        ImageButton plusBtn = (ImageButton) rootView.findViewById(R.id.plus_btn);
        ImageButton minusBtn = (ImageButton) rootView.findViewById(R.id.minus_btn);
        listView = (ListView) rootView.findViewById(R.id.exportList);

        list = new ArrayList<>();
        list.add(new Item("매출", "라면", "3500"));
        list.add(new Item("매출", "라면", "3500"));
        list.add(new Item("매출", "라면", "3500"));

        adapter = new ItemAdapter(getActivity(), list);

        listView.setDivider(new ColorDrawable(Color.GRAY));
        listView.setDividerHeight(1);
        listView.setAdapter(adapter);

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0 ; count = adapter.getCount() ;

                for (int i=0; i<count; i++) {
                    listView.setItemChecked(i, true) ;
                }

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

}