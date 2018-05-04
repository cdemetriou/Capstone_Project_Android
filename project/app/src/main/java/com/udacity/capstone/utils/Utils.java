package com.udacity.capstone.utils;

import android.support.annotation.Nullable;
import android.util.Log;

import com.udacity.capstone.data.Constants;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by christosdemetriou on 20/04/2018.
 */

public class Utils {

    public static String getMD5(long timeStamp) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest((timeStamp + Constants.PRIVATE_KEY + Constants.PUBLIC_KEY).getBytes());

            BigInteger number = new BigInteger(1, messageDigest);
            String md5 = number.toString(16);
            while (md5.length() < 32) md5 = 0 + md5;
            return md5;

        } catch (NoSuchAlgorithmException e) {
            Log.e("DataManager", "Error hashing required parameters: " + e.getMessage());
            return "";
        }
    }

    public static Map<String, String> getQueryMap() {
        long timeStamp = System.currentTimeMillis();

        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("apikey", Constants.PUBLIC_KEY);
        queryMap.put("hash", Utils.getMD5(timeStamp));
        queryMap.put("ts", String.valueOf(timeStamp));

        return queryMap;
    }
}
