package com.example.hesiliang.myapplication;


import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.widget.Toast;


public class firstactivity extends AppCompatActivity {
    Message message;
    private BluetoothAdapter bluetoothAdapter;
    public String[][] rssiForMap = new String[30][2];

    private static final double A_Value = 86;
    /**
     * A - 发射端和接收端相隔1米时的信号强度
     */
    private static final double n_Value = 2.5;

    /**
     * n - 环境衰减因子
     */
    int i = 0;//计数

    //符合我注册的过滤器 ,就可以进入onReceive方法
    class MBluetoothBroadCast extends BroadcastReceiver {
        //一旦被发现或链接,我们就能能收到广播;
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //如果我们搜索到了其他的蓝牙,就会进入这个判断
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //得到搜索到的蓝牙设备
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    //用设备获取蓝牙名字
                    short rssi = intent.getExtras().getShort(BluetoothDevice.EXTRA_RSSI);
                    double rssi1 = rssi;
                    boolean j = false;
                    String name = device.getName() + "_" + device.getAddress();
                    for (int m = 0; m < 30; m++) {
                        if (rssiForMap[m][0] != null) {
                            if (rssiForMap[m][0].equals(name)) {
                                rssi1 = (Double.parseDouble(rssiForMap[m][1]) + getDistance(rssi)) / 2;//取平均值
                                rssiForMap[m][1] = String.valueOf(rssi1);
                                j = true;
                            }
                        }
                    }

                    if (j) {
                    } else {
                        rssiForMap[i][0] = name;
                        rssiForMap[i][1] = String.valueOf(getDistance(rssi));
                        i++;
                    }
                    System.out.println(device.getName() + getDistance(rssi));
                }
                //如果搜索完毕,会进入这个判断;
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //

            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstlayout);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        message = (Message) getApplicationContext();
        if (ContextCompat.checkSelfPermission(firstactivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(firstactivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);//请求权限
        } else {
            for (int k = 0; k < 10; k++) {
                searchBluetooh();
                delay(100);//延时搜索10次取平均值
            }
            Toast.makeText(firstactivity.this, "已搜索完成", Toast.LENGTH_LONG).show();
        }

        Button button1 = (Button) findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (message.isMapfull()) {//判断是否四个定位器都被设置
                    Intent intent = new Intent(firstactivity.this, result.class);
                    Log.d("lalala", "llll");
                    startActivity(intent);
                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(firstactivity.this);
                    dialog.setTitle("请检查相关设置");
                    dialog.setMessage("部分信息未设置");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("去设置定位器", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent1 = new Intent(firstactivity.this, setting.class);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent1);
                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Intent intent1 = new Intent(firstactivity.this, firstactivity.class);
                            //intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            //startActivity(intent1);
                        }
                    });
                    dialog.show();
                }
            }
        });
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.setRssiForMap1(rssiForMap);//存储蓝牙数据
                Intent intent = new Intent(firstactivity.this, setting.class);
                Log.d("hehehe", "hhhh");
                startActivity(intent);
            }
        });

        Button button3 = (Button) findViewById(R.id.button5);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (bluetoothAdapter != null && bluetoothAdapter.enable()) {
                    for (int k = 0; k < 10; k++) {
                        searchBluetooh();
                        delay(100);
                    }
                    message.setRssiForMap1(rssiForMap);
                    Intent intent = new Intent(firstactivity.this, buletoothfinder.class);
                    Log.d("hehehe", "hhhh");
                    startActivity(intent);
                } else {
                    AlertDialog.Builder dialog1 = new AlertDialog.Builder(firstactivity.this);
                    dialog1.setTitle("错误");
                    dialog1.setMessage("蓝牙设备未打开");
                    dialog1.setCancelable(true);
                    dialog1.show();
                }
            }
        });
        Button button4 = (Button) findViewById(R.id.button6);
        button4.setOnClickListener(new View.OnClickListener() {//清除数组中的蓝牙数据
            @Override
            public void onClick(View v) {
                message.clear();
                for (int k = 0; k < 30; k++) {
                    for (int i = 0; i < 2; i++) {
                        rssiForMap[k][i] = null;
                    }
                }
                AlertDialog.Builder dialog1 = new AlertDialog.Builder(firstactivity.this);
                dialog1.setTitle("成功");
                dialog1.setMessage("已清空");
                dialog1.setCancelable(true);
                dialog1.show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    for (int k = 0; k < 10; k++) {
                        searchBluetooh();
                        delay(100);
                    }
                    Toast.makeText(firstactivity.this, "已搜索完成", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "你已拒绝开启权限", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void searchBluetooh() {
        //添加蓝牙可以被发现
        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        MBluetoothBroadCast mBluetoothBroadCast = new MBluetoothBroadCast();
        registerReceiver(mBluetoothBroadCast, intentFilter);

        IntentFilter intentFilter1 = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mBluetoothBroadCast, intentFilter1);

        //开始找蓝牙设备
        bluetoothAdapter.startDiscovery();


    }

    public static double getDistance(int rssi) {
        int iRssi = Math.abs(rssi);
        double power = (iRssi - A_Value) / (10 * n_Value);
        return Math.pow(10, power);
    }//通过rssi值获取距离

    private void delay(int ms) {
        try {
            Thread.currentThread();
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }//延时

}
