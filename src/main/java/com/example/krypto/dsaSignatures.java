package com.example.krypto;

import java.io.Serializable;
import java.math.BigInteger;

public class dsaSignatures implements Serializable {
    public BigInteger s1, s2;

    public dsaSignatures(BigInteger s1, BigInteger s2){
        this.s1 = s1;
        this.s2 =s2;
    }
}
