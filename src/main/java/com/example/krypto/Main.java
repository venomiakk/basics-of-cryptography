package com.example.krypto;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws UnsupportedEncodingException {
        aesAlgorithm AES = new aesAlgorithm();
        //int a = 32;
        //System.out.println((byte)a);

        //byte b02 = (byte) 0x02, b03 = 0x03;

        //System.out.println(-57 & 0xff);
        //System.out.println((byte)(199 & 0xff));
        int[][] tab = {
                {0,1,2,3},
                {4,5,6,7},
                {8,9,10,11},
                {12,13,14,15}
        };
        /*
         * 0  4  8  12
         * 1  5  9  13
         * 2  6  10 14
         * 3  7  11 15
         */
        //System.out.println(Arrays.deepToString(tab));
        //System.out.println(tab[3][2]);

    }

    public static void f1(int[] array){
        for (int i = 0; i < array.length; i++){
            array[i] = i+10;
        }
    }

    public static void test(){
        int[] arr = {1,2,3,4,5};
        f1(arr);
        //System.out.println(Arrays.toString(arr));
        byte[] b = {0xd, 0x1a};
        int[] i = {0x63, 0x1a};
        System.out.println(Arrays.toString(b));
        System.out.println(Arrays.toString(i));
    }
}
