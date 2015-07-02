package com.example.howard0702.blackjack;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeSettingFragment extends Fragment {
    ListView listView;
    ListAdapter listAdapter;
    String [] sValue =new String[4];
    public ChangeSettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_change_setting, container, false);
        firstValue();
        listView = (ListView) rootView.findViewById (R.id.settingListView);
        listAdapter = new ListAdapter((MainActivity)getActivity(),sValue);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,long id) {
                if(listView.getItemAtPosition(2).equals("")){
                    Toast.makeText(getActivity(),"起始金額不可為空", Toast.LENGTH_SHORT).show();
                }else if(Integer.valueOf((String)listView.getItemAtPosition(2))==0){
                    Toast.makeText(getActivity(),"起始金額不可為0", Toast.LENGTH_SHORT).show();
                }
                else{
                    writeValue((String)listView.getItemAtPosition(0),(String)listView.getItemAtPosition(1),(String)listView.getItemAtPosition(2),(String)listView.getItemAtPosition(3));
                    MainActivity p=(MainActivity)getActivity();
                    p.switchFragment(3);
                }
            }
        });


        return rootView;
    }

    public void firstValue(){
        MyDBHelper dbHelper;
        String t1,t2,t3,t4;
        MainActivity parent = (MainActivity)getActivity();
        // Required empty public constructor
        dbHelper = new MyDBHelper((MainActivity)getActivity());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor =
                db.query("GameSetting", // a. table
                        new String[] {"ID","Title","DeckCount","Amount","Gamblabe"}, // b. column names
                        "ID = ?",                          // selections
                        new String[] {parent.id.toString()},  // selections args
                        null, // e. group by
                        null, // f. having
                        "ID desc", // g. order by
                        null); // h. limit
        cursor.moveToFirst();
        t1=cursor.getString(1);
        t2=cursor.getString(2);
        t3=cursor.getString(3);
        t4=cursor.getString(4);
        db.close();
        dbHelper.close();
        sValue[0]=t1;
        sValue[1]=t2;
        sValue[2]=t3;
        sValue[3]=t4;
    }

    public void writeValue(String sv1,String sv2,String sv3,String sv4){
        MainActivity p=(MainActivity)getActivity();
        MyDBHelper dbHelper=new MyDBHelper(this.getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Title",Integer.valueOf(sv1)+1);//預設主題1
        values.put("DeckCount",Integer.valueOf(sv2)+2);//用設牌組5
        values.put(" Amount",sv3);//預設起始1000
        values.put("Gamblabe",sv4);//預設true → 1
        db.update("GameSetting", values, "ID = ?", new String[]{p.id.toString()});
        Toast.makeText(getActivity(), "更新成功", Toast.LENGTH_SHORT).show();
        db.close();
        dbHelper.close();
    }
}
