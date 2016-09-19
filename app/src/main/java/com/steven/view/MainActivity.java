package com.steven.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Line l1= (Line) findViewById(R.id.line1);
        l1.setType(Line.LineType.LINE_FAIL);
        Line l2= (Line) findViewById(R.id.line2);
        l2.setType(Line.LineType.LINE_PROGRESS);
        Line l3= (Line) findViewById(R.id.line3);
        l3.setType(Line.LineType.LINE_FAIL);
        Line l4= (Line) findViewById(R.id.line4);
        l4.setType(Line.LineType.LINE_SUCCESS);
        Line l5= (Line) findViewById(R.id.line5);
        l5.setType(Line.LineType.LINE_PROGRESS);
        Line l6= (Line) findViewById(R.id.line6);
        l6.setType(Line.LineType.LINE_SUCCESS);
        l6.setLineGone();

        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SecondActivity.class));
            }
        });
    }
}
