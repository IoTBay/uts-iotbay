/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * This class simplifies the hashing of a plaintext string into a hex-encoded hashed version of that string.
 * 
 * @author Rhys Hanrahan 11000801
 * @since 2020-05-25
 */
public class Hash {
    
    /**
     * A SHA 256 hash implementation with Hex encoding.
     * Based on: https://www.baeldung.com/sha-256-hashing-java
     * 
     * @param plainText The plaintex string to hash.
     * @return A SHA256 hash, encoded as a hexadecimal string.
     */
    public static String SHA256(String plainText)
    {
        try 
        {
            //Convert plaintext String into a byte array containing the hash.
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(plainText.getBytes(StandardCharsets.UTF_8));
            //Convert bytes to hexadecimal String and return.
            return Hash.bytesToHex(encodedhash);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to convert string to SHA256 Hash", e);
            return "";
        }
    }
    
    /**
     * This function takes a byte array and returns those bytes in a hexadecimal encoded string.
     * 
     * @param hash The byte array to encode.
     * @return A string with the hexadecimal value.
     */
    private static String bytesToHex(byte[] hash) 
    {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
        String hex = Integer.toHexString(0xff & hash[i]);
        if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
