package com.udacity.capstone.utils;

import android.util.Log;

import com.udacity.capstone.data.Constants;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


public class Utils {



    public static Map<String, String> getQueryMap() {
        long timeStamp = System.currentTimeMillis();

        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("apikey", Constants.PUBLIC_KEY);
        queryMap.put("hash", Utils.getMD5(timeStamp));
        queryMap.put("ts", String.valueOf(timeStamp));

        return queryMap;
    }

    private static String getMD5(long timeStamp) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest((timeStamp + Constants.PRIVATE_KEY + Constants.PUBLIC_KEY).getBytes());

            BigInteger number = new BigInteger(1, messageDigest);
            StringBuilder md5 = new StringBuilder(number.toString(16));
            while (md5.length() < 32) md5.insert(0, 0);
            return md5.toString();

        } catch (NoSuchAlgorithmException e) {
            Log.e("DataManager", "Error hashing required parameters: " + e.getMessage());
            return "";
        }
    }
}
