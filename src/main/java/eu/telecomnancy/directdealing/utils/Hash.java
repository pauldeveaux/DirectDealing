package eu.telecomnancy.directdealing.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Hash {

    public static String hash(String msg)  {
        SecureRandom random = new SecureRandom();
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hashedPassword = md.digest(msg.getBytes(StandardCharsets.UTF_8));
            String s = new String(hashedPassword, StandardCharsets.UTF_8);
            return s;
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
