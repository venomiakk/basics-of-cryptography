package com.example.krypto;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class dsaAlgorithm {

    static SecureRandom random = new SecureRandom();
    static Random ran = new Random();
    static MessageDigest digest;
    static BigInteger p,q,h,r,a, b, r1, s1, s2, pm1, sp,u1,u2,t;
    static int l;
    public dsaAlgorithm()
    {
        try{
            digest= MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex)
        {
            ex.printStackTrace();
        }
    }
    public static void signature()
    {
        generatR();
        calcR1();
        calcS1();
        calcS2("dupa");
    }

    public static void checkSignature()
    {
        System.out.println(s1.intValue());
        System.out.println(s2.intValue());
        calcSp();
        calcU1("dupa");
        calcU2();
        calcT();
        if(t.compareTo(s1)==0)
        {
            System.out.println("mamyTo");
        }

    }



    //region generowanieKlucza

    public static void generateP()
    {
        l = ran.nextInt(512,1024);
        BigInteger pom1, pom2;
        do
        {
            pom1 = BigInteger.probablePrime(l,random);
            pom2 = pom1.subtract(BigInteger.ONE);
            pom1 = pom1.subtract(pom2.remainder(q));
        } while (!pom1.isProbablePrime(2));
        p=pom1;
    }
    public static void generateQ()
    {
        q = BigInteger.probablePrime(160,random);
    }

    public static void generateH() {
        pm1=p.subtract(BigInteger.ONE);
        BigInteger pom1=new BigInteger(l-2,random);
        while(true)
            if (pom1.modPow(pm1.divide(q),p).compareTo(BigInteger.ONE)==1)break;
            else pom1=new BigInteger(l-2,random);
        h=pom1.modPow(pm1.divide(q),p); // finalne h

    }

    public static void generateAandB()
    {
        a=new BigInteger(160-2,random);
        while (a.compareTo(BigInteger.ZERO) != 1);
        b=h.modPow(a,p);
    }


    public static void generatR()
    {
        r = new BigInteger(160-2,ran);
    }

    public static void calcR1()
    {
        r1=r.modInverse(q);
    }

    public static void calcS1()
    {
        s1 = h.modPow(r,p).mod(q);
    }

    public static void calcS2(String tekst)
    {
        digest.update(tekst.getBytes());
        BigInteger hash=new BigInteger(1, digest.digest());
        BigInteger pom=hash.add(a.multiply(s1)); //(f(m)+as1)
        s2 = r1.multiply(pom).mod(q);//r1(f(m)+as1)%q
    }

    //endregion

    //region sprawdzanieKlucza
    public static void calcSp()
    {
        sp = s2.modInverse(q);
    }

    public static void calcU1(String tekst)
    {
        digest.update(tekst.getBytes());
        BigInteger hash=new BigInteger(1, digest.digest());
        u1 = hash.multiply(sp).mod(q);
    }

    public static void calcU2()
    {
        u2 = s1.multiply(sp).mod(q);
    }

    public static void calcT()
    {
        t =h.modPow(u1, p).multiply(b.modPow(u2, p)).mod(p).mod(q);
    }
    //endregion
}
