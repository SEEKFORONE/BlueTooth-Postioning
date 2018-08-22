package com.example.hesiliang.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class result extends Activity {
    Message message;
    float location[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        message = (Message) getApplicationContext();
        TextView Xloc = (TextView) findViewById(R.id.textView4);
        TextView Yloc = (TextView) findViewById(R.id.textView6);
        TextView Zloc = (TextView) findViewById(R.id.textView8);
        location = message.requestServer();
        String X = String.valueOf(location[0][0]);
        String X1;
        if (X.length() >= 7) {
            X1 = X.substring(0, 6);
        } else X1 = X;
        Xloc.setText(X1);
        String Y = String.valueOf(location[1][0]);
        String Y1;
        if (Y.length() >= 7) {
            Y1 = Y.substring(0, 6);
        } else Y1 = Y;
        Yloc.setText(Y1);
        String Z = String.valueOf(location[2][0]);
        String Z1;
        if (Z.length() >= 7) {
            Z1 = Z.substring(0, 6);
        } else Z1 = Z;
        Zloc.setText(Z1);
        Button button = (Button) findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(result.this, firstactivity.class);
                startActivity(intent);
            }
        });
    }
}
