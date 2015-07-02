package com.example.howard0702.blackjack;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    public Fragment newFragment;
    public String mail;
    public Integer id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            if(ifFindData()==false) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new PlaceholderFragment())
                        .commit();
            }
            else{
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new GameMainFragment())
                        .commit();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener{
        public Button btnRegister,btnLogin;
        EditText editEmail,editPassword;
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            btnRegister=(Button)rootView.findViewById(R.id.toRegister);
            btnRegister.setOnClickListener(this);
            btnLogin=(Button)rootView.findViewById(R.id.login);
            btnLogin.setOnClickListener(this);
            editEmail=(EditText)rootView.findViewById(R.id.loginEmail);
            editPassword=(EditText)rootView.findViewById(R.id.loginPassword);
            return rootView;
        }

        @Override
        public void onClick(View v){
            if(v.getId()==R.id.toRegister){
                MainActivity parent = (MainActivity)this.getActivity();
                parent.switchFragment(1);
            }
            else if(v.getId()==R.id.login){
                if(ifEmail(editEmail.getText().toString())==true){
                    if(ifCorrect(editEmail.getText().toString(), editPassword.getText().toString())==true){
                        MainActivity parent = (MainActivity)this.getActivity();
                        parent.mail=editEmail.getText().toString();
                        parent.writeTemp();//寫入帳號暫存
                        parent.switchFragment(2);
                    }
                    else{
                        Toast.makeText(v.getContext(), "密碼輸入錯誤", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(v.getContext(), "無此帳號", Toast.LENGTH_SHORT).show();
                }
            }
        }

        public boolean ifEmail(String email) {
            MyDBHelper dbHelper = new MyDBHelper(this.getActivity());
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor =
                    db.query("SystemUser", // a. table
                            new String[] {"Email"}, // b. column names
                            "Email = ?",                          // selections
                            new String[] {email},  // selections args
                            null, // e. group by
                            null, // f. having
                            "ID desc", // g. order by
                            null); // h. limit

            if (cursor != null && cursor.getCount() > 0) {
                db.close();
                dbHelper.close();
                return true;
            }
            db.close();
            dbHelper.close();
            return false;
        }

        public boolean ifCorrect(String email,String password) {
            MyDBHelper dbHelper = new MyDBHelper(this.getActivity());
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor =
                    db.query("SystemUser", // a. table
                            new String[] {"ID","Email", "Password"}, // b. column names
                            "Email = ? and Password=?",                          // selections
                            new String[] {email,password},  // selections args
                            null, // e. group by
                            null, // f. having
                            "ID desc", // g. order by
                            null); // h. limit

            if (cursor != null && cursor.getCount() > 0) {
                MainActivity parent = (MainActivity)this.getActivity();
                cursor.moveToFirst();
                parent.id=cursor.getInt(0);
                db.close();
                dbHelper.close();
                return true;
            }
            db.close();
            dbHelper.close();
            return false;
        }
    }

    public void switchFragment(int page) {
        if(page==0){
            newFragment=new PlaceholderFragment();
        }
        else if(page==1){
            newFragment=new RegisterFragment();
        }
        else if(page==2){
            newFragment=new GameMainFragment();
        }
        else if(page==3){
            newFragment=new SettingFragment();
        }
        else if(page==4){
            newFragment=new ChangeNameFragment();
        }
        else if(page==5){
            newFragment=new ChangePasswordFragment();
        }
        else if(page==6){
            newFragment=new ChangeSettingFragment();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, newFragment);
        //transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if (keyCode == KeyEvent.KEYCODE_BACK) { // 攔截返回鍵
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("確認視窗")
                    .setMessage("確定要結束應用程式嗎?")
                    //.setIcon(R.drawable.ic_launcher)
                    .setPositiveButton("確定",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    finish();
                                }
                            })
                    .setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub

                                }
                            }).show();
        }
        return true;
    }
    private void writeTemp(){
        MainActivity parent = this;
        MyDBHelper dbHelper = new MyDBHelper(this);
        SQLiteDatabase  db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ID",parent.id);
        values.put("Email",parent.mail);//預設主題1
        db.insert("TempLogin", null, values);
        Toast.makeText(this, "已登入\n下次可以直接使用", Toast.LENGTH_SHORT).show();
        db.close();
        dbHelper.close();
    }
    private boolean ifFindData(){
        MyDBHelper dbHelper = new MyDBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("TempLogin", // a. table
                new String[] {"ID,Email"}, // b. column names
                null,                          // selections
                null,  // selections args
                null, // e. group by
                null, // f. having
                "ID desc", // g. order by
                null); // h. limit
        cursor.moveToFirst();
        if(cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
            this.mail=cursor.getString(1);
            this.id=cursor.getInt(0);
            db.close();
            dbHelper.close();
            return true;
        }
        db.close();
        dbHelper.close();
        return false;
    }
}
