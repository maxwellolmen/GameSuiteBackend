package fr.cermak.gamesuite.account;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class AccountManager {

    private Map<String, String> sessionKeys;

    public AccountManager() {
        sessionKeys = new HashMap<>();
    }

    // Returns:
    //   0: success
    //   1: account exists
    //   2: invalid username
    //   3: invalid password
    public int createAccount(String username, String password) {
        // TODO: Verify account doesn't exist
        if (false) {
            return 1;
        }

        if (username.length() < 3 || username.length() > 16) {
            return 2;
        }

        if (password.length() < 8 || password.length() > 64) {
            return 3;
        }

        String hash = generateHash(password);

        // TODO: Push account to database
        return 0;
    }

    public String generateSessionKey() {
        SecureRandom random = new SecureRandom();
        byte[] sessionKey = new byte[16];
        random.nextBytes(sessionKey);
        StringBuilder keyBuilder = new StringBuilder();

        for (byte b : sessionKey) {
            keyBuilder.append(String.format("%02x", b));
        }

        return keyBuilder.toString();
    }

    public static String generateHash(String password) {
        String pepperedPassword = password + "$9FAp!N#";
        StringBuilder hash = new StringBuilder();

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = sha.digest(pepperedPassword.getBytes());

            for (byte b : hashedBytes) {
                hash.append(String.format("%02x", b));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hash.toString();
    }
}