package com.example.a01020072846.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class ImportFragment extends Fragment {
    private ItemAdapter adapter;
    Context mContext;
    ListView listView;
    int count, checked;
    ArrayList<Item> list;

    public ImportFragment() {

    }

    @SuppressLint("ValidFragment")
    public ImportFragment(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_import, container, false);
        ImageButton plusBtn = (ImageButton) rootView.findViewById(R.id.plus_btn);
        ImageButton minusBtn = (ImageButton) rootView.findViewById(R.id.minus_btn);
        listView = (ListView) rootView.findViewById(R.id.importList);

        list = new ArrayList<>();
        list.add(new Item("매출", "라면", "3500"));
        list.add(new Item("매출", "라면", "3500"));
        list.add(new Item("매출", "라면", "3500"));

        adapter = new ItemAdapter(list);

        listView.setDivider(new ColorDrawable(Color.GRAY));
        listView.setDividerHeight(1);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = (Item)(adapter.getItem(position));
                String name = item.getItemName();
                Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
                count = adapter.getCount();

                if(count > 0) {
                    checked = listView.getCheckedItemPosition();
                    list = adapter.getList();
                    list.remove(checked);
                }

                listView.clearChoices();
                adapter.notifyDataSetChanged();
            }
        });

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Plus", Toast.LENGTH_SHORT).show();
            }
        });
        
        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Minus", Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }

}
