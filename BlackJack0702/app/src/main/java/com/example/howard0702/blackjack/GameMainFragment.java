package com.example.howard0702.blackjack;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameMainFragment extends Fragment implements View.OnClickListener{
    public Button btnPlayGame,btnSetting,btnReturn;
    public String mail;
    public TextView showEmail;
    public GameMainFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_game_main, container, false);
        ifhave();
        btnPlayGame=(Button)rootView.findViewById(R.id.playGame);
        btnSetting=(Button)rootView.findViewById(R.id.setting);
        btnReturn=(Button)rootView.findViewById(R.id.returnLogin);
        btnPlayGame.setOnClickListener(this);
        btnSetting.setOnClickListener(this);
        btnReturn.setOnClickListener(this);
        showEmail=(TextView)rootView.findViewById(R.id.textView3);
        MainActivity parent = (MainActivity)this.getActivity();
        showEmail.setText(parent.mail);
        return rootView;
    }

    @Override
    public void onClick(View v){
        if(v.getId()==R.id.playGame){
            //
        }else if(v.getId()==R.id.setting){
            MainActivity parent = (MainActivity)this.getActivity();
            parent.switchFragment(3);
        }else if(v.getId()==R.id.returnLogin){
            leaveDel();
            Toast.makeText(getActivity(), "已登出\n請重新登入", Toast.LENGTH_SHORT).show();
            MainActivity parent = (MainActivity)this.getActivity();
            parent.switchFragment(0);
        }
    }

    public void ifhave(){
        MainActivity parent = (MainActivity)this.getActivity();
        // Required empty public constructor
        MyDBHelper dbHelper = new MyDBHelper(this.getActivity());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor =
                db.query("GameSetting", // a. table
                        new String[] {"ID"}, // b. column names
                        "ID = ?",                          // selections
                        new String[] {parent.id.toString()},  // selections args
                        null, // e. group by
                        null, // f. having
                        "ID desc", // g. order by
                        null); // h. limit

        if (cursor != null && cursor.getCount() > 0) {
            Toast.makeText(getActivity(), "使用舊有預設", Toast.LENGTH_SHORT).show();
            db.close();
            dbHelper.close();
        }
        else{
            db.close();
            dbHelper.close();
            create();//產生資料
        }
    }

    public void create(){
        MainActivity parent = (MainActivity)this.getActivity();
        MyDBHelper dbHelper = new MyDBHelper(this.getActivity());
        SQLiteDatabase  db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ID",parent.id);
        values.put("Title","1");//預設主題1
        values.put("DeckCount","5");//用設牌組5
        values.put(" Amount","1000");//預設起始1000
        values.put("Gamblabe","1");//預設true → 1
        db.insert("GameSetting", null, values);
        Toast.makeText(getActivity(), "新用戶，載入預設項目", Toast.LENGTH_SHORT).show();
        db.close();
        dbHelper.close();
    }
    private void leaveDel(){
        MainActivity p=(MainActivity)getActivity();
        MyDBHelper dbHelper = new MyDBHelper(this.getActivity());
        SQLiteDatabase  db = dbHelper.getWritableDatabase();
        db.delete("TempLogin","ID=?",new String[]{p.id.toString()});
        db.close();
        dbHelper.close();
    }
}
