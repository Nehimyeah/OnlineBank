package com.example.Util;

import com.example.domain.Account;

import java.math.BigDecimal;
import java.util.Random;

public class Util {

    public static String generateAccountNum(){
        Random rnd = new Random();
        int n = 1000000 + rnd.nextInt(9000000);
        return Integer.toString(n);
    }

    public static boolean validate(Account checkingAccount, BigDecimal existingAmount) {
        if (checkingAccount.getBalance().compareTo(BigDecimal.ZERO) < 0 || checkingAccount.getBalance().compareTo(existingAmount) < 0) {
            return false;
        }
        return true;
    }

}
