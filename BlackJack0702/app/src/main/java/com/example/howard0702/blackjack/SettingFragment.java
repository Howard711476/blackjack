package com.example.howard0702.blackjack;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;//改這
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.howard0702.blackjack.dummy.DummyContent;


public class SettingFragment extends ListFragment{
    String[] option = {"更改名稱","更改密碼","更改遊戲設定","返回"};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, option));
    }
    public void onListItemClick(ListView parent, View v, int position, long id) {
        Toast.makeText(getActivity(), "您選擇項目是 : " + option[position], Toast.LENGTH_SHORT).show();
        MainActivity p = (MainActivity)this.getActivity();
        if(position==0){
            p.switchFragment(4);
        }
        else if(position==1){
            p.switchFragment(5);
        }
        else if(position==2){
            p.switchFragment(6);
        }
        else if(position==3){
            p.switchFragment(2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_list, container, false);
    }
}
