package com.example.krypto;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String messageStr = "abcdef";
        byte[] message = messageStr.getBytes();

        /*
         * 128-bit key
         * Hope this is right :)
         */
        byte[][] key = {
                {0x0, 0x1, 0x2, 0x3},
                {0x4, 0x5, 0x6, 0x7},
                {0x8, 0x9, 0xa, 0xb},
                {0xc, 0xd, 0xe, 0xf}
        };

        //System.out.println(key[0][1]);

        aesAlgorithm AES = new aesAlgorithm(key, 10);
        //AES.aesEncryption(message);
        AES.aesDecryption(AES.aesEncryption(message));

    }

}
