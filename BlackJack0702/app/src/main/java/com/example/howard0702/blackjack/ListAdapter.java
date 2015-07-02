package com.example.howard0702.blackjack;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by zoo88115 on 2015/5/29 0029.
 */
public class ListAdapter extends BaseAdapter{
    private Context mContext;
    private static LayoutInflater inflater = null;
    String s1,s2,s3,s4;
    Spinner spinner1,spinner2,spinner3;
    EditText amount;

    public ListAdapter(Context c,String [] sValue) {
        mContext = c;
        s1=sValue[0];
        s2=sValue[1];
        s3=sValue[2];
        s4=sValue[3];
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        if(position==0)
            return s1;
        else if(position==1)
            return s2;
        else if(position==2)
            return s3;
        else if(position==3)
            return s4;
        else return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
            if (position == 0) {
                String[] option = new String[]{"1", "2"};
                view = inflater.inflate(R.layout.layout_theme_change, null);
                ArrayAdapter<String> a1 = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, option);
                spinner1 = (Spinner) view.findViewById(R.id.spinner);
                spinner1.setAdapter(a1);
                spinner1.setSelection(Integer.valueOf(s1) - 1);
                spinner1.setOnItemSelectedListener(new ListViewSpinnerSelected(0,spinner1.getSelectedItemPosition()));
            } else if (position == 1) {
                String[] option = new String[]{"2", "3", "4", "5"};
                view = inflater.inflate(R.layout.layout_deckcount_change, null);
                ArrayAdapter<String> a1 = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, option);
                spinner2 = (Spinner) view.findViewById(R.id.spinner2);
                spinner2.setAdapter(a1);
                spinner2.setSelection(Integer.valueOf(s2) - 2);
                spinner2.setOnItemSelectedListener(new ListViewSpinnerSelected(1,spinner2.getSelectedItemPosition()));
            } else if (position == 2) {
                view = inflater.inflate(R.layout.layout_amount_change, null);
                amount = (EditText) view.findViewById(R.id.editText);
                amount.addTextChangedListener(new TextWatcher(){
                    public void afterTextChanged(Editable s) {
                            s3=s.toString();
                    }
                    public void beforeTextChanged(CharSequence s, int start, int count, int after){}
                    public void onTextChanged(CharSequence s, int start, int before, int count){}
                });
                amount.setText(s3);
            } else if (position == 3) {
                String[] option = new String[]{"不可", "可"};
                view = inflater.inflate(R.layout.layout_bet_change, null);
                ArrayAdapter<String> a1 = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, option);
                spinner3 = (Spinner) view.findViewById(R.id.spinner3);
                spinner3.setAdapter(a1);
                spinner3.setSelection(Integer.valueOf(s4));
                spinner3.setOnItemSelectedListener(new ListViewSpinnerSelected(3,spinner3.getSelectedItemPosition()));
            } else if (position == 4) {
                view = inflater.inflate(R.layout.data_save_layout, null);
            }

        return view;

    }


    class ListViewSpinnerSelected implements AdapterView.OnItemSelectedListener{
        int p,op;
        ListViewSpinnerSelected(int pos,int opv){
            p=pos;
            op=opv;
        }
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(p==0){
                s1=String.valueOf(position);
            }
            else if(p==1){
                s2=String.valueOf(position);
            }
            else if(p==3){
                s4=String.valueOf(position);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
