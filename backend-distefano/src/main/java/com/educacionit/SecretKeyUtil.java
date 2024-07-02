package com.educacionit;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

import io.jsonwebtoken.io.Decoders;

public class SecretKeyUtil {
    public static void main(String[] args) {
    	
        // Generar un array de bytes seguro
        byte[] keyBytes = new byte[32]; // 256 bits
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(keyBytes);

        // Codificar los bytes en Base64
        String secretKey = Base64.getEncoder().encodeToString(keyBytes);
        
        // Imprimir la clave secreta
        System.out.println("SECRET_KEY: " + secretKey);
        
        //String secretKey = "TU_SECRET_KEY_GENERADA";
        keyBytes = Decoders.BASE64.decode(secretKey);
        
        // Ahora keyBytes contiene los bytes originales de la clave
        System.out.println("Decoded key bytes: " + Arrays.toString(keyBytes));
        System.out.println(bytesToHex(keyBytes));
    }
    
    
    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
