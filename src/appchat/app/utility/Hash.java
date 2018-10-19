package appchat.app.utility;

import java.security.AlgorithmConstraints;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Hash {
    private static Random random = new Random();

    public static String randomString(int length){
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String string = new String();
        for (int i = 0; i < length; i++){
            string += chars.charAt(random.nextInt(chars.length()));
        }
        return string;
    }

    public static String generateSaltedSHA1(String passwordString, String salt){
        String generatedPassword = null;
        byte[] bytesalt = salt.getBytes();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(bytesalt);
            byte[] bytes = md.digest(passwordString.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }

//    public static void main(String[] args) {
//        System.out.println(generateSaltedSHA1("mocchua97","TF5D09ZK"));
//    }
}
