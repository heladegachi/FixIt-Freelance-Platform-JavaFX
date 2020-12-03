/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Utility.*;
import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class Checksum {

    String Algorithm = "SHA-1"; // or MD5 etc.

    public static String createChecksum(String filename) throws
            Exception {
        try {
            String s = filename;
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            Random rand = new Random();
            byte[] salt = new byte[12];
            rand.nextBytes(salt);
            md5.update(salt);
            md5.update(s.getBytes(), 0, s.length());
            String signature = new BigInteger(1, md5.digest()).toString(16);
            //System.out.println("Signature: " + signature);
            return signature;

        } catch (final NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String get_SHA_512_SecurePassword(String passwordToHash, String salt) throws UnsupportedEncodingException {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes("UTF-8"));
            byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
    
    public static String genererCode()throws
            Exception {
    
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String code=sdf.format(cal.getTime());
        return createChecksum(code);
    }
    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
              sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
           }
            return sb.toString();
        } 
        catch (java.security.NoSuchAlgorithmException e) {
            
        }
            return null;
    }
    
}
