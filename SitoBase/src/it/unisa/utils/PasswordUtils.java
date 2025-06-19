package it.unisa.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtils {

    // Funzione per creare un hash SHA-256 della password
    public static String toHash(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(password.getBytes());
        StringBuilder hexString = new StringBuilder();

        // Converte i byte dell'hash in una stringa esadecimale
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        
        return hexString.toString();
    }
 // Metodo per verificare la password hashata
    public static boolean checkPassword(String password, String hashedPassword) throws NoSuchAlgorithmException {
        String hashedInputPassword = toHash(password);
        return hashedInputPassword.equals(hashedPassword);
    }
}
