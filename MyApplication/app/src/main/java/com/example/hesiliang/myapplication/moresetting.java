package com.example.hesiliang.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class moresetting extends AppCompatActivity {
    Message message;
    String S = "0";
    private String name1;
    private int Xloc1;
    private int Yloc1;
    private int Zloc1;
    private String storeloc[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moresetting);

        message = (Message) getApplicationContext();
        storeloc = message.getRssiForMap();
        Intent intent = getIntent(); // 取得从上一个Activity当中传递过来的Intent对象
        if (intent != null) {
            String data = intent.getStringExtra("name"); // 从Intent当中根据key取得value
            if (storeloc[Integer.parseInt(data) - 1][0] != null) {
                EditText name1 = (EditText) findViewById(R.id.textView12);
                EditText Xloc1 = (EditText) findViewById(R.id.textView4);
                EditText Yloc1 = (EditText) findViewById(R.id.textView6);
                EditText Zloc1 = (EditText) findViewById(R.id.textView8);
                name1.setText(storeloc[Integer.parseInt(data) - 1][0]);
                Xloc1.setText(storeloc[Integer.parseInt(data) - 1][2]);
                Yloc1.setText(storeloc[Integer.parseInt(data) - 1][3]);
                Zloc1.setText(storeloc[Integer.parseInt(data) - 1][4]);
            }
            S = data;
            TextView textView = (TextView) findViewById(R.id.textView7);
            textView.setText("第" + data + "号定位器");
        }


        Button button = (Button) findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = (EditText) findViewById(R.id.textView12);
                EditText Xloc = (EditText) findViewById(R.id.textView4);
                EditText Yloc = (EditText) findViewById(R.id.textView6);
                EditText Zloc = (EditText) findViewById(R.id.textView8);
                name1 = name.getText().toString();
                Xloc1 = Integer.parseInt(Xloc.getText().toString());
                Yloc1 = Integer.parseInt(Yloc.getText().toString());
                Zloc1 = Integer.parseInt(Zloc.getText().toString());
                if (message.ifexistLocator(name1)!=5) {
                    Toast.makeText(moresetting.this, "此定位器已存在于第"+(message.ifexistLocator(name1)+1)+"号定位器", Toast.LENGTH_LONG).show();
                } else {
                    boolean ifT = message.addLocation(name1, Xloc1, Yloc1, Zloc1, Integer.parseInt(S) - 1);

                    if (ifT) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(moresetting.this);
                        dialog.setTitle("设置成功");
                        dialog.setMessage("你已经成功设置第 " + S+ "号定位器");
                        dialog.setCancelable(false);
                        dialog.setPositiveButton("返回主菜单", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent1 = new Intent(moresetting.this, firstactivity.class);
                                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent1);
                            }
                        });
                        dialog.setNegativeButton("继续设置", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent1 = new Intent(moresetting.this, setting.class);
                                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent1);
                            }
                        });
                        dialog.show();

                    } else {
                        Toast.makeText(moresetting.this, "此定位器未检测到", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        Button button1 = (Button) findViewById(R.id.button7);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(moresetting.this, setting.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
            }
        });

    }


}
