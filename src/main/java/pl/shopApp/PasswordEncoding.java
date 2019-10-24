package pl.shopApp;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordEncoding {

    public PasswordEncoding() {
    }

    public String getHashPassword(String password) {
        MessageDigest md;
        String returnedPassword = null;
        try {
            // Select the message digest for the hash computation -> SHA-256
            md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPassword)
                sb.append(String.format("%02x", b));

            returnedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return returnedPassword;
    }

}