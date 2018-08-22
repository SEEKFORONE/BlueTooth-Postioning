package com.example.hesiliang.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class BluetoothAdapter1 extends ArrayAdapter<Bluetoothitem> {
    private int resourseId;

    public BluetoothAdapter1(Context context, int textviewResourseId, List<Bluetoothitem> objects) {
        super(context, textviewResourseId, objects);
        resourseId = textviewResourseId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Bluetoothitem bluetoothitem=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourseId,parent,false);
        TextView textView1=(TextView)view.findViewById(R.id.textView15);
        TextView textView2=(TextView)view.findViewById(R.id.textView16);
        textView1.setText(bluetoothitem.getName());
        textView2.setText(" "+bluetoothitem.getRssi());
        return view;
    }
}
