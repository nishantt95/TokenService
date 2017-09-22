package com.cclogic.security;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityUtils {

    /**
     * Static method to generate hash of password in hex format (160 bytes)
     *
     * @param input
     * @param salt
     * @param iterations
     * @return hash of password in hex format
     *
     * password (input) -> charArray ->PBEFunc()
     */
    public static String generateHash(String input, String salt, Integer iterations) {
        String passwordHash = null;
        if (null == input) {
            return null;
        }
        try {
            char[] chars = input.toCharArray();
            byte[] saltBytes = salt.getBytes();
			/* Using Password Based Key Derivative Function mixed with Hash Based Message Authentication Code */
            PBEKeySpec spec = new PBEKeySpec(chars, saltBytes, iterations, 64 * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = skf.generateSecret(spec).getEncoded();
            passwordHash = toHex(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("Could not generate hashed password due to: ", e);
        }
        return passwordHash;
    }

    /**
     * Static method to validate the password with the stored hashed password
     *
     * @param originalPassword
     * @param storedPassword
     * @param storedSalt
     * @param storedIterations
     * @return true - if given password generates same hash as of stored password
     *
     * password -> charArray -> PEBFunc
     */
    public static boolean validatePassword(String originalPassword, String storedPassword, String storedSalt, Integer storedIterations) {
        int diff = 1;
        try {
            byte[] salt = fromHex(storedSalt);
            byte[] hash = fromHex(storedPassword);

            System.out.println("Validation\nSalt : "+salt+"\nHash : "+hash);

            PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, storedIterations, hash.length * 8);

            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            byte[] testHash = skf.generateSecret(spec).getEncoded();

            System.out.println("storedPass : "+storedPassword+"\n"+"test hash : "+testHash);

            diff = hash.length ^ testHash.length;

            for (int i = 0; i < hash.length && i < testHash.length; i++) {
                diff |= hash[i] ^ testHash[i];
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("Error in validating hashed password due to: ", e);
        }
        return diff == 0;

    }

    /**
     * Static method to generate strong random salt for SHA1
     *
     * @return salt
     */
    public static String getSalt() {
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            byte[] salt = new byte[16];
            sr.nextBytes(salt);
            return salt.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("Error while generating salt for hashing due to: ", e);
        }
        return "";
    }

    /**
     * Convert an byte array to hexadecimal form
     *
     * @param array
     * @return hexadecimal form of given array
     */
    public static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    /**
     * Generate the byte array from the hexadecimal value
     *
     * @param hex
     * @return byte array of given hexadecimal
     */
    private static byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

}
