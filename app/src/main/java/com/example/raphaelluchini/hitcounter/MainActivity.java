package com.example.raphaelluchini.hitcounter;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public TextView textView = null;
    public TextView info = null;
    public Button resetButton = null;
    public int count = 0;
    public DBHelper mydb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.counter);
        info = (TextView) findViewById(R.id.info);
        resetButton = (Button) findViewById(R.id.buttonReset);

        mydb = new DBHelper(this);
        Cursor cursor = mydb.getNumber(1);
        if (cursor != null && cursor.moveToFirst()){
            count = Integer.parseInt(cursor.getString(0));
            cursor.close();
            setColors(count, textView, info);
        }else{
            mydb.insertNumber(count);
        }

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = 0;
                mydb.updateNumber(1, count);
                textView.setText(String.valueOf(count));
            }
        });

        textView.setText(String.valueOf(count));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                count++;
                return true;
        }
        setColors(count, textView, info);
        textView.setText(String.valueOf(count));
        mydb.updateNumber(1, count);
        return true;
    }

    public void setColors(Integer num, TextView t, TextView i){
        if(num % 10 == 0){
            RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainLayout);
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            int color2 = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            layout.setBackgroundColor(color);
            t.setTextColor(color2);
            i.setTextColor(color2);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }
}
