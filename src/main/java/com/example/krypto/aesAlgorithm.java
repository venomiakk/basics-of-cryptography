package com.example.krypto;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class aesAlgorithm {
    /*
     *
     *   - Generowanie kluczy nie powinno odbywać się w samymy algorytmie
     *       - Do odszyfrowania należy najpierw wygenerować klucze a potem użyć ich w odwrotnej kolejności
     */
    private final int[] sbox = {
            0x63, 0x7C, 0x77, 0x7B, 0xF2, 0x6B, 0x6F, 0xC5, 0x30, 0x01, 0x67, 0x2B, 0xFE, 0xD7, 0xAB, 0x76,
            0xCA, 0x82, 0xC9, 0x7D, 0xFA, 0x59, 0x47, 0xF0, 0xAD, 0xD4, 0xA2, 0xAF, 0x9C, 0xA4, 0x72, 0xC0,
            0xB7, 0xFD, 0x93, 0x26, 0x36, 0x3F, 0xF7, 0xCC, 0x34, 0xA5, 0xE5, 0xF1, 0x71, 0xD8, 0x31, 0x15,
            0x04, 0xC7, 0x23, 0xC3, 0x18, 0x96, 0x05, 0x9A, 0x07, 0x12, 0x80, 0xE2, 0xEB, 0x27, 0xB2, 0x75,
            0x09, 0x83, 0x2C, 0x1A, 0x1B, 0x6E, 0x5A, 0xA0, 0x52, 0x3B, 0xD6, 0xB3, 0x29, 0xE3, 0x2F, 0x84,
            0x53, 0xD1, 0x00, 0xED, 0x20, 0xFC, 0xB1, 0x5B, 0x6A, 0xCB, 0xBE, 0x39, 0x4A, 0x4C, 0x58, 0xCF,
            0xD0, 0xEF, 0xAA, 0xFB, 0x43, 0x4D, 0x33, 0x85, 0x45, 0xF9, 0x02, 0x7F, 0x50, 0x3C, 0x9F, 0xA8,
            0x51, 0xA3, 0x40, 0x8F, 0x92, 0x9D, 0x38, 0xF5, 0xBC, 0xB6, 0xDA, 0x21, 0x10, 0xFF, 0xF3, 0xD2,
            0xCD, 0x0C, 0x13, 0xEC, 0x5F, 0x97, 0x44, 0x17, 0xC4, 0xA7, 0x7E, 0x3D, 0x64, 0x5D, 0x19, 0x73,
            0x60, 0x81, 0x4F, 0xDC, 0x22, 0x2A, 0x90, 0x88, 0x46, 0xEE, 0xB8, 0x14, 0xDE, 0x5E, 0x0B, 0xDB,
            0xE0, 0x32, 0x3A, 0x0A, 0x49, 0x06, 0x24, 0x5C, 0xC2, 0xD3, 0xAC, 0x62, 0x91, 0x95, 0xE4, 0x79,
            0xE7, 0xC8, 0x37, 0x6D, 0x8D, 0xD5, 0x4E, 0xA9, 0x6C, 0x56, 0xF4, 0xEA, 0x65, 0x7A, 0xAE, 0x08,
            0xBA, 0x78, 0x25, 0x2E, 0x1C, 0xA6, 0xB4, 0xC6, 0xE8, 0xDD, 0x74, 0x1F, 0x4B, 0xBD, 0x8B, 0x8A,
            0x70, 0x3E, 0xB5, 0x66, 0x48, 0x03, 0xF6, 0x0E, 0x61, 0x35, 0x57, 0xB9, 0x86, 0xC1, 0x1D, 0x9E,
            0xE1, 0xF8, 0x98, 0x11, 0x69, 0xD9, 0x8E, 0x94, 0x9B, 0x1E, 0x87, 0xE9, 0xCE, 0x55, 0x28, 0xDF,
            0x8C, 0xA1, 0x89, 0x0D, 0xBF, 0xE6, 0x42, 0x68, 0x41, 0x99, 0x2D, 0x0F, 0xB0, 0x54, 0xBB, 0x16
    };

    private final int[] invSbox = {
            0x52, 0x09, 0x6A, 0xD5, 0x30, 0x36, 0xA5, 0x38, 0xBF, 0x40, 0xA3, 0x9E, 0x81, 0xF3, 0xD7, 0xFB,
            0x7C, 0xE3, 0x39, 0x82, 0x9B, 0x2F, 0xFF, 0x87, 0x34, 0x8E, 0x43, 0x44, 0xC4, 0xDE, 0xE9, 0xCB,
            0x54, 0x7B, 0x94, 0x32, 0xA6, 0xC2, 0x23, 0x3D, 0xEE, 0x4C, 0x95, 0x0B, 0x42, 0xFA, 0xC3, 0x4E,
            0x08, 0x2E, 0xA1, 0x66, 0x28, 0xD9, 0x24, 0xB2, 0x76, 0x5B, 0xA2, 0x49, 0x6D, 0x8B, 0xD1, 0x25,
            0x72, 0xF8, 0xF6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xD4, 0xA4, 0x5C, 0xCC, 0x5D, 0x65, 0xB6, 0x92,
            0x6C, 0x70, 0x48, 0x50, 0xFD, 0xED, 0xB9, 0xDA, 0x5E, 0x15, 0x46, 0x57, 0xA7, 0x8D, 0x9D, 0x84,
            0x90, 0xD8, 0xAB, 0x00, 0x8C, 0xBC, 0xD3, 0x0A, 0xF7, 0xE4, 0x58, 0x05, 0xB8, 0xB3, 0x45, 0x06,
            0xD0, 0x2C, 0x1E, 0x8F, 0xCA, 0x3F, 0x0F, 0x02, 0xC1, 0xAF, 0xBD, 0x03, 0x01, 0x13, 0x8A, 0x6B,
            0x3A, 0x91, 0x11, 0x41, 0x4F, 0x67, 0xDC, 0xEA, 0x97, 0xF2, 0xCF, 0xCE, 0xF0, 0xB4, 0xE6, 0x73,
            0x96, 0xAC, 0x74, 0x22, 0xE7, 0xAD, 0x35, 0x85, 0xE2, 0xF9, 0x37, 0xE8, 0x1C, 0x75, 0xDF, 0x6E,
            0x47, 0xF1, 0x1A, 0x71, 0x1D, 0x29, 0xC5, 0x89, 0x6F, 0xB7, 0x62, 0x0E, 0xAA, 0x18, 0xBE, 0x1B,
            0xFC, 0x56, 0x3E, 0x4B, 0xC6, 0xD2, 0x79, 0x20, 0x9A, 0xDB, 0xC0, 0xFE, 0x78, 0xCD, 0x5A, 0xF4,
            0x1F, 0xDD, 0xA8, 0x33, 0x88, 0x07, 0xC7, 0x31, 0xB1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xEC, 0x5F,
            0x60, 0x51, 0x7F, 0xA9, 0x19, 0xB5, 0x4A, 0x0D, 0x2D, 0xE5, 0x7A, 0x9F, 0x93, 0xC9, 0x9C, 0xEF,
            0xA0, 0xE0, 0x3B, 0x4D, 0xAE, 0x2A, 0xF5, 0xB0, 0xC8, 0xEB, 0xBB, 0x3C, 0x83, 0x53, 0x99, 0x61,
            0x17, 0x2B, 0x04, 0x7E, 0xBA, 0x77, 0xD6, 0x26, 0xE1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0C, 0x7D
    };



    private final int[] rcon = {
            0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a,
            0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39,
            0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a,
            0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8,
            0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef,
            0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc,
            0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b,
            0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3,
            0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94,
            0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20,
            0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35,
            0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f,
            0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04,
            0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63,
            0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd,
            0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d
    };




    private byte[][][] roundKeys;
    private int numberOfRounds;


    public aesAlgorithm(byte[][] key, int roundsNum) {
        numberOfRounds = roundsNum;

        roundKeys = new byte[numberOfRounds+1][4][4];
        byte[][] tmpKey = keyExpansion(key, 1);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                roundKeys[0][i][j] = tmpKey[i][j];
            }
        }

        for (int i = 1; i <= numberOfRounds ; i++) {
            tmpKey = keyExpansion(roundKeys[i-1], i+1);
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    roundKeys[i][j][k] = tmpKey[j][k];
                }
            }
        }
    }


    public byte[] aesEncryption(byte[] messageParam) {
        byte[] interimMessage = messageParam.clone();
        int originalMsgLen = messageParam.length;

        //Fit length of the message
        int len = messageParam.length / 16;
        int desiredLen;
        if (len == 0) {
            desiredLen = 16;
        } else if ((messageParam.length % 16) != 0) {
            desiredLen = (len + 1) * 16;
        } else {
            desiredLen = len * 16;
        }



        //Fill empty spaces with '0'
        int numOfZeros = 0;
        byte[] formattedMessage = new byte[desiredLen];
        for (int i = 0; i < desiredLen; i++) {
            if (i < interimMessage.length) {
                formattedMessage[i] = interimMessage[i];
            } else {
                formattedMessage[i] = 0;
                numOfZeros++;
            }

        }

        //Divide to blocks 4x4
        byte[] cipher = new byte[desiredLen+1];
        int msgIndex = 0;
        int index = 0;
        byte[][] block = new byte[4][4];
        while (index < desiredLen) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    block[i][j] = formattedMessage[index];
                    index++;
                }
            }
            System.out.println("Blok do zaszyfrowania: " + Arrays.deepToString(block));

            byte[][] encryptedBlock = encrypt(block);

            for (int i = 0; i < encryptedBlock.length; i++) {
                for (int j = 0; j < encryptedBlock[0].length; j++) {
                    cipher[msgIndex++] = encryptedBlock[i][j];
                }
            }
        }
        cipher[desiredLen] = (byte) numOfZeros;
        System.out.println("Zaszyfrowana wiadomosc:");
        System.out.println(Arrays.toString(cipher));
        return cipher;
    }

    private byte[][] encrypt(byte[][] blockParam) {
        /*
         * Steps:
         *   1. keyExpansion - generating numberOfRounds+1 subKeys (128-bit)
         *   2. addRoundKey - block XOR generatedKey
         *   3. 9 rounds: (for 128-bit key)
         *       1. subBytes - changing bytes from block with bytes from sbox
         *       2. shiftRows(block[4x4]) - 1st row stays, next rows: rotate_left(ROL) by row_number-1
         *                                   (ex.: for 2nd row ROL by 1, 3rd -> ROL by 2 ...)
         *       3. mixColumns - multiply block by certain matrix
         *       4. addRoundKey - whole block XOR next subKey
         *   4. last round: (10th for 128-bit key)
         *       1. subBytes
         *       2. shiftRows
         *       3. addRoundKey
         */

        byte[][] block = new byte[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                block[i][j] = blockParam[i][j];
            }
        }

        //INITIAL ROUND
        addRoundKey(block, roundKeys[0]);

        for (int i = 1; i < numberOfRounds; i++) {
            subBytes(block);
            shiftRows(block);
            block = mixColumns2(block);
            addRoundKey(block, roundKeys[i]);
        }

        //LAST ROUND
        subBytes(block);
        shiftRows(block);
        addRoundKey(block, roundKeys[numberOfRounds]);

        return block;
    }


    private byte[][] keyExpansion(byte[][] roundKey, int round) {
        byte[][] currentKey = new byte[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                currentKey[i][j] = roundKey[i][j];
            }
        }

        byte temp = currentKey[3][0];
        currentKey[3][0] = currentKey[3][1];
        currentKey[3][1] = currentKey[3][2];
        currentKey[3][2] = currentKey[3][3];
        currentKey[3][3] = temp;

        for (int i = 0; i < 4; i++) {
            currentKey[3][i] = (byte) sbox[(currentKey[3][i] & 0xff)];
        }

        currentKey[0][0] ^= (byte) rcon[round];
        for (int i = 0; i < 4; i++) {
            currentKey[0][i] ^= currentKey[3][i];
        }


        for (int i = 1; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                currentKey[i][j] ^= currentKey[i-1][j];
            }
        }
        return currentKey;
    }

    private void addRoundKey(byte[][] block, byte[][] roundKey) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                block[i][j] ^= roundKey[i][j];
            }
        }
    }

    private void shiftRows(byte[][] block) {
        byte[][] interim = new byte[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                interim[i][j] = block[i][j];
            }
        }

        //2nd row:
        block[0][1] = interim[1][1];
        block[1][1] = interim[2][1];
        block[2][1] = interim[3][1];
        block[3][1] = interim[0][1];

        //3rd row
        block[0][2] = interim[2][2];
        block[1][2] = interim[3][2];
        block[2][2] = interim[0][2];
        block[3][2] = interim[1][2];

        //4th row
        block[0][3] = interim[3][3];
        block[1][3] = interim[0][3];
        block[2][3] = interim[1][3];
        block[3][3] = interim[2][3];
    }

    private void subBytes(byte[][] block) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                block[i][j] = (byte) sbox[block[i][j] & 0xff];
            }
        }
    }


    public  byte fMul(byte a, byte b)
    {
        byte aa = a, bb = b, r = 0, t;
        while (aa != 0)
        {
            if ((aa & 1) != 0)
                r = (byte) (r ^ bb);
            t = (byte) (bb & 0x20);
            bb = (byte) (bb << 1);
            if (t != 0)
                bb = (byte) (bb ^ 0x1b);
            aa = (byte) ((aa & 0xff) >> 1);
        }
        return r;
    }


    public byte[] aesDecryption(byte[] messageParam) {
        byte[] interimMessage = new byte[messageParam.length - 1];
        for (int i = 0; i <interimMessage.length; i++) {
            interimMessage[i] = messageParam[i];
        }

        int addedZeros = messageParam[messageParam.length - 1];

        System.out.println("Z dodanymi 0: " + Arrays.toString(interimMessage));

        byte[] plainText = new byte[interimMessage.length];
        int msgIndex = 0;
        int index = 0;
        byte[][] block = new byte[4][4];
        while (index < interimMessage.length) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    block[i][j] = interimMessage[index++];
                }
            }
            System.out.println("Blok do zdeszyfrowania: " + Arrays.deepToString(block));

            byte[][] decryptedBlock = decrypt(block);
            for (int i = 0; i < decryptedBlock.length; i++) {
                for (int j = 0; j < decryptedBlock[0].length; j++) {
                    plainText[msgIndex++] = decryptedBlock[i][j];
                }
            }

        }

        byte[] output = new byte[interimMessage.length-addedZeros];
        for (int i = 0; i < output.length; i++) {
            output[i] = plainText[i];
        }

        System.out.println("Zdeszyfrowana wiadomosc:");
        System.out.println(Arrays.toString(output));
        return output;
    }

    private byte[][] decrypt(byte[][] blockParam) {
        byte[][] block = new byte[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                block[i][j] = blockParam[i][j];
            }
        }

        //TODO: START OF DECRYPTION
        //INITIAL ROUND
        addRoundKey(block, roundKeys[numberOfRounds]);

        for (int i = numberOfRounds-1; i >= 1 ; i--) {
            inv_subBytes(block);
            inv_shiftRows(block);
            addRoundKey(block, roundKeys[i]);
            //inv_mixColumns(block);
            block = invMixColumns2(block);
        }

        //LAST ROUND
        inv_subBytes(block);
        inv_shiftRows(block);
        inv_addRoundKey(block, roundKeys[0]);

        return block;
    }

    private void inv_subBytes(byte[][] block){
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                block[i][j] = (byte) invSbox[block[i][j] & 0xff];
            }
        }
    }

    private void inv_shiftRows(byte[][] block){
        byte[][] interim = new byte[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                interim[i][j] = block[i][j];
            }
        }

        //2nd row:
        block[0][1] = interim[3][1];
        block[1][1] = interim[0][1];
        block[2][1] = interim[1][1];
        block[3][1] = interim[2][1];

        //3rd row
        block[0][2] = interim[2][2];
        block[1][2] = interim[3][2];
        block[2][2] = interim[0][2];
        block[3][2] = interim[1][2];

        //4th row
        block[0][3] = interim[1][3];
        block[1][3] = interim[2][3];
        block[2][3] = interim[3][3];
        block[3][3] = interim[0][3];
    }
    private void inv_addRoundKey(byte[][] block, byte[][] roundKey){
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                block[i][j] ^= roundKey[i][j];
            }
        }
    }

    private  byte[][] invMixColumns2(byte[][] s)
    {
        int[] sp = new int[4];
        byte b02 = (byte)0x0e, b03 = (byte)0x0b, b04 = (byte)0x0d, b05 = (byte)0x09;
        for (int c = 0; c < 4; c++)
        {
            sp[0] = fMul(b02, s[0][c]) ^ fMul(b03, s[1][c]) ^ fMul(b04,s[2][c])  ^ fMul(b05,s[3][c]);
            sp[1] = fMul(b05, s[0][c]) ^ fMul(b02, s[1][c]) ^ fMul(b03,s[2][c])  ^ fMul(b04,s[3][c]);
            sp[2] = fMul(b04, s[0][c]) ^ fMul(b05, s[1][c]) ^ fMul(b02,s[2][c])  ^ fMul(b03,s[3][c]);
            sp[3] = fMul(b03, s[0][c]) ^ fMul(b04, s[1][c]) ^ fMul(b05,s[2][c])  ^ fMul(b02,s[3][c]);
            for (int i = 0; i < 4; i++) s[i][c] = (byte)(sp[i]);
        }
        return s;
    }

    private  byte[][] mixColumns2(byte[][] s)
    {
        int[] sp = new int[4];
        byte b02 = (byte)0x02, b03 = (byte)0x03;
        for (int c = 0; c < 4; c++)
        {
            sp[0] = fMul(b02, s[0][c]) ^ fMul(b03, s[1][c]) ^ s[2][c]  ^ s[3][c];
            sp[1] = s[0][c]  ^ fMul(b02, s[1][c]) ^ fMul(b03, s[2][c]) ^ s[3][c];
            sp[2] = s[0][c]  ^ s[1][c]  ^ fMul(b02, s[2][c]) ^ fMul(b03, s[3][c]);
            sp[3] = fMul(b03, s[0][c]) ^ s[1][c]  ^ s[2][c]  ^ fMul(b02, s[3][c]);
            for (int i = 0; i < 4; i++) s[i][c] = (byte)(sp[i]);
        }
        return s;
    }
}
