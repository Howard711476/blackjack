package com.example.howard0702.blackjack;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeNameFragment extends Fragment implements View.OnClickListener{
    EditText changeName;
    Button btn1,btn2;

    public ChangeNameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MainActivity parent = (MainActivity)this.getActivity();
        View rootView=inflater.inflate(R.layout.fragment_change_name, container, false);
        changeName=(EditText)rootView.findViewById(R.id.editChangeName);
        changeName.setText(getName(parent.mail));
        btn1=(Button)rootView.findViewById(R.id.changeNameCheck);
        btn2=(Button)rootView.findViewById(R.id.changeNameReturn);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        return rootView;
    }

    public String getName(String m){
        String a;
        MyDBHelper dbHelper = new MyDBHelper(this.getActivity());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor =
                db.query("SystemUser", // a. table
                        new String[] {"Name,Email"}, // b. column names
                        "Email = ?",                          // selections
                        new String[] {m},  // selections args
                        null, // e. group by
                        null, // f. having
                        "ID desc", // g. order by
                        null); // h. limit
        cursor.moveToFirst();
        a= cursor.getString(0);
        db.close();
        dbHelper.close();
        return a;
    }

    @Override
    public void onClick(View v){
        if(v.getId()==R.id.changeNameCheck){
            updateName();
            Toast.makeText(v.getContext(), "更新成功", Toast.LENGTH_SHORT).show();
        }else if(v.getId()==R.id.changeNameReturn){
            MainActivity parent = (MainActivity)this.getActivity();
            parent.switchFragment(3);
        }
    }

    public void updateName(){
        MainActivity parent = (MainActivity)this.getActivity();
        MyDBHelper dbHelper = new MyDBHelper(this.getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", changeName.getText().toString());

        db.update("SystemUser", values, "Email = ?", new String[]{parent.mail});  //給條件 mail相同
        db.close();
        dbHelper.close();
    }
}
