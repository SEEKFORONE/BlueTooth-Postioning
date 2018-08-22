package com.example.hesiliang.myapplication;


import android.content.Context;

import java.util.List;
import java.util.Map;

public class getMessage {
    private Context ctx;
    private Map<String, List<IbeaconRecord>> seemap;
    private static final double A_Value = 50;
    /**
     * A - 发射端和接收端相隔1米时的信号强度
     */
    private static final double n_Value = 2.5;
    /**
     * n - 环境衰减因子
     */

    private String[][] rssiForMap;


    public getMessage(Context context) {
        super();
        BLEPositioning lePositioning = new BLEPositioning(context);
        seemap = lePositioning.getMap();
        rssiForMap = new String[seemap.size()][2];
      /*  rssiForMap = new String[][]{
                {"lalala","4"},
                {"hehehe","6"},
                {"kakaka","7"},
                {"nenene","5"},
        };*/
        getRssi();
    }


    /**
     * 根据Rssi获得返回的距离,返回数据单位为m
     *
     * @param rssi
     * @return
     */
    public static double getDistance(int rssi) {
        int iRssi = Math.abs(rssi);
        double power = (iRssi - A_Value) / (10 * n_Value);
        return Math.pow(10, power);
    }

    public void getRssi() {
        int j = 0;
        for (String s : seemap.keySet()) {
            List<IbeaconRecord> recordlist = seemap.get(s);
            int k = 0;
            for (int i = 0; i < recordlist.size(); i++) {
                IbeaconRecord record = recordlist.get(i);
                k += record.rssi;
            }
            int c = k / recordlist.size();
            rssiForMap[j][0] = s;
            rssiForMap[j][1] = Double.toString(getDistance(c));
            j++;
        }
    }

    public String[][] getRssiForMap() {
        return rssiForMap;
    }
}
