package com.example.hesiliang.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class setting extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView:
                Intent intent = new Intent(setting.this, moresetting.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("name", "1");
                startActivity(intent);
                break;
            case R.id.textView2:
                Intent intent1 = new Intent(setting.this, moresetting.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent1.putExtra("name", "2");
                startActivity(intent1);
                break;
            case R.id.textView9:
                Intent intent2 = new Intent(setting.this, moresetting.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent2.putExtra("name", "3");
                startActivity(intent2);
                break;
            case R.id.textView10:
                Intent intent3 = new Intent(setting.this, moresetting.class);
                intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent3.putExtra("name", "4");
                startActivity(intent3);
                break;
        }
    }

}
