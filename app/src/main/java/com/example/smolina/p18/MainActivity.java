package com.example.smolina.p18;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et1, et2, et3;
    private Button button1L, button1R, button4L, button4R, buttonL, buttonR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        et3 = (EditText) findViewById(R.id.et3);

        button1L = (Button) findViewById(R.id.button1L);
        button1R = (Button) findViewById(R.id.button1R);
        button4L = (Button) findViewById(R.id.button4L);
        button4R = (Button) findViewById(R.id.button4R);
        buttonL = (Button) findViewById(R.id.buttonL);
        buttonR = (Button) findViewById(R.id.buttonR);


    }

    public void left(View view){
        if(buttonL.isPressed()==true){
            button1R.setVisibility(View.INVISIBLE);
            button4R.setVisibility(View.INVISIBLE);
            button1L.setVisibility(View.VISIBLE);
            button4L.setVisibility(View.VISIBLE);
        }
    }

    public void right(View view){
        if(buttonR.isPressed()==true){
            button1L.setVisibility(View.INVISIBLE);
            button4L.setVisibility(View.INVISIBLE);
            button1R.setVisibility(View.VISIBLE);
            button4R.setVisibility(View.VISIBLE);
        }
    }

    public void insert(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Administration", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        String code = et1.getText().toString();
        String description = et2.getText().toString();
        String price = et3.getText().toString();
        ContentValues reg = new ContentValues();
        reg.put("code", code);
        reg.put("description", description);
        reg.put("price", price);
        db.insert("Articles", null, reg);
        db.close();
        et1.setText("");
        et2.setText("");
        et3.setText("");
        Toast.makeText(this, "Article was inserted", Toast.LENGTH_LONG).show();
    }

    public void searchByCode(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Administration", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        String code = et1.getText().toString();
        Cursor row = db.rawQuery("SELECT description, price FROM Articles WHERE code=" + code, null);
        if(row.moveToFirst()){
            et2.setText(row.getString(0));
            et3.setText(row.getString(1));
        }else{
            Toast.makeText(this, "Article doesn't exists or wasn't found by database", Toast.LENGTH_LONG).show();
            db.close();
        }
    }

    public void searchByDescription(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Administration", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        String description = et2.getText().toString();
        Cursor row = db.rawQuery("SELECT code, price FROM Articles WHERE description='" + description +"'", null);
        if(row.moveToFirst()){
            et1.setText(row.getString(0));
            et3.setText(row.getString(1));
        }else{
            Toast.makeText(this, "Article wasn't found on database or it isn't the correct description", Toast.LENGTH_LONG).show();
            db.close();
        }
    }

    public void deleteByCode(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Administration", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        String code = et1.getText().toString();
        int quantity = db.delete("Articles", "code="+code, null);
        db.close();;
        et1.setText("");
        et2.setText("");
        et3.setText("");
        if( quantity == 1){
            Toast.makeText(this, "Article was deleted correctly", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Article wasn't found", Toast.LENGTH_LONG).show();
        }
    }

    public void free(View view){
        et1.setText("");
        et2.setText("");
        et3.setText("");
    }

    public void change(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Administration", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        String code = et1.getText().toString();
        String description = et2.getText().toString();
        String price = et3.getText().toString();
        ContentValues reg = new ContentValues();
        reg.put("code", code);
        reg.put("description", description);
        reg.put("price", price);
        int quantity = db.update("Articles", reg, "code=" + code, null);
        db.close();
        if(quantity == 1){
            Toast.makeText(this, "Updated successfully", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Article doesn't exists or database is corrupt", Toast.LENGTH_LONG).show();
        }
    }

}
