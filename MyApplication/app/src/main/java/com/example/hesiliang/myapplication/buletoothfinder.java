package com.example.hesiliang.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class buletoothfinder extends AppCompatActivity {
    Message message;

    private List<Bluetoothitem> bluetoothitemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buletoothfinder);
        initBluetooth();
        BluetoothAdapter1 adapter1 = new BluetoothAdapter1(buletoothfinder.this, R.layout.buletoothitem, bluetoothitemList);
        ListView listView = (ListView) findViewById(R.id.ListView1);
        listView.setAdapter(adapter1);
    }

    private void initBluetooth() {
        message = (Message) getApplicationContext();
        String Iget[][];
        Iget = message.getRssiForMap1();
        for (int i = 0; i < Iget.length; i++) {
            if (Iget[i][1] != null) {
                String ss=String.valueOf(Double.parseDouble(Iget[i][1]));
                if (ss.length()>=7){
                    ss=ss.substring(0,6);
                }
                double num=Double.parseDouble(ss);
                Bluetoothitem bluetoothitem = new Bluetoothitem(Iget[i][0], num);
                bluetoothitemList.add(bluetoothitem);
            }

        }
    }
}
