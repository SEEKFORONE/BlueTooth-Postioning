package com.example.hesiliang.myapplication;

import android.app.Application;

public class Message extends Application {
    private String rssiForMap1[][];//存储所有蓝牙数据
    private String rssiForMap[][] = new String[4][5];//存储用于定位的蓝牙，第一列名称，第二列距离，第三列X，第四列Y，第五列Z

    public String[][] getRssiForMap() {
        return rssiForMap;
    }

    public String[][] getRssiForMap1() {
        return rssiForMap1;
    }

    public void setRssiForMap1(String array[][]) {
        rssiForMap1 = new String[array.length][5];
        for (int i = 0; i < array.length; i++) {
            rssiForMap1[i][0] = array[i][0];
            rssiForMap1[i][1] = array[i][1];
        }

    }

    public boolean isMapfull() {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 5; j++) {
                if (rssiForMap[i][j] == null) {
                    count++;
                }
            }
        }
        if (count > 0) {
            return false;
        } else return true;
    }//四个定位器是否都被设置

    public void clear() {
        if (rssiForMap1 != null) {
            for (int i = 0; i < rssiForMap1.length; i++) {
                rssiForMap1[i][0] = null;
                rssiForMap1[i][1] = null;
                rssiForMap1[i][2] = null;
                rssiForMap1[i][3] = null;
                rssiForMap1[i][4] = null;
            }
        }
        if (rssiForMap != null) {
            for (int i = 0; i < 4; i++) {
                rssiForMap[i][0] = null;
                rssiForMap[i][1] = null;
                rssiForMap[i][2] = null;
                rssiForMap[i][3] = null;
                rssiForMap[i][4] = null;
            }
        }
    }//清空数组

    public int ifexistLocator(String key) {
        for (int i = 0; i < 4; i++) {
            if (rssiForMap[i][0] != null && rssiForMap[i][0].equals(key)) {
                return i;
            }
            return 5;
        }
        return 5;
    }//判断用于设置定位器的蓝牙是否已存在与数组中，并占用了哪个定位器

    public boolean addLocation(String key, int X, int Y, int Z, int num) {
        for (int i = 0; i < rssiForMap1.length; i++) {
            if (rssiForMap1[i][0] != null && rssiForMap1[i][0].equals(key)) {
                rssiForMap[num][0] = key;
                rssiForMap[num][1] = rssiForMap1[i][1];
                rssiForMap[num][2] = Integer.toString(X);
                rssiForMap[num][3] = Integer.toString(Y);
                rssiForMap[num][4] = Integer.toString(Z);
                return true;
            }
        }
        return false;
    }//easy，设置定位器

    public float[][] requestServer()

    {
        float location[][] = new float[3][1];
        float matrixarray1[][] = new float[3][1];
        float matrixarray2[][] = new float[3][3];
        float matrixarray3[][] = new float[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrixarray2[i][j] = 2 * (Integer.parseInt(rssiForMap[0][2 + j]) - Integer.parseInt(rssiForMap[i + 1][2 + j]));
            }
        }
        matrixarray3 = getN(matrixarray2);
        double R1 = (Double.parseDouble(rssiForMap[0][1]));
        int X1 = (Integer.parseInt(rssiForMap[0][2]));
        int Y1 = (Integer.parseInt(rssiForMap[0][3]));
        int Z1 = (Integer.parseInt(rssiForMap[0][4]));
        double R2 = (Double.parseDouble(rssiForMap[1][1]));
        int X2 = (Integer.parseInt(rssiForMap[1][2]));
        int Y2 = (Integer.parseInt(rssiForMap[1][3]));
        int Z2 = (Integer.parseInt(rssiForMap[1][4]));
        double R3 = (Double.parseDouble(rssiForMap[2][1]));
        int X3 = (Integer.parseInt(rssiForMap[2][2]));
        int Y3 = (Integer.parseInt(rssiForMap[2][3]));
        int Z3 = (Integer.parseInt(rssiForMap[2][4]));
        double R4 = (Double.parseDouble(rssiForMap[3][1]));
        int X4 = (Integer.parseInt(rssiForMap[3][2]));
        int Y4 = (Integer.parseInt(rssiForMap[3][3]));
        int Z4 = (Integer.parseInt(rssiForMap[3][4]));
        matrixarray1[0][0] = (float) (R2 * R2) - (float) (R1 * R1) - (X2 ^ 2 - X1 ^ 2 + Y2 ^ 2 - Y1 ^ 2 + Z2 ^ 2 - Z1 ^ 2);
        matrixarray1[1][0] = (float) (R3 * R3) - (float) (R1 * R1) - (X3 ^ 2 - X1 ^ 2 + Y3 ^ 2 - Y1 ^ 2 + Z3 ^ 2 - Z1 ^ 2);
        matrixarray1[2][0] = (float) (R4 * R4) - (float) (R1 * R1) - (X4 ^ 2 - X1 ^ 2 + Y4 ^ 2 - Y1 ^ 2 + Z4 ^ 2 - Z1 ^ 2);
        location = matrix(matrixarray3, matrixarray1);

        return location;
    }//这里是整个求位置的算法

    /**
     * 1
     * 求解代数余子式 输入：原始矩阵+行+列 现实中真正的行和列数目
     */
    public static float[][] getDY(float[][] data, int h, int v) {
        int H = data.length;
        int V = data[0].length;
        float[][] newData = new float[H - 1][V - 1];

        for (int i = 0; i < newData.length; i++) {

            if (i < h - 1) {
                for (int j = 0; j < newData[i].length; j++) {
                    if (j < v - 1) {
                        newData[i][j] = data[i][j];
                    } else {
                        newData[i][j] = data[i][j + 1];
                    }
                }
            } else {
                for (int j = 0; j < newData[i].length; j++) {
                    if (j < v - 1) {
                        newData[i][j] = data[i + 1][j];
                    } else {
                        newData[i][j] = data[i + 1][j + 1];
                    }
                }

            }
        }
        // System.out.println("---------------------代数余子式测试.---------------------------------");
        // for(int i=0;i<newData.length;i++){
        // for(int j=0;j<newData[i].length;j++){
        // System.out.print("newData["+i+"]"+"["+j+"]="+newData[i][j]+"   ");
        // }
        //
        // System.out.println();
        // }

        return newData;
    }

    /**
     * 求解行列式的模----------->最终的总结归纳
     *
     * @param data
     * @return
     */
    public static float getHL(float[][] data) {

        // 终止条件
        if (data.length == 2) {
            return data[0][0] * data[1][1] - data[0][1] * data[1][0];
        }

        float total = 0;
        // 根据data 得到行列式的行数和列数
        int num = data.length;
        // 创建一个大小为num 的数组存放对应的展开行中元素求的的值
        float[] nums = new float[num];

        for (int i = 0; i < num; i++) {
            if (i % 2 == 0) {
                nums[i] = data[0][i] * getHL(getDY(data, 1, i + 1));
            } else {
                nums[i] = -data[0][i] * getHL(getDY(data, 1, i + 1));
            }
        }
        for (int i = 0; i < num; i++) {
            total += nums[i];
        }
        System.out.println("total=" + total);
        return total;
    }

    /**
     * 取得转置矩阵
     *
     * @param A
     * @return
     */
    public static float[][] getA_T(float[][] A) {
        int h = A.length;
        int v = A[0].length;
        // 创建和A行和列相反的转置矩阵
        float[][] A_T = new float[v][h];
        // 根据A取得转置矩阵A_T
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < h; j++) {
                A_T[j][i] = A[i][j];
            }
        }
        System.out.println("取得转置矩阵  wanbi........");
        return A_T;
    }

    /**
     * 求解逆矩阵------>z最后的总结和归纳
     *
     * @param data
     * @return
     */
    public static float[][] getN(float[][] data) {
        // 先是求出行列式的模|data|
        float A = getHL(data);
        // 创建一个等容量的逆矩阵
        float[][] newData = new float[data.length][data.length];

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length; j++) {
                float num;
                if ((i + j) % 2 == 0) {
                    num = getHL(getDY(data, i + 1, j + 1));
                } else {
                    num = -getHL(getDY(data, i + 1, j + 1));
                }

                newData[i][j] = num / A;
            }
        }

        // 转置 代数余子式转制
        newData = getA_T(newData);
        // 打印
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length; j++) {
                System.out.print("newData[" + i + "][" + j + "]= "
                        + newData[i][j] + "   ");
            }

            System.out.println();
        }

        return newData;
    }

    public static float[][] matrix(float a[][], float b[][]) {
        //当a的列数与矩阵b的行数不相等时，不能进行点乘，返回null
        if (a[0].length != b.length)
            return null;
        //c矩阵的行数y，与列数x
        int y = a.length;
        int x = b[0].length;
        float c[][] = new float[y][x];
        for (int i = 0; i < y; i++)
            for (int j = 0; j < x; j++)
                //c矩阵的第i行第j列所对应的数值，等于a矩阵的第i行分别乘以b矩阵的第j列之和
                for (int k = 0; k < b.length; k++)
                    c[i][j] += a[i][k] * b[k][j];
        return c;
    }

}
