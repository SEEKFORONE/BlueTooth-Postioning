package com.example.hesiliang.myapplication;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.List;

import java.util.Map;

import android.bluetooth.BluetoothAdapter;

import android.bluetooth.BluetoothDevice;

import android.bluetooth.BluetoothManager;

import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanResult;
import android.content.Context;

import android.os.Build;

import android.os.Handler;
import android.bluetooth.le.ScanCallback;

public class BLEPositioning {


    private
    Context m_ctx;

    private
    Handler handler;


    private
    BluetoothManager bluetoothManager;

    private
    BluetoothAdapter mBluetoothAdapter;

    private BluetoothLeScanner mBluetoothLeScanner;

    private List<BluetoothDevice> devices = new ArrayList<BluetoothDevice>();
    // 存储蓝牙扫描结果，key - name_address, value - List<IBeaconRecord>

    private Map<String, List<IbeaconRecord>> mapBltScanResult;


    public BLEPositioning(Context ctx) {

        super();

        this.m_ctx = ctx;

        initParam();
        startScan();
    }


    /**
     * 初始化
     */

    private void initParam() {

        handler = new Handler();


        mapBltScanResult = new HashMap<String, List<IbeaconRecord>>();

        // 设备SDK版本大于17（Build.VERSION_CODES.JELLY_BEAN_MR1）才支持BLE 4.0

        if
                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {

            bluetoothManager = (BluetoothManager) this.m_ctx

                    .getSystemService(Context.BLUETOOTH_SERVICE);

            mBluetoothAdapter = bluetoothManager.getAdapter();
            mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
        }

    }


    /**
     * 蓝牙扫描回调，获取扫描获得的蓝牙设备信息
     */

    // private BluetoothAdapter.LeScanCallback bltScanCallback = new BluetoothAdapter.LeScanCallback() {

    //   @Override

    // public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {

    /**

     * 参数列表描述

     * 1.device - BluetoothDevice类对象，

     *      通过该对象可以得到硬件地址（比如"00:11:22:AA:BB:CC"）、设备名称等信息

     * 2.rssi - 蓝牙设备场强值，小于0的int值

     * 3.scanRecord - 这里内容比较丰富，像UUID、Major、Minor都在这里

     */

    //  IbeaconRecord record = new IbeaconRecord();

    //   if
    //      (fromScanData(scanRecord, record)) {

    //  String address = device.getAddress();   // 获取Mac地址

    // String name = device.getName();         // 获取设备名称

    // String key = name + "_"
    //      + address;

    // record.rssi = rssi;     // 场强

    // if
    //    (mapBltScanResult.containsKey(key)) {

    //   mapBltScanResult.get(key).add(record);

    // } else {

    //   ArrayList<IbeaconRecord> list = new
    //   ArrayList<IbeaconRecord>();

    //   list.add(record);
//
    //  mapBltScanResult.put(key, list);

    //  }

    // }

    // }

    //  };


    /**
     * 开始扫描蓝牙设备
     */

    public void startScan() {

        mapBltScanResult.clear();


        if (mBluetoothAdapter != null
                && mBluetoothAdapter.isEnabled()) {

            // 5秒后停止扫描，毕竟扫描蓝牙设备比较费电，根据定位及时性自行调整该值
         /*   handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mBluetoothLeScanner.stopScan(scanCallback);

                }

            }, 2 * 1000);*/

            mBluetoothLeScanner.startScan(scanCallback); // 开始扫描
            mBluetoothLeScanner.stopScan(scanCallback);
        }

    }
    private ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult results) {
            super.onScanResult(callbackType, results);
            BluetoothDevice device = results.getDevice();
            if (!devices.contains(device)) {  //判断是否已经添加
                devices.add(device);//也可以添加devices.getName()到列表，这里省略            }
                // callbackType：回调类型
                // result：扫描的结果，不包括传统蓝牙        }
            }
        }
    };

    ;


    public Map<String, List<IbeaconRecord>> getMap() {
        return mapBltScanResult;
    }

    /**
     * 解析蓝牙信息数据流
     * <p>
     * 注：该段代码是从网上看到的，来源不详
     *
     * @param scanData
     * @param record
     * @return
     */

    private boolean fromScanData(byte[] scanData, IbeaconRecord record) {

        int startByte = 2;

        boolean patternFound = false;

        while (startByte <= 5) {

            if (((int) scanData[startByte + 2] & 0xff) == 0x02
                    && ((int) scanData[startByte + 3] & 0xff) == 0x15) {

                // yes! This is an iBeacon

                patternFound = true;

                break;

            } else if (((int) scanData[startByte] & 0xff) == 0x2d && ((int) scanData[startByte + 1] & 0xff) == 0x24
                    && ((int) scanData[startByte + 2] & 0xff) == 0xbf && ((int) scanData[startByte + 3] & 0xff) == 0x16) {

                return false;

            } else if (((int) scanData[startByte] & 0xff) == 0xad && ((int) scanData[startByte + 1] & 0xff) == 0x77
                    && ((int) scanData[startByte + 2] & 0xff) == 0x00 && ((int) scanData[startByte + 3] & 0xff) == 0xc6) {
                return false;

            }
            startByte++;
        }


        if (patternFound == false) {

            // This is not an iBeacon


            return false;

        }


        // 获得Major属性

        record.major = (scanData[startByte + 20] & 0xff) * 0x100 + (scanData[startByte + 21] & 0xff);


        // 获得Minor属性

        record.minor = (scanData[startByte + 22] & 0xff) * 0x100 + (scanData[startByte + 23] & 0xff);

        // record.tx_power = (int) scanData[startByte + 24]; // this one is

        // signed

        // record.accuracy = calculateAccuracy(record.tx_power, record.rssi);

        // if (record.accuracy < 0) {

        // return false;

        // }

        try {
            byte[] proximityUuidBytes = new byte[16];

            System.arraycopy(scanData, startByte + 4, proximityUuidBytes, 0, 16);

            String hexString = bytesToHex(proximityUuidBytes);

            StringBuilder sb = new StringBuilder();

            sb.append(hexString.substring(0, 8));

            sb.append("-");

            sb.append(hexString.substring(8, 12));

            sb.append("-");

            sb.append(hexString.substring(12, 16));

            sb.append("-");

            sb.append(hexString.substring(16, 20));

            sb.append("-");

            sb.append(hexString.substring(20, 32));

            // beacon.put("proximity_uuid", sb.toString());

            // 获得UUID属性

            record.uuid = sb.toString();

        } catch (Exception e) {

            e.printStackTrace();

        }


        return true;
    }


    private char[] hexArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};


    private String bytesToHex(byte[] bytes) {

        char[] hexChars = new char[bytes.length * 2];

        int v;

        for (int j = 0; j < bytes.length; j++) {

            v = bytes[j] & 0xFF;

            hexChars[j * 2] = hexArray[v >>> 4];

            hexChars[j * 2 + 1] = hexArray[v & 0x0F];

        }

        return new String(hexChars);

    }
}