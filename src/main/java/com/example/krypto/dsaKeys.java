package com.example.krypto;

import java.io.Serializable;
import java.math.BigInteger;

public class dsaKeys implements Serializable {
    public BigInteger q, h, a, b, modp;

    public dsaKeys(BigInteger q, BigInteger h, BigInteger a, BigInteger b, BigInteger modp){
        this.q = q;
        this.h = h;
        this.a = a;
        this.b = b;
        this.modp = modp;
    }
}
