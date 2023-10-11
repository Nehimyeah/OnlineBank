package com.example.utils;

import java.util.Random;

public class Util {
    public static String generateAccountNum(){
        Random rnd = new Random();
        int n = 1000000 + rnd.nextInt(9000000);
        return Integer.toString(n);
    }
}
